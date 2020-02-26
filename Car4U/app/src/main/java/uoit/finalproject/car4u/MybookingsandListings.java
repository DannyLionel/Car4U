package uoit.finalproject.car4u;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.ArrayList;

public class MybookingsandListings extends AppCompatActivity {

    ArrayList<BookedCar> carsArrayList;
    ArrayList<Cars> carsArrayList2;
    ListView lv;
    ListView lv2;
    Button back_to_car_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybookingsand_listings);

        carsArrayList = new ArrayList();
        carsArrayList2 = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);
        lv2 = (ListView) findViewById(R.id.list2);

        back_to_car_search = (Button)findViewById(R.id.back_to_home_button1);
        back_to_car_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MybookingsandListings.this, SelectionPage.class);
                intent.putExtra("ID", getIntent().getStringExtra("ID") );
                intent.putExtra("first_name", getIntent().getStringExtra("first_name") );
                intent.putExtra("last_name", getIntent().getStringExtra("last_name"));
                intent.putExtra("username", getIntent().getStringExtra("username"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);

            }

        });


        background_for_mylistingandbooking bg= new background_for_mylistingandbooking(MybookingsandListings.this,MybookingsandListings.this);
        bg.execute(getIntent().getStringExtra("ID"),"https://allansantosh.com/mobileappproject/my_listing_and_booking.php","1");

        background_for_mylistingandbooking bgg= new background_for_mylistingandbooking(MybookingsandListings.this,MybookingsandListings.this);
        bgg.execute(getIntent().getStringExtra("ID"),"https://allansantosh.com/mobileappproject/my_listing_and_booking1.php","2");
    }


    class readJSON_mylistandbook extends AsyncTask<String, Integer, String> {

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
                    carsArrayList.add(new BookedCar(
                            cars_object.getString("Make"),
                            cars_object.getString("Model"),
                            cars_object.getString("Owner"),
                            cars_object.getString("Image"),
                            cars_object.getString("CarID"),
                            cars_object.getString("From_Date"),
                            cars_object.getString("To_Date"),
                            cars_object.getString("Price")
                    ));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            CustomListAdapter adapter = new CustomListAdapter(MybookingsandListings.this,  carsArrayList);
            lv.setAdapter(adapter);


        }
    }
    class readJSON_mylistandbook2 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            return readURL(params[0]);
        }


        @Override
        protected void onPostExecute(String content) {

            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("the_array");

                //carsArrayList2.clear();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject cars_object = jsonArray.getJSONObject(i);
                    carsArrayList2.add(new Cars(
                            cars_object.getString("Make_ID"),
                            cars_object.getString("Model_ID"),
                            cars_object.getString("CarID"),
                            cars_object.getString("Make"),
                            cars_object.getString("Model"),
                            cars_object.getString("Owner"),
                            cars_object.getString("Price"),
                            cars_object.getString("Image"),
                            cars_object.getString("Seats")
                    ));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            CustomListAdapter2 adapter2 = new CustomListAdapter2(MybookingsandListings.this,  carsArrayList2);
            lv2.setAdapter(adapter2);


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

    public void onBackPressed(){
        // Do Nothing
    }


    private class CustomListAdapter extends BaseAdapter {
        private ArrayList<BookedCar> searchArrayList;
        private LayoutInflater mInflater;
        Context context;

        private CustomListAdapter(Context context, ArrayList<BookedCar> results) {
            searchArrayList = results;
            mInflater = LayoutInflater.from(context);
            this.context = context;
        }

        public int getCount() {
            return searchArrayList.size();
        }

        public Object getItem(int position) {
            return searchArrayList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            CustomListAdapter.ViewHolder holder;
            final String car_id;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.listing_layout, null);
                holder = new CustomListAdapter.ViewHolder();
                holder.txtMake = (TextView) convertView.findViewById(R.id.make_textView);
                holder.txtModel = (TextView) convertView.findViewById(R.id.model_textView);
                holder.car_image = (ImageView) convertView.findViewById(R.id.list_view_search_results_image);
                holder.txtOwner = (TextView) convertView.findViewById(R.id.owner_textView);
                holder.txtPrice = (TextView) convertView.findViewById(R.id.price_textView);
                holder.txtdate = (TextView) convertView.findViewById(R.id.dates_textView);

                convertView.setTag(holder);
            } else {
                holder = (CustomListAdapter.ViewHolder) convertView.getTag();
            }


            holder.txtMake.setText(searchArrayList.get(position).getMake() + " " + searchArrayList.get(position).getModel());
            holder.txtModel.setText("");
            holder.txtOwner.setText("Owner: " + searchArrayList.get(position).getOwner());
            holder.txtPrice.setText("$" + String.format("%.2f", Double.parseDouble(searchArrayList.get(position).getPrice())));
            holder.txtdate.setText("Dates: " + searchArrayList.get(position).getFromdate() + " - " + searchArrayList.get(position).getTodate());
            Picasso.with(context).load(searchArrayList.get(position).getImage()).into(holder.car_image);

            return convertView;
        }

        class ViewHolder {
            TextView txtMake;
            TextView txtModel;
            TextView txtOwner;
            TextView txtPrice;
            TextView txtdate;
            ImageView car_image;
        }


    }

    private class CustomListAdapter2 extends BaseAdapter {
        private ArrayList<Cars> searchArrayList;
        private LayoutInflater mInflater;
        Context context;

        private CustomListAdapter2(Context context, ArrayList<Cars> results) {
            searchArrayList = results;
            mInflater = LayoutInflater.from(context);
            this.context = context;
        }

        public int getCount() {
            return searchArrayList.size();
        }

        public Object getItem(int position) {
            return searchArrayList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            CustomListAdapter2.ViewHolder2 holder;
            final String car_id;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.listing_layout, null);
                holder = new CustomListAdapter2.ViewHolder2();
                holder.txtMake = (TextView) convertView.findViewById(R.id.make_textView);
                holder.txtModel = (TextView) convertView.findViewById(R.id.model_textView);
                holder.car_image = (ImageView) convertView.findViewById(R.id.list_view_search_results_image);
                holder.txtOwner = (TextView) convertView.findViewById(R.id.owner_textView);
                holder.txtPrice = (TextView) convertView.findViewById(R.id.price_textView);
                holder.txtdate = (TextView) convertView.findViewById(R.id.dates_textView);

                convertView.setTag(holder);
            } else {
                holder = (CustomListAdapter2.ViewHolder2) convertView.getTag();
            }


            holder.txtMake.setText(searchArrayList.get(position).getMake() + " " + searchArrayList.get(position).getModel());
            holder.txtModel.setText("");
            holder.txtOwner.setText("Owner: " + searchArrayList.get(position).getOwner());
            holder.txtPrice.setText("$" + String.format("%.2f", Double.parseDouble(searchArrayList.get(position).getPrice())));
            holder.txtdate.setText("Seats: " + searchArrayList.get(position).getSeats());
            Picasso.with(context).load(searchArrayList.get(position).getImage()).into(holder.car_image);

            return convertView;
        }

        class ViewHolder2 {
            TextView txtMake;
            TextView txtModel;
            TextView txtOwner;
            TextView txtPrice;
            TextView txtdate;
            ImageView car_image;
        }


    }

    public class background_for_mylistingandbooking extends AsyncTask<String, Void,String> {

        Context context;
        Activity activity;
        String option;



        public background_for_mylistingandbooking(Context context, Activity activity) {
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

                if (option.equalsIgnoreCase("1")) {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new readJSON_mylistandbook().execute("https://allansantosh.com/mobileappproject/my_listing_and_booking_results.json");
                        }
                    });

                }

                if (option.equalsIgnoreCase("2")) {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new readJSON_mylistandbook2().execute("https://allansantosh.com/mobileappproject/my_listing_and_booking_results1.json");
                        }
                    });

                }


            } else {

            }
        }

        @Override
        protected String doInBackground(String... voids) {
            String result = "";
            String id = voids[0];
            String link = voids[1];
            option = voids[2];

            String connstr = link;

            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data =    URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

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
