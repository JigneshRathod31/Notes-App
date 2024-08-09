package com.jignesh.notesapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jignesh.notesapp.R;
import com.jignesh.notesapp.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {

    ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        animateViews();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            }
        }, 1600);
    }

    private void animateViews() {
        Animation translateLeftToRight = new TranslateAnimation(-200.0f, 0f, 0, 0);
        translateLeftToRight.setDuration(1200);
        binding.tvAppTitle.startAnimation(translateLeftToRight);

        Animation translateRightToLeft = new TranslateAnimation(200.0f, 0f, 0, 0);
        translateRightToLeft.setDuration(1200);
        binding.tvTagLine.startAnimation(translateRightToLeft);

        Animation scaleUp = new ScaleAnimation(0f, 1f, 0f, 1f, 50f, 50f);
        scaleUp.setDuration(1200);
        binding.ivNotesLogo.startAnimation(scaleUp);

    }
}