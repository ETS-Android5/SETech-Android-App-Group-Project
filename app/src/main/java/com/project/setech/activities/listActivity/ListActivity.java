package com.project.setech.activities.listActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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