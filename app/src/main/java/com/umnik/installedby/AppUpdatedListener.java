package com.umnik.installedby;

import java.util.ArrayList;
import java.util.HashMap;

public interface AppUpdatedListener {
    void installedAppListUpdated(ArrayList<HashMap<String, Object>> packageInfos);
}
