package fr.esiea.geotwitter_esiea;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Charly on 25/05/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context mcontext;
    private List<Tweet> lTweet;

    public RecyclerViewAdapter(Context mcontext, List<Tweet> lTweet) {
        this.mcontext = mcontext;
        this.lTweet = lTweet;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        view = inflater.inflate(R.layout.tweet_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.user.setText(lTweet.get(position).getUser_name());
        holder.tweet.setText(lTweet.get(position).getText());
        holder.fav.setText(lTweet.get(position).getFav_nb());
        holder.retweet.setText(lTweet.get(position).getRt_nb());

        Picasso.with(mcontext).load(lTweet.get(position).getImage_url()).into(holder.picture);
    }

    @Override
    public int getItemCount() {
        return lTweet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView user;
        TextView tweet;
        TextView fav;
        TextView retweet;
        ImageView picture;

        public MyViewHolder(View itemView) {
            super(itemView);

            user = itemView.findViewById(R.id.user);
            tweet = itemView.findViewById(R.id.tweet);
            fav = itemView.findViewById(R.id.fav);
            retweet = itemView.findViewById(R.id.retweet);
            picture = itemView.findViewById(R.id.picture);

        }
    }
}
