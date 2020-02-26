package uoit.finalproject.car4u;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class LoginPage extends AppCompatActivity {

    EditText username;
    EditText password;
    ArrayList<User> userArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        userArrayList = new ArrayList<>();
        Button login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = findViewById(R.id.editText_username);
                password = findViewById(R.id.editText_password);

                if(username.getText().toString().equalsIgnoreCase("")) {

                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(LoginPage.this, "Enter your username!");

                }

                else {

                    if(password.getText().toString().equalsIgnoreCase("")) {

                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(LoginPage.this, "Enter your password!");

                    }

                    else {


                        background_for_login bg = new background_for_login(LoginPage.this,LoginPage.this);
                        bg.execute(username.getText().toString(), password.getText().toString());

                    } }
            }
        });


        Button register_button = findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(intent);
            }
        });


    }

    public void onBackPressed(){
        // Do Nothing
    }


    public class background_for_login extends AsyncTask <String, Void,String> {

        AlertDialog dialog;
        Context context;
        Activity activity;

        public Boolean login = false;

        public background_for_login(Context context, Activity activity) {
            this.context = context;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {
            if (s.contains("success")) {


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new readJSON1().execute("https://allansantosh.com/mobileappproject/login_results.json");
                    }
                });


            } else {

                ViewDialog alert = new ViewDialog();
                alert.showDialog(activity, "Login Failed! Check username and password!");
            }
        }

        @Override
        protected String doInBackground(String... voids) {
            String result = "";
            String user = voids[0];
            String pass = voids[1];

            String connstr = "https://allansantosh.com/mobileappproject/login_check.php";

            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8")
                        + "&&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                reader.close();
                ips.close();
                http.disconnect();
                return result;

            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            }


            return result;
        }

    }

    class readJSON1 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            return readURL(params[0]);
        }


        @Override
        protected void onPostExecute(String content) {

            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("the_array");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject user_object = jsonArray.getJSONObject(i);
                    userArrayList.add(new User(
                            user_object.getString("id"),
                            user_object.getString("first_name"),
                            user_object.getString("last_name"),
                            user_object.getString("username"),
                            user_object.getString("email")
                    ));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            Intent intent = new Intent(LoginPage.this, SelectionPage.class);
            intent.putExtra("ID", userArrayList.get(0).getId() );
            intent.putExtra("first_name", userArrayList.get(0).getFirst_name() );
            intent.putExtra("last_name", userArrayList.get(0).getLast_name());
            intent.putExtra("username", userArrayList.get(0).getUsername());
            intent.putExtra("email", userArrayList.get(0).getEmail());

            startActivity(intent);


        }
    }

    private String readURL(String theURL) {

        StringBuilder content = new StringBuilder();

        try {

            URL url = new URL(theURL);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            while ((line = bufferedReader.readLine()) != null) {

                content.append(line + "\n");

            }

            bufferedReader.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return content.toString();

    }



}

