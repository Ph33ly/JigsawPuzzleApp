package com.example.csit451sapien.jigsaw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * made by mauricio
 */
public class Register extends AppCompatActivity {

    EditText ET_NAME, ET_USER_NAME, ET_USER_PASS;
    String name, user_name, user_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ET_NAME = (EditText)findViewById(R.id.name);
        ET_USER_NAME = (EditText)findViewById(R.id.new_user_name);
        ET_USER_PASS = (EditText)findViewById(R.id.new_user_pass);
    }

    public void userReg(View view){

        name = ET_NAME.getText().toString();
        user_name = ET_USER_NAME.getText().toString();
        user_pass = ET_USER_PASS.getText().toString();
        //pass above information to background task
        String method = "register";

        if (!user_name.equals("") && !user_pass.equals("") && !name.equals("")) {
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(method, name, user_name, user_pass);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Please Enter All Fields", Toast.LENGTH_SHORT).show();
        }
    }
}
