package detection.com.trainerai.Fragments;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import detection.com.trainerai.R;
import detection.com.trainerai.utilities.DataProcessor;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindTrainers extends Fragment {

    Button filtertrainers;


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_find_trainers, container, false);

        filtertrainers = v.findViewById(R.id.but_filt_trn);

        filtertrainers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View view = getLayoutInflater().inflate(R.layout.fragment_filters, null);
                BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
                dialog.setContentView(view);
                dialog.show();

                final Spinner cityspinner = view.findViewById(R.id.city_filt);
                final TextView cityfinal = view.findViewById(R.id.cityfinal);
                List<String> list = new ArrayList<String>();
                list.add("----");
                list.add("list 1");
                list.add("list 22");
                list.add("list 2");
                list.add("list 3");
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cityspinner.setAdapter(dataAdapter);

                cityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = parent.getItemAtPosition(position).toString();

                        if (!selectedItem.equals("----")) {
                            cityfinal.setVisibility(View.VISIBLE);
                            cityfinal.setText("Selected city: " + selectedItem);
                            DataProcessor.setStr("city", selectedItem);
                            Toast.makeText(getActivity(), DataProcessor.getStr("city"), Toast.LENGTH_SHORT).show();
                            cityspinner.setVisibility(View.GONE);
                        }
                    } // to close the onItemSelected

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
        return v;
    }

}