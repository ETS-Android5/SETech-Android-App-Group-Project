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
import com.project.setech.model.itemType.GPU;
import com.project.setech.model.itemType.Motherboard;
import com.project.setech.util.CategoryType;

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
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    protected void onStart() {
        super.onStart();

        collectionReference.whereEqualTo("category", db.collection("Categories").document("GPU")).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (QueryDocumentSnapshot items : queryDocumentSnapshots) {
                        // Turn object into the type we need
                        // Testing as CPU for now
                        Log.d("Items", items.get("name").toString());

                        Map<String, String> specifications = (Map<String, String>) items.get("specifications");

                        assert specifications != null;
                        List<String> imageReferences = (List<String>) items.get("images");
                        List<Integer> formatedReferences = new ArrayList<>();

                        for (String i : imageReferences) {
                            formatedReferences.add(getResources().getIdentifier(i,"drawable",getPackageName()));
                        }

                        GPU newCPU = new GPU(
                                items.getString("name"),
                                formatedReferences,
                                items.getString("price"),
                                specifications.get("productModel"),
                                specifications.get("memSize"),
                                specifications.get("baseClockSpeed"),
                                specifications.get("boostClockSpeed"),
                                specifications.get("maxDisplays"),
                                specifications.get("length"),
                                specifications.get("dispPorts"),
                                specifications.get("hdmiPorts")
                        );

                        itemsList.add(newCPU);

                        // Create recycler view
                        listViewAdapter =  new ListViewAdapter(ListActivity.this,itemsList, CategoryType.GPU);
                        recyclerView.setAdapter(listViewAdapter);
                        listViewAdapter.notifyDataSetChanged();
                    }
                } else {
                    // No objects were found
                    Log.d("Items", "empty");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

//        collectionReference.whereEqualTo("category", db.collection("Categories").document("Motherboard")).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                if (!queryDocumentSnapshots.isEmpty()) {
//                    for (QueryDocumentSnapshot items : queryDocumentSnapshots) {
//                        // Turn object into the type we need
//                        // Testing as CPU for now
//                        Log.d("Items", items.get("name").toString());
//
//                        Map<String, String> specifications = (Map<String, String>) items.get("specifications");
//
//                        assert specifications != null;
//                        List<String> imageReferences = (List<String>) items.get("images");
//                        List<Integer> formatedReferences = new ArrayList<>();
//
//                        for (String i : imageReferences) {
//                            formatedReferences.add(getResources().getIdentifier(i,"drawable",getPackageName()));
//                        }
//
//                        Motherboard newCPU = new Motherboard(
//                                items.getString("name"),
//                                formatedReferences,
//                                items.getString("price"),
//                                specifications.get("mbSocket"),
//                                specifications.get("wifi"),
//                                specifications.get("chipset"),
//                                specifications.get("formFactor"),
//                                specifications.get("multiGpuSupport"),
//                                specifications.get("memType"),
//                                specifications.get("pciSlots"),
//                                specifications.get("fourPinRgbHeader")
//                        );
//
//                        itemsList.add(newCPU);
//
//                        // Create recycler view
//                        listViewAdapter =  new ListViewAdapter(ListActivity.this,itemsList, CategoryType.Motherboard);
//                        recyclerView.setAdapter(listViewAdapter);
//                        listViewAdapter.notifyDataSetChanged();
//                    }
//                } else {
//                    // No objects were found
//                    Log.d("Items", "empty");
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });

//        collectionReference.whereEqualTo("category", db.collection("Categories").document("CPU")).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                if (!queryDocumentSnapshots.isEmpty()) {
//                    for (QueryDocumentSnapshot items : queryDocumentSnapshots) {
//                        // Turn object into the type we need
//                        // Testing as CPU for now
//                        Log.d("Items", items.get("name").toString());
//
//                        Map<String, String> specifications = (Map<String, String>) items.get("specifications");
//
//                        assert specifications != null;
//                        List<String> imageReferences = (List<String>) items.get("images");
//                        List<Integer> formatedReferences = new ArrayList<>();
//
//                        for (String i : imageReferences) {
//                            formatedReferences.add(getResources().getIdentifier(i,"drawable",getPackageName()));
//                        }
//
//                        CPU newCPU = new CPU(
//                                items.getString("name"),
//                                formatedReferences,
//                                items.getString("price"),
//                                specifications.get("cpuFamily"),
//                                specifications.get("numCores"),
//                                specifications.get("cpuSocket"),
//                                specifications.get("clockSpeed"),
//                                specifications.get("boostClockSpeed")
//                        );
//
//                        itemsList.add(newCPU);
//
//                        // Create recycler view
//                        listViewAdapter =  new ListViewAdapter(ListActivity.this,itemsList, CategoryType.CPU);
//                        recyclerView.setAdapter(listViewAdapter);
//                        listViewAdapter.notifyDataSetChanged();
//                    }
//                } else {
//                    // No objects were found
//                    Log.d("Items", "empty");
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
    }
}