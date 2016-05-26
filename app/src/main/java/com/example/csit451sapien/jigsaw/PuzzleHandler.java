package com.example.csit451sapien.jigsaw;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileInputStream;

/**
 * Created by mauricio on 4/14/2016.
 * Modified by Brian
 */

public class PuzzleHandler extends JigsawHomePage implements View.OnClickListener {
    // RadioGroup radiogroup = (RadioGroup) findViewById(R.id.rGroup);
    //RadioButton radioButton;
    //public final static String EXTRA_MESSAGE = "com.example.csit451sapien.jigsaw.MESSAGE";
    //String string;

    private String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_handler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageName = getIntent().getExtras().getString("imageName");
        displayImage(imageName);

        // set listeners on difficulty btns
        findViewById(R.id.easyPlay).setOnClickListener(this);
        findViewById(R.id.mediumPlay).setOnClickListener(this);
        findViewById(R.id.hardPlay).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    // set nav menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        // about us function inherited
        switch (id){
            case R.id.home:
                Toast.makeText(getApplicationContext(),"Nothing feels like home!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, JigsawHomePage.class));
                break;
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    // img chosen by user
    private void displayImage(String imageName) {
        ImageView chosenPuzzle = (ImageView) findViewById(R.id.chosenPuzzle);

        // if user picked their own img, get from file. else get resource
        if (imageName.equals("")) {
            Bitmap bitmap = getSavedImage();
            chosenPuzzle.setImageBitmap(bitmap);
        }
        else {
            int imgResource = this.getResources().getIdentifier(imageName + "xxhdp", "drawable", getPackageName());
            chosenPuzzle.setImageResource(imgResource);
        }
    }

    // retrieves saved file with bitmap
    protected Bitmap getSavedImage() {

        Bitmap bitmap = null;
        try {
            FileInputStream fis = openFileInput(CHOSEN_IMAGE);
            bitmap = BitmapFactory.decodeStream(fis);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    // handles click events on btns
    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent(PuzzleHandler.this, PlayPuzzle.class);
        intent.putExtra("imageName", imageName);

        switch (id) {
            case R.id.easyPlay:
                intent.putExtra("difficulty", 3);
                break;
            case R.id.mediumPlay:
                intent.putExtra("difficulty", 5);
                break;
            case R.id.hardPlay:
                intent.putExtra("difficulty", 7);
                break;
            default:
                // do nothing
        }
        startActivity(intent);
    }
}
