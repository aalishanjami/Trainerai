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
public class Profile extends Fragment {

    DataProcessor dataProcessor;
    String strname, stremail, strdob, strheightfoot, strheightinch, strweight, strpic;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        dataProcessor = new DataProcessor(getActivity());

        strname = dataProcessor.getStr("name");
        stremail = dataProcessor.getStr("email");
        strdob = dataProcessor.getStr("dob");
        strheightfoot = dataProcessor.getStr("heightfoot");
        strheightinch = dataProcessor.getStr("heightinch");
        strweight = dataProcessor.getStr("weight");
        strpic = dataProcessor.getStr("pic");

        TextView test = v.findViewById(R.id.test2);
        test.setText(strname + stremail + strdob + strheightfoot + strheightinch + strweight + strpic);

        return v;
    }

}
