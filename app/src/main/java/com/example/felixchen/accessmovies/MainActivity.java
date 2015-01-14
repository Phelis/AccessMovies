package com.example.felixchen.accessmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.felixchen.accessmovies.MoviesBaseAdapter.MovieData;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    // the Rotten Tomatoes API key of your application! get this from their website
    private static final String API_KEY = "ya7upksndh72bpfkyg4w74nh";
   // private static final String API_KEY = "test";

    // the number of movies you want to get in a single request to their web server
    private static final int MOVIE_PAGE_LIMIT = 50;

    private EditText searchBox;
    private Button searchButton;
    private ListView moviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBox = (EditText) findViewById(R.id.text_search_box);
        searchButton = (Button) findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener()
        {
            // send an API request when the button is pressed
            @Override
            public void onClick(View arg0)
            {
                new RequestTask().execute("http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=" + API_KEY + "&q=" + searchBox.getText().toString().trim() + "&page_limit=" + MOVIE_PAGE_LIMIT);
            }
        });
        moviesList = (ListView) findViewById(R.id.list_movies);
    }

    private void refreshMoviesList(ArrayList<MovieData> arrayLists)
    {
        moviesList.setAdapter(new MoviesBaseAdapter(this, arrayLists));
    }

    private class RequestTask extends AsyncTask<String, String, String>
    {
        // make a request to the specified url
        @Override
        protected String doInBackground(String... uri)
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try
            {
                // make a HTTP request
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK)
                {
                    // request successful - read the response and close the connection
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    responseString = out.toString();
                }
                else
                {
                    // request failed - close the connection
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            }
            catch (Exception e)
            {
                Log.d("Test", "Couldn't make a successful request!");
            }
            return responseString;
        }

        // if the request above completed successfully, this method will
        // automatically run so you can do something with the response
        @Override
        protected void onPostExecute(String response)
        {
            super.onPostExecute(response);

            if (response != null)
            {
                try
                {
                    // convert the String response to a JSON object,
                    // because JSON is the response format Rotten Tomatoes uses
                    JSONObject jsonResponse = new JSONObject(response);

                    // fetch the array of movies in the response
                    JSONArray movies = jsonResponse.getJSONArray("movies");

                    // add each movie's title to an array
                    String[] movieTitles = new String[movies.length()];

                    // create an movie data
                    ArrayList<MovieData> movieDataList = new ArrayList<MovieData>();

                    for (int i = 0; i < movies.length(); i++)
                    {
                        MovieData movieData = new MovieData();

                        JSONObject movie = movies.getJSONObject(i);
                        movieTitles[i] = movie.getString("title");
                        movieData.title = movie.getString("title");

                        // Log.d(LOG_TAG, "title: " + movie.getString("title"));

                        // assign movie year to movieData
                        // Log.d(LOG_TAG, "year: " + movie.getString("year"));
                        movieData.year = movie.getString("year");

                        // assign movie ratings to movieData
                        JSONObject ratings = movie.getJSONObject("ratings");
                        // Log.d(LOG_TAG, "ratings: " + ratings.getString("audience_score"));
                        movieData.ratings = ratings.getString("audience_score");

                        // assign movie thumbnail url to movieData
                        JSONObject thumbnail = movie.getJSONObject("posters");
                        // Log.d(LOG_TAG, "thumbnail: "+ thumbnail.getString("thumbnail"));
                        movieData.url = thumbnail.getString("thumbnail");

                        movieDataList.add(movieData);
                    }

                    // update the UI
                    refreshMoviesList(movieDataList);
                }
                catch (JSONException e)
                {
                    Log.d("Test", "Failed to parse the JSON response!");
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
