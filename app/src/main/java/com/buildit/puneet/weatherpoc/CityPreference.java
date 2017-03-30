package com.buildit.puneet.weatherpoc;

import android.app.Activity;
import android.content.SharedPreferences;
 
public class CityPreference {
     
    SharedPreferences prefs;
/**
 * CityPreference constructor
 * @param Activity activity
 * Activity based and mode private
*/
    public CityPreference(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    /**
     * getCity is used to get city name saved in preferences
     * @return City name
     */
    public String getCity(){
        //return prefs.getString("city", "Jerusalem, IS");        
        //return prefs.getString("city", "Sydney, AU");        
        return prefs.getString("city", "Jerusalem, IL");        
    }

    /**
     * setCity is used to save city name into prefences
     * @param city
     */
    void setCity(String city){
        prefs.edit().putString("city", city).commit();
    }
     
}