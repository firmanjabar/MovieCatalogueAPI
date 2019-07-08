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
import com.firmanjabar.submission3.model.TvModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TvDetaislActivity extends AppCompatActivity {

    ImageView imgBdSeries, imgPosterSeries;
    TextView titleSeries, dateSeries, genreSeries, descriptionSeries, createdSeries, companySeries, statusSeries, runtimeSeries, episodeSeries, seasonSeries;
    RatingBar rateSeries;
    ProgressBar pBseries;
    Button btnSeries;
    ScrollView scrollViewSeries;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);

        pBseries = findViewById(R.id.progress_bar_detail);
        btnSeries = findViewById(R.id.load);
        scrollViewSeries = findViewById(R.id.scrollView);
        imgBdSeries = findViewById(R.id.detail_img_bd);
        imgPosterSeries = findViewById(R.id.detail_img_poster);
        titleSeries = findViewById(R.id.detail_title);
        dateSeries = findViewById(R.id.detail_date);
        genreSeries = findViewById(R.id.detail_genre);
        descriptionSeries = findViewById(R.id.detail_description);
        rateSeries = findViewById(R.id.detail_rate);
        createdSeries = findViewById(R.id.created_by);
        companySeries = findViewById(R.id.txt_company);
        statusSeries = findViewById(R.id.txt_status);
        runtimeSeries = findViewById(R.id.txt_runtime);
        episodeSeries = findViewById(R.id.txt_total_eps);
        seasonSeries = findViewById(R.id.txt_total_season);

        TvModel tvModel = getIntent().getParcelableExtra("tv");
        getDetailTv(tvModel.getIdTv());

        String title = tvModel.getTitleTv();
        String date = tvModel.getDateTv();
        String imgPoster = tvModel.getImgPosterTv();
        String imgBD = tvModel.getImgBdTv();
        float rate = (float) tvModel.getRateTv();

        titleSeries.setText(title);
        dateSeries.setText(date);
        rateSeries.setRating(rate/2);

        Glide.with(TvDetaislActivity.this)
                .load("https://image.tmdb.org/t/p/original/"+imgPoster)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imgPosterSeries);

        Glide.with(TvDetaislActivity.this)
                .load("https://image.tmdb.org/t/p/original/"+imgBD)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imgBdSeries);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(tvModel.getTitleTv());
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void getDetailTv ( int idSeries ) {
        String url = "https://api.themoviedb.org/3/tv/"+idSeries+"?api_key=f6bcf9844871b7ddb1c34b9fa7ce750e&language=en-US";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess( int statusCode, Header[] headers, byte[] responseBody) {
                pBseries.setVisibility(View.INVISIBLE);
                scrollViewSeries.setVisibility(View.VISIBLE);
                try {
                    String response = new String(responseBody);
                    JSONObject object = new JSONObject(response);
                    String description = object.getString("overview");
                    descriptionSeries.setText(description);

                    String status = object.getString("status");
                    statusSeries.setText(status);

                    String runtime = object.getString("episode_run_time");
                    runtimeSeries.setText(runtime);

                    String episode = object.getString("number_of_episodes");
                    episodeSeries.setText(episode);

                    String season = object.getString("number_of_seasons");
                    seasonSeries.setText(season);

                    JSONArray jsonArray = object.getJSONArray("genres");
                    List<String> genreList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String genreName = jsonObject.getString("name");
                        genreList.add(genreName);
                    }
                    String genres = TextUtils.join(", ", genreList);
                    genreSeries.setText(genres);

                    JSONArray jsonArray2 = object.getJSONArray("created_by");
                    List<String> createdList = new ArrayList<>();
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        JSONObject jsonObject = jsonArray2.getJSONObject(i);
                        String createdName = jsonObject.getString("name");
                        createdList.add(createdName);
                    }
                    String created = TextUtils.join(", ", createdList);
                    createdSeries.setText(created);

                    JSONArray jsonArray3 = object.getJSONArray("production_companies");
                    List<String> compList = new ArrayList<>();
                    for (int i = 0; i < jsonArray3.length(); i++){
                        JSONObject jsonObject = jsonArray3.getJSONObject(i);
                        String compName = jsonObject.getString("name");
                        compList.add(compName);
                    }
                    String compName = TextUtils.join(", ", compList);
                    companySeries.setText(compName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pBseries.setVisibility(View.INVISIBLE);
                btnSeries.setVisibility(View.VISIBLE);
                btnSeries.setOnClickListener(new View.OnClickListener() {
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
