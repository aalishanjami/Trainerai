package detection.com.trainerai.Fragments;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import detection.com.trainerai.Activities.RegisterUser;
import detection.com.trainerai.Models.Adapters.DataAdapter;
import detection.com.trainerai.Models.Adapters.PostsAdapter;
import detection.com.trainerai.Models.CreatePost;
import detection.com.trainerai.Models.CreateUser;
import detection.com.trainerai.Models.PostResponse;
import detection.com.trainerai.Models.PostsArray;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    DataProcessor dataProcessor;
    String strid, strname, stremail, strdob, strheightfoot, strheightinch, strweight, strpic, strpost;
    Button post;
    EditText etpost;
    RecyclerView mRecyclerView;
    PostsAdapter postsAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        dataProcessor = new DataProcessor(getActivity());

        strid = dataProcessor.getStr("id");
        strname = dataProcessor.getStr("name");
        stremail = dataProcessor.getStr("email");
        strdob = dataProcessor.getStr("dob");
        strheightfoot = dataProcessor.getStr("heightfoot");
        strheightinch = dataProcessor.getStr("heightinch");
        strweight = dataProcessor.getStr("weight");
        strpic = dataProcessor.getStr("pic");
        mRecyclerView = v.findViewById(R.id.rv_post);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        post = v.findViewById(R.id.but_postfromprofile);
        etpost = v.findViewById(R.id.et_writepostfromprofile);

        TextView username = v.findViewById(R.id.tv_name);
        username.setText(strname);

        TextView emailuser = v.findViewById(R.id.tv_email);


        String[] parts = stremail.split("@", 2);
        String email1 = parts[0];
        String email2 = parts[1];

        if (!email1.isEmpty()) {
            emailuser.setText(email1);
        }

        PostResponse postResponse = new PostResponse(strid);
        sendNetwrokRequest(postResponse);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strpost = etpost.getText().toString();

                CreatePost createPost = new CreatePost(strname, strpost, strpic, strid);
                sendPostRequest(createPost);

            }
        });

        TextView godetailed = v.findViewById(R.id.tv_see_det_profile);
        godetailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailedProfile fragment = new DetailedProfile();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_homepage, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        ImageView godetiv = v.findViewById(R.id.iv_see_det_profile);
        godetiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailedProfile fragment = new DetailedProfile();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_homepage, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return v;
    }

    //function that sends data through retrofit to db
    private void sendNetwrokRequest(PostResponse postResponse) {


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://thefaceb.com/trxpi/posts/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        Trxpi trxpi = retrofit.create(Trxpi.class);
        Call<PostResponse> call = trxpi.postsuser(strid, postResponse);

        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    ArrayList<PostsArray> postsArrays;
                    postsArrays = new ArrayList<PostsArray>(response.body().getRecords());
                    mRecyclerView.setAdapter(new PostsAdapter(postsArrays, getActivity(), new PostsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(PostsArray item) {
                            Toast.makeText(getActivity(), item.getName(), Toast.LENGTH_SHORT).show();
                        }
                    }));

                } else {
                    Toast.makeText(getActivity(), "wrongresponse!" + response.errorBody(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "wrong!" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //function that sends data through retrofit to db
    private void sendPostRequest(CreatePost createPost) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://thefaceb.com/trxpi/posts/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        Trxpi trxpi = retrofit.create(Trxpi.class);
        Call<CreatePost> call = trxpi.createpostnew(createPost);

        call.enqueue(new Callback<CreatePost>() {
            @Override
            public void onResponse(Call<CreatePost> call, Response<CreatePost> response) {
                PostResponse postResponse = new PostResponse(strid);
                sendNetwrokRequest(postResponse);
                Toast.makeText(getActivity(), "ok" + response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<CreatePost> call, Throwable t) {
                Toast.makeText(getActivity(), "wrong!" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
