package com.app.ismart.rcvbase;

import android.view.View;

/**
 * Created by Asad on 2/2/2016.
 */
public interface IonItemClickListenerWithPayload {
    void onRecyclerItemClick(View view, int position, Object payLoad);
}
