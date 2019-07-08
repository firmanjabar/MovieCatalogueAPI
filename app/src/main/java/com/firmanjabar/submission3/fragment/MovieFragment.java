package com.firmanjabar.submission3.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.firmanjabar.submission3.R;
import com.firmanjabar.submission3.adapter.MovieAdapter;
import com.firmanjabar.submission3.model.MovieModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class MovieFragment extends Fragment {

    private View v;
    private RecyclerView recyclerView;
    private ProgressBar progBar;
    private Button btn;
    private MovieAdapter movieAdapter;
    private ArrayList<MovieModel> mData = new ArrayList<>();

    public MovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_movie, container, false);
        btn = v.findViewById(R.id.load);
        progBar = v.findViewById(R.id.progress_bar_movie);
        recyclerView = v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getData();

        return v;
    }

    public void getData () {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=f6bcf9844871b7ddb1c34b9fa7ce750e&language=en-US";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess ( int statusCode, Header[] headers, byte[] responseBody ) {
                progBar.setVisibility(View.INVISIBLE);
                try {
                    String res = new String(responseBody);
                    JSONObject obj = new JSONObject(res);
                    JSONArray jsonArray = obj.getJSONArray("results");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        MovieModel movieModel = new MovieModel(object);
                        mData.add(movieModel);
                    }
                    movieAdapter = new MovieAdapter(getActivity());
                    movieAdapter.setmData(mData);
                    recyclerView.setAdapter(movieAdapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure ( int statusCode, Header[] headers, byte[] responseBody, Throwable error ) {
                progBar.setVisibility(View.INVISIBLE);
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick ( View v ) {
                        if(getFragmentManager() != null){
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.detach(MovieFragment.this).attach(MovieFragment.this).commit();
                        }
                    }
                });
                Toast.makeText(getContext(),R.string.connection, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
