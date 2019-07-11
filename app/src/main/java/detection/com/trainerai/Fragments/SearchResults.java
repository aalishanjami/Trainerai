package detection.com.trainerai.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import detection.com.trainerai.Models.Adapters.DataAdapter;
import detection.com.trainerai.Models.RecordsArray;
import detection.com.trainerai.Models.SearchResponse;
import detection.com.trainerai.Models.Trxpi;
import detection.com.trainerai.R;
import detection.com.trainerai.utilities.DataProcessor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SearchResults extends Fragment {

    String strquery;
    ProgressBar mProgressBar;
    RecyclerView mRecyclerView;
    DataAdapter dataAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_results, container, false);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.cars_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        strquery = DataProcessor.getStr("search");
        SearchResponse searchResponse = new SearchResponse(strquery);

        sendNetwrokRequest(searchResponse);
        return v;
    }

    //function that sends data through retrofit to db
    private void sendNetwrokRequest(SearchResponse searchResponse) {


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://thefaceb.com/trxpi/trainers/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        Trxpi trxpi = retrofit.create(Trxpi.class);
        Call<SearchResponse> call = trxpi.search(strquery, searchResponse);

        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                mProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<RecordsArray> recordArrays;
                    recordArrays = new ArrayList<RecordsArray>(response.body().getRecords());
//                        dataAdapter = new DataAdapter(recordArrays, getActivity());
                    mRecyclerView.setAdapter(new DataAdapter(recordArrays, getActivity(), new DataAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecordsArray item) {
                            Toast.makeText(getActivity(), "item clicked" + item.getId() + item.getCity(), Toast.LENGTH_SHORT).show();
                            DataProcessor.setStr("searchid", item.getId());
                            SearchProfile fragment = new SearchProfile();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_homepage, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    }));

                } else {
                    Toast.makeText(getActivity(), "wrong!" + response.errorBody(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "wrong!" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
