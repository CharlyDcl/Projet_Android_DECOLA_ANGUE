package fr.esiea.geotwitter_esiea;

/**
 * Created by Charly on 25/05/2018.
 */

public class Tweet {

    private String user_name;
    private String text;
    private int fav_nb;
    private int rt_nb;
    private String image_url;

    public Tweet() {
    }

    public Tweet(String user_name, String text, int fav_nb, int rt_nb, String image_url) {
        this.user_name = user_name;
        this.text = text;
        this.fav_nb = fav_nb;
        this.rt_nb = rt_nb;
        this.image_url = image_url;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getText() {
        return text;
    }

    public int getFav_nb() {
        return fav_nb;
    }

    public int getRt_nb() {
        return rt_nb;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFav_nb(int fav_nb) {
        this.fav_nb = fav_nb;
    }

    public void setRt_nb(int rt_nb) {
        this.rt_nb = rt_nb;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
