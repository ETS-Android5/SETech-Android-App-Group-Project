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
import com.project.setech.model.itemType.CPU;

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
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }

    @Override
    protected void onStart() {
        super.onStart();

        collectionReference.whereEqualTo("category", db.collection("Categories").document("CPU")).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (QueryDocumentSnapshot items : queryDocumentSnapshots) {
                        // Turn object into the type we need
                        Log.d("Items", items.get("name").toString());

                        Map<String, String> specifications = (Map<String, String>) items.get("specifications");

                        assert specifications != null;

                        CPU newCPU = new CPU(
                                items.getString("name"),
                                (List<String>) items.get("images"),
                                Float.parseFloat(items.getString("price")),
                                specifications.get("cpuFamily"),
                                specifications.get("numCores"),
                                specifications.get("cpuSocket"),
                                specifications.get("clockSpeed"),
                                specifications.get("boostClockSpeed")
                        );

                        itemsList.add(newCPU);

                        Log.d("Items", newCPU.toString());

                        // Create recycler view
                    }
                } else {
                    Log.d("Items", "empty");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}