package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    public static final String LOG_TAG = NewsActivity.class.getName();
    private static final String GRDN_REQUEST_URL = "http://content.guardianapis.com/search?";
    private static final int NEWS_LOADER_ID = 1;

    private TextView emptyView;
    private ProgressBar progressBar;
    private ListView listView;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        emptyView = findViewById(R.id.news_empty_or_no_connection);
        progressBar = findViewById(R.id.loading_indicator);
        listView = findViewById(R.id.list);
        adapter = new NewsAdapter(this, new ArrayList<News>());
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyView);

        LoaderManager loaderManager = getLoaderManager();
        if (networkInfo != null
                && networkInfo.isConnectedOrConnecting()) {
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            emptyView.setText(R.string.internet_connection_problem);
            progressBar.setVisibility(View.GONE);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews = adapter.getItem(position);

                Uri newsUri = Uri.parse(currentNews.getUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPreferences  = PreferenceManager.getDefaultSharedPreferences(this);
        String news_section = sharedPreferences.getString(
                getString(R.string.section_key),
                getString(R.string.section_default));
        String orderBy = sharedPreferences.getString(
                getString(R.string.order_by_key),
                getString(R.string.order_by_default));
        Uri baseUri = Uri.parse(GRDN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("section", news_section);
        uriBuilder.appendQueryParameter("order-by", orderBy);
        uriBuilder.appendQueryParameter("api-key","bbfed83f-b0a4-484e-8363-5a850ef8e7b7");
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        adapter.clear();
        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        }
        progressBar.setVisibility(View.GONE);
        emptyView.setText(R.string.news_found_problem);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.action_setting){
            Intent settingsIntent = new Intent(this, SettingActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
