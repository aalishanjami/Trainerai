package detection.com.trainerai.Models.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import detection.com.trainerai.Models.RecordsArray;
import detection.com.trainerai.R;
import detection.com.trainerai.utilities.DataProcessor;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(RecordsArray item);
    }
    private ArrayList<RecordsArray> articles;
    private Context context;
    private OnItemClickListener listener;

    public DataAdapter(ArrayList<RecordsArray> articles, Context context, OnItemClickListener listener) {
        this.context = context;
        this.articles = articles;
        this.listener = listener;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, final int i) {

        final String id = articles.get(i).getId();
        final String strname = articles.get(i).getName();
        final String strrating = articles.get(i).getRating();
        final String strpic = articles.get(i).getPic();
        final String strcity = articles.get(i).getCity();
        viewHolder.name.setText(strname);
        viewHolder.city.setText(strcity);
        viewHolder.rating.setText(strrating);
        viewHolder.bind(articles.get(i), listener);

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, city, rating;


        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name_search_res);
            city = (TextView) view.findViewById(R.id.city_search_res);
            rating = (TextView) view.findViewById(R.id.rating_search_res);
        }
        public void bind(final RecordsArray item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}