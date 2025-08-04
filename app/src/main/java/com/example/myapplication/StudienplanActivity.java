package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

public class StudienplanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ConstraintLayout layout = new ConstraintLayout(this);
        layout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        ));
        layout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));

        TextView textView = new TextView(this);
        textView.setId(TextView.generateViewId());
        textView.setText("Dein Studienplan wird hier angezeigt.");
        textView.setTextSize(20);
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.black));

        layout.addView(textView);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);
        constraintSet.connect(textView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 200);
        constraintSet.connect(textView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 32);
        constraintSet.connect(textView.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 32);
        constraintSet.applyTo(layout);

        setContentView(layout);
    }
}
