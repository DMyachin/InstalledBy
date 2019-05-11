package com.umnik.installedby;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "Main Screen";
    private PackageManager packageManager;
    private ArrayList<PackageInfo> mDataSet = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        packageManager = getPackageManager();
        getInstalledApps();

        createRecyclerView();
        setRefreshLayout();
    }

    private void setRefreshLayout() {
        final SwipeRefreshLayout refreshLayout = findViewById(R.id.swipeRl);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInstalledApps();
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

    private void getInstalledApps() {
        List<PackageInfo> installedApps = packageManager.getInstalledPackages(0);
        mDataSet.clear();
        mDataSet.addAll(installedApps);
    }

}
