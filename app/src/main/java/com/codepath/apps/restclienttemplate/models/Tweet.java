package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public Entities entities;
    public boolean hasMedia;
    public ArrayList<String> embeddedImages;
    public String firstEmbeddedImage;


    //empty constructor needed by the parceler library.
    public Tweet() {
    }

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.entities = Entities.fromJson(jsonObject.getJSONObject("entities"));
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

        JSONObject entities = jsonObject.getJSONObject("entities");
        if (entities.has("media")){
            JSONArray media = entities.getJSONArray("media");
            tweet.hasMedia = true;
            tweet.embeddedImages = new ArrayList<>();
            for (int i=0; i<media.length(); i++){
                tweet.embeddedImages.add(media.getJSONObject(i).getString("media_url_https"));
            }
            tweet.firstEmbeddedImage = tweet.embeddedImages.get(0);
            Log.i("Tweet", "firstEmbeddedImage: " + tweet.firstEmbeddedImage);
        } else {
            tweet.hasMedia = false;
            tweet.embeddedImages = new ArrayList<>();
        }

        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
