package com.example.anirview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

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

        // Get views
        ImageView bannerImage = findViewById(R.id.bannerImage);
        TextView animeTitle = findViewById(R.id.animeTitle);
        TextView animeSubtitle = findViewById(R.id.animeSubtitle);
        TextView animeReview = findViewById(R.id.animeReview);

        // Get data from Intent
        Intent intent = getIntent();
        boolean isJsonReview = intent.getBooleanExtra("IS_JSON_REVIEW", false);

        if (isJsonReview) {
            // Load JSON-based review
            String title = intent.getStringExtra("TITLE");
            String subtitle = intent.getStringExtra("SUBTITLE");
            String reviewText = intent.getStringExtra("REVIEW_TEXT");
            String imageUrl = intent.getStringExtra("IMAGE_URL");

            animeTitle.setText(title);
            animeSubtitle.setText(subtitle);
            animeReview.setText(reviewText);
            Glide.with(this).load(imageUrl).into(bannerImage);
        } else {
            // Load static resource-based review
            int titleResId = intent.getIntExtra("TITLE_RES_ID", 0);
            int subtitleResId = intent.getIntExtra("SUBTITLE_RES_ID", 0);
            int reviewResId = intent.getIntExtra("REVIEW_RES_ID", 0);
            String imageUrl = intent.getStringExtra("IMAGE_URL");

            if (titleResId != 0) animeTitle.setText(titleResId);
            if (subtitleResId != 0) animeSubtitle.setText(subtitleResId);
            if (reviewResId != 0) animeReview.setText(reviewResId);
            Glide.with(this).load(imageUrl).into(bannerImage);
        }
    }
}
