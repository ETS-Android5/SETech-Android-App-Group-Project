package com.project.setech.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.project.setech.R;
import com.project.setech.model.IItem;
import com.project.setech.repository.IRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is extended by ListActivity and SearchActivity due to the similar layout and functionality they have
 */
public class ParentActivity extends AppCompatActivity {

    public IRepository repository;

    public RecyclerView recyclerView;

    public Button clicked;
    public Button orderClicked;
    public String order;

    public String queryString;

    public Button sortByOpenButton;

    public Button priceSortButton;
    public Button nameSortButton;
    public Button viewsSortButton;

    public Button increasingSortButton;
    public Button decreasingSortButton;

    public LinearLayout sortByExpandedLayout;

    public ProgressBar listRecyclerProgressBar;

    public List<IItem> itemsList;
    public String searchString;
    public String clickedString;
    public ActionBar ab;
    public SearchView searchView;
    public String identity;

    /**
     * This method is called when this activity is created
     * @param savedInstanceState A mapping from String keys to various Parcelable values.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //actionbar
        ab = getSupportActionBar();

        //set back button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        //initialise list of item
        itemsList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        sortByOpenButton = findViewById(R.id.sortByOpenButton);

        priceSortButton = findViewById(R.id.priceButton);
        nameSortButton = findViewById(R.id.nameButton);
        viewsSortButton = findViewById(R.id.viewsButton);

        increasingSortButton = findViewById(R.id.increasingButton);
        decreasingSortButton = findViewById(R.id.decreasingButton);

        sortByExpandedLayout = findViewById(R.id.sortByExpandedLayout);
        sortByExpandedLayout.setVisibility(View.GONE);

        //Listener for price sort button
        priceSortButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                clickedString = "price";
                selectSortButton(priceSortButton, false);
            }
        });

        //Listener for name sort button
        nameSortButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                clickedString = "name";
                selectSortButton(nameSortButton, false);
            }
        });

        //Listener for view sort button
        viewsSortButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                clickedString = "view";
                selectSortButton(viewsSortButton, false);
            }
        });

        //Listener for increasing sort button
        increasingSortButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                order = "increase";
                selectOrderSortButton(increasingSortButton, false);
            }
        });

        //Listener for decreasing sort button
        decreasingSortButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                order = "decrease";
                selectOrderSortButton(decreasingSortButton, false);
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

        if (!itemsList.isEmpty()) {
            return;
        }
    }

    /**
     * This method is used to change appearance for order sort buttons and notify the application this button is selected
     * @param sortBtn The button clicked
     * @param first ariable to determine if the activity is just being created or not
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void selectOrderSortButton(Button sortBtn, boolean first) {
        increasingSortButton.setBackground(AppCompatResources.getDrawable(this, R.drawable.sort_button_border_not_highlighted));
        decreasingSortButton.setBackground(AppCompatResources.getDrawable(this, R.drawable.sort_button_border_not_highlighted));

        increasingSortButton.setTextColor(AppCompatResources.getColorStateList(this, R.color.grey));
        decreasingSortButton.setTextColor(AppCompatResources.getColorStateList(this, R.color.grey));

        sortBtn.setBackground(AppCompatResources.getDrawable(this, R.drawable.sort_button_border_highlighted));
        sortBtn.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));

        orderClicked = sortBtn;
    }

    /**
     * This method is used to change appearance for sort buttons and notify the application this button is selected
     * @param sortBtn The button clicked
     * @param first Variable to determine if the activity is just being created or not
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void selectSortButton(Button sortBtn, boolean first) {
        priceSortButton.setBackground(AppCompatResources.getDrawable(this,R.drawable.sort_button_border_not_highlighted));
        nameSortButton.setBackground(AppCompatResources.getDrawable(this,R.drawable.sort_button_border_not_highlighted));
        viewsSortButton.setBackground(AppCompatResources.getDrawable(this,R.drawable.sort_button_border_not_highlighted));

        priceSortButton.setTextColor(AppCompatResources.getColorStateList(this,R.color.grey));
        nameSortButton.setTextColor(AppCompatResources.getColorStateList(this,R.color.grey));
        viewsSortButton.setTextColor(AppCompatResources.getColorStateList(this,R.color.grey));

        sortBtn.setBackground(AppCompatResources.getDrawable(this,R.drawable.sort_button_border_highlighted));
        sortBtn.setTextColor(AppCompatResources.getColorStateList(this,R.color.black));

        clicked = sortBtn;
    }
}