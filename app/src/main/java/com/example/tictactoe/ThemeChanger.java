package com.example.tictactoe;

import android.app.Activity;

public class ThemeChanger {

    private static final int DARK_THEME = 1;
    private static int PREF_THEME = 0;

    /**
     * Get the current Theme
     * @param activity
     */
    public void getTheme(Activity activity){

        if(PREF_THEME != DARK_THEME){
            activity.setTheme(R.style.AppTheme);
        }
        else{
            activity.setTheme(R.style.Theme_AppCompat);
        }
    }

    /**
     * Set prefered theme : '0' = light; '1' = dark;
     * @param theme
     */
    public void setPrefTheme(int theme){
        PREF_THEME = theme;
    }

    /**
     * Get preferred theme
     * @return
     */
    public int getPrefTheme(){
        return  PREF_THEME;
    }
}
