package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    Context mContext;

    public NewsAdapter(@NonNull Context context, List<News> news) {
        super(context, 0, news);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }

        News currentNews = getItem(position);

        TextView newsName = convertView.findViewById(R.id.tv_section_name);
        TextView newsTitle = convertView.findViewById(R.id.tv_title);
        TextView newsDate = convertView.findViewById(R.id.date);
        TextView newsAuthorName = convertView.findViewById(R.id.tv_author_name);

        newsName.setText(currentNews.getSectionName());
        newsTitle.setText(currentNews.getTitle());
        newsDate.setText(String.valueOf(formatDate(currentNews.getTime())));

        String authorName = currentNews.getAuthorName();
        if (authorName == null) {
            newsAuthorName.setVisibility(View.GONE);
        } else {
            newsAuthorName.setText(mContext.getString(R.string.by_author) + " " + authorName);
        }
        return convertView;
    }

    private String formatDate(String newsDate) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = formatDate.parse(newsDate);
            formatDate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
            newsDate = formatDate.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newsDate;
    }
}
