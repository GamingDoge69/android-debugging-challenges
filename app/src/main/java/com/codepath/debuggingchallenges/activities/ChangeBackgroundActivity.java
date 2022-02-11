package com.codepath.debuggingchallenges.activities;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.codepath.debuggingchallenges.R;

public class ChangeBackgroundActivity extends AppCompatActivity {

    private int oldColor = Color.BLUE;

    private RelativeLayout mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_background);
        mainView = findViewById(R.id.content);
        mainView.setBackgroundColor(oldColor);
    }

    public void onGoClick(View view) {
        mainView.setBackgroundColor(getNextColor());
    }

    private int getNextColor() {
        int newColor = (oldColor == Color.BLUE) ? Color.RED : Color.BLUE;
        oldColor = newColor;
        return newColor;
    }
}
