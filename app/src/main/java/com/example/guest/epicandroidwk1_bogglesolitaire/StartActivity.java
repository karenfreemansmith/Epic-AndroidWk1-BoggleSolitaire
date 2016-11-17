package com.example.guest.epicandroidwk1_bogglesolitaire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.easyButton) ImageButton mEasyButton;
    @Bind(R.id.hardButton) ImageButton mHardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(StartActivity.this);
        mEasyButton.setOnClickListener(this);
        mHardButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        if(view == mEasyButton){
            intent.putExtra("difficulty", 0);
        } else {
            intent.putExtra("difficulty", 1);
        }
        startActivity(intent);
    }
}
