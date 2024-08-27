package com.EsyDigi.esyDigi.Interface;

import android.view.View;

/**
 * Created by indianrenters on 12/19/16.
 */

public interface MultipleRecycleViewClickListener {

    public void onClick(View view, int position);
    public void onClick(View view, int position, int secondPosition);
    public void onClick(View view, int position, int secondPosition, int thirdPosition);
    public void onClick(View view, int... position);
}
