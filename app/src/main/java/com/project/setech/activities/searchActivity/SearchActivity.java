package com.project.setech.activities.searchActivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import com.project.setech.R;
import com.project.setech.activities.ParentActivity;
import com.project.setech.activities.detailsActivity.DetailsActivity;
import com.project.setech.activities.listActivity.listRecyclerView.RecyclerItemClickListener;
import com.project.setech.activities.mainActivity.MainActivity;
import com.project.setech.activities.searchActivity.searchRecyclerView.SearchViewAdapter;

import com.project.setech.model.NewItemFactory;
import com.project.setech.repository.Repository;
import com.project.setech.util.Animations.Animations;
import com.project.setech.util.Animations.IAnimations;
import com.project.setech.util.CategoryType;

/**
 * This class is used to represent a list of items after a search from MainActivity
 */
public class SearchActivity extends ParentActivity {

    private SearchViewAdapter searchViewAdapter;

    private IAnimations animations= new Animations(SearchActivity.this);

    /**
     * This method is called when this activity is created
     * @param savedInstanceState A mapping from String keys to various Parcelable values.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        identity = "search";
        super.onCreate(savedInstanceState);

        int columns = 2;

        recyclerView.setLayoutManager(new GridLayoutManager(this, columns));

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(SearchActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Log.d("test", "onItemClick: "+itemsList.get(position).getId());

                        Intent newIntent = new Intent(SearchActivity.this, DetailsActivity.class);
                        newIntent.putExtra("ItemId", itemsList.get(position).getId());
                        newIntent.putExtra("SearchBoolean", true);
                        newIntent.putExtra("QueryString", queryString);
                        startActivity(newIntent);
                        finish();
                    }
                })
        );

        //sort by button listener
        sortByOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sortByExpandedLayout.getVisibility() == View.GONE) {
                    sortByExpandedLayout.setVisibility(View.VISIBLE);
                    sortByOpenButton.setCompoundDrawablesWithIntrinsicBounds(null,null, AppCompatResources.getDrawable(SearchActivity.this,R.drawable.arrow_up),null);
                    animations.setSlideDownAnimation(sortByExpandedLayout);
                }
                else {
                    animations.setSlideUpAnimation(sortByExpandedLayout).setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            sortByExpandedLayout.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    sortByOpenButton.setCompoundDrawablesWithIntrinsicBounds(null,null,AppCompatResources.getDrawable(SearchActivity.this,R.drawable.arrow_down),null);
                }
            }
        });
    }

    /**
     * This method is executed when the activity start
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStart() {
        super.onStart();

        searchString = (String) getIntent().getSerializableExtra("SearchString");
        getSupportActionBar().setTitle("Search Results For: " + searchString);

        queryString = searchString;

        repository = new Repository(SearchActivity.this,new NewItemFactory());

        repository.fetchItems(items -> {

            itemsList = items;

            searchViewAdapter = new SearchViewAdapter(SearchActivity.this, itemsList, CategoryType.ALL);

            recyclerView.setAdapter(searchViewAdapter);
            searchViewAdapter.getFilter().filter(searchString);
            searchViewAdapter.notifyDataSetChanged();

            listRecyclerProgressBar = findViewById(R.id.listRecyclerProgressBar);
            listRecyclerProgressBar.setVisibility(View.GONE);

            selectSortButton(nameSortButton, true);
            selectOrderSortButton(increasingSortButton, true);
        });
    }

    /**
     * This method is used to change appearance for order sort buttons and notify the application this button is selected
     * @param sortBtn The button clicked
     * @param first Variable to determine if the activity is just being created or not
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void selectOrderSortButton(Button sortBtn, boolean first) {
        super.selectOrderSortButton(sortBtn, first);

        if(!first) {
            searchViewAdapter.sortByType(clicked.getText().toString().toLowerCase().trim(),order);
        } else {
            clicked = nameSortButton;
            searchViewAdapter.sortByType("",order);
        }
    }

    /**
     * This method is used to change appearance for sort buttons and notify the application this button is selected
     * @param sortBtn The button clicked
     * @param first Variable to determine if the activity is just being created or not
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void selectSortButton(Button sortBtn, boolean first) {
        super.selectSortButton(sortBtn,first);
        if(!first) {
            if (orderClicked == increasingSortButton) {
                order = "increase";
            } else if (orderClicked == decreasingSortButton) {
                order = "decrease";
            }

            searchViewAdapter.sortByType(clicked.getText().toString().toLowerCase().trim(),order);
        } else {
            clicked = nameSortButton;
            searchViewAdapter.sortByType("",order);
        }
    }

    /**
     * This method start a new SearchActivity with a search string to perform a new search
     * @param searchString The search query user submitted
     */
    public void goToSearchActivity(String searchString) {
        Intent newIntent = new Intent(SearchActivity.this, SearchActivity.class);
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

        //styling from menu.xml
        getMenuInflater().inflate(R.menu.menu, menu);

        //initialise searchView
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Type here to search");

        //listener for searchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            //the itemList would updated when user submit a query string and a new SearchActivity would start
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryString = query;
                goToSearchActivity(queryString);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This method is executed when back button is clicked
     * @return Boolean object
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * This method is executed when back button is clicked, it will go back to MainActivity
     */
    @Override
    public void onBackPressed() {
        Intent newIntent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(newIntent);
        finish();
    }
}