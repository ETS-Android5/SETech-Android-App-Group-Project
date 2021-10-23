package com.project.setech.activities.listActivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

public class ListActivity extends ParentActivity {

    private ListViewAdapter listViewAdapter;

    private IAnimations animations = new Animations(ListActivity.this);

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void selectOrderSortButton(Button sortBtn, boolean first) {
        super.selectOrderSortButton(sortBtn, first);

        if(!first) {

            if (clicked == nameSortButton) {
                listViewAdapter.sortName(order);
            } else if (clicked == priceSortButton) {
                listViewAdapter.sortPrice(order);
            } else if (clicked == viewsSortButton) {
                listViewAdapter.sortView(order);
            }
        } else {
            order = "increase";
            clickedString = "name";
            clicked = nameSortButton;
            listViewAdapter.sortName(order);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void selectSortButton(Button sortBtn, boolean first) {
        super.selectSortButton(sortBtn,first);
        if(!first) {
            if (orderClicked == increasingSortButton) {
                order = "increase";
            } else if (orderClicked == decreasingSortButton) {
                order = "decrease";
            }

            if (clicked == nameSortButton) {
                listViewAdapter.sortName(order);
            } else if (clicked == priceSortButton) {
                listViewAdapter.sortPrice(order);
            } else if (clicked == viewsSortButton) {
                listViewAdapter.sortView(order);
            }
        } else {
            order = "increase";
            clickedString = "name";
            clicked = nameSortButton;
            listViewAdapter.sortName(order);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Type here to search");


        View closeButton = searchView.findViewById(R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                listViewAdapter.setButtonAndString(order, clickedString);
                listViewAdapter.backToFull();

                EditText et = (EditText) findViewById(R.id.search_src_text);
                et.setText("");

                //Clear query
                searchView.setQuery("", false);
                searchView.onActionViewCollapsed();
                menuItem.collapseActionView();

                TextView noItemsFoundText = (TextView) ((Activity) ListActivity.this).findViewById(R.id.noItemsFoundText);

                noItemsFoundText.setVisibility(View.GONE);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty()) {
                    listViewAdapter.setButtonAndString(order, clickedString);
                    listViewAdapter.getFilter().filter(newText);
                } else {
                    listViewAdapter.setButtonAndString(order, clickedString);
                    listViewAdapter.backToFull();
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent newIntent = new Intent(ListActivity.this, MainActivity.class);
        startActivity(newIntent);
        finish();
    }
}