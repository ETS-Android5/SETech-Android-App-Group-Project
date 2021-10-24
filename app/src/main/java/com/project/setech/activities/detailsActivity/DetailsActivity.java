package com.project.setech.activities.detailsActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.firestore.Query;
import com.project.setech.R;
import com.project.setech.activities.listActivity.ListActivity;
import com.project.setech.activities.listActivity.listRecyclerView.RecyclerItemClickListener;
import com.project.setech.activities.mainActivity.MainActivity;
import com.project.setech.activities.mainActivity.mainRecyclerView.MainListViewAdapter;
import com.project.setech.activities.searchActivity.SearchActivity;
import com.project.setech.model.IItem;
import com.project.setech.model.NewItemFactory;
import com.project.setech.repository.IRepository;
import com.project.setech.repository.Repository;
import com.project.setech.util.Animations.Animations;
import com.project.setech.util.Animations.IAnimations;
import com.project.setech.util.CategoryType;
import com.project.setech.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to represent the details and images of an item from varying categories.
 */
public class DetailsActivity extends AppCompatActivity {

    private IRepository repository = new Repository(DetailsActivity.this,new NewItemFactory());

    private boolean searchBoolean = false;
    private boolean fromMainScreen = false;
    private String queryString;
    private Context context;
    private IItem item;
    private CategoryType categoryType;

    private LinearLayout parentLayout;

    private ImageButton leftButton;
    private ImageButton rightButton;

    private ImageView itemImage;

    private ImageButton circle1;
    private ImageButton circle2;
    private ImageButton circle3;

    private TextView itemTitle;
    private TextView itemCategory;

    private LinearLayout specificationsLayout;

    private TextView itemPrice;

    private ProgressBar progressBar;

    private RecyclerView recyclerView;
    private MainListViewAdapter mainViewAdapter;
    private List<IItem> topItemsList;

    private int currentlySelectedImageIndex = 0;

    IAnimations animations = new Animations(DetailsActivity.this);

    /**
     * This method is called when this activity is created
     * @param savedInstanceState A mapping from String keys to various Parcelable values.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Actionbar
        ActionBar actionBar = getSupportActionBar();

        // Set back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        // Setting fields to views
        parentLayout = findViewById(R.id.parentLayout);
        parentLayout.setVisibility(View.GONE);

        leftButton = findViewById(R.id.leftDetailsButton);
        rightButton = findViewById(R.id.rightDetailsButton);

        itemImage = findViewById(R.id.detailsItemImage);

        circle1 = findViewById(R.id.imageCircle1);
        circle2 = findViewById(R.id.imageCircle2);
        circle3 = findViewById(R.id.imageCircle3);

        itemTitle = findViewById(R.id.detailsItemTitle);
        itemCategory = findViewById(R.id.detailsItemCategory);

        specificationsLayout = findViewById(R.id.specificationsLayout);

        itemPrice = findViewById(R.id.detailsItemPrice);

        progressBar = findViewById(R.id.detailsProgressBar);

        topItemsList = new ArrayList<>();
        recyclerView = findViewById(R.id.detailsRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // Fetch item with Id
        String itemId = (String) getIntent().getSerializableExtra("ItemId");

        searchBoolean = (boolean) getIntent().getSerializableExtra("SearchBoolean");
        queryString = (String) getIntent().getSerializableExtra("QueryString");

        if (getIntent().getExtras().containsKey("FromMainScreen")) {
            fromMainScreen = (boolean) getIntent().getExtras().getBoolean("FromMainScreen");
        }

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(DetailsActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent newIntent = new Intent(DetailsActivity.this, DetailsActivity.class);
                        newIntent.putExtra("ItemId", topItemsList.get(position).getId());
                        newIntent.putExtra("SearchBoolean", searchBoolean);
                        newIntent.putExtra("QueryString", queryString);
                        newIntent.putExtra("FromMainScreen", fromMainScreen);
                        startActivity(newIntent);
                        finish();
                    }
                })
        );
        // Get specific item specifications nad images
        repository.fetchItem(itemId, (item, categoryType, specifications) -> {
            this.item = item;
            this.categoryType = categoryType;
            // Increment viewCount by 1
            repository.updateItemValue(itemId,"viewCount",Integer.toString(Integer.parseInt(item.getViewCount()) + 1));

            progressBar.setVisibility(View.GONE);
            parentLayout.setVisibility(View.VISIBLE);

            itemImage.setImageResource(item.getImages().get(currentlySelectedImageIndex));
            itemTitle.setText(item.getName());
            itemCategory.setText(categoryType.toString());
            itemPrice.setText("$"+item.getPrice());
            getSupportActionBar().setTitle(categoryType.toString());

            // Populate specifications data layout
            assert specifications != null;
            for (String key : specifications.keySet()) {

                LayoutInflater li = LayoutInflater.from(DetailsActivity.this);
                View specRow = li.inflate(R.layout.specifications_row, null, false);

                TextView specName = specRow.findViewById(R.id.specName);
                specName.setText(Util.splitCamelCase(key));

                TextView specValue = specRow.findViewById(R.id.specValue);
                specValue.setText(specifications.get(key));

                specificationsLayout.addView(specRow);
                animations.setFadeAnimation(specRow);
            }

            populateTopItemPicks(categoryType);

            // Add on click handlers to Left, Right and circle buttons
            rightButton.setOnClickListener(view -> {
                onImageRightArrowClick();
            });

            leftButton.setOnClickListener(view -> {
                onImageLeftArrowClick();
            });

            circle1.setOnClickListener(view -> {
                onImageButtonClick(0);
            });

            circle2.setOnClickListener(view -> {
                onImageButtonClick(1);
            });

            circle3.setOnClickListener(view -> {
                onImageButtonClick(2);
            });
        });
    }

    /**
     * This method retrieves the top viewed items based on viewCount and displays on the bottom of
     * the screen.
     * @param type Category type
     */
    private void populateTopItemPicks(CategoryType type) {
        repository.fetchItems(type, "viewCount", Query.Direction.DESCENDING, 15, items -> {
            topItemsList = items;
            // Create recycler view
            mainViewAdapter = new MainListViewAdapter(DetailsActivity.this, topItemsList, type);
            recyclerView.setAdapter(mainViewAdapter);
            mainViewAdapter.notifyDataSetChanged();
        });
    }

