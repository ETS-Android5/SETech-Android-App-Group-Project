package com.project.setech.activities.listActivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.setech.R;
import com.project.setech.activities.ParentActivity;
import com.project.setech.activities.detailsActivity.DetailsActivity;
import com.project.setech.activities.listActivity.listRecyclerView.ListViewAdapter;
import com.project.setech.activities.listActivity.listRecyclerView.RecyclerItemClickListener;
import com.project.setech.activities.mainActivity.MainActivity;
import com.project.setech.model.NewItemFactory;
import com.project.setech.repository.Repository;
import com.project.setech.util.Animations.Animations;
import com.project.setech.util.Animations.IAnimations;
import com.project.setech.util.CategoryType;

/**
 * This class is used to represent a list of items of a specific category type
 */
public class ListActivity extends ParentActivity {

    private ListViewAdapter listViewAdapter;

    private IAnimations animations = new Animations(ListActivity.this);

    /**
     * This method is called when this activity is created
     * @param savedInstanceState A mapping from String keys to various Parcelable values.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        identity = "list";
        super.onCreate(savedInstanceState);

        ab.setTitle(getIntent().getSerializableExtra("CategoryType").toString());

        int columns = 2;

        if ((CategoryType) getIntent().getSerializableExtra("CategoryType") == CategoryType.Motherboard) {
            columns = 1;
        }

        recyclerView.setLayoutManager(new GridLayoutManager(this, columns));

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(ListActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Log.d("test", "onItemClick: "+itemsList.get(position).getId());

                        Intent newIntent = new Intent(ListActivity.this, DetailsActivity.class);
                        newIntent.putExtra("ItemId", itemsList.get(position).getId());
                        newIntent.putExtra("SearchBoolean", false);
                        newIntent.putExtra("QueryString", "");
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
                    sortByOpenButton.setCompoundDrawablesWithIntrinsicBounds(null,null,AppCompatResources.getDrawable(ListActivity.this,R.drawable.arrow_up),null);
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
                    sortByOpenButton.setCompoundDrawablesWithIntrinsicBounds(null,null,AppCompatResources.getDrawable(ListActivity.this,R.drawable.arrow_down),null);
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

        CategoryType categoryType = (CategoryType) getIntent().getSerializableExtra("CategoryType");
        repository = new Repository(ListActivity.this, new NewItemFactory());

        repository.fetchItems(categoryType, items -> {

            itemsList = items;

            listViewAdapter = new ListViewAdapter(ListActivity.this, itemsList, categoryType);

            recyclerView.setAdapter(listViewAdapter);
            listViewAdapter.notifyDataSetChanged();

            listRecyclerProgressBar = findViewById(R.id.listRecyclerProgressBar);
            listRecyclerProgressBar.setVisibility(View.GONE);

            selectSortButton(nameSortButton, true);
            selectOrderSortButton(increasingSortButton, true);

        });
    }

    /**
     * This method is used to change appearance for order sort buttons and notify the application this button is selected
     * @param sortBtn The button clicked
     * @param first ariable to determine if the activity is just being created or not
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void selectOrderSortButton(Button sortBtn, boolean first) {
        super.selectOrderSortButton(sortBtn, first);

        if(!first) {
            listViewAdapter.sortByType(clicked.getText().toString().toLowerCase().trim(),order);
        } else {
            clicked = nameSortButton;
            listViewAdapter.sortByType("",order);
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

            Log.d("test",clicked.getText().toString());
            listViewAdapter.sortByType(clicked.getText().toString().toLowerCase().trim(),order);
        } else {
            clicked = nameSortButton;
            listViewAdapter.sortByType("",order);
        }
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

        //close button("x") on the search view and its listener
        View closeButton = searchView.findViewById(R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                //when close button is clicked, the itemList would become full again with corresponding sort order
                listViewAdapter.setButtonAndString(order, clickedString);
                listViewAdapter.backToFull();

                //clear text field when close button is clicked
                EditText et = (EditText) findViewById(R.id.search_src_text);
                et.setText("");

                //clear query
                searchView.setQuery("", false);
                searchView.onActionViewCollapsed();
                menuItem.collapseActionView();

                TextView noItemsFoundText = (TextView) ((Activity) ListActivity.this).findViewById(R.id.noItemsFoundText);

                noItemsFoundText.setVisibility(View.GONE);
            }
        });

        //listener for searchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            //the itemList would updated dynamically when user's query string changes/deleted
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty()) {
                    //when there is changes in query string
                    listViewAdapter.setButtonAndString(order, clickedString);
                    listViewAdapter.getFilter().filter(newText);
                } else {
                    //when query string is cleared
                    listViewAdapter.setButtonAndString(order, clickedString);
                    listViewAdapter.backToFull();
                }
                Log.d("test", newText + " =========");
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
        Intent newIntent = new Intent(ListActivity.this, MainActivity.class);
        startActivity(newIntent);
        finish();
    }
}