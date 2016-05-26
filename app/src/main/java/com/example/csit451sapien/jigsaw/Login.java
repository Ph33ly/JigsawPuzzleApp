package com.example.csit451sapien.jigsaw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    /**
     * made by mauricio
     */
    Button button1;
    EditText ET_NAME, ET_PASS;
    String login_name,login_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button1 = (Button)findViewById(R.id.button_login);
        ET_NAME = (EditText)findViewById(R.id.user_name);
        ET_PASS = (EditText)findViewById(R.id.user_pass);
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

            case R.id.register:
                Toast.makeText(getApplicationContext(), "Lets get you registered!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.about_us:
                Toast.makeText(getApplicationContext(),"Learn all about us", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AboutUs.class));
                break;
            default:
                // do nothing
        }
        return super.onOptionsItemSelected(item);
    }
/**
    public void userReg(View view) {

        startActivity(new Intent(this, Register.class));
    }**/

    public void userLogin(View view) {
        login_name = ET_NAME.getText().toString();
        login_pass = ET_PASS.getText().toString();
        String method = "login";

        if (!login_name.equals("") && !login_pass.equals("")) {
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(method, login_name, login_pass);
        }
        else {
            Toast.makeText(getApplicationContext(),"Please Enter All Fields", Toast.LENGTH_SHORT).show();
        }
    }
}//*******************************************************************************************


