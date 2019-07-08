package com.firmanjabar.submission3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firmanjabar.submission3.model.MovieModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MovieDetailsActivity extends AppCompatActivity {

    ImageView imgBdMovie, imgPosterMovie;
    TextView titleMovie, dateMovie, genreMovie, descriptionMovie, companyMovie, runtimeMovie, statusMovie;
    RatingBar rateMovie;
    ProgressBar pBmovie;
    Button btnMovie;
    ScrollView scrollViewMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        pBmovie = findViewById(R.id.progress_bar_detail);
        btnMovie = findViewById(R.id.load);
        scrollViewMovie = findViewById(R.id.scrollView);
        imgBdMovie = findViewById(R.id.detail_img_bd);
        imgPosterMovie = findViewById(R.id.detail_img_poster);
        titleMovie = findViewById(R.id.detail_title);
        dateMovie = findViewById(R.id.detail_date);
        genreMovie = findViewById(R.id.detail_genre);
        descriptionMovie = findViewById(R.id.detail_description);
        rateMovie = findViewById(R.id.detail_rate);
        companyMovie = findViewById(R.id.txt_company);
        runtimeMovie = findViewById(R.id.txt_runtime);
        statusMovie = findViewById(R.id.txt_status);

        MovieModel movies = getIntent().getParcelableExtra("movies");
        getDetailMovie(movies.getMovieID());

        String title = movies.getTitle();
        String date = movies.getDate();
        String imgPoster = movies.getImgPoster();
        String imgBD = movies.getImgBD();
        float rate = (float) movies.getRate();

        titleMovie.setText(title);
        dateMovie.setText(date);
        rateMovie.setRating(rate/2);

        Glide.with(MovieDetailsActivity.this)
                .load("https://image.tmdb.org/t/p/original/"+imgPoster)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imgPosterMovie);

        Glide.with(MovieDetailsActivity.this)
                .load("https://image.tmdb.org/t/p/original/"+imgBD)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imgBdMovie);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(movies.getTitle());
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void getDetailMovie ( int movieID ) {
        String url = "https://api.themoviedb.org/3/movie/"+movieID+"?api_key=f6bcf9844871b7ddb1c34b9fa7ce750e&language=en-US";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                pBmovie.setVisibility(View.INVISIBLE);
                scrollViewMovie.setVisibility(View.VISIBLE);
                try {
                    String response = new String(responseBody);
                    JSONObject object = new JSONObject(response);
                    String description = object.getString("overview");
                    descriptionMovie.setText(description);

                    String status = object.getString("status");
                    statusMovie.setText(status);

                    String runtime = object.getString("runtime");
                    runtimeMovie.setText(runtime);

                    JSONArray jsonArray = object.getJSONArray("genres");
                    List<String> genreList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String genreName = jsonObject.getString("name");
                        genreList.add(genreName);
                    }
                    String genres = TextUtils.join(", ", genreList);
                    genreMovie.setText(genres);

                    JSONArray jsonArray2 = object.getJSONArray("production_companies");
                    List<String> compList = new ArrayList<>();
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        JSONObject jsonObject = jsonArray2.getJSONObject(i);
                        String compName = jsonObject.getString("name");
                        compList.add(compName);
                    }
                    String companies = TextUtils.join(", ", compList);
                    companyMovie.setText(companies);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pBmovie.setVisibility(View.INVISIBLE);
                btnMovie.setVisibility(View.VISIBLE);
                btnMovie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(getIntent());
                        finish();
                    }
                });
                Toast.makeText(getApplicationContext(),R.string.connection, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
