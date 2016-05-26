package com.example.csit451sapien.jigsaw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ErrorLogin extends AppCompatActivity {
    /**
     * made by chris
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView mT2=(TextView) findViewById(R.id.myDspV2);
        Intent myIntnt=getIntent();

        mT2.setText(myIntnt.getStringExtra("msg2User2") + "  try again !");

        findViewById(R.id.errorLogBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ErrorLogin.this, Login.class));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.about_us:
                Toast.makeText(getApplicationContext(),"Learn all about us", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AboutUs.class));
                break;
            case R.id.register:
                Toast.makeText(getApplicationContext(), "Lets get you registered!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Register.class));
                break;
            default:
                // do nothing
        }
        return super.onOptionsItemSelected(item);
    }

}
