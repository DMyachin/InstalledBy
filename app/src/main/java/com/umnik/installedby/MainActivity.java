package com.umnik.installedby;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AppUpdatedListener {
    private final String TAG = "Main Screen";
    private InstalledApps mApps;
    private ArrayList<HashMap<String, Object>> mDataSet = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApps = new InstalledApps(this);
        mApps.setAppListUpdatedListener(this);
        mApps.getInstalledPackages();

        createRecyclerView();
        setRefreshLayout();
    }

    private void setRefreshLayout() {
        final SwipeRefreshLayout refreshLayout = findViewById(R.id.swipeRl);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mApps.getInstalledPackages();
                mAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void createRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.CardListRv);
        recyclerView.setItemViewCacheSize(mDataSet.size());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mAdapter = new AppInfoAdapter(mDataSet);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void installedAppListUpdated(ArrayList<HashMap<String, Object>> packageInfos) {
        mDataSet.clear();
        mDataSet.addAll(packageInfos);
    }
}
