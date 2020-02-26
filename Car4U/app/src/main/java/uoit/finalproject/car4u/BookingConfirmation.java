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
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
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
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class BookingConfirmation extends AppCompatActivity {

    TextView car_name_text_view, owner_text_view, price_text_view, date_textview;
    ImageView car_image;
    Button backbutton, booknow_button;
    long days;
    Double total_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);

        car_name_text_view = findViewById(R.id.car_name_text_view);
        owner_text_view = findViewById(R.id.car_owner_text_view);
        price_text_view = findViewById(R.id.car_price_text_view);
        car_image = findViewById(R.id.image_car_book_confirm);
        date_textview = findViewById(R.id.from_to_date_text_view);

        BookingConfirmation.background_for_booking_confirm_searchresults bg = new BookingConfirmation.background_for_booking_confirm_searchresults(BookingConfirmation.this,BookingConfirmation.this);
        bg.execute(getIntent().getStringExtra("CarID"));


        String sDate1= getIntent().getStringExtra("fromdate");
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String sDate2= getIntent().getStringExtra("todate");
        Date date2 = null;
        try {
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate2);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        long ms = date2.getTime() - date1.getTime() + 86400000;
        days = TimeUnit.DAYS.convert(ms, TimeUnit.MILLISECONDS);
        double price1 = Double.parseDouble(getIntent().getStringExtra("price"));
        total_price = price1 * days;

        booknow_button = (Button)findViewById(R.id.booking_confim_book_now_button);
        booknow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BookingConfirmation.this, ConfirmationPage.class);
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
                intent.putExtra("total_price", Double.toString(total_price));

                startActivity(intent);

            }

        });


        backbutton = (Button)findViewById(R.id.booking_confim_backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BookingConfirmation.this, CarSearch.class);
                intent.putExtra("ID", getIntent().getStringExtra("ID") );
                intent.putExtra("first_name", getIntent().getStringExtra("first_name") );
                intent.putExtra("last_name", getIntent().getStringExtra("last_name"));
                intent.putExtra("username", getIntent().getStringExtra("username"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                intent.putExtra("maxprice", getIntent().getStringExtra("maxprice"));
                intent.putExtra("price", getIntent().getStringExtra("price"));
                intent.putExtra("total_price", Double.toString(total_price));
                startActivity(intent);

            }

        });


    }


    public class background_for_booking_confirm_searchresults extends AsyncTask<String, Void,String> {

        Context context;
        Activity activity;

        public Boolean login = false;

        public background_for_booking_confirm_searchresults(Context context, Activity activity) {
            this.context = context;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {
            if (s.contains("success")) {

                Log.d("Read the php","and got success");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new readJSON_booking_confirm().execute("https://allansantosh.com/mobileappproject/booking_confirm_view_result.json");
                    }
                });


            } else {

                ViewDialog alert = new ViewDialog();
                alert.showDialog(activity, "Error! Your serach resulted in 0 results!");
            }
        }

        @Override
        protected String doInBackground(String... voids) {
            String result = "";
            String id = voids[0];

            String connstr = "https://allansantosh.com/mobileappproject/book_confirm_view.php";

            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data =
                        URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

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

    class readJSON_booking_confirm extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            return readURL(params[0]);
        }


        @Override
        protected void onPostExecute(String content) {

            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("the_array");

                //carsArrayList.clear();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject cars_object = jsonArray.getJSONObject(i);
                    String dayss;

                    if (days == 1) {
                        dayss = "day";
                    }
                    else {
                        dayss = "days";
                    }

                    car_name_text_view.setText(cars_object.getString("make") + " " + cars_object.getString("model"));
                    owner_text_view.setText("Owner: " + cars_object.getString("first_name") + " " + cars_object.getString("last_name") );
                    price_text_view.setText("Price: " + days + " " + dayss + " x $" + cars_object.getString("price") + " = $" + String.format("%.2f", total_price));
                    Picasso.with(BookingConfirmation.this).load(cars_object.getString("image_link")).into(car_image);
                    date_textview.setText("From: " + getIntent().getStringExtra("fromdate") + " - " + getIntent().getStringExtra("todate"));


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

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
