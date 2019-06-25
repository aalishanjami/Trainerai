package detection.com.trainerai.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import detection.com.trainerai.Models.CreateUser;
import detection.com.trainerai.Models.LoginResponse;
import detection.com.trainerai.Models.Trxpi;
import detection.com.trainerai.R;
import detection.com.trainerai.utilities.DataProcessor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Login extends AppCompatActivity {

    EditText loginemail, loginpassword;
    Button login;
    DataProcessor dataProccessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dataProccessor = new DataProcessor(Login.this);

        String JWTTEST = dataProccessor.getStr("jwt");

        if (!JWTTEST.isEmpty()){
            startActivity(new Intent(Login.this, HomeActivity.class));
        }

        loginemail = findViewById(R.id.login_email);
        loginpassword = findViewById(R.id.login_password);
        login = findViewById(R.id.but_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String stremail = loginemail.getText().toString();
                final String strpassword = loginpassword.getText().toString();

                LoginResponse loginResponse = new LoginResponse(stremail, strpassword);
                sendNetwrokRequest(loginResponse);
            }
        });

        //Moving to home activity
        Button gomain = findViewById(R.id.gomain);
        gomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, HomeActivity.class));
            }
        });
        Button goreguser = findViewById(R.id.goreguser);
        goreguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, RegisterUser.class));
            }
        });
    }

    //function that sends data through retrofit to db
    private void sendNetwrokRequest (LoginResponse loginResponse){
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://thefaceb.com/trxpi/users/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        Trxpi trxpi = retrofit.create(Trxpi.class);
        Call<LoginResponse> call = trxpi.loginuser(loginResponse);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                try {
                    if (response.isSuccessful()) {

                        dataProccessor.setStr("jwt", response.body().getJwt());
//                        Toast.makeText(Login.this, "ok" + response.body().getJwt(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Login.this, HomeActivity.class));
                    }
                    else {
                        Toast.makeText(Login.this, "wrong!" + response.errorBody(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Login.this, "wrong!" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}