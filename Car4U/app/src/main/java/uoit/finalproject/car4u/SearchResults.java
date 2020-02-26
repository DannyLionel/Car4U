package uoit.finalproject.car4u;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
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

import org.json.JSONException;


public class SearchResults extends AppCompatActivity {

    static final String STATE_SCROLL_POSITION = "scrollPosition";
    int savedPosition = 0;

    ArrayList<Cars> carsArrayList;
    ListView lv;
    Button back_to_car_search;
    String num_seats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        carsArrayList = new ArrayList();
        lv = (ListView) findViewById(R.id.list);

        back_to_car_search = (Button)findViewById(R.id.back_to_search_button);
        back_to_car_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SearchResults.this, CarSearch.class);
                intent.putExtra("ID", getIntent().getStringExtra("ID") );
                intent.putExtra("first_name", getIntent().getStringExtra("first_name") );
                intent.putExtra("last_name", getIntent().getStringExtra("last_name"));
                intent.putExtra("username", getIntent().getStringExtra("username"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);

            }

        });

        num_seats = getIntent().getStringExtra("num_seats");

        if (num_seats.equalsIgnoreCase("Any")) {
            num_seats = "9999";
        }

        SearchResults.background_for_searchresults bg= new SearchResults.background_for_searchresults(SearchResults.this,SearchResults.this);
        bg.execute(getIntent().getStringExtra("ID"),getIntent().getStringExtra("first_name"),
                getIntent().getStringExtra("last_name"), getIntent().getStringExtra("username"),
                getIntent().getStringExtra("email"), getIntent().getStringExtra("makeid"), getIntent().getStringExtra("modelid"),
                getIntent().getStringExtra("fromdate"),getIntent().getStringExtra("todate"),getIntent().getStringExtra("location"),
                getIntent().getStringExtra("maxprice"),getIntent().getStringExtra("suv_or_not"),num_seats);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save the user's current game state
        int currentPosition = lv.getFirstVisiblePosition();
        savedInstanceState.putInt(STATE_SCROLL_POSITION,
                currentPosition);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        savedPosition = savedInstanceState
                .getInt(STATE_SCROLL_POSITION);
        lv.setSelection(savedPosition);

    }

    class readJSON extends AsyncTask<String, Integer, String> {

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
                    carsArrayList.add(new Cars(
                            cars_object.getString("MakeID"),
                            cars_object.getString("ModelID"),
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

            Parcelable state = lv.onSaveInstanceState();

            CustomListAdapter adapter = new CustomListAdapter(SearchResults.this,  carsArrayList);
            lv.setAdapter(adapter);

            lv.onRestoreInstanceState(state);
            lv.smoothScrollToPosition(savedPosition);

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

    class CustomListAdapter extends BaseAdapter {
        private ArrayList<Cars> searchArrayList;
        private LayoutInflater mInflater;
        Context context;

        public CustomListAdapter(Context context, ArrayList<Cars> results) {
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
            final String price1;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_layout_search_results, null);
                holder = new CustomListAdapter.ViewHolder();
                holder.txtMake = (TextView) convertView.findViewById(R.id.make_textView);
                holder.txtModel = (TextView) convertView.findViewById(R.id.model_textView);
                holder.car_image = (ImageView) convertView.findViewById(R.id.list_view_search_results_image);
                holder.txtOwner = (TextView) convertView.findViewById(R.id.owner_textView);
                holder.txtPrice = (TextView) convertView.findViewById(R.id.price_textView);
                holder.txtSeats = (TextView) convertView.findViewById(R.id.seats_textView);
                holder.book_now = (Button) convertView.findViewById(R.id.rent_my_car_button);

                convertView.setTag(holder);
            } else {
                holder = (CustomListAdapter.ViewHolder) convertView.getTag();
            }


            holder.txtMake.setText(searchArrayList.get(position).getMake() + " " + searchArrayList.get(position).getModel());
            holder.txtModel.setText("");
            holder.txtOwner.setText("Owner: " + searchArrayList.get(position).getOwner());
            holder.txtPrice.setText("$" + String.format("%.2f", Double.parseDouble(searchArrayList.get(position).getPrice())));
            holder.txtSeats.setText("Seats: " + searchArrayList.get(position).getSeats());
            Picasso.with(context).load(searchArrayList.get(position).getImage()).into(holder.car_image);
            car_id = searchArrayList.get(position).getCarid();
            price1 = searchArrayList.get(position).getPrice();

            holder.book_now.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(SearchResults.this, BookingConfirmation.class);
                    intent.putExtra("ID", getIntent().getStringExtra("ID"));
                    intent.putExtra("CarID", car_id );
                    intent.putExtra("first_name", getIntent().getStringExtra("first_name") );
                    intent.putExtra("last_name", getIntent().getStringExtra("last_name"));
                    intent.putExtra("username", getIntent().getStringExtra("username"));
                    intent.putExtra("email", getIntent().getStringExtra("email"));
                    intent.putExtra("fromdate", getIntent().getStringExtra("fromdate"));
                    intent.putExtra("todate", getIntent().getStringExtra("todate"));
                    intent.putExtra("maxprice", getIntent().getStringExtra("maxprice"));
                    intent.putExtra("price", price1);
                    startActivity(intent);
                }
            });

            return convertView;
        }

       class ViewHolder {
            TextView txtMake;
            TextView txtModel;
            TextView txtOwner;
            TextView txtPrice;
            TextView txtSeats;
            ImageView car_image;
            Button book_now;
        }


    }

    public class background_for_searchresults extends AsyncTask<String, Void,String> {

        Context context;
        Activity activity;

        public Boolean login = false;

        public background_for_searchresults(Context context, Activity activity) {
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
                        new SearchResults.readJSON().execute("https://allansantosh.com/mobileappproject/search_results.json");
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
            String first_name = voids[1];
            String last_name = voids[2];
            String username = voids[3];
            String email = voids[4];
            String makeid = voids[5];
            String modelid = voids[6];
            String fromdate = voids[7];
            String todate = voids[8];
            String location = voids[9];
            String price = voids[10];
            String suv_or_not = voids[11];
            String num_seats = voids[12];

            String connstr = "https://allansantosh.com/mobileappproject/search_results.php";

            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data =    URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8")
                        + "&&" + URLEncoder.encode("first_name", "UTF-8") + "=" + URLEncoder.encode(first_name, "UTF-8")
                        + "&&" + URLEncoder.encode("last_name", "UTF-8") + "=" + URLEncoder.encode(last_name, "UTF-8")
                        + "&&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                        + "&&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                        + "&&" + URLEncoder.encode("makeid", "UTF-8") + "=" + URLEncoder.encode(makeid, "UTF-8")
                        + "&&" + URLEncoder.encode("modelid", "UTF-8") + "=" + URLEncoder.encode(modelid, "UTF-8")
                        //+ "&&" + URLEncoder.encode("fromdate", "UTF-8") + "=" + URLEncoder.encode(fromdate, "UTF-8")
                        //+ "&&" + URLEncoder.encode("todate", "UTF-8") + "=" + URLEncoder.encode(todate, "UTF-8")
                        + "&&" + URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(location, "UTF-8")
                        + "&&" + URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(price, "UTF-8")
                        + "&&" + URLEncoder.encode("suv_or_not", "UTF-8") + "=" + URLEncoder.encode(suv_or_not, "UTF-8")
                        + "&&" + URLEncoder.encode("num_seats", "UTF-8") + "=" + URLEncoder.encode(num_seats, "UTF-8");

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