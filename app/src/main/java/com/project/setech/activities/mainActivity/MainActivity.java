package com.project.setech.activities.mainActivity;

import static com.project.setech.util.CategoryType.ALL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.firestore.Query;
import com.project.setech.R;
import com.project.setech.activities.detailsActivity.DetailsActivity;
import com.project.setech.activities.listActivity.ListActivity;
import com.project.setech.activities.listActivity.listRecyclerView.RecyclerItemClickListener;
import com.project.setech.activities.mainActivity.mainRecyclerView.MainListViewAdapter;
import com.project.setech.activities.searchActivity.SearchActivity;
import com.project.setech.model.CategoryFactor;
import com.project.setech.model.ICategory;
import com.project.setech.model.IItem;
import com.project.setech.model.NewItemFactory;
import com.project.setech.repository.IRepository;
import com.project.setech.repository.Repository;
import com.project.setech.util.CategoryType;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the home screen of the application and shows the categories and top picks of the
 * items that are on showcase.
 */
public class MainActivity extends AppCompatActivity {

    private IRepository repository;

    private LinearLayout categoryLayout;

    private RecyclerView recyclerView;
    private MainListViewAdapter mainViewAdapter;
    private List<IItem> topItemsList;
    private List<ICategory> categoriesList;

    private ProgressBar mainTopPicksProgressBar;
    private ProgressBar mainCategoriesProgressbar;

    /**
     * This method is called when this activity is created
     * @param savedInstanceState A mapping from String keys to various Parcelable values.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializedRecyclerView();

        topItemsList = new ArrayList<>();
    }

    /**
     * This method populates the recycler view that displays the top viewed items.
     */
    private void initializedRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        ;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, recyclerView, (view, position) -> {
                    Intent newIntent = new Intent(MainActivity.this, DetailsActivity.class);
                    newIntent.putExtra("ItemId", topItemsList.get(position).getId());
                    newIntent.putExtra("SearchBoolean", false);
                    newIntent.putExtra("QueryString", "");
                    newIntent.putExtra("FromMainScreen", true);
                    startActivity(newIntent);
                    finish();
                })
        );
    }
    /**
     * This method is executed when the activity starts
     */
    protected void onStart() {
        super.onStart();

        if (!topItemsList.isEmpty()) {
            return;
        }

        repository = new Repository(MainActivity.this, new NewItemFactory(), new CategoryFactor());

        repository.fetchItems("viewCount", Query.Direction.DESCENDING, 15, items -> {
            topItemsList = items;

            // Create recycler view
            mainViewAdapter = new MainListViewAdapter(MainActivity.this, topItemsList, ALL);
            recyclerView.setAdapter(mainViewAdapter);
            mainViewAdapter.notifyDataSetChanged();

            mainTopPicksProgressBar = findViewById(R.id.mainTopPicksProgressBar);
            mainTopPicksProgressBar.setVisibility(View.GONE);

        });
        // Fetch and display the categories
        repository.fetchCategories(categories -> {
            categoriesList = categories;

            for (ICategory c : categories){
                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View categoryCard = li.inflate(R.layout.category_card, null, false);

                ImageView img = categoryCard.findViewById(R.id.categoryCardImage);
                TextView name = categoryCard.findViewById(R.id.categoryCardName);
                TextView desc = categoryCard.findViewById(R.id.categoryCardDescription);

                CardView card = categoryCard.findViewById(R.id.categoryCard);
                card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToListActivity(CategoryType.valueOf(c.getId()));
                    }
                });

                img.setImageResource(c.getCategoryImage());
                name.setText(c.getName());
                desc.setText(c.getDescription());

                categoryLayout = findViewById(R.id.categoryLayout);

                categoryLayout.addView(categoryCard);

            }

            mainCategoriesProgressbar = findViewById(R.id.mainCategoriesProgressbar);
            mainCategoriesProgressbar.setVisibility(View.GONE);
        });
    }

    /**
     * This method redirects the user to the ListActivity screen.
     * @param type Category type that is clicked by the user
     */
    public void goToListActivity(CategoryType type) {
        Intent newIntent = new Intent(MainActivity.this, ListActivity.class);
        newIntent.putExtra("CategoryType", type);
        startActivity(newIntent);
        finish();
    }

    /**
     * This method redirects the user to the SearchActivity screen.
     * @param searchString The string that the user inputs in the search bar
     */
    public void goToSearchActivity(String searchString) {
        Intent newIntent = new Intent(MainActivity.this, SearchActivity.class);
        newIntent.putExtra("SearchString", searchString);
        startActivity(newIntent);
        finish();
    }

    /**
     * This method creates the search button and provides search functionality
     * @param menu Menu object
     * @return A boolean object
     */
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