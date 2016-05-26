package com.example.csit451sapien.jigsaw;

/**
 * Created by mauricio on 4/14/2016.
 * Modified by Brian
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class JigsawHomePage extends AppCompatActivity implements View.OnClickListener {

    protected static final String CHOSEN_IMAGE = "chosen_image";
    protected static final String RESULTS_LOG = "results_log";
    protected String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jigsaw_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username = SharedValues.username;

        // set listeners for the pre selected images and buttons
        findViewById(R.id.image1).setOnClickListener(this);
        findViewById(R.id.image2).setOnClickListener(this);
        findViewById(R.id.image3).setOnClickListener(this);
        findViewById(R.id.image4).setOnClickListener(this);
        findViewById(R.id.button_logout).setOnClickListener(this);
        findViewById(R.id.upload_imgBtn).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    // set nav bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.about_us:
                Toast.makeText(getApplicationContext(), "Learn all about us", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AboutUs.class));
                break;
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    // Handles events on view
    @Override
    public void onClick(View v) {

        int id = v.getId();
        Intent intent = new Intent(JigsawHomePage.this, PuzzleHandler.class);
        String msg = "Starting puzzle!";

        switch (id) {
            case R.id.image1:
                intent.putExtra("imageName", "image1");
                break;
            case R.id.image2:
                intent.putExtra("imageName", "image2");
                break;
            case R.id.image3:
                intent.putExtra("imageName", "image3");
                break;
            case R.id.image4:
                intent.putExtra("imageName", "image4");
                break;
            case R.id.button_logout:
                msg = "Goodbye!";
                intent = new Intent(JigsawHomePage.this, Login.class);
                break;
            case R.id.upload_imgBtn:
                msg = "Upload Your Own Image!";
                intent = new Intent(JigsawHomePage.this, ImageUpload.class);
                break;
            default:
                // do nothing
        }
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}