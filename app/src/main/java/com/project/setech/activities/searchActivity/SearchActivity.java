package com.project.setech.activities.searchActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.project.setech.R;
import com.project.setech.activities.listActivity.listRecyclerView.ListViewAdapter;
import com.project.setech.activities.mainActivity.MainActivity;
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

                // Create recycler view
                searchViewAdapter = new SearchViewAdapter(SearchActivity.this, itemsList, CategoryType.ALL);
                searchViewAdapter.getFilter().filter(searchString.toString());
                recyclerView.setAdapter(searchViewAdapter);
                searchViewAdapter.notifyDataSetChanged();
            }
        });
    }

    public void goToSearchActivity(String searchString) {
        Intent newIntent = new Intent(SearchActivity.this, SearchActivity.class);
        newIntent.putExtra("SearchString", searchString);
        startActivity(newIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                goToSearchActivity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}