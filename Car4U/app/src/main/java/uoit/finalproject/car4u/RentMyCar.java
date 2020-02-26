package uoit.finalproject.car4u;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

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
import java.util.UUID;

public class RentMyCar extends AppCompatActivity {

    public static final String UPLOAD_URL = "https://allansantosh.com/mobileappproject/uploadimage.php";

    String[] seats_array = {"5", "7", "8"};
    RadioButton radioButton, radioButton2;
    Spinner seat_spinner;
    Button backbutton, rentmycar_button, set_image_button;
    EditText make,model,price,location;
    String suv_or_Car, user_id;
    ImageView car_rental_image;
    String proper_image_link;

    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_my_car);


        proper_image_link = "https://allansantosh.com/mobileappproject/images/";


        user_id = getIntent().getStringExtra("ID");
        radioButton = findViewById(R.id.radioButton);
        radioButton.setChecked(true);

        radioButton2 = findViewById(R.id.radioButton2);


        car_rental_image = findViewById(R.id.car_image_rental);

        set_image_button = findViewById(R.id.car_image_rental_button);
        set_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFileChooser();
            }

        });




        make = findViewById(R.id.make_field);
        model = findViewById(R.id.model_field);
        price = findViewById(R.id.price_field);
        location = findViewById(R.id.location_field);


        seat_spinner = findViewById(R.id.seats_spinner);
        ArrayAdapter<String> seat_adapter =
                new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_layout, seats_array);
        seat_adapter.setDropDownViewResource(R.layout.spinner_drop_down_layout);
        seat_spinner.setAdapter(seat_adapter);


        backbutton = (Button) findViewById(R.id.back_to_home_button);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RentMyCar.this, SelectionPage.class);
                intent.putExtra("ID", getIntent().getStringExtra("ID"));
                intent.putExtra("first_name", getIntent().getStringExtra("first_name"));
                intent.putExtra("last_name", getIntent().getStringExtra("last_name"));
                intent.putExtra("username", getIntent().getStringExtra("username"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);

            }

        });

        rentmycar_button = (Button) findViewById(R.id.rent_my_car_button);
        rentmycar_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ViewDialog alert = new ViewDialog();

                if (make.getText().toString().equalsIgnoreCase("")) {
                    alert.showDialog(RentMyCar.this, "Error! Enter the make of the vehicle!");
                } else {

                    if (model.getText().toString().equalsIgnoreCase("")) {
                        alert.showDialog(RentMyCar.this, "Error! Enter the model of the vehicle!");
                    } else {

                        if (location.getText().toString().equalsIgnoreCase("")) {
                            alert.showDialog(RentMyCar.this, "Error! Enter the pickup location!");

                        } else {
                            if (price.getText().toString().equalsIgnoreCase("")) {
                                alert.showDialog(RentMyCar.this, "Error! Enter the max price to rent at!");
                            } else {

                                if(radioButton.isChecked()) {
                                    suv_or_Car = "0";
                                }

                                if(radioButton2.isChecked()) {
                                    suv_or_Car = "1";
                                }


                                uploadMultipart();

                               background_for_rent_car_yours bg = new background_for_rent_car_yours(RentMyCar.this,RentMyCar.this);
                                bg.execute(make.getText().toString(),model.getText().toString(),location.getText().toString(),price.getText().toString(),suv_or_Car,
                                        user_id,seat_spinner.getSelectedItem().toString(), proper_image_link);
                            }
                        }
                    }
                }
            }
        });

    }


    public class background_for_rent_car_yours extends AsyncTask<String, Void,String> {

        Context context;
        Activity activity;

        public background_for_rent_car_yours(Context context, Activity activity) {
            this.context = context;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {
            if (s.contains("success")) {

                ViewDialog alert = new ViewDialog();
                alert.showDialog(activity, "Success! You have successfully listed your car!");
                make.setText("");
                model.setText("");
                price.setText("");
                location.setText("");


            } else {

                ViewDialog alert = new ViewDialog();
                alert.showDialog(activity, "Error! Unable to list your car!");
            }
        }

        @Override
        protected String doInBackground(String... voids) {
            String result = "";
            String rent_make = voids[0];
            String rent_model = voids[1];
            String rent_location = voids[2];
            String rent_price = voids[3];
            String rent_suv_or_Car = voids[4];
            String rent_user_id = voids[5];
            String rent_seats = voids[6];
            String rent_link = voids[7]+ voids[1] + "_user_"+ voids[5] + ".jpg";

            String connstr = "https://allansantosh.com/mobileappproject/rentcar.php";

            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                Log.d(rent_make, rent_model);
                Log.d(rent_location, rent_price);
                Log.d(rent_suv_or_Car, rent_user_id);
                Log.d(rent_seats, rent_link);



                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("make", "UTF-8") + "=" + URLEncoder.encode(rent_make, "UTF-8")
                        + "&&" + URLEncoder.encode("model", "UTF-8") + "=" + URLEncoder.encode(rent_model, "UTF-8")
                        + "&&" + URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(rent_location, "UTF-8")
                        + "&&" + URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(rent_price, "UTF-8")
                        + "&&" + URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(rent_user_id, "UTF-8")
                        + "&&" + URLEncoder.encode("num_seats", "UTF-8") + "=" + URLEncoder.encode(rent_seats, "UTF-8")
                        + "&&" + URLEncoder.encode("image_link", "UTF-8") + "=" + URLEncoder.encode(rent_link, "UTF-8")
                        + "&&" + URLEncoder.encode("car_suv", "UTF-8") + "=" + URLEncoder.encode(rent_suv_or_Car, "UTF-8");
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



    @Override
    public void onBackPressed() {
        //your code when back button pressed

    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                car_rental_image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    public void uploadMultipart() {
        //getting name for the image
        String name = model.getText().toString().trim();

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            Log.d(name,user_id);

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", name) //Adding text parameter to the request
                    .addParameter("user_id_rental", user_id)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}