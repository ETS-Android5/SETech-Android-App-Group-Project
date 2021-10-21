package com.project.setech.activities.mainActivity;

import static com.project.setech.util.CategoryType.ALL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.firestore.Query;
import com.project.setech.R;
import com.project.setech.activities.detailsActivity.DetailsActivity;
import com.project.setech.activities.listActivity.ListActivity;
import com.project.setech.activities.listActivity.listRecyclerView.RecyclerItemClickListener;
import com.project.setech.activities.mainActivity.mainRecyclerView.MainListViewAdapter;
import com.project.setech.activities.searchActivity.SearchActivity;
import com.project.setech.model.CategoryFactor;
import com.project.setech.model.IItem;
import com.project.setech.model.NewItemFactory;
import com.project.setech.repository.IRepository;
import com.project.setech.repository.Repository;
import com.project.setech.util.CategoryType;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private IRepository repository;

    private CardView motherboardCard;
    private CardView gpuCard;
    private CardView cpuCard;

    private RecyclerView recyclerView;
    private MainListViewAdapter mainViewAdapter;
    private List<IItem> topItemsList;

    private ProgressBar mainTopPicksProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializedRecyclerView();

        motherboardCard = findViewById(R.id.motherboardCard);
        gpuCard = findViewById(R.id.gpuCard);
        cpuCard = findViewById(R.id.cpuCard);

        topItemsList= new ArrayList<>();

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

    private void initializedRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, recyclerView , (view, position) -> {
                    Intent newIntent = new Intent(MainActivity.this, DetailsActivity.class);
                    newIntent.putExtra("ItemId", topItemsList.get(position).getId());
                    newIntent.putExtra("SearchBoolean", false);
                    newIntent.putExtra("QueryString", "");
                    newIntent.putExtra("FromMainScreen",true);
                    startActivity(newIntent);
                    finish();
                })
        );
    }

    protected void onStart() {
        super.onStart();

        if(!topItemsList.isEmpty()){
            return;
        }

        repository = new Repository(MainActivity.this, new NewItemFactory(),new CategoryFactor());

        repository.fetchItems("viewCount", Query.Direction.DESCENDING, 15, items -> {
            topItemsList = items;

            // Create recycler view
            mainViewAdapter = new MainListViewAdapter(MainActivity.this, topItemsList, ALL);
            recyclerView.setAdapter(mainViewAdapter);
            mainViewAdapter.notifyDataSetChanged();

            mainTopPicksProgressBar = findViewById(R.id.mainTopPicksProgressBar);
            mainTopPicksProgressBar.setVisibility(View.GONE);

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
        searchView.setMaxWidth(Integer.MAX_VALUE);
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