package fr.esiea.geotwitter_esiea;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button device_loc;
    Button search_loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("test affichage");
        setContentView(R.layout.activity_main);
        // TwitterAuthAndSearch.startActionAuthAndSearch(this);

        device_loc = (Button) findViewById(R.id.device_location);
        search_loc = (Button) findViewById(R.id.search_location);

        device_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start tweet_show and get location
                Intent intent = new Intent(MainActivity.this, DisplayTweetActivity.class);
                startActivity(intent);
            }
        });


        search_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, SearchActivity.class);

                startActivity(intent2);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.credits:

                return true;
            case R.id.home:
                Intent intent2 = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent2);
                Toast.makeText(getApplicationContext(), getString(R.string.home), Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}