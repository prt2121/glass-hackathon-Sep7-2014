package com.intellibins.recyclethis;

import android.app.Application;
import android.content.res.Resources;
import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellibins.recyclethis.model.BinData;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by prt2121 on 9/7/14.
 */
public class MyApp extends Application {

    private static final String TAG = MyApp.class.getSimpleName();
    private static BinData mBinData;
    private static List<Location> mSortedBinLocations;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "MyApp onCreate");
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBinData = parseJson();
                mSortedBinLocations = sortBins();
            }
        }).start();
    }

    private BinData parseJson() {
        String jsonText = null;
        try {
            Resources res = getResources();
            InputStream inputStream = res.openRawResource(R.raw.json);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            jsonText = new String(b);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(jsonText, BinData.class);
    }

    public static BinData getBinData() {
        return mBinData;
    }

    public static List<Location> getBinLocations() {
        return mSortedBinLocations;
    }

    public static List<Location> sortBins() {
        List<List<String>> lists = mBinData.getData();
        List<Location> locations = new ArrayList<Location>();
        for(List<String> strings: lists) {
            try {
                int len = strings.size();
                String lng = strings.get(len - 1);
                String lat = strings.get(len - 2);
                Location bin = new Location(strings.get(len - 3));
                bin.setLatitude(Double.parseDouble(lat));
                bin.setLongitude(Double.parseDouble(lng));
                locations.add(bin);
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
        }
        return sortLocations(locations, 40.742994, -73.984030);
    }

    public static List<Location> sortLocations(List<Location> locations, final double myLatitude,final double myLongitude) {
        Comparator comp = new Comparator<Location>() {
            @Override
            public int compare(Location o, Location o2) {
                float[] result1 = new float[3];
                android.location.Location.distanceBetween(myLatitude, myLongitude, o.getLatitude(), o.getLongitude(), result1);
                Float distance1 = result1[0];

                float[] result2 = new float[3];
                android.location.Location.distanceBetween(myLatitude, myLongitude, o2.getLatitude(), o2.getLongitude(), result2);
                Float distance2 = result2[0];

                return distance1.compareTo(distance2);
            }
        };
        Collections.sort(locations, comp);
        return locations;
    }

}
