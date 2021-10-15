package com.project.setech.activities.mainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.setech.R;
import com.project.setech.activities.listActivity.ListActivity;
import com.project.setech.activities.listActivity.listRecyclerView.ListViewAdapter;
import com.project.setech.activities.mainActivity.mainRecyclerView.MainListViewAdapter;
import com.project.setech.model.IItem;
import com.project.setech.model.Item;
import com.project.setech.model.ItemFactory;
import com.project.setech.util.CategoryType;
import com.project.setech.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private ImageView cpuMainImage;
    private ImageView gpuMainImage;
    private ImageView motherboardMainImage;
    private TextView cpuMain;
    private TextView gpuMain;
    private TextView motherboardMain;
    private TextView cpuMainDescription;
    private TextView gpuMainDescription;
    private TextView motherboardMainDescription;

    private RecyclerView recyclerView;
    private MainListViewAdapter mainViewAdapter;
    private List<IItem> topItemsList;

    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private CollectionReference ref = db.collection("Categories");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cpuMainImage= findViewById(R.id.cpuMainImage);
        gpuMainImage= findViewById(R.id.gpuMainImage);
        motherboardMainImage= findViewById(R.id.motherboardMainImage);
        cpuMain= findViewById(R.id.cpuMain);
        gpuMain= findViewById(R.id.gpuMain);
        motherboardMain=findViewById(R.id.motherboardMain);
        cpuMainDescription=findViewById(R.id.cpuMainDescription);
        gpuMainDescription=findViewById(R.id.gpuMainDescription);
        motherboardMainDescription=findViewById(R.id.motherboardMainDescription);

        topItemsList= new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //set up adapter
        int columns= 2;

        cpuMainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToListActivity(CategoryType.CPU);
            }
        });
        cpuMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToListActivity(CategoryType.CPU);
            }
        });
        cpuMainDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToListActivity(CategoryType.CPU);
            }
        });

        gpuMainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToListActivity(CategoryType.GPU);
            }
        });
        gpuMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToListActivity(CategoryType.GPU);
            }
        });
        gpuMainDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToListActivity(CategoryType.GPU);
            }
        });

        motherboardMainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToListActivity(CategoryType.Motherboard);
            }
        });
        motherboardMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToListActivity(CategoryType.Motherboard);
            }
        });
        motherboardMainDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToListActivity(CategoryType.Motherboard);
            }
        });
    }

    protected void onStart() {
        super.onStart();

        if(!topItemsList.isEmpty()){
            return;
        }
        ItemFactory itemFactory= new ItemFactory();
        CategoryType type= (CategoryType) getIntent().getSerializableExtra("CategoryType");
        /*List<Item> topItems = retrieveTopItems();
        for(Item i: topItems){
            List<Integer> formattedImagePaths= Util.formatDrawableStringList((List<String>) i.get("images"),MainActivity.this);
            Map<String,String> specifications = (Map<String, String>) i.get("specifications");
            IItem newItem = itemFactory.createItem(i.getString("name"), formattedImagePaths,i.getString("price"),specifications,type);

        itemsList.add(newItem);

        mainViewAdapter = new MainListViewAdapter(MainActivity.this, itemsList,type);
        recyclerView.setAdapter(mainViewAdapter);
        mainViewAdapter.notifyDataSetChanged();
        }*/
    }

    public void goToListActivity(CategoryType type) {
        Intent newIntent = new Intent(MainActivity.this, ListActivity.class);
        newIntent.putExtra("CategoryType", type);
        startActivity(newIntent);
        finish();
    }

}