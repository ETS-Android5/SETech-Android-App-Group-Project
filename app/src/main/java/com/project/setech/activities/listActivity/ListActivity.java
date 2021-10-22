package com.project.setech.activities.listActivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.setech.R;
import com.project.setech.activities.Animations;
import com.project.setech.activities.detailsActivity.DetailsActivity;
import com.project.setech.activities.listActivity.listRecyclerView.ListViewAdapter;
import com.project.setech.activities.listActivity.listRecyclerView.RecyclerItemClickListener;
import com.project.setech.activities.mainActivity.MainActivity;
import com.project.setech.model.IItem;
import com.project.setech.model.NewItemFactory;
import com.project.setech.repository.IRepository;
import com.project.setech.repository.Repository;
import com.project.setech.util.CategoryType;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private IRepository repository;

    private Context context;
    private RecyclerView recyclerView;
    private ListViewAdapter listViewAdapter;

    private Button clicked;
    private Button orderClicked;
    private String order;
    private String clickedString;

    private Button sortByOpenButton;

    private Button priceSortButton;
    private Button nameSortButton;
    private Button viewsSortButton;

    private Button increasingSortButton;
    private Button decreasingSortButton;

    private LinearLayout sortByExpandedLayout;

    private ProgressBar listRecyclerProgressBar;

    private List<IItem> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initializeRecyclerView();

        //actionbar
        ActionBar actionBar = getSupportActionBar();

        //set back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        itemsList = new ArrayList<>();

        actionBar.setTitle(getIntent().getSerializableExtra("CategoryType").toString());

        sortByOpenButton = findViewById(R.id.sortByOpenButton);

        priceSortButton = findViewById(R.id.priceButton);
        nameSortButton = findViewById(R.id.nameButton);
        viewsSortButton = findViewById(R.id.viewsButton);

        increasingSortButton = findViewById(R.id.increasingButton);
        decreasingSortButton = findViewById(R.id.decreasingButton);

        sortByExpandedLayout = findViewById(R.id.sortByExpandedLayout);
        sortByExpandedLayout.setVisibility(View.GONE);

        //Animations animations = new Animations(context);

        sortByOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sortByExpandedLayout.getVisibility() == View.GONE) {
                    sortByExpandedLayout.setVisibility(View.VISIBLE);
                    sortByOpenButton.setCompoundDrawablesWithIntrinsicBounds(null,null,AppCompatResources.getDrawable(ListActivity.this,R.drawable.arrow_up),null);
                    //animations.setFadeAnimation(sortByExpandedLayout);
                }
                else {
                    //animations.slideUpAnim(sortByExpandedLayout);
                    sortByOpenButton.setCompoundDrawablesWithIntrinsicBounds(null,null,AppCompatResources.getDrawable(ListActivity.this,R.drawable.arrow_down),null);
                }
            }
        });

        priceSortButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                clickedString = "price";
                selectSortButton(priceSortButton, false);
            }
        });

        nameSortButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                clickedString = "name";
                selectSortButton(nameSortButton, false);
            }
        });

        viewsSortButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                clickedString = "view";
                selectSortButton(viewsSortButton, false);
            }
        });

        increasingSortButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                order = "increase";
                selectOrderSortButton(increasingSortButton, false);
            }
        });

        decreasingSortButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                order = "decrease";
                selectOrderSortButton(decreasingSortButton, false);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void selectSortButton(Button sortBtn, boolean first) {
        priceSortButton.setBackground(AppCompatResources.getDrawable(this,R.drawable.sort_button_border_not_highlighted));
        nameSortButton.setBackground(AppCompatResources.getDrawable(this,R.drawable.sort_button_border_not_highlighted));
        viewsSortButton.setBackground(AppCompatResources.getDrawable(this,R.drawable.sort_button_border_not_highlighted));

        priceSortButton.setTextColor(AppCompatResources.getColorStateList(this,R.color.grey));
        nameSortButton.setTextColor(AppCompatResources.getColorStateList(this,R.color.grey));
        viewsSortButton.setTextColor(AppCompatResources.getColorStateList(this,R.color.grey));

        sortBtn.setBackground(AppCompatResources.getDrawable(this,R.drawable.sort_button_border_highlighted));
        sortBtn.setTextColor(AppCompatResources.getColorStateList(this,R.color.black));

        clicked = sortBtn;

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
            clicked = nameSortButton;
            clickedString = "name";
            listViewAdapter.sortName(order);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void selectOrderSortButton(Button sortBtn, boolean first) {
        increasingSortButton.setBackground(AppCompatResources.getDrawable(this,R.drawable.sort_button_border_not_highlighted));
        decreasingSortButton.setBackground(AppCompatResources.getDrawable(this,R.drawable.sort_button_border_not_highlighted));

        increasingSortButton.setTextColor(AppCompatResources.getColorStateList(this,R.color.grey));
        decreasingSortButton.setTextColor(AppCompatResources.getColorStateList(this,R.color.grey));

        sortBtn.setBackground(AppCompatResources.getDrawable(this,R.drawable.sort_button_border_highlighted));
        sortBtn.setTextColor(AppCompatResources.getColorStateList(this,R.color.black));

        orderClicked = sortBtn;

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
            clicked = nameSortButton;
            clickedString = "name";
            listViewAdapter.sortName(order);
        }
    }

    private void initializeRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

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
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStart() {
        super.onStart();

        if (!itemsList.isEmpty()) {
            return;
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);


        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
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

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty()) {
                    listViewAdapter.setButtonAndString(order, clickedString);
                    listViewAdapter.getFilter().filter(newText);
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