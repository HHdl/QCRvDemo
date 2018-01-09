package com.qdcares.qcrvdemo.listener;

import android.view.View;

/**
 * Created by handaolin on 2017/10/22.
 */

public interface OnItemClickListener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);

    void onItemLeftClick(View view, int position);

    void onItemRightClick(View view, int position);
}
