package fr.esiea.geotwitter_esiea;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charly on 25/05/2018.
 */

public class DisplayTweetActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    private List<Tweet> lTweet = new ArrayList<>();
    public static final String SET_TWEET = "fr.esiea.geotwitter_esiea.SET_TWEET";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_display);
        IntentFilter intentFilter = new IntentFilter(SET_TWEET);
        LocalBroadcastManager.getInstance(this).registerReceiver(new setTweet(), intentFilter);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview_id);

        setuprecyclerview(lTweet);
    }

    private void setuprecyclerview(List<Tweet> lTweet) {
        RecyclerViewAdapter madapter = new RecyclerViewAdapter(this,lTweet);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(madapter);
    }

    private class setTweet extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String s = getIntent().getStringExtra("result");
            System.out.println(s);
        }
    }
}
