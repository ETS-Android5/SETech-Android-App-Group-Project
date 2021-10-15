package com.project.setech.activities.searchActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.project.setech.R;
import com.project.setech.activities.listActivity.listRecyclerView.ListViewAdapter;
import com.project.setech.activities.searchActivity.searchRecyclerView.SearchViewAdapter;
import com.project.setech.model.IItem;
import com.project.setech.model.ItemFactory;
import com.project.setech.util.CategoryType;
import com.project.setech.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private SearchViewAdapter searchViewAdapter;

    private List<IItem> itemsList;
    private String searchString;
    private CollectionReference collectionReference = db.collection("Items");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        itemsList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        int columns = 2;

        recyclerView.setLayoutManager(new GridLayoutManager(this, columns));
    }

    @Override
    protected void onStart() {
        super.onStart();

        searchString = (String) getIntent().getSerializableExtra("SearchString");

        ItemFactory itemFactory = new ItemFactory();

        CategoryType type = CategoryType.ALL;

        collectionReference.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot items : queryDocumentSnapshots) {

                    List<Integer> formattedImagePaths = Util.formatDrawableStringList((List<String>) items.get("images"), SearchActivity.this);
                    Map<String, String> specifications = (Map<String, String>) items.get("specifications");

                    IItem newItem = itemFactory.createItem(items.getString("name"), formattedImagePaths, items.getString("price"), specifications, type);
                    System.out.println("ITEM NAME: "+newItem.getName());
                    itemsList.add(newItem);
                }
                System.out.println("SIZE: "+itemsList.size());
                // Create recycler view
                searchViewAdapter = new SearchViewAdapter(SearchActivity.this, itemsList, CategoryType.ALL);
                searchViewAdapter.getFilter().filter(searchString.toString());
                recyclerView.setAdapter(searchViewAdapter);
                searchViewAdapter.notifyDataSetChanged();
            }
        });
    }
}