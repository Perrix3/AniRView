package com.example.anirview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // Button to go back to the main menu activity
        ImageButton backButtonn = findViewById(R.id.backButtonn);
        backButtonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MainMenuActivity on click
                Intent intent = new Intent(ReviewActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });


    }
}
