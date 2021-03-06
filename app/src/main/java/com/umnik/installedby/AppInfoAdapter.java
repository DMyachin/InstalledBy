package com.umnik.installedby;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import static android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS;

class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.ViewHolder> {
    private final String TAG = "AppInfoAdapter";
    private ArrayList<HashMap<String, Object>> data;
//    private PackageManager packageManager;

    AppInfoAdapter(ArrayList<HashMap<String, Object>> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_app_info_card, parent, false);
//        packageManager = cardView.getContext().getPackageManager();
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final CardView cardView = holder.cv;
        final Context context = cardView.getContext();
        final Resources res = context.getResources();

        final ImageView appIcon = cardView.findViewById(R.id.appIconIm);
        final TextView appName = cardView.findViewById(R.id.appNameTv);
        final TextView appPackage = cardView.findViewById(R.id.packageNameTv);
        final TextView installPath = cardView.findViewById(R.id.installPathTv);
        final TextView installedBy = cardView.findViewById(R.id.installedByTv);

        appIcon.setImageDrawable((Drawable) data.get(position).get("icon"));
        appName.setText((String) data.get(position).get("label"));
        appPackage.setText((String) data.get(position).get("packageName"));
        installPath.setText((String) data.get(position).get("installedTo"));

        String source = (String) data.get(position).get("installedBy");
//        String source = packageManager.getInstallerPackageName(data.get(position).packageName);
        if (source != null && source.equals("com.android.vending")) {
            installedBy.setTextColor(res.getColor(R.color.verifyOk));
        } else {
            installedBy.setTextColor(res.getColor(R.color.verifyFail));
        }
//
        installedBy.setText(String.format("%s %s", res.getString(R.string.appInstalledByText), source));
//
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = (String) data.get(position).get("packageName");

                Log.d(TAG, String.format("onClick: %s", packageName));
                Intent appInfo = new Intent(ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + packageName));
                context.startActivity(appInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;

        ViewHolder(@NonNull CardView cardView) {
            super(cardView);
            cv = cardView;
        }
    }
}
