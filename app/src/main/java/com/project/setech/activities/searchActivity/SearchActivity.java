package com.project.setech.activities.searchActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.project.setech.R;
import com.project.setech.activities.detailsActivity.DetailsActivity;
import com.project.setech.activities.listActivity.ListActivity;
import com.project.setech.activities.listActivity.listRecyclerView.ListViewAdapter;
import com.project.setech.activities.listActivity.listRecyclerView.RecyclerItemClickListener;
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

    private Button sortByOpenButton;

    private Button priceSortButton;
    private Button nameSortButton;
    private Button viewsSortButton;

    private Button increasingSortButton;
    private Button decreasingSortButton;

    private LinearLayout sortByExpandedLayout;

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

        sortByOpenButton = findViewById(R.id.sortByOpenButton);

        priceSortButton = findViewById(R.id.priceButton);
        nameSortButton = findViewById(R.id.nameButton);
        viewsSortButton = findViewById(R.id.viewsButton);

        increasingSortButton = findViewById(R.id.increasingButton);
        decreasingSortButton = findViewById(R.id.decreasingButton);

        selectSortButton(priceSortButton);
        selectOrderSortButton(increasingSortButton);

        sortByExpandedLayout = findViewById(R.id.sortByExpandedLayout);
        sortByExpandedLayout.setVisibility(View.GONE);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(SearchActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Log.d("test", "onItemClick: "+itemsList.get(position).getId());

                        Intent newIntent = new Intent(SearchActivity.this, DetailsActivity.class);
                        newIntent.putExtra("ItemId", itemsList.get(position).getId());
                        startActivity(newIntent);
                        finish();
                    }
                })
        );

        sortByOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sortByExpandedLayout.getVisibility() == View.GONE) {
                    sortByExpandedLayout.setVisibility(View.VISIBLE);
                    sortByOpenButton.setCompoundDrawablesWithIntrinsicBounds(null,null, AppCompatResources.getDrawable(SearchActivity.this,R.drawable.arrow_up),null);
                }
                else {
                    sortByExpandedLayout.setVisibility(View.GONE);
                    sortByOpenButton.setCompoundDrawablesWithIntrinsicBounds(null,null,AppCompatResources.getDrawable(SearchActivity.this,R.drawable.arrow_down),null);
                }
            }
        });

        priceSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSortButton(priceSortButton);

                // Sort the itemList by price
            }
        });

        nameSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSortButton(nameSortButton);

                // Sort the itemList by name
            }
        });

        viewsSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSortButton(viewsSortButton);

                // Sort the itemList by views
            }
        });

        increasingSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectOrderSortButton(increasingSortButton);

                // Sort the itemList by views
            }
        });

        decreasingSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectOrderSortButton(decreasingSortButton);

                // Sort the itemList by views
            }
        });
    }

    private void selectSortButton(Button sortBtn) {
        priceSortButton.setBackground(AppCompatResources.getDrawable(this,R.drawable.sort_button_border_not_highlighted));
        nameSortButton.setBackground(AppCompatResources.getDrawable(this,R.drawable.sort_button_border_not_highlighted));
        viewsSortButton.setBackground(AppCompatResources.getDrawable(this,R.drawable.sort_button_border_not_highlighted));

        priceSortButton.setTextColor(AppCompatResources.getColorStateList(this,R.color.grey));
        nameSortButton.setTextColor(AppCompatResources.getColorStateList(this,R.color.grey));
        viewsSortButton.setTextColor(AppCompatResources.getColorStateList(this,R.color.grey));

        sortBtn.setBackground(AppCompatResources.getDrawable(this,R.drawable.sort_button_border_highlighted));
        sortBtn.setTextColor(AppCompatResources.getColorStateList(this,R.color.black));
    }

    private void selectOrderSortButton(Button sortBtn) {
        increasingSortButton.setBackground(AppCompatResources.getDrawable(this, R.drawable.sort_button_border_not_highlighted));
        decreasingSortButton.setBackground(AppCompatResources.getDrawable(this, R.drawable.sort_button_border_not_highlighted));

        increasingSortButton.setTextColor(AppCompatResources.getColorStateList(this, R.color.grey));
        decreasingSortButton.setTextColor(AppCompatResources.getColorStateList(this, R.color.grey));

        sortBtn.setBackground(AppCompatResources.getDrawable(this, R.drawable.sort_button_border_highlighted));
        sortBtn.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!itemsList.isEmpty()) {
            return;
        }

        searchString = (String) getIntent().getSerializableExtra("SearchString");

        ItemFactory itemFactory = new ItemFactory();

        CategoryType type = CategoryType.ALL;

        collectionReference.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot items : queryDocumentSnapshots) {

                    List<Integer> formattedImagePaths = Util.formatDrawableStringList((List<String>) items.get("images"), SearchActivity.this);
                    Map<String, String> specifications = (Map<String, String>) items.get("specifications");

                    IItem newItem = itemFactory.createItem(items.getId(),items.getString("name"), formattedImagePaths, items.getString("price"),items.getString("viewCount"), specifications, type);
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