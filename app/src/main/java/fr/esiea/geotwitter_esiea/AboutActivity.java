package fr.esiea.geotwitter_esiea;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
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
                Intent intent = new Intent(AboutActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.credits:
                sendNotificationWithCredits();
                return true;
            case R.id.home:
                Intent intent2 = new Intent(AboutActivity.this, MainActivity.class);
                startActivity(intent2);
                Toast.makeText(getApplicationContext(), getString(R.string.home), Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendNotificationWithCredits() {
        //send a notification with credits
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this, "Credits")
                .setSmallIcon(R.mipmap.ic_logo)
                .setContentTitle(getString(R.string.credits_notif))
                .setContentText(getString(R.string.about_text));

        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.androidauthority.com/"));
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager Notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notif.notify(1, notifBuilder.build());

    }
}
