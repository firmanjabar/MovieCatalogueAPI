package com.firmanjabar.submission3.fragment;

import android.os.Bundle;
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
import com.firmanjabar.submission3.adapter.TvAdapter;
import com.firmanjabar.submission3.model.TvModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class TvFragment extends Fragment {

    private View v;
    private RecyclerView recyclerView;
    private ProgressBar progBar;
    private Button btn;
    private TvAdapter tvAdapter;
    private ArrayList<TvModel> mData = new ArrayList<>();

    public TvFragment () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState ) {
        v = inflater.inflate(R.layout.fragment_tv, container, false);
        btn = v.findViewById(R.id.load);
        progBar = v.findViewById(R.id.progress_bar_tv);
        recyclerView = v.findViewById(R.id.recyclerview2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getDataTv();

        return v;
    }

    private void getDataTv () {
        String url = "https://api.themoviedb.org/3/tv/popular?api_key=f6bcf9844871b7ddb1c34b9fa7ce750e&language=en-US";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess ( int statusCode, Header[] headers, byte[] responseBody ) {
                progBar.setVisibility(View.INVISIBLE);
                try {
                    String response = new String(responseBody);
                    JSONObject responseObject = new JSONObject(response);
                    JSONArray results = responseObject.getJSONArray("results");
                    for(int i = 0; i < results.length(); i++){
                        JSONObject object = results.getJSONObject(i);
                        TvModel tvModel = new TvModel(object);
                        mData.add(tvModel);
                    }
                    tvAdapter = new TvAdapter(getActivity());
                    tvAdapter.setmData(mData);
                    recyclerView.setAdapter(tvAdapter);
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
                            fragmentTransaction.detach(TvFragment.this).attach(TvFragment.this).commit();
                        }
                    }
                });
                Toast.makeText(getContext(),R.string.connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
