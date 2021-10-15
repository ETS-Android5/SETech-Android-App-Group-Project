package com.project.setech.activities.detailsActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.setech.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

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
                            DocumentSnapshot categoryDoc = categoryTask.getResult();

                            List<Integer> formattedImagePaths = Util.formatDrawableStringList((List<String>) itemDoc.get("images"), DetailsActivity.this);
                            Map<String,String> specifications = (Map<String, String>) itemDoc.get("specifications");

                            categoryType = CategoryType.valueOf(categoryDoc.getId());
                            item = itemFactory.createItem(itemDoc.getId(),itemDoc.getString("name"),formattedImagePaths,itemDoc.getString("price"),itemDoc.getString("viewCount"),specifications, categoryType);

                            itemImage.setImageResource(item.getImages().get(0));
                            itemTitle.setText(item.getName());
                            itemCategory.setText(categoryType.toString());
                            itemPrice.setText("$"+item.getPrice());

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
}