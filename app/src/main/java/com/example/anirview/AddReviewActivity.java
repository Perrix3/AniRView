package com.example.anirview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddReviewActivity extends AppCompatActivity {

    private EditText titleEditText, scoreEditText, subtitleEditText, reviewEditText, posterUrlEditText;
    private Button addReviewButton, cancelButton;

    private static final String FILENAME = "reviews.json"; // The file name to store reviews in JSON format

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        // Initialize the EditTexts and Buttons
        titleEditText = findViewById(R.id.title);
        scoreEditText = findViewById(R.id.score);
        subtitleEditText = findViewById(R.id.subtitle);
        reviewEditText = findViewById(R.id.review);
        posterUrlEditText = findViewById(R.id.poster_url);

        addReviewButton = findViewById(R.id.add_review_button);
        cancelButton = findViewById(R.id.cancel_button);

        // Set listeners for the buttons
        addReviewButton.setOnClickListener(view -> addReview());
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the WatchActivity on click
                Intent intent = new Intent(AddReviewActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });

    }

    private void addReview() {
        String title = titleEditText.getText().toString();
        String score = scoreEditText.getText().toString();
        String subtitle = subtitleEditText.getText().toString();
        String review = reviewEditText.getText().toString();
        String posterUrl = posterUrlEditText.getText().toString();

        if (title.isEmpty() || score.isEmpty() || subtitle.isEmpty() || review.isEmpty() || posterUrl.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the review object
        Review newReview = new Review(title, score, subtitle, review, posterUrl);

        // Save the review to the JSON file
        saveReviewToJSON(newReview);

        // Close MainMenuActivity, so it does the onCreate() again
        MainMenuActivity.closeMainMenu();

        // Toast to indicate the review was added
        Toast.makeText(this, "Review Added Successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddReviewActivity.this, MainMenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveReviewToJSON(Review review) {
        ArrayList<Review> reviews = loadReviewsFromJSON();

        // Add the new review to the list
        reviews.add(review);

        // Convert the list to JSON and save it to a file
        Gson gson = new Gson();
        String json = gson.toJson(reviews);

        try (FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save review", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<Review> loadReviewsFromJSON() {
        ArrayList<Review> reviews = new ArrayList<>();

        try (FileInputStream fis = openFileInput(FILENAME)) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String json = new String(buffer);

            Gson gson = new Gson();
            Type reviewListType = new TypeToken<ArrayList<Review>>(){}.getType();
            reviews = gson.fromJson(json, reviewListType);
        } catch (IOException e) {
            // No file found, return an empty list
            e.printStackTrace();
        }

        return reviews;
    }

    // Review class to hold review data
    public static class Review {
        private String title;
        private String score;
        private String subtitle;
        private String review;
        private String posterUrl;

        public Review(String title, String score, String subtitle, String review, String posterUrl) {
            this.title = title;
            this.score = score;
            this.subtitle = subtitle;
            this.review = review;
            this.posterUrl = posterUrl;
        }

        // Getters and setters for JSON serialization (if necessary)
        public String getTitle() {
            return title;
        }

        public String getScore() {
            return score;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public String getReview() {
            return review;
        }

        public String getPosterUrl() {
            return posterUrl;
        }
    }
}
