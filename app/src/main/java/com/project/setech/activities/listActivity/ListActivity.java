package com.project.setech.activities.listActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.setech.R;
import com.project.setech.activities.listActivity.listRecyclerView.ListViewAdapter;
import com.project.setech.model.IItem;
import com.project.setech.model.ItemFactory;
import com.project.setech.model.itemType.CPU;
import com.project.setech.model.itemType.GPU;
import com.project.setech.model.itemType.Motherboard;
import com.project.setech.util.CategoryType;
import com.project.setech.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private ListViewAdapter listViewAdapter;

    private Button sortByOpenButton;

    private Button priceSortButton;
    private Button nameSortButton;
    private Button viewsSortButton;

    private Button increasingSortButton;
    private Button decreasingSortButton;

    private LinearLayout sortByExpandedLayout;

    private List<IItem> itemsList;

    private CollectionReference collectionReference = db.collection("Items");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        itemsList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        int columns = 2;

        if ((CategoryType) getIntent().getSerializableExtra("CategoryType") == CategoryType.Motherboard) {
            columns = 1;
        }

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

        sortByOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sortByExpandedLayout.getVisibility() == View.GONE) {
                    sortByExpandedLayout.setVisibility(View.VISIBLE);
                    sortByOpenButton.setCompoundDrawablesWithIntrinsicBounds(null,null,AppCompatResources.getDrawable(ListActivity.this,R.drawable.arrow_up),null);
                }
                else {
                    sortByExpandedLayout.setVisibility(View.GONE);
                    sortByOpenButton.setCompoundDrawablesWithIntrinsicBounds(null,null,AppCompatResources.getDrawable(ListActivity.this,R.drawable.arrow_down),null);
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
        increasingSortButton.setBackground(AppCompatResources.getDrawable(this,R.drawable.sort_button_border_not_highlighted));
        decreasingSortButton.setBackground(AppCompatResources.getDrawable(this,R.drawable.sort_button_border_not_highlighted));

        increasingSortButton.setTextColor(AppCompatResources.getColorStateList(this,R.color.grey));
        decreasingSortButton.setTextColor(AppCompatResources.getColorStateList(this,R.color.grey));

        sortBtn.setBackground(AppCompatResources.getDrawable(this,R.drawable.sort_button_border_highlighted));
        sortBtn.setTextColor(AppCompatResources.getColorStateList(this,R.color.black));
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!itemsList.isEmpty()) {
            return;
        }

        ItemFactory itemFactory = new ItemFactory();
        CategoryType type = (CategoryType) getIntent().getSerializableExtra("CategoryType");

        collectionReference.whereEqualTo("category", db.collection("Categories").document(type.toString())).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot items : queryDocumentSnapshots) {
                    // Turn object into the type we need

                    List<Integer> formattedImagePaths = Util.formatDrawableStringList((List<String>) items.get("images"),ListActivity.this);
                    Map<String,String> specifications = (Map<String, String>) items.get("specifications");

                    IItem newItem = itemFactory.createItem(items.getString("name"),formattedImagePaths,items.getString("price"),specifications,type);

                    itemsList.add(newItem);

                    // Create recycler view
                    listViewAdapter = new ListViewAdapter(ListActivity.this, itemsList, type);
                    recyclerView.setAdapter(listViewAdapter);
                    listViewAdapter.notifyDataSetChanged();
                }
            } else {
                // No objects were found
                Log.d("Items", "empty");
            }
        });
    }
}