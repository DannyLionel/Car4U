package uoit.finalproject.car4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class SelectionPage extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_selection_page);

        TextView textView = findViewById(R.id.welcome_and_name);
        textView.setText("Welcome " + getIntent().getStringExtra("first_name") + " " + getIntent().getStringExtra("last_name"));

        TextView textView2 = findViewById(R.id.username_and_email);
        textView2.setText("Username: " + getIntent().getStringExtra("username") + " || Email: " + getIntent().getStringExtra("email"));

        ImageButton search_cars = findViewById(R.id.search_cars_button);
        search_cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SelectionPage.this, CarSearch.class);
                intent.putExtra("ID", getIntent().getStringExtra("ID") );
                intent.putExtra("first_name", getIntent().getStringExtra("first_name") );
                intent.putExtra("last_name", getIntent().getStringExtra("last_name"));
                intent.putExtra("username", getIntent().getStringExtra("username"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);
            }
        });


        ImageButton rent_my_car = findViewById(R.id.rent_my_car_button);
        rent_my_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SelectionPage.this, RentMyCar.class);
                intent.putExtra("ID", getIntent().getStringExtra("ID") );
                intent.putExtra("first_name", getIntent().getStringExtra("first_name") );
                intent.putExtra("last_name", getIntent().getStringExtra("last_name"));
                intent.putExtra("username", getIntent().getStringExtra("username"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);
            }
        });

        Button mybooking_listings = findViewById(R.id.mylistings);
        mybooking_listings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SelectionPage.this, MybookingsandListings.class);
                intent.putExtra("ID", getIntent().getStringExtra("ID") );
                intent.putExtra("first_name", getIntent().getStringExtra("first_name") );
                intent.putExtra("last_name", getIntent().getStringExtra("last_name"));
                intent.putExtra("username", getIntent().getStringExtra("username"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);
            }
        });

    }

    public void onBackPressed(){
        // Do Nothing
    }

}
