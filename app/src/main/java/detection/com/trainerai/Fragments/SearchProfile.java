package detection.com.trainerai.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import detection.com.trainerai.Activities.HomeActivity;
import detection.com.trainerai.Activities.Message;
import detection.com.trainerai.Models.DataResponse;
import detection.com.trainerai.Models.SearchProfileResponse;
import detection.com.trainerai.Models.Trxpi;
import detection.com.trainerai.R;
import detection.com.trainerai.utilities.DataProcessor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchProfile extends Fragment {

    TextView name, email, address, phone, clients, rating, joined, city;
    ProgressBar mProgressBar;
    LinearLayout ll1,ll2;
    Button msg;


    public SearchProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_profile, container, false);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progress_bar_profile);
        mProgressBar.setVisibility(View.VISIBLE);
        ll1 = v.findViewById(R.id.ll1);
        ll2 = v.findViewById(R.id.ll2);

        ll1.setVisibility(View.INVISIBLE);
        ll2.setVisibility(View.INVISIBLE);

        String id = DataProcessor.getStr("searchid");

        SearchProfileResponse searchProfileResponse = new SearchProfileResponse(id);
        sendNetwrokRequest(searchProfileResponse);

        name = v.findViewById(R.id.search_profile_name);
        email = v.findViewById(R.id.search_profile_email);
        address = v.findViewById(R.id.search_profile_address);
        phone = v.findViewById(R.id.search_profile_phone);
        clients = v.findViewById(R.id.search_profile_clients);
        rating = v.findViewById(R.id.search_profile_rating);
        joined = v.findViewById(R.id.search_profile_joined);
        city = v.findViewById(R.id.search_profile_city);

        return v;
    }

    //function that sends data through retrofit to db
    private void sendNetwrokRequest(SearchProfileResponse searchProfileResponse) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://thefaceb.com/trxpi/trainers/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        String id = DataProcessor.getStr("searchid");

        Trxpi trxpi = retrofit.create(Trxpi.class);
        Call<SearchProfileResponse> call = trxpi.searchprofile(id, searchProfileResponse);

        call.enqueue(new Callback<SearchProfileResponse>() {
            @Override
            public void onResponse(Call<SearchProfileResponse> call, Response<SearchProfileResponse> response) {

                mProgressBar.setVisibility(View.GONE);
                ll1.setVisibility(View.VISIBLE);
                ll2.setVisibility(View.VISIBLE);

                try {
                    if (response.isSuccessful()) {

                        name.setText(response.body().getName());
                        email.setText(response.body().getEmail());
                        address.setText(response.body().getAddress());
                        phone.setText(response.body().getPhone());
                        clients.setText(response.body().getClients());
                        rating.setText(response.body().getRating());
                        city.setText(response.body().getCity());
                        joined.setText(response.body().getJoined());

                    } else {
                        Toast.makeText(getActivity(), "wrong!" + response.errorBody(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SearchProfileResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "wrong!" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}

