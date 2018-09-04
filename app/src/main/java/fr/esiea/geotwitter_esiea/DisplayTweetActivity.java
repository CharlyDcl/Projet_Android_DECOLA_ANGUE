package fr.esiea.geotwitter_esiea;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import twitter4j.QueryResult;
import twitter4j.Status;

/**
 * Created by Charly on 25/05/2018.
 */

public class DisplayTweetActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    private List<Tweet> lTweet = new ArrayList<>();
    private LocationManager locationManager = null;
    public static final String SET_TWEET = "fr.esiea.geotwitter_esiea.SET_TWEET";

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            new setTweet(intent);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_display);
        IntentFilter intentFilter = new IntentFilter(SET_TWEET);

        LocalBroadcastManager.getInstance(this).registerReceiver(br, intentFilter);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview_id);

        launchSearch();
        setUpRecyclerView(lTweet);
    }

    private void launchSearch() {
        String selection = getIntent().getStringExtra("TAG");
        if (selection.equals("device_location")) {
            //get device location then call TwitterAuthAndSearch.startActionAuthAndSearch with parameters
            //check location permission if denied call a request permission
            getdevicePosition();
        } else if (selection.equals("address")) {
            //get address from intent and get its location then call TwitterAuthAndSearch.startActionAuthAndSearch with parameters
            getaddressLocation();
        }
    }

    private void getaddressLocation() {
        String address = getIntent().getStringExtra("ADDRESS");
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocationName(address, 3);
            if (addresses == null) {
                return;
            }
            Address location = addresses.get(0);
            TwitterAuthAndSearch.startActionAuthAndSearch(this, location.getLatitude(), location.getLongitude());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                @SuppressLint("MissingPermission") Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (loc != null) {
                    TwitterAuthAndSearch.startActionAuthAndSearch(this, loc.getLatitude(), loc.getLongitude());
                }

            } else {
                Toast.makeText(this, "Permission for ACCES_FINE_LOCATION was not granted", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void getdevicePosition() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(this, "Location permission is needed to localise the device", Toast.LENGTH_LONG).show();
                }
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (loc != null) {
                    TwitterAuthAndSearch.startActionAuthAndSearch(this, loc.getLatitude(), loc.getLongitude());
                }
            }
        }
    }

    private void setUpRecyclerView(List<Tweet> lTweet) {
        RecyclerViewAdapter madapter = new RecyclerViewAdapter(this, lTweet);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(madapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(br);
    }

    private class setTweet extends BroadcastReceiver {
        //use a separate class with a public list of Tweet
        private Intent TweetIntent;

        public setTweet (Intent intent){
            this.TweetIntent = intent;
        }
        //!!!!!!!!!!!!!!!!!!!!!!!essayer d'utiliser le TweetIntent!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle args = intent.getBundleExtra("DataResult");
            QueryResult qResult = (QueryResult) args.getSerializable("Result");
            System.out.println(qResult);
            List<Status> resultList = qResult.getTweets();


            for (Status item : resultList) {
                //new tweet item to add to the list
                System.out.println("in activity @ fav : " + item.getUser().getFavouritesCount());
                Tweet newTweet = new Tweet();

                //set values of the item
                newTweet.setText(item.getText());
                newTweet.setUser_name(item.getUser().getName());
                newTweet.setRt_nb(item.getRetweetCount());
                newTweet.setFav_nb(item.getUser().getFavouritesCount());
                newTweet.setImage_url(item.getUser().getProfileImageURL());
                lTweet.add(newTweet);
            }
        }
    }
}
