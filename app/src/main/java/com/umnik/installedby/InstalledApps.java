package com.umnik.installedby;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class InstalledApps {
    private static final String TAG = "[InstalledApps]";
    private PackageManager mPm;
    private AppUpdatedListener listener;

    InstalledApps(Context context) {
        mPm = context.getPackageManager();
    }

    void setAppListUpdatedListener(AppUpdatedListener listener) {
        this.listener = listener;
    }

    void getInstalledPackages() {
        List<PackageInfo> packages = mPm.getInstalledPackages(0);
        HashMap<String, Object> anApp;
        ArrayList<HashMap<String, Object>> apps = new ArrayList<>(packages.size());

        for (PackageInfo packageInfo : packages) {
            anApp = new HashMap<>(5);

            anApp.put("packageName", packageInfo.packageName);
            anApp.put("icon", packageInfo.applicationInfo.loadIcon(mPm));
            anApp.put("label", packageInfo.applicationInfo.loadLabel(mPm).toString());
            anApp.put("installedTo", packageInfo.applicationInfo.sourceDir);
            anApp.put("installedBy", mPm.getInstallerPackageName((String) anApp.get("packageName")));
            apps.add(anApp);

        }

        listener.installedAppListUpdated(apps);
    }
}
