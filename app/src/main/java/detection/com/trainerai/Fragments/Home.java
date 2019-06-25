package detection.com.trainerai.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import detection.com.trainerai.Activities.Camera;
import detection.com.trainerai.Activities.Login;
import detection.com.trainerai.R;
import detection.com.trainerai.utilities.DataProcessor;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    Toolbar mToolbar;
    ImageView camera;
    DataProcessor dataProccessor;


    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home, container, false);
         mToolbar = v.findViewById(R.id.toolbar_home);
         camera = v.findViewById(R.id.but_camera);
         TextView test = v.findViewById(R.id.test);

        dataProccessor = new DataProcessor(getActivity());
        String strjwt = dataProccessor.getStr("jwt");
        test.setText(strjwt);


         camera.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 moveToCamera();
             }
         });



        if (mToolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        }
        return v;

    }

    private void moveToCamera () {

        Intent i = new Intent(getActivity(), Camera.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);

    }
}
