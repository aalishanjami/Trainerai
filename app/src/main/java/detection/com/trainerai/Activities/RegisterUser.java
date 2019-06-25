package detection.com.trainerai.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import detection.com.trainerai.Models.CreateUser;
import detection.com.trainerai.Models.Trxpi;
import detection.com.trainerai.R;
import detection.com.trainerai.utilities.InputFilterMinMax;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterUser extends AppCompatActivity {

    EditText name, email, password, dob, heightfoot, heightinch, weight, pic;
    Button register;
    ImageView dp;
    final Calendar myCalendar = Calendar.getInstance();
    public static final int PICK_IMAGE_REQUEST = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                dp.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register_user);

            //typecasting user data
            name = findViewById(R.id.et_name);
            email = findViewById(R.id.et_email);
            password = findViewById(R.id.et_password);
            dob = findViewById(R.id.et_dob);
            heightfoot = findViewById(R.id.et_heightfoot);
            heightinch = findViewById(R.id.et_heightinch);
            weight = findViewById(R.id.et_weight);
            pic = findViewById(R.id.et_pic);
            register = findViewById(R.id.but_reguser);
            dp = findViewById(R.id.iv_pic);

            dp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   chooseImage();
                }
            });

            //Minimum and Maximum values for int. 1-12 for inch, 1-10 for foot and 1-999 for weight in kgs
            heightfoot.setFilters(new InputFilter[]{new InputFilterMinMax("1", "10")});
            heightinch.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
            weight.setFilters(new InputFilter[]{new InputFilterMinMax("1", "999")});

            //date picker dialog for date of birth of user
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }
            };

            //calender initialisation upon clicking edit text of date of  birth
            dob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(RegisterUser.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });



            //upon clicking the register button, object of create user class is made and data is stored
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String strname = name.getText().toString();
                    final String stremail = email.getText().toString();
                    final String strpass = password.getText().toString();
                    final String strdob = dob.getText().toString();
                    final String strheightfoot = heightfoot.getText().toString();
                    final String strheightinch = heightinch.getText().toString();
                    final String strweight = weight.getText().toString();
                    final String strpic = pic.getText().toString();

                    CreateUser createUser = new CreateUser(strname, stremail, strpass, strdob, strheightfoot, strheightinch, strweight, strpic);
                    sendNetwrokRequest(createUser);
                }

            });
        }

        //function that sends data through retrofit to db
        private void sendNetwrokRequest (CreateUser createUser){
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://thefaceb.com/trxpi/users/")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();

            Trxpi trxpi = retrofit.create(Trxpi.class);
            Call<CreateUser> call = trxpi.createAccount(createUser);

            call.enqueue(new Callback<CreateUser>() {
                @Override
                public void onResponse(Call<CreateUser> call, Response<CreateUser> response) {
                    Toast.makeText(RegisterUser.this, "ok" + response.body().getId(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<CreateUser> call, Throwable t) {
                    Toast.makeText(RegisterUser.this, "wrong!" + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        //extract date from date picker function and set it on edit text
        private void updateLabel () {
            String myFormat = "dd-MMMM, yyyy"; //01-July, 1999 format
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            dob.setText(sdf.format(myCalendar.getTime()));
        }

    public void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    }
