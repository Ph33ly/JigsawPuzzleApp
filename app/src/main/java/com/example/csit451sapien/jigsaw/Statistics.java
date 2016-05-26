package com.example.csit451sapien.jigsaw;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Statistics extends Activity {

    private final String RESULTS_LOG = "results_log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(RESULTS_LOG, Context.MODE_PRIVATE);
        String results = sharedPreferences.getString(SharedValues.username, "");
        SharedPreferences.Editor editor = getSharedPreferences(RESULTS_LOG, Context.MODE_PRIVATE).edit();

        if (results.split(":").length == 1) {
            results = "0:0";
        }
        String[] stringArray = results.split(":");
        int timesWon = Integer.parseInt(stringArray[1]);
        int timesPlayed = Integer.parseInt(stringArray[0]);
        timesWon++;
        results = stringArray[1] + ":" + Integer.toString(timesWon);

        SharedValues.timesWon = timesWon;
        SharedValues.timesPlayed = timesPlayed;

        editor.putString(SharedValues.username, results);
        editor.commit();
    }
}
