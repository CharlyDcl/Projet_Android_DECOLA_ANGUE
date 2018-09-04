package fr.esiea.geotwitter_esiea;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
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

        device_loc = (Button) findViewById(R.id.device_location);
        search_loc = (Button) findViewById(R.id.search_location);

        device_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start tweet show and get location of the device
                Intent intent = new Intent(MainActivity.this, DisplayTweetActivity.class);
                intent.putExtra("TAG", "device_location");
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
                sendNotificationWithCredits();
                return true;
            case R.id.home:
                Intent intent2 = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent2);
                Toast.makeText(getApplicationContext(), getString(R.string.home), Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendNotificationWithCredits() {
        //send a notification with credits

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Credits", "channel_credits",NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("description of the channel");
            NotificationManager notifmanager = getSystemService(NotificationManager.class);
            if (notifmanager != null) {
                notifmanager.createNotificationChannel(channel);
            }
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getString(R.string.link_github)));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(MainActivity.this, "Credits")
                .setSmallIcon(R.mipmap.ic_app_logo_round)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.mipmap.ic_app_logo_round))
                .setContentTitle(getString(R.string.credits_notif))
                .setContentText("Credits of the application and link to the github directory")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.about_text) + "\n" + getString(R.string.src_code)))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat Notif = NotificationManagerCompat.from(this);
        Notif.notify(1, notifBuilder.build());
    }
}