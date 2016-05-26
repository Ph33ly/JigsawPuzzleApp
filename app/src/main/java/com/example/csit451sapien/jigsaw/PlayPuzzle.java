package com.example.csit451sapien.jigsaw;

/**
 * Created by mauricio on 4/14/2016.
 * Modified by Brian
 */

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.game.view.view.PuzzleLayout;


public class PlayPuzzle extends PuzzleHandler implements CelebrateInterface {

    private String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        increaseTimesPlayed();
        SharedValues.username = username;

        Bitmap bitmap;

        // Get/Set user img and difficulty setting
        imageName = getIntent().getExtras().getString("imageName");
        int imageDifficulty = getIntent().getExtras().getInt("difficulty");

        if (imageName.equals("")) {
            bitmap = getSavedImage();
        }
        else {
            int imgResource = getResources().getIdentifier(imageName + "xxhdp", "drawable", "com.example.csit451sapien.jigsaw");
            bitmap = BitmapFactory.decodeResource(getResources(), imgResource);
        }

        PuzzleLayout.setImage(bitmap);
        PuzzleLayout.setColumn(imageDifficulty);
        PuzzleLayout.setInterface(this);


        //button at the top, changes difficulty, returns you to puzzleHandler activity
        findViewById(R.id.chg_diff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayPuzzle.this, PuzzleHandler.class);
                intent.putExtra("imageName", imageName);
                startActivity(intent);
            }
        });
    }

    // Celebration callback when game is completed
    @Override
    public void celebration() {
        // play victory song
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.celebrateaudio);
        mediaPlayer.start();

        // show victory msg and options
        FragmentManager manager = getFragmentManager();
        Celebrate celebrateDialog = new Celebrate();
        celebrateDialog.show(manager, "");
    }


    private void increaseTimesPlayed() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(RESULTS_LOG, Context.MODE_PRIVATE);
        String results = sharedPreferences.getString(username, "");
        SharedPreferences.Editor editor = getSharedPreferences(RESULTS_LOG, Context.MODE_PRIVATE).edit();

        // first time
        if (results.equals("") || results.split(":").length == 1) {
            results = "1:0";
        }
        else {
            String[] stringArray = results.split(":");
            int timesPlayed = Integer.parseInt(stringArray[0]);
            timesPlayed++;
            results = Integer.toString(timesPlayed) + ":" + stringArray[1];
        }

        editor.putString(username, results);
        editor.commit();
    }

    // Disable back btn
    @Override
    public void onBackPressed() {
    }
}
