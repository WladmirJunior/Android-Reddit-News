package wlad.com.netbeetest.adpters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wlad.com.netbeetest.R;
import wlad.com.netbeetest.models.News;
import wlad.com.netbeetest.models.NewsData;

/**
 * Created by wlad on 22/05/17.
 */

public class CommentsRecyclerViewAdapter extends RecyclerView.Adapter<CommentsRecyclerViewAdapter.NewsViewHolder> {

    public interface NewsClickListener{
        void onItemClick(NewsData newsData);
    }

    private Context context;
    private List<NewsData> newsDataList;
    private NewsClickListener newsClickListener;

    public CommentsRecyclerViewAdapter(Context context, List<NewsData> newsDataList) {
        this.context = context;
        this.newsDataList = newsDataList;
    }

    public void setNewsClickListener(NewsClickListener newsClickListener) {
        this.newsClickListener = newsClickListener;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_recycler, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.body.setText(newsDataList.get(position).getBody());
        holder.author.setText(newsDataList.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return newsDataList.size();
    }

    public void clear(){
        newsDataList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<News> list){
        List<NewsData> newsDatas = new ArrayList<>();
        for (News news:list) {
            newsDatas.add(news.getNewsData());
        }
        newsDataList.addAll(newsDatas);
        notifyDataSetChanged();
    }

    public List<NewsData> getAll(){
        return newsDataList;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView body;
        final TextView author;

        void configure(){
            author.setText("");
        }

        NewsViewHolder(View itemView) {
            super(itemView);
            body = (TextView) itemView.findViewById(R.id.text_title);
            author = (TextView) itemView.findViewById(R.id.text_author);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(newsClickListener != null){
                newsClickListener.onItemClick(newsDataList.get(getAdapterPosition()));
            }
        }
    }
}
