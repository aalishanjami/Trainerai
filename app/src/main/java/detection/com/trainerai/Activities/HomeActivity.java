package detection.com.trainerai.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import detection.com.trainerai.Fragments.Exercises;
import detection.com.trainerai.Fragments.FindTrainers;
import detection.com.trainerai.Fragments.Home;
import detection.com.trainerai.Fragments.Profile;
import detection.com.trainerai.Models.DataResponse;
import detection.com.trainerai.Models.Trxpi;
import detection.com.trainerai.R;
import detection.com.trainerai.utilities.DataProcessor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HomeActivity extends AppCompatActivity {


    DataProcessor dataProccessor;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //Moving to home
                    Home home = new Home();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_homepage, home);
                    transaction.commit();
                    return true;
                case R.id.navigation_exercises:
                    //Moving to exercises
                    Exercises fragment = new Exercises();
                    FragmentTransaction extransaction = getSupportFragmentManager().beginTransaction();
                    extransaction.replace(R.id.frame_homepage, fragment);
                    extransaction.commit();
                    return true;
                case R.id.navigation_find:
                    //Moving to find trainers
                    FindTrainers findTrainers = new FindTrainers();
                    FragmentTransaction findtransaction = getSupportFragmentManager().beginTransaction();
                    findtransaction.replace(R.id.frame_homepage, findTrainers);
                    findtransaction.commit();
                    return true;
                case R.id.navigation_profile:
                    //Moving to profile
                    Profile profile = new Profile();
                    FragmentTransaction proftransaction = getSupportFragmentManager().beginTransaction();
                    proftransaction.replace(R.id.frame_homepage, profile);
                    proftransaction.commit();
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        dataProccessor = new DataProcessor(HomeActivity.this);
        String jwtstr = dataProccessor.getStr("jwt");

        if (jwtstr.isEmpty()) {
            startActivity(new Intent(HomeActivity.this, Login.class));
        }

        DataResponse dataResponse = new DataResponse(jwtstr);
        sendNetwrokRequest(dataResponse);


        //Moving to home
        Home home = new Home();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_homepage, home);
        transaction.commit();

        //defining bottom nav
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    //function that sends data through retrofit to db
    private void sendNetwrokRequest (DataResponse dataResponse){

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://thefaceb.com/trxpi/users/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        Trxpi trxpi = retrofit.create(Trxpi.class);
        Call<DataResponse> call = trxpi.jwtget(dataResponse);

        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {

                try {
                    if (response.isSuccessful()) {

                        dataProccessor.setStr("id", String.valueOf(response.body().getData().getId()));
                        dataProccessor.setStr("name", response.body().getData().getName());
                        dataProccessor.setStr("email", response.body().getData().getEmail());
                        dataProccessor.setStr("dob", response.body().getData().getDob());
                        dataProccessor.setStr("heightfoot", response.body().getData().getHeightfoot());
                        dataProccessor.setStr("heightinch", response.body().getData().getHeightinch());
                        dataProccessor.setStr("weight", response.body().getData().getWeight());
                        dataProccessor.setStr("pic", response.body().getData().getPic());

                    }
                    else {
                        Toast.makeText(HomeActivity.this, "wrong!" + response.errorBody(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "wrong!" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
