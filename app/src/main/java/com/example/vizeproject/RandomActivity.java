package com.example.vizeproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class RandomActivity extends AppCompatActivity {

    LinearLayout ll_datas;
    EditText et_adet, et_maks, et_min;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        ll_datas = findViewById(R.id.ll_datas);
        et_adet = findViewById(R.id.et_adet);
        et_maks = findViewById(R.id.et_maks);
        et_min = findViewById(R.id.et_min);

        try {
            et_min.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        int adet = Integer.parseInt(et_adet.getText().toString());
                        int maxPossible = Integer.parseInt(et_maks.getText().toString());
                        int minPossible = Integer.parseInt(et_min.getText().toString());

                        ll_datas.removeAllViews();

                        for (int ite = 0; ite < adet; ite++) {
                            createRows(maxPossible, minPossible);
                        }
                        return  true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
        }
    }

    private void createRows(int maxPossible, int minPossible) {
        Context context = RandomActivity.this;

        int maxValue, minValue, midValue;

        do {
            minValue = minPossible + new Random().nextInt(maxPossible - minPossible);
            maxValue = minValue + new Random().nextInt(maxPossible - minValue);
            midValue = minValue + new Random().nextInt(maxValue - minValue);
        } while (minValue == maxValue || minValue == midValue || midValue == maxValue);

        String yuzde = Double.toString(((double) (midValue - minValue) / (maxValue - minValue)) * 100);
        String parts[] = yuzde.split("\\.");
        String percentage = parts[0];

        ConstraintLayout constraintLayout = new ConstraintLayout(context);
        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));


        //midValue %percentage
        TextView percentageText = new TextView(context);
        percentageText.setId(View.generateViewId());
        String content = midValue + " %" + percentage;
        percentageText.setText(content);

        ConstraintLayout.LayoutParams paramsForPercentageText =
                new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        paramsForPercentageText.topToTop = ConstraintSet.PARENT_ID;
        paramsForPercentageText.startToStart = ConstraintSet.PARENT_ID;
        paramsForPercentageText.setMargins(dpToPx(180), dpToPx(30), 0, 0);

        percentageText.setLayoutParams(paramsForPercentageText);

        constraintLayout.addView(percentageText);


        //minValue
        TextView minValueText = new TextView(context);
        minValueText.setId(View.generateViewId());
        minValueText.setText(String.valueOf(minValue));

        ConstraintLayout.LayoutParams paramsForMinValueText =
                new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        paramsForMinValueText.topToTop = ConstraintSet.PARENT_ID;
        paramsForMinValueText.startToStart = ConstraintSet.PARENT_ID;
        paramsForMinValueText.setMargins(dpToPx(100), dpToPx(40), 0, 0);

        minValueText.setLayoutParams(paramsForMinValueText);

        constraintLayout.addView(minValueText);


        //maxValue
        TextView maxValueText = new TextView(context);
        maxValueText.setId(View.generateViewId());
        maxValueText.setText(String.valueOf(maxValue));

        ConstraintLayout.LayoutParams paramsForMaxValueText =
                new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        paramsForMaxValueText.topToTop = ConstraintSet.PARENT_ID;
        paramsForMaxValueText.endToEnd = ConstraintSet.PARENT_ID;
        paramsForMaxValueText.setMargins(dpToPx(0), dpToPx(40), dpToPx(100),0);

        maxValueText.setLayoutParams(paramsForMaxValueText);

        constraintLayout.addView(maxValueText);


        //progressBar
        ProgressBar progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setId(View.generateViewId());

        ConstraintLayout.LayoutParams paramsForPb = new ConstraintLayout.LayoutParams(dpToPx(150), dpToPx(20));
        paramsForPb.topToTop = ConstraintSet.PARENT_ID;
        paramsForPb.startToStart = minValueText.getId();
        paramsForPb.endToEnd = maxValueText.getId();
        paramsForPb.setMargins(dpToPx(30), dpToPx(40), dpToPx(30), dpToPx(100));

        progressBar.setLayoutParams(paramsForPb);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            progressBar.setMin(0);
        }
        progressBar.setProgress(Integer.parseInt(percentage));
        progressBar.setMax(100);

        constraintLayout.addView(progressBar);


        ll_datas.addView(constraintLayout);
    }

    public int dpToPx(int dp){
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}