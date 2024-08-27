package com.EsyDigi.esyDigi.Interface;


import android.view.View;

/**
 * use it either by getting id of view or by tag
 */
public interface ViewListener {

   /**
    *
    * @param position this can be either adapter detail_profile_professional_list_item position
    *                 or any position,please set according to your requirement
    * @param view     this will be the view on which action happened
     */
   public void onClick(int position, View view);

}
