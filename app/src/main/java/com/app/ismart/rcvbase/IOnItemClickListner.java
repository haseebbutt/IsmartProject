package com.app.ismart.rcvbase;

import android.view.View;

/**
 * Created by UmerKiani on 2/1/2016.
 */
public interface IOnItemClickListner<T> {

    void onRecyclerItemClick(T model, View view, int position);


}
