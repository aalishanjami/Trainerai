package detection.com.trainerai.Fragments;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import detection.com.trainerai.Models.RecordsArray;
import detection.com.trainerai.Models.SearchResponse;
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
public class FindTrainers extends Fragment {

    Button filtertrainers, searchmain;
    EditText searchbar;


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_find_trainers, container, false);

        filtertrainers = v.findViewById(R.id.but_filt_trn);
        searchmain = v.findViewById(R.id.trainersearchbut);
        searchbar = v.findViewById(R.id.et_searchbar);


        searchmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strsearchbar = searchbar.getText().toString();
                SearchResponse searchResponse = new SearchResponse(strsearchbar);

                sendNetwrokRequest(searchResponse);
            }
        });

        /*IF FILTER BUTTON IS SELECTED*/
        filtertrainers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*INFLATE LAYOUT OF FILTER FRAGMENT IN BOTTOM SHEET*/

                final View view = getLayoutInflater().inflate(R.layout.fragment_filters, null);
                BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
                dialog.setContentView(view);
                dialog.show();

                /*THERE ARE 3 SPINNERS IN FILTER FRAGMENT AND ONE BUTTON*/

                /*SETTING
                 * SPINNER
                 * OF
                 * CITY
                 * AND ADDING ITEMS*/
                final Spinner cityspinner = view.findViewById(R.id.city_filt);
                final TextView cityfinal = view.findViewById(R.id.cityfinal);
                List<String> list = new ArrayList<String>();
                list.add("----");
                list.add("list 1");
                list.add("list 22");
                list.add("list 2");
                list.add("list 3");
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                        R.layout.item_spinner, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cityspinner.setAdapter(dataAdapter);

                /*SETTING
                 * SPINNER
                 * OF
                 * RATING
                 * AND ADDING ITEMS */
                final Spinner ratingspinner = view.findViewById(R.id.ratingspinner);
                final TextView ratingfinal = view.findViewById(R.id.ratingfinal);
                List<String> ratinglist = new ArrayList<String>();
                ratinglist.add("----");
                ratinglist.add("list 1");
                ratinglist.add("list 22");
                ratinglist.add("list 2");
                ratinglist.add("list 3");
                ArrayAdapter<String> ratingdataAdapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, ratinglist);
                ratingdataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ratingspinner.setAdapter(ratingdataAdapter);

                /*SETTING
                 * SPINNER
                 * OF
                 * CLIENT
                 * AND ADDING ITEMS */

                final Spinner clientspinner = view.findViewById(R.id.clientspinner);
                final TextView clientfinal = view.findViewById(R.id.clientfinal);
                List<String> clientlist = new ArrayList<String>();
                clientlist.add("----");
                clientlist.add("list 1");
                clientlist.add("list 22");
                clientlist.add("list 2");
                clientlist.add("list 3");
                ArrayAdapter<String> clientdataAdapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, clientlist);
                clientdataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                clientspinner.setAdapter(clientdataAdapter);

                /*LISTENER
                 * BEGINS*/

                /*IN EACH LISTENER IF SELECTED VALUE IN SPINNER IS NOT "----"
                 * THEN SPINNER VALUE WILL BE ADDED TO SHARED PREFERENCES
                 * SPINNER VISIBILTY WILL BE GONE AND TEXT VIEW WILL VE VISIBLE SHOWING SELECTED VALUE
                 * */

                /*CITY SPINNER LISTENER*/

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

                /*RATING SPINNER LISTENER*/

                ratingspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = parent.getItemAtPosition(position).toString();

                        if (!selectedItem.equals("----")) {
                            ratingfinal.setVisibility(View.VISIBLE);
                            ratingfinal.setText("Selected rating: " + selectedItem);
                            DataProcessor.setStr("rating", selectedItem);
                            Toast.makeText(getActivity(), DataProcessor.getStr("rating"), Toast.LENGTH_SHORT).show();
                            ratingspinner.setVisibility(View.GONE);
                        }
                    } // to close the onItemSelected

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                /*CLIENTS SPINNER LISTENER*/

                clientspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = parent.getItemAtPosition(position).toString();

                        if (!selectedItem.equals("----")) {
                            clientfinal.setVisibility(View.VISIBLE);
                            clientfinal.setText("Selected clients: " + selectedItem);
                            DataProcessor.setStr("clients", selectedItem);
                            Toast.makeText(getActivity(), DataProcessor.getStr("clients"), Toast.LENGTH_SHORT).show();
                            clientspinner.setVisibility(View.GONE);
                        }
                    } // to close the onItemSelected

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                /*AFTER FILTERS SELECTED USERS CAN SEARCH*/

                final Button searchfilt = view.findViewById(R.id.but_searchnow);
                searchfilt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
        return v;
    }

    //function that sends data through retrofit to db
    private void sendNetwrokRequest(SearchResponse searchResponse) {


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://thefaceb.com/trxpi/trainers/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        final String strsearchbar = searchbar.getText().toString();
        Trxpi trxpi = retrofit.create(Trxpi.class);
        Call<SearchResponse> call = trxpi.search(strsearchbar, searchResponse);

        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {

                try {
                    if (response.isSuccessful()) {
                        ArrayList<RecordsArray> recordArrays;
                        recordArrays = new ArrayList<RecordsArray>(response.body().getRecords());
                        DataProcessor.setStr("search" , strsearchbar);
//                        Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getActivity(), "ok" + response.body(), Toast.LENGTH_LONG).show();
                        SearchResults fragment = new SearchResults();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_homepage, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    } else {
                        Toast.makeText(getActivity(), "wrong!" + response.errorBody(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "wrong!" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}