package detection.com.trainerai.Models.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import detection.com.trainerai.Models.PostsArray;
import detection.com.trainerai.R;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(PostsArray item);
    }

    private ArrayList<PostsArray> articles;
    private Context context;

    private OnItemClickListener listener;

    public PostsAdapter(ArrayList<PostsArray> articles, Context context, OnItemClickListener listener) {
        this.context = context;
        this.articles = articles;
        this.listener = listener;
    }

    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostsAdapter.ViewHolder viewHolder, final int i) {

        final String strname = articles.get(i).getName();
        final String strcontent = articles.get(i).getContent();
        final String strdate = articles.get(i).getDate();

        viewHolder.name.setText(strname);
        viewHolder.content.setText(strcontent);
        viewHolder.date.setText("Date posted: " + strdate);

        viewHolder.bind(articles.get(i), listener);

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, content, date;


        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.post_name);
            content = view.findViewById(R.id.post_content);
            date = view.findViewById(R.id.post_date);
        }

        public void bind(final PostsArray item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}
