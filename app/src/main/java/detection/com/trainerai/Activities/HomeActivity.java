package detection.com.trainerai.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

import detection.com.trainerai.Fragments.Exercises;
import detection.com.trainerai.Fragments.FindTrainers;
import detection.com.trainerai.Fragments.Home;
import detection.com.trainerai.Fragments.Profile;
import detection.com.trainerai.R;
import detection.com.trainerai.utilities.DataProcessor;

public class HomeActivity extends AppCompatActivity {
    private TextView mTextMessage;
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

        if (jwtstr.isEmpty()){
            startActivity(new Intent(HomeActivity.this, Login.class));
        }




        //Moving to home
        Home home = new Home();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_homepage, home);
        transaction.commit();

        //defining bottom nav
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}
