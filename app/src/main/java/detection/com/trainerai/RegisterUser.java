package detection.com.trainerai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUser extends AppCompatActivity {

    EditText name, email, password, dob, height, weight, pic;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        //typecasting user data
        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_pwd);
        dob = findViewById(R.id.et_dob);
        height = findViewById(R.id.et_height);
        weight = findViewById(R.id.et_weight);
        pic = findViewById(R.id.et_pic);
        register = findViewById(R.id.but_reguser);

        final String strname = name.getText().toString();
        final String stremail = email.getText().toString();
        final String strpass = password.getText().toString();
        final String strdob = dob.getText().toString();
        final String strheight = height.getText().toString();
        final String strweight = weight.getText().toString();
        final String strpic = pic.getText().toString();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().createUser(strname, stremail, strpass, strdob, strheight, strweight, strpic);
                call.enqueue(new Callback<ResponseBody>(){

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String s = response.body().string();
                            Toast.makeText(RegisterUser.this, s, Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(RegisterUser.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

        });
    }
}
