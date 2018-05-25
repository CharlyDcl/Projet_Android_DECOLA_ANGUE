package fr.esiea.geotwitter_esiea;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.util.List;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Charly on 24/05/2018.
 */

public class TwitterAuthAndSearch extends IntentService {

    private static double lng;
    private static double lat;

    private static final String CONSUMER_KEY = "Bij0qQHZdOTX1ddUee0dj6PNY";
    private static final String CONSUMER_SECRET = "qNqrSrSUe701KijylZEgwNl65uVkuONUqg28LQtLznvrzFIsPL";
    private static final String ACTION_authentication = "fr.esiea.geotwitter_esiea.action.authentication";

    public TwitterAuthAndSearch() {
        super("TwitterAuthAndSearch");
    }

    public static void startActionAuthAndSearch(Context context, double lat,double lng) {
        Intent intent = new Intent(context, TwitterAuthAndSearch.class);
        intent.setAction(ACTION_authentication);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            handleActionAuthAndSearch();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    private List<Status> handleActionAuthAndSearch() throws TwitterException {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setApplicationOnlyAuthEnabled(true);
        builder.setOAuthConsumerKey(CONSUMER_KEY);
        builder.setOAuthConsumerSecret(CONSUMER_SECRET);
        Configuration configuration = builder.build();
        Twitter twitter = new TwitterFactory(configuration).getInstance();
        twitter.getOAuth2Token();

        //example paris geolocation
        lat = 48.866667;
        lng = 2.333333;
        Query query = new Query();
        GeoLocation loc = new GeoLocation(lat,lng);
        Query.Unit unit = Query.KILOMETERS;
        query.setGeoCode(loc,100,unit);

        QueryResult result;


            result = twitter.search(query);
            List<Status> tweets = result.getTweets();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(DisplayTweetActivity.SET_TWEET).putExtra("result",result));

            for (Status tweet : tweets) {

                System.out.println("@ fav : " + tweet.getUser().getFavouritesCount());
                //System.out.println("fav :" + tweet.getFavoriteCount());

            }
        return tweets;
    }

}
