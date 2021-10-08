package com.project.setech.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.project.setech.R;
import com.project.setech.activities.listActivity.ListActivity;
import com.project.setech.util.CategoryType;

public class MainActivity extends AppCompatActivity {

    private Button cpuButton;
    private Button gpuButton;
    private Button motherboardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cpuButton = findViewById(R.id.cpuButton);
        gpuButton = findViewById(R.id.gpuButton);
        motherboardButton = findViewById(R.id.motherboardButton);

        cpuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToListActivity(CategoryType.CPU);
            }
        });

        gpuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToListActivity(CategoryType.GPU);
            }
        });

        motherboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToListActivity(CategoryType.Motherboard);
            }
        });
    }

    public void goToListActivity(CategoryType type) {
        Intent newIntent = new Intent(MainActivity.this, ListActivity.class);
        newIntent.putExtra("CategoryType", type);
        startActivity(newIntent);
        finish();
    }
}