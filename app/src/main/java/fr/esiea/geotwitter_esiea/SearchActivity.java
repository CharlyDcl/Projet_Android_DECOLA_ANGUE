package fr.esiea.geotwitter_esiea;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.List;

/**
 * Created by Charly on 25/05/2018.
 */

public class SearchActivity extends AppCompatActivity {

    Button search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        search = (Button) findViewById(R.id.search_tweet);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this,DisplayTweetActivity.class);
                startActivity(intent);
            }
        });
    }

}
