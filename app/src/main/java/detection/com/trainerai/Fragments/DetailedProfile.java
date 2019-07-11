package detection.com.trainerai.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import detection.com.trainerai.R;
import detection.com.trainerai.utilities.DataProcessor;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailedProfile extends Fragment {

    TextView name, email, bday, height, weight;
    String strid, strname, stremail, strdob, strheightfoot, strheightinch, strweight;
    DataProcessor dataProcessor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_detailed_profile, container, false);

        strid = dataProcessor.getStr("id");
        strname = dataProcessor.getStr("name");
        stremail = dataProcessor.getStr("email");
        strdob = dataProcessor.getStr("dob");
        strheightfoot = dataProcessor.getStr("heightfoot");
        strheightinch = dataProcessor.getStr("heightinch");
        strweight = dataProcessor.getStr("weight");

        name = v.findViewById(R.id.tv_dt_name);
        email = v.findViewById(R.id.tv_dt_email);
        bday = v.findViewById(R.id.tv_dt_bday);
        height = v.findViewById(R.id.tv_dt_height);
        weight = v.findViewById(R.id.tv_dt_weight);

        name.setText(strname);
        email.setText(stremail);
        bday.setText(strdob);
        height.setText(strheightfoot + "'" + strheightinch + "''");
        weight.setText(strweight + "Kg");

        return v;
    }

}