    /**
     * This method switches the displayed image to the image that is after the current image.
     */
    private void onImageRightArrowClick() {
        currentlySelectedImageIndex = (currentlySelectedImageIndex + 1) % 3;
        itemImage.setImageResource(item.getImages().get(currentlySelectedImageIndex));
        highlightImageCircle();

        animations.setFadeAnimation(itemImage);
    }

    /**
     * This method switches the displayed image to the image that is before the current image.
     */
    private void onImageLeftArrowClick() {
        currentlySelectedImageIndex = (currentlySelectedImageIndex - 1) % 3;
        if (currentlySelectedImageIndex < 0) {
            currentlySelectedImageIndex = 2;
        }
        itemImage.setImageResource(item.getImages().get(currentlySelectedImageIndex));
        highlightImageCircle();

        animations.setFadeAnimation(itemImage);
    }

    /**
     * This method allows for animations to be set when the circle button is clicked.
     * @param index the index number of the selected image.
     */
    private void onImageButtonClick(int index) {
        currentlySelectedImageIndex = index;
        itemImage.setImageResource(item.getImages().get(currentlySelectedImageIndex));
        highlightImageCircle();

        animations.setFadeAnimation(itemImage);
    }

    /**
     * This method highlights the circle button that is clicked by the user.
     */
    private void highlightImageCircle() {
        if (currentlySelectedImageIndex == 0) {
            circle1.setImageResource(R.drawable.ic_circle_filled);
            circle2.setImageResource(R.drawable.ic_circle_hollow);
            circle3.setImageResource(R.drawable.ic_circle_hollow);
        } else if (currentlySelectedImageIndex == 1) {
            circle1.setImageResource(R.drawable.ic_circle_hollow);
            circle2.setImageResource(R.drawable.ic_circle_filled);
            circle3.setImageResource(R.drawable.ic_circle_hollow);
        } else if (currentlySelectedImageIndex == 2) {
            circle1.setImageResource(R.drawable.ic_circle_hollow);
            circle2.setImageResource(R.drawable.ic_circle_hollow);
            circle3.setImageResource(R.drawable.ic_circle_filled);
        }
    }

    /**
     * This method is executed when the back button is clicked.
     * @return A boolean object
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * This method returns the user to the activity that they were previously on before the current
     * activity.
     */
    @Override
    public void onBackPressed() {

        Intent newIntent;
        if (fromMainScreen) {
            newIntent = new Intent(DetailsActivity.this, MainActivity.class);
        } else if (searchBoolean) {
            newIntent = new Intent(DetailsActivity.this, SearchActivity.class);
            newIntent.putExtra("SearchString", queryString);
        } else {
            newIntent = new Intent(DetailsActivity.this, ListActivity.class);
            newIntent.putExtra("CategoryType", categoryType);
        }
        startActivity(newIntent);
        finish();
    }
}