package uoit.finalproject.car4u;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CarSearch extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView from_date,to_date;
    Date from_date_date, to_date_date;
    AlertDialog dialog;
    EditText price, city;
    String selection;
    ArrayList <Make> makeArrayList = new ArrayList<Make>();
    ArrayList <Model> modelArrayList = new ArrayList<Model>();
    Spinner make_spinner, model_spinner, seat_spinner;
    String id_number_for_make, id_number_for_model;
    Button back_to_selection, car_search_button;
    String [] seats_array = {"Any","5","7","8"};
    RadioButton radioButton,radioButton2, radioButton3;
    String suv_or_not_main, from_Date_text,to_Date_text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_search);

        suv_or_not_main = "9999";

        radioButton = (RadioButton)findViewById(R.id.radioButton);
        radioButton2 = (RadioButton)findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton)findViewById(R.id.radioButton3);

        radioButton3.setChecked(true);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rent_radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                background_for_makesearch bg = new background_for_makesearch(CarSearch.this,CarSearch.this);
                bg.execute("notneeded");

            }
        });

        back_to_selection = (Button)findViewById(R.id.back_to_home_button);
        back_to_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CarSearch.this, SelectionPage.class);
                intent.putExtra("ID", getIntent().getStringExtra("ID") );
                intent.putExtra("first_name", getIntent().getStringExtra("first_name") );
                intent.putExtra("last_name", getIntent().getStringExtra("last_name"));
                intent.putExtra("username", getIntent().getStringExtra("username"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);

            }

        });

        from_date = findViewById(R.id.date_from_field);
        from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selection = "from";
                showDatePickerDialog();
            }
        });

        to_date = findViewById(R.id.date_to_field);
        to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selection = "to";
                showDatePickerDialog();
            }
        });

        make_spinner = findViewById(R.id.make_spinner);
        model_spinner = findViewById(R.id.model_spinner);
        seat_spinner = findViewById(R.id.seats_spinner);

        background_for_makesearch bg = new background_for_makesearch(CarSearch.this,CarSearch.this);
        bg.execute("notneeded");


        make_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                String selectedItem = parent.getItemAtPosition(position).toString();

                for (int i = 0; i < makeArrayList.size(); i ++){

                    if(selectedItem.equalsIgnoreCase(makeArrayList.get(i).getName())) {
                        id_number_for_make = makeArrayList.get(i).getId();
                    }
                }

                if(radioButton.isChecked()) {

                    suv_or_not_main = "0";

                    background_for_modelsearch bgg = new background_for_modelsearch(CarSearch.this,CarSearch.this);
                    bgg.execute(id_number_for_make, "0");

                }

                if(radioButton2.isChecked()) {

                    suv_or_not_main = "1";

                    background_for_modelsearch bgg = new background_for_modelsearch(CarSearch.this,CarSearch.this);
                    bgg.execute(id_number_for_make, "1");

                }

                if(radioButton3.isChecked()) {

                    suv_or_not_main = "9999";

                    background_for_modelsearch bgg = new background_for_modelsearch(CarSearch.this,CarSearch.this);
                    bgg.execute(id_number_for_make, "9999");

                }



            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        model_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                String selectedItem = parent.getItemAtPosition(position).toString();

                for (int i = 0; i < modelArrayList.size(); i ++){

                    if(selectedItem.equalsIgnoreCase(modelArrayList.get(i).getName())) {
                        id_number_for_model = modelArrayList.get(i).getId();
                    }
                }

            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        ArrayAdapter<String> seat_adapter =
                new ArrayAdapter<String>(getApplicationContext(),  R.layout.spinner_layout, seats_array);
        seat_adapter.setDropDownViewResource( R.layout.spinner_drop_down_layout);

        seat_spinner.setAdapter(seat_adapter);

        price = (EditText)findViewById(R.id.price_field);
        city = (EditText)findViewById(R.id.location_field);

        car_search_button = (Button)findViewById(R.id.rent_my_car_button);
        car_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewDialog alert = new ViewDialog();

                if (from_date.getText().toString().equalsIgnoreCase("")) {
                    alert.showDialog(CarSearch.this, "Error! Enter the date you want to rent the car from!");
                }

                else {

                    if (from_date.getText().toString().equalsIgnoreCase("")) {
                        alert.showDialog(CarSearch.this, "Error! Enter the date you want to rent the car till!");
                    }

                    else {

                        if(from_date_date.compareTo(to_date_date) > 0) {
                            alert.showDialog(CarSearch.this, "Error! The 'From Date' is greater than 'To Date'!");
                        }

                        else {

                            if (city.getText().toString().equalsIgnoreCase("")) {
                                alert.showDialog(CarSearch.this, "Error! Enter the pickup location!");
                            }

                            else {

                                if(price.getText().toString().equalsIgnoreCase("")){
                                    alert.showDialog(CarSearch.this, "Error! Enter the max price budget!");
                                }

                                else {

                                    Intent intent = new Intent(CarSearch.this, SearchResults.class);
                                    intent.putExtra("ID", getIntent().getStringExtra("ID") );
                                    intent.putExtra("first_name", getIntent().getStringExtra("first_name") );
                                    intent.putExtra("last_name", getIntent().getStringExtra("last_name"));
                                    intent.putExtra("username", getIntent().getStringExtra("username"));
                                    intent.putExtra("email", getIntent().getStringExtra("email"));
                                    intent.putExtra("makeid", id_number_for_make);
                                    intent.putExtra("modelid", id_number_for_model);
                                    intent.putExtra("fromdate", from_Date_text);
                                    intent.putExtra("todate", to_Date_text);
                                    intent.putExtra("location", city.getText().toString());
                                    intent.putExtra("maxprice", price.getText().toString());
                                    intent.putExtra("suv_or_not", suv_or_not_main);
                                    intent.putExtra("num_seats", seat_spinner.getSelectedItem().toString());

                                    startActivity(intent);


                                }

                            }
                        }

                    }




                }

            }

        });

    }

    public class background_for_makesearch extends AsyncTask<String, Void,String> {

        Context context;
        Activity activity;

        public Boolean login = false;

        public background_for_makesearch(Context context, Activity activity) {
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
                        new CarSearch.readJSON2().execute("https://allansantosh.com/mobileappproject/make_results.json");
                    }
                });


            } else {

                ViewDialog alert = new ViewDialog();
                alert.showDialog(activity, "Error! Unable to get Car Makes List!");
            }
        }

        @Override
        protected String doInBackground(String... voids) {
            String result = "";
            String notneeded = voids[0];

            String connstr = "https://allansantosh.com/mobileappproject/search_make.php";

            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));

                String data = URLEncoder.encode("notneeded", "UTF-8") + "=" + URLEncoder.encode(notneeded, "UTF-8");
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

    public class background_for_modelsearch extends AsyncTask<String, Void,String> {

        AlertDialog dialog;
        Context context;
        Activity activity;

        public background_for_modelsearch(Context context, Activity activity) {
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
                        new CarSearch.readJSON3().execute("https://allansantosh.com/mobileappproject/model_results.json");
                    }
                });


            } else {

                ViewDialog alert = new ViewDialog();
                alert.showDialog(activity, "Error! Unable to get Car Models List!");
            }
        }

        @Override
        protected String doInBackground(String... voids) {
            String result = "";
            String make = voids[0];
            String is_suv_or_not = voids[1];

            String connstr = "https://allansantosh.com/mobileappproject/search_model.php";

            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("make", "UTF-8") + "=" + URLEncoder.encode(make, "UTF-8")
                        + "&&" + URLEncoder.encode("suv_or_not", "UTF-8") + "=" + URLEncoder.encode(is_suv_or_not, "UTF-8");
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


    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        month = month + 1;

        if (selection.equalsIgnoreCase("from")) {
            from_Date_text = year + "-" + month + "-" + dayOfMonth;
            try {
                from_date_date = new SimpleDateFormat("yyyy-MM-dd").parse(from_Date_text);
            }
            catch (Exception e) {
                e.printStackTrace();
                ViewDialog alert = new ViewDialog();
                alert.showDialog(CarSearch.this, "Error! Unable to set date!");
            }
            from_date.setText("From Date: " + from_Date_text);
        }

        else {
            to_Date_text = year + "-" + month + "-" + dayOfMonth;
            try {
                to_date_date = new SimpleDateFormat("yyyy-MM-dd").parse(to_Date_text);
            }
            catch (Exception e){
                e.printStackTrace();
                ViewDialog alert = new ViewDialog();
                alert.showDialog(CarSearch.this, "Error! Unable to set date!");
            }
            to_date.setText("To Date: " + to_Date_text);
        }

    }


    class readJSON2 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            return readURL(params[0]);
        }


        @Override
        protected void onPostExecute(String content) {

            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("the_array");

                makeArrayList.clear();
                makeArrayList.add(new Make("9999","Any"));

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject make_object = jsonArray.getJSONObject(i);
                    makeArrayList.add(new Make(
                            make_object.getString("id"),
                            make_object.getString("name")
                    ));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayAdapter<Make> adapter =
                    new ArrayAdapter<Make>(getApplicationContext(),  R.layout.spinner_layout, makeArrayList);
            adapter.setDropDownViewResource( R.layout.spinner_drop_down_layout);

            make_spinner.setAdapter(adapter);

        }
    }

    class readJSON3 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            return readURL(params[0]);
        }


        @Override
        protected void onPostExecute(String content) {

            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("the_array");

                modelArrayList.clear();
                modelArrayList.add(new Model("9999","Any"));

                if (!id_number_for_make.equalsIgnoreCase("9999")) {

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject make_object = jsonArray.getJSONObject(i);
                    modelArrayList.add(new Model(
                            make_object.getString("id"),
                            make_object.getString("model")
                    ));

                }}
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayAdapter<Model> adapter =
                    new ArrayAdapter<Model>(getApplicationContext(),  R.layout.spinner_layout, modelArrayList);
            adapter.setDropDownViewResource( R.layout.spinner_drop_down_layout);

            model_spinner.setAdapter(adapter);

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

    @Override
    public void onBackPressed() {
        //your code when back button pressed

    }

}