package uoit.finalproject.car4u;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class ConfirmationPage extends AppCompatActivity {

    Button homebutton;
    String total_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);

        total_price = getIntent().getStringExtra("total_price");


        homebutton = findViewById(R.id.back_to_home_button);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ConfirmationPage.this, SelectionPage.class);
                intent.putExtra("ID", getIntent().getStringExtra("ID") );
                intent.putExtra("CarID", getIntent().getStringExtra("CarID") );
                intent.putExtra("first_name", getIntent().getStringExtra("first_name") );
                intent.putExtra("last_name", getIntent().getStringExtra("last_name"));
                intent.putExtra("username", getIntent().getStringExtra("username"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                intent.putExtra("fromdate", getIntent().getStringExtra("fromdate"));
                intent.putExtra("todate", getIntent().getStringExtra("todate"));
                intent.putExtra("maxprice", getIntent().getStringExtra("maxprice"));
                intent.putExtra("price", getIntent().getStringExtra("price"));
                intent.putExtra("total_price",getIntent().getStringExtra("total_price"));
                startActivity(intent);
            }
        });


        background_for_confirmationpage bg= new background_for_confirmationpage(ConfirmationPage.this,ConfirmationPage.this);
        bg.execute(getIntent().getStringExtra("CarID"),getIntent().getStringExtra("ID"),getIntent().getStringExtra("fromdate"),
                getIntent().getStringExtra("todate"));
    }


    public class background_for_confirmationpage extends AsyncTask<String, Void,String> {

        Context context;
        Activity activity;

        public Boolean login = false;

        public background_for_confirmationpage(Context context, Activity activity) {
            this.context = context;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {

        }

        @Override
        protected String doInBackground(String... voids) {
            String result = "";
            String Carid = voids[0];
            String id = voids[1];
            String fromdate = voids[2];
            String todate = voids[3];

            String connstr = "https://allansantosh.com/mobileappproject/book.php";

            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                Log.d("",fromdate);
                Log.d("",todate);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("Car_ID", "UTF-8") + "=" + URLEncoder.encode(Carid, "UTF-8")
                        + "&&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8")
                        + "&&" + URLEncoder.encode("fromdate", "UTF-8") + "=" + URLEncoder.encode(fromdate, "UTF-8")
                        + "&&" + URLEncoder.encode("total_price", "UTF-8") + "=" + URLEncoder.encode(total_price, "UTF-8")
                        + "&&" + URLEncoder.encode("todate", "UTF-8") + "=" + URLEncoder.encode(todate, "UTF-8");

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

}
