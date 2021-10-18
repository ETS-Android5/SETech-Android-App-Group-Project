package com.project.setech.activities.mainActivity;

import static com.project.setech.util.CategoryType.ALL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;
import com.project.setech.R;
import com.project.setech.activities.detailsActivity.DetailsActivity;
import com.project.setech.activities.listActivity.ListActivity;
import com.project.setech.activities.listActivity.listRecyclerView.ListViewAdapter;
import com.project.setech.activities.listActivity.listRecyclerView.RecyclerItemClickListener;
import com.project.setech.activities.mainActivity.mainRecyclerView.MainListViewAdapter;
import com.project.setech.activities.searchActivity.SearchActivity;
import com.project.setech.activities.searchActivity.searchRecyclerView.SearchViewAdapter;
import com.project.setech.model.IItem;
import com.project.setech.model.Item;
import com.project.setech.model.ItemFactory;
import com.project.setech.util.CategoryType;
import com.project.setech.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private CardView motherboardCard;
    private CardView gpuCard;
    private CardView cpuCard;

    private RecyclerView recyclerView;
    private MainListViewAdapter mainViewAdapter;
    private List<IItem> topItemsList;

    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private CollectionReference ref = db.collection("Items");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        motherboardCard = findViewById(R.id.motherboardCard);
        gpuCard = findViewById(R.id.gpuCard);
        cpuCard = findViewById(R.id.cpuCard);

        topItemsList= new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent newIntent = new Intent(MainActivity.this, DetailsActivity.class);
                        newIntent.putExtra("ItemId", topItemsList.get(position).getId());
                        newIntent.putExtra("SearchBoolean", false);
                        newIntent.putExtra("QueryString", "");
                        newIntent.putExtra("FromMainScreen",true);
                        startActivity(newIntent);
                        finish();
                    }
                })
        );

        // CPU on CLICK
        cpuCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToListActivity(CategoryType.CPU);
            }
        });

        // GPU on CLICK
        gpuCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToListActivity(CategoryType.GPU);
            }
        });

        // Motherboard on CLICK
        motherboardCard.setOnClickListener(new View.OnClickListener() {
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
        CategoryType type= ALL;

        ref.orderBy("viewCount", Query.Direction.DESCENDING).limit(15).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot items : queryDocumentSnapshots) {

                    try {
                        List<Integer> formattedImagePaths = Util.formatDrawableStringList((List<String>) items.get("images"), MainActivity.this);
                        Map<String, String> specifications = (Map<String, String>) items.get("specifications");

                        IItem newItem = itemFactory.createItem(items.getId(), items.getString("name"), formattedImagePaths, items.getString("price"), items.getString("viewCount"), specifications, type);
                        topItemsList.add(newItem);
                        Log.d("Item loading", items.getString("viewCount"));
                    }
                    catch (Exception e) {
                        Log.d("Item loading", items.getString("name") + " failed to be loaded.");
                    }
                }

                // Create recycler view
                mainViewAdapter = new MainListViewAdapter(MainActivity.this, topItemsList, type);
                recyclerView.setAdapter(mainViewAdapter);
                mainViewAdapter.notifyDataSetChanged();
            }
        });

    }

    public void goToListActivity(CategoryType type) {
        Intent newIntent = new Intent(MainActivity.this, ListActivity.class);
        newIntent.putExtra("CategoryType", type);
        startActivity(newIntent);
        finish();
    }

    public void goToSearchActivity(String searchString) {
        Intent newIntent = new Intent(MainActivity.this, SearchActivity.class);
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