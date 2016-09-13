package com.fathy.themovieapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchMovie extends AsyncTask<String, String, List<MyMovie>> {


    List<MyMovie> movies;
    Context context;
    public FetchMovie(Context context, List<MyMovie> movies){
        this.movies = movies;
        this.context = context;
    }



    @Override
    protected List<MyMovie> doInBackground(String... params) {
                 ///https://api.themoviedb.org/3/movie/550/videos?api_key=c9d5b152a06f9f0ebceb82fbace5c84c
        if(params[0].equals("popular") || params[0].equals("top_rated")){
            String baseUrl = "https://api.themoviedb.org/3/movie/" + params[0] + "?";
            return getMovieURL(getJSON(baseUrl));
        }
        else{

            String baseUrl = "https://api.themoviedb.org/3/movie/" + params[0] + "?";
            getDetails(getJSON(baseUrl), Integer.parseInt(params[1]));



            return movies;


        }

    }

    public void getDetails(String jsonstr, int position){
        MyMovie movie = new MyMovie();
        try {
            JSONObject jsonObject = new JSONObject(jsonstr);
            String title = jsonObject.getString("original_title");
            String overview = jsonObject.getString("overview");
            String releaseDate = jsonObject.getString("release_date");
            int time = jsonObject.getInt("runtime");
            double vote_average = jsonObject.getDouble("vote_average");
            movies.get(position).setDetails( title, overview, releaseDate, time, vote_average);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getJSON(String base){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JSONString = null;


        String api_key = "c9d5b152a06f9f0ebceb82fbace5c84c";
        try {
            String baseUrl = base;

            final String API_param = "api_key";
            Uri builtUri = Uri.parse(baseUrl).buildUpon()
                    .appendQueryParameter(API_param, api_key).build();

            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }

            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            JSONString = buffer.toString();
            return JSONString.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }



    public List<MyMovie> getMovieURL(String jsonstr) {
        movies = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonstr);
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject resultObject = results.getJSONObject(i);
                String poster = resultObject.getString("poster_path");
                int id = resultObject.getInt("id");
                MyMovie movie = new MyMovie();
                movie.set_ID_URL(id, "http://image.tmdb.org/t/p/w320/" + poster);
                movies.add(movie);
            }
            return movies;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


}