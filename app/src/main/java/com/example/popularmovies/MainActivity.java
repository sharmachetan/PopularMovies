package com.example.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        CharSequence successToast= "Connected";
        CharSequence failToast = "No Internet Connection";

        String addr ="https://api.themoviedb.org/3/movie/550?api_key=146f88f9a9030c6b8b67eb1be3ae7484" ;

        if (networkInfo != null && networkInfo.isConnected()){
            Toast.makeText(getApplicationContext(),successToast,Toast.LENGTH_SHORT);
            FetchMovies fetchMovies =  new FetchMovies();
            fetchMovies.execute();
        }
        else {
            Toast.makeText(getApplicationContext(),failToast,Toast.LENGTH_SHORT);
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

    public static class FetchMovies extends AsyncTask<URL,Void,Void>{
        private String DEBUG_TAG = MainActivity.class.getSimpleName();
        InputStream is = null;

         @Override
        protected Void doInBackground(URL... params) {
             /*this.downloadUrl();*/

        private String downloadUrl(String myUrl) throws IOException {
            try {

                final String MOVIE_BASE_URL ="http://image.tmdb.org/t/p/";
                final String POSTER_SIZE = "W185";
                final String POSTER_PATH="s";


                Uri builtUri =Uri.parse(MOVIE_BASE_URL).buildUpon().appendQueryParameter();

                URL url = new URL(myUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                int response = conn.getResponseCode();
                Log.d(DEBUG_TAG, "Response code: " + response);
                is = conn.getInputStream();

                String contentString = readIt(is);


                return contentString;
            }finally {
                if (is != null){
                    is.close();
                }
            }
        }
        public String readIt(InputStream inputStream){
            Reader reader =null;
            reader = new InputStreamReader(inputStream);

            return reader.toString();

        }
    }
}
