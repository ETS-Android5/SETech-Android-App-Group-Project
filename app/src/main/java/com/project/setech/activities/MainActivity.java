package com.project.setech.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.project.setech.R;
import com.project.setech.activities.listActivity.ListActivity;

public class MainActivity extends AppCompatActivity {

    private Button goToListAcitivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToListAcitivityButton = findViewById(R.id.goToListAcitivityButton);

        goToListAcitivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
                finish();
            }
        });
    }
}