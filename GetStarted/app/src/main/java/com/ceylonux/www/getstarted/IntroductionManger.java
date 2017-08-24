package com.ceylonux.www.getstarted;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Yasintha on 8/24/2017.
 */

public class IntroductionManger {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public  IntroductionManger(Context context){
        this.context=context;
        pref=context.getSharedPreferences("first",0);
        editor=pref.edit();
    }
    public  void setFirst(boolean isFirst){
       editor.putBoolean("check",isFirst);
        editor.commit();
    }
    public boolean Check(){
        return pref.getBoolean("check",true);
    }
}
