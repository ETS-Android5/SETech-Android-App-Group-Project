package com.project.setech.activities.detailsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.setech.R;
import com.project.setech.activities.listActivity.ListActivity;
import com.project.setech.model.IItem;
import com.project.setech.model.ItemFactory;
import com.project.setech.util.CategoryType;
import com.project.setech.util.Util;

import java.util.List;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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

    private int currentlySelectedImageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

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

        // Fetch item with Id
        String itemId = (String) getIntent().getSerializableExtra("ItemId");
        DocumentReference itemDocRef = db.collection("Items").document(itemId);

        ItemFactory itemFactory = new ItemFactory();

        itemDocRef.get().addOnCompleteListener(itemTask -> {
            if (itemTask.isSuccessful()) {
                DocumentSnapshot itemDoc = itemTask.getResult();

                assert itemDoc != null;
                if (itemDoc.exists()) {
                    // figure out which item type it is and cast it through item factor
                    itemDoc.getDocumentReference("category").get().addOnCompleteListener(categoryTask -> {
                        if (categoryTask.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            parentLayout.setVisibility(View.VISIBLE);

                            DocumentSnapshot categoryDoc = categoryTask.getResult();

                            List<Integer> formattedImagePaths = Util.formatDrawableStringList((List<String>) itemDoc.get("images"), DetailsActivity.this);
                            Map<String,String> specifications = (Map<String, String>) itemDoc.get("specifications");

                            categoryType = CategoryType.valueOf(categoryDoc.getId());
                            item = itemFactory.createItem(itemDoc.getId(),itemDoc.getString("name"),formattedImagePaths,itemDoc.getString("price"),itemDoc.getString("viewCount"),specifications, categoryType);

                            itemImage.setImageResource(item.getImages().get(currentlySelectedImageIndex));
                            itemTitle.setText(item.getName());
                            itemCategory.setText(categoryType.toString());
                            itemPrice.setText("$"+item.getPrice());

                            // Populate specifications data layout
                            assert specifications != null;
                            for (String key : specifications.keySet()) {

                                LayoutInflater li = LayoutInflater.from(DetailsActivity.this);
                                View specRow = li.inflate(R.layout.specifications_row,null,false);

                                TextView specName = specRow.findViewById(R.id.specName);
                                specName.setText(Util.splitCamelCase(key));

                                TextView specValue = specRow.findViewById(R.id.specValue);
                                specValue.setText(specifications.get(key));

                                specificationsLayout.addView(specRow);
                            }

                            // Add on click handlers to Left, Right and circle buttons
                            rightButton.setOnClickListener(view -> {
                                onImageArrowClick();
                            });

                            leftButton.setOnClickListener(view -> {
                                onImageArrowClick();
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

                            // Increment view count
                            Map<String,Object> data = itemDoc.getData();
                            data.put("viewCount",Integer.toString(Integer.parseInt(itemDoc.getString("viewCount"))+1));
                            itemDocRef.set(data);
                        }
                    });
                }
                else {
                    Log.d("DetailsActivity", "No item with id "+itemId+" found!");
                }
            }
        });
    }

    private void onImageArrowClick() {
        currentlySelectedImageIndex = (currentlySelectedImageIndex+1) % 3;
        itemImage.setImageResource(item.getImages().get(currentlySelectedImageIndex));
        highlightImageCircle();
    }

    private void onImageButtonClick(int index) {
        currentlySelectedImageIndex = index;
        itemImage.setImageResource(item.getImages().get(currentlySelectedImageIndex));
        highlightImageCircle();
    }

    private void highlightImageCircle() {
        if (currentlySelectedImageIndex == 0) {
            circle1.setImageResource(R.drawable.ic_circle_filled);
            circle2.setImageResource(R.drawable.ic_circle_hollow);
            circle3.setImageResource(R.drawable.ic_circle_hollow);
        }
        else if (currentlySelectedImageIndex == 1) {
            circle1.setImageResource(R.drawable.ic_circle_hollow);
            circle2.setImageResource(R.drawable.ic_circle_filled);
            circle3.setImageResource(R.drawable.ic_circle_hollow);
        }
        else if (currentlySelectedImageIndex == 2) {
            circle1.setImageResource(R.drawable.ic_circle_hollow);
            circle2.setImageResource(R.drawable.ic_circle_hollow);
            circle3.setImageResource(R.drawable.ic_circle_filled);
        }
    }
}