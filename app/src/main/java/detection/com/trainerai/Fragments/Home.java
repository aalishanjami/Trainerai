package detection.com.trainerai.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import detection.com.trainerai.Models.PostResponse;
import detection.com.trainerai.Models.PostsArray;
import detection.com.trainerai.Activities.Camera;
import detection.com.trainerai.Activities.Login;
import detection.com.trainerai.Activities.Message;
import detection.com.trainerai.Models.Adapters.PostsAdapter;
import detection.com.trainerai.Models.AllPostsResponse;
import detection.com.trainerai.Models.CreatePost;
import detection.com.trainerai.Models.PostResponse;
import detection.com.trainerai.Models.PostsArray;
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
public class Home extends Fragment {

    Toolbar mToolbar;
    ImageView camera, msgs;
    DataProcessor dataProcessor;

    RecyclerView mRecyclerView;
    PostsAdapter postsAdapter;

    String strid, strname, stremail, strdob, strheightfoot, strheightinch, strweight, strpic, strpost;
    Button post;
    EditText etpost;


    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mToolbar = v.findViewById(R.id.toolbar_home);
        camera = v.findViewById(R.id.but_camera);
        msgs = v.findViewById(R.id.but_msgs);
        mRecyclerView = v.findViewById(R.id.rv_allposts);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(recycledViewPool);

        Button post = v.findViewById(R.id.but_postfromhome);
        etpost = v.findViewById(R.id.et_writepostfromhome);

        strid = dataProcessor.getStr("id");
        strname = dataProcessor.getStr("name");
        stremail = dataProcessor.getStr("email");
        strdob = dataProcessor.getStr("dob");
        strheightfoot = dataProcessor.getStr("heightfoot");
        strheightinch = dataProcessor.getStr("heightinch");
        strweight = dataProcessor.getStr("weight");


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strpost = etpost.getText().toString();
                CreatePost createPost = new CreatePost(strname, strpost, strpic, strid);
                sendPostRequest(createPost);
            }
        });



        AllPostsResponse allPostsResponse = new AllPostsResponse();
        sendNetwrokRequest(allPostsResponse);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToCamera();
            }
        });

        msgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Message.class));
            }
        });

        if (mToolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        }
        return v;
    }

    private void moveToCamera() {
        Intent i = new Intent(getActivity(), Camera.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);
    }


    //function that sends data through retrofit to db
    private void sendNetwrokRequest(AllPostsResponse allPostsResponse) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://thefaceb.com/trxpi/posts/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        Trxpi trxpi = retrofit.create(Trxpi.class);
        Call<AllPostsResponse> call = trxpi.allposts(allPostsResponse);

        call.enqueue(new Callback<AllPostsResponse>() {
            @Override
            public void onResponse(Call<AllPostsResponse> call, Response<AllPostsResponse> response) {
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
            public void onFailure(Call<AllPostsResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "wrong!" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //function that sends data through retrofit to db
    private void sendPostRequest (CreatePost createPost){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://thefaceb.com/trxpi/posts/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        Trxpi trxpi = retrofit.create(Trxpi.class);
        Call<CreatePost> call = trxpi.createpostnew(createPost);

        call.enqueue(new Callback<CreatePost>() {
            @Override
            public void onResponse(Call<CreatePost> call, Response<CreatePost> response) {
                AllPostsResponse allPostsResponse = new AllPostsResponse();
                sendNetwrokRequest(allPostsResponse);
                Toast.makeText(getActivity(), "ok" + response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<CreatePost> call, Throwable t) {
                Toast.makeText(getActivity(), "wrong!" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
