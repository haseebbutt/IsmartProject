package com.app.ismart.rcvbase;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by Asad on 1/28/2016.
 */
public class RecyclerViewUtils {

    public static LinearLayoutManager getVerticalLinearLayoutManager(Context context) {
        LinearLayoutManager manager = new LinearLayoutManager(context);

        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    public static LinearLayoutManager getHorizontalLinearLayoutManager(Context context) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return manager;
    }


    public static LinearLayoutManager getGridLayoutManager(Context context , int colCount) {
        GridLayoutManager manager = new GridLayoutManager(context,colCount);
//        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return manager;
    }
    public static LinearLayoutManager getGridLayoutManagerHORIZONTAL(Context context , int colCount) {
        GridLayoutManager manager = new GridLayoutManager(context,colCount);
       manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return manager;
    }
}
