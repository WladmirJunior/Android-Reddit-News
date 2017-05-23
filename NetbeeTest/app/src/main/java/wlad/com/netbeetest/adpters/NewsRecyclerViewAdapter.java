package wlad.com.netbeetest.adpters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wlad.com.netbeetest.R;
import wlad.com.netbeetest.model.News;
import wlad.com.netbeetest.model.NewsData;

/**
 * Created by wlad on 22/05/17.
 */

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    private List<NewsData> newsDataList;

    public NewsRecyclerViewAdapter(List<NewsData> newsDataList) {
        this.newsDataList = newsDataList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.title.setText(newsDataList.get(position).title);
        holder.author.setText(String.format("Submitted by %s", newsDataList.get(position).author));
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
            newsDatas.add(news.newsData);
        }
        newsDataList.addAll(newsDatas);
        notifyDataSetChanged();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{

        final ImageView thumbImage;
        final TextView title;
        final TextView author;

        NewsViewHolder(View itemView) {
            super(itemView);
            thumbImage = (ImageView) itemView.findViewById(R.id.image_thumb);
            title = (TextView) itemView.findViewById(R.id.text_title);
            author = (TextView) itemView.findViewById(R.id.text_author);
        }
    }
}
