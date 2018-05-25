package fr.esiea.geotwitter_esiea;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by Charly on 25/05/2018.
 */

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
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
                Intent intent = new Intent(AboutActivity.this,AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.credits:
                //do a notification
                return true;
            case R.id.home:
                Intent intent2 = new Intent(AboutActivity.this,MainActivity.class);
                startActivity(intent2);
                Toast.makeText(getApplicationContext(),getString(R.string.home),Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
