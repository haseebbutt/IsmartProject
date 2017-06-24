package com.app.ismart.async;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

/**
 * Created by OmerKiani on 8/6/2016.
 */
public class AsyncDispatcher {

    public AsyncDispatcher(IAsync iref, @Nullable Object... params) {
        new DynamicAsyncTask(iref).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
    }

}
