package fr.esiea.geotwitter_esiea;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.util.List;
import java.util.Locale;

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

    private static Query query;

    private static double lng;
    private static double lat;
    private static String language;

    private static final String CONSUMER_KEY = "YOUR_CONSUMER_KEY";
    private static final String CONSUMER_SECRET = "YOUR_CONSUMER_SECRET";
    private static final String ACTION_authentication = "fr.esiea.geotwitter_esiea.action.authentication";

    public TwitterAuthAndSearch() {
        super("TwitterAuthAndSearch");
    }

    public static void startActionAuthAndSearch(Context context, double lat, double lng) {
        TwitterAuthAndSearch.lat = lat;
        TwitterAuthAndSearch.lng = lng;
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

        //authentication with API keys
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setApplicationOnlyAuthEnabled(true);
        builder.setOAuthConsumerKey(CONSUMER_KEY);
        builder.setOAuthConsumerSecret(CONSUMER_SECRET);
        Configuration configuration = builder.build();
        Twitter twitter = new TwitterFactory(configuration).getInstance();
        twitter.getOAuth2Token();

        //example for Paris geolocation
        //lat = 48.866667;
        //lng = 2.333333;

        //define query parameters
        GeoLocation loc = new GeoLocation(lat, lng);
        Query.Unit unit = Query.KILOMETERS;
        language = Locale.getDefault().getLanguage();

        //build the query
        query = new Query();
        query.setGeoCode(loc, 100, unit);
        query.setLang(language);

        //search with query and get result
        QueryResult result = twitter.search(query);

        List<Status> tweets = result.getTweets();

        //send result with broadcast
        Bundle arguments = new Bundle();
        arguments.putSerializable("Result", result);
        Intent intentResult = new Intent(DisplayTweetActivity.SET_TWEET).putExtra("DataResult", arguments);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intentResult);

        for (Status tweet : tweets) {
            System.out.println("result : " + result);
            //System.out.println("in twitterauthandsearch @ fav : " + tweet.getUser().getFavouritesCount());
            //System.out.println("fav :" + tweet.getFavoriteCount());

        }
        return tweets;
    }

}
