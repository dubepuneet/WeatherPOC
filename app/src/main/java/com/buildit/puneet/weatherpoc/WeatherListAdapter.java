package com.buildit.puneet.weatherpoc;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

/**
 * Created by dell laptop on 3/30/2017.
 */

public class WeatherListAdapter extends BaseAdapter {

    private Activity activity;

    private static LayoutInflater inflater=null;
    private ArrayList data;
    private WeatherRes weatherRes= null;

    /**
     * WeatherListAdapter constructor
     * @param Activity a
     * @param ArrayList arr
     */
    public WeatherListAdapter(Activity a, ArrayList arr) {
        activity = a;
        data = arr;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView cityField;
        public TextView updatedField;
        public TextView detailsField;
        public TextView currentTemperatureField;
        public TextView weatherIcon;

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

            vi = inflater.inflate(R.layout.lisr_row, null);
            holder = new ViewHolder();
            holder.cityField = (TextView) vi.findViewById(R.id.city_field);
            holder.updatedField = (TextView) vi.findViewById(R.id.updated_field);
            holder.detailsField = (TextView) vi.findViewById(R.id.details_field);
            holder.currentTemperatureField = (TextView) vi.findViewById(R.id.current_temperature_field);
            holder.weatherIcon = (TextView) vi.findViewById(R.id.weather_icon);
        weatherRes = (WeatherRes)data.get(position);
        holder.cityField.setText(weatherRes.getCity().toUpperCase(Locale.US) +
                ", " +
                weatherRes.getCountry());

        holder.detailsField.setText(
                weatherRes.getDescription().toUpperCase(Locale.US) +
                        "\n" + "Humidity: " + weatherRes.getHumidity() + "%" +
                        "\n" + "Pressure: " + weatherRes.getPressure() + " hPa");

        holder.currentTemperatureField.setText(
                String.format("%.2f", weatherRes.getTemp())+ " ℃");

        DateFormat df = DateFormat.getDateTimeInstance();
        String updatedOn = weatherRes.getLastUpdate();
        holder.updatedField.setText("Date Time: " + updatedOn);

        notifyDataSetChanged();
        return vi;
    }
}