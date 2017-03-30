package com.buildit.puneet.weatherpoc;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class WeatherFragment extends Fragment {
    Typeface weatherFont;
     
    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    TextView currentTemperatureField;
    TextView weatherIcon;
    ArrayList<WeatherRes> arrayList = new ArrayList<WeatherRes>();
    Handler handler;
 	WeatherListAdapter weatherListAdapter = null;
    public WeatherFragment(){   
        handler = new Handler();
    }
	ListView listview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        cityField = (TextView)rootView.findViewById(R.id.city_field);
        updatedField = (TextView)rootView.findViewById(R.id.updated_field);
        detailsField = (TextView)rootView.findViewById(R.id.details_field);
        currentTemperatureField = (TextView)rootView.findViewById(R.id.current_temperature_field);
        weatherIcon = (TextView)rootView.findViewById(R.id.weather_icon);
		listview = (ListView) rootView.findViewById(R.id.list);

//        weatherIcon.setTypeface(weatherFont);
        return rootView; 
    }
    

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    updateWeatherData(new CityPreference(getActivity()).getCity());
	}

	/**
	 * updateWeatherData is used to Get weather forecast for selected city.
	 * @param city
	 */
	private void updateWeatherData(final String city){
	    new Thread(){
	        public void run(){
	            final JSONObject json = RemoteFetch.getJSON(getActivity(), city);
	            if(json == null){
	                handler.post(new Runnable(){
	                    public void run(){
	                        Toast.makeText(getActivity(), 
	                                getActivity().getString(R.string.place_not_found), 
	                                Toast.LENGTH_LONG).show(); 
	                    }
	                });
	            } else {
	                handler.post(new Runnable(){
	                    public void run(){
	                        renderWeather(json);
	                    }
	                });
	            }               
	        }
	    }.start();
	}

	/**
	 * renderWeather is used to parse JSONObject response.
	 * @param json
	 */
	private void renderWeather(JSONObject json){
		System.out.print("Response >>>>"+json.toString());
		arrayList.clear();
	    try {
			JSONArray list = json.getJSONArray("list");

			for(int i = 0; i< list.length(); i++){
				WeatherRes weatherRes = new WeatherRes();
				weatherRes.setCity(json.getJSONObject("city").getString("name"));
				weatherRes.setCountry(json.getJSONObject("city").getString("country"));
				JSONObject  jsonObject = list.getJSONObject(i);
				JSONObject main = jsonObject.getJSONObject("main");
				JSONArray weather = jsonObject.getJSONArray("weather");
				weatherRes.setDescription(weather.getJSONObject(0).getString("description"));
				weatherRes.setPressure(main.getString("pressure"));
				weatherRes.setHumidity(main.getString("humidity"));
				weatherRes.setTemp(main.getDouble("temp"));
				weatherRes.setLastUpdate(jsonObject.getString("dt_txt"));
				arrayList.add(weatherRes);
			}

			weatherListAdapter=new WeatherListAdapter( getActivity(), arrayList);
			listview.setAdapter( weatherListAdapter );
	         
	    }catch(Exception e){
	        Log.e("SimpleWeather", "One or more fields not found in the JSON data");
	    }
	}

	/**
	 * changeCity is used to call updateWeatherData with selected city.
	 * @param city
	 */
	public void changeCity(String city){
	    updateWeatherData(city);
		listview.invalidateViews();
	}
}