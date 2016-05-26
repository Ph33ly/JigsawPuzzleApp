package com.example.csit451sapien.jigsaw;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by mauricio on 2/10/2016.
 */
public class BackgroundTask extends AsyncTask<String, Void, String> {
    private static String msgPasser="";
    Button button1;
    AlertDialog alertDialog;
    Context ctx;
    BackgroundTask(Context ctx)
    {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login Information...");
    }

    @Override
    protected String doInBackground(String... params) {
        //String reg_url = "http://amicitie.com/SapienJigsaw/wp-config.php";
        String reg_url = "http://amicitie.com/SapienJigsaw/register.php";
        String login_url = "http://amicitie.com/SapienJigsaw/login.php";
        //THE STRINGS ABOVE SETUP THE INTERNET CONNECTION FOR ANDROID DEVICE
        String method = params[0];
        if (method.equals("register"))
        {
            String name = params[1];
            String user_name = params[2];
            String user_pass = params[3];

            try
            {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("user","UTF-8") + "=" + URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("user_name","UTF-8") + "=" + URLEncoder.encode(user_name,"UTF-8")+"&"+
                        URLEncoder.encode("user_pass","UTF-8") + "=" + URLEncoder.encode(user_pass,"UTF-8");
                //write this data to buffer writer
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Registration Success...";
            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (method.equals("login"))
        {
            String login_name = params[1];
            String login_pass = params[2];
            SharedValues.username = login_name;

            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                //specify data
                String data = URLEncoder.encode("login_name","UTF-8")+"="+URLEncoder.encode(login_name, "UTF-8")+"&"+
                        URLEncoder.encode("login_pass","UTF-8")+"="+URLEncoder.encode(login_pass, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //get response from server
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = " ";
                String line = " ";
                while ((line = bufferedReader.readLine()) != null){
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            if (result.equals("Registration Success...")) {
                Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            } else {
                //this Intent takes you the apps home landing page after login validation
                Intent newIntent1 = new Intent(ctx, JigsawHomePage.class);
                //this intent takes you to an activity that says login failed try again
                Intent newIntent2 = new Intent(ctx, ErrorLogin.class);
                if (result.length() > 50) {
                    newIntent1.putExtra("msg2User", "Success ");
                    ctx.startActivity(newIntent1);
                } else {
                    newIntent2.putExtra("msg2User2", "No  Success ");
                    ctx.startActivity(newIntent2);
                }

            }
        }
        else {
            Toast.makeText(ctx,"Unable to Connect", Toast.LENGTH_SHORT).show();
        }
    }
}
