package com.buildit.puneet.weatherpoc;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class WeatherActivity extends ActionBarActivity {

    Spinner spinner = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		 
	    if (savedInstanceState == null) {
	        getSupportFragmentManager().beginTransaction()
	                .add(R.id.container, new WeatherFragment())
	                .commit();
	    }
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weather, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        spinner = (Spinner) MenuItemCompat.getActionView(item);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		R.array.city, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
        String city = new CityPreference(this).getCity();
        if(city.equals("London,GB")){
            spinner.setSelection(0);
        }else if(city.equals("Mumbai,IN")){
            spinner.setSelection(1);
        }else if(city.equals("Bangaluru,IN")){
            spinner.setSelection(2);
        }else if(city.equals("Chennai,IN")){
            spinner.setSelection(3);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                changeCity(spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

	    return false;
		
	}

    /**
     * changeCity is used to change city in preferences and call changeCity method of child fragment.
     * @param city
     */
	public void changeCity(String city){
	    WeatherFragment wf = (WeatherFragment)getSupportFragmentManager()
	                            .findFragmentById(R.id.container);
	    wf.changeCity(city);
	    new CityPreference(this).setCity(city);

	}
}
