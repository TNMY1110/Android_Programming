package com.cookandroid.project10_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class ResultActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        setTitle("투표 결과");

        Intent intent = getIntent();
        int[] voteResult = intent.getIntArrayExtra("VoteCount");

        Integer[] imageFileID = {
                R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,
                R.drawable.pic4, R.drawable.pic5, R.drawable.pic6,
                R.drawable.pic7, R.drawable.pic8, R.drawable.pic9
        };

        ViewFlipper vFlipper = (ViewFlipper) findViewById(R.id.vFlipper);
        Button btnStart = (Button) findViewById(R.id.btnStart);
        Button btnStop = (Button) findViewById(R.id.btnStop);
        Button btnReturn = (Button) findViewById(R.id.btnReturn);

        Integer[] sortedIndex = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        Arrays.sort(sortedIndex, (a, b) -> voteResult[b] - voteResult[a]);

        for (int i = 0; i < 9; i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(imageFileID[sortedIndex[i]]);
            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            vFlipper.addView(iv);
        }

        vFlipper.setFlipInterval(1000);
        vFlipper.startFlipping();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vFlipper.startFlipping();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vFlipper.stopFlipping();
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}