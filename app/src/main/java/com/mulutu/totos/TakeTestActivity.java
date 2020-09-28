package com.mulutu.totos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mulutu.totos.models.Category;
import com.mulutu.totos.models.Child;
import com.mulutu.totos.models.Clinic;
import com.mulutu.totos.utils.ChildrenRecyclerAdapter;
import com.mulutu.totos.utils.ClinicsRecyclerAdapter;
import com.mulutu.totos.utils.RecyclerTouchListener;
import com.mulutu.totos.utils.TestCategoriesRecyclerAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class TakeTestActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private String user_id;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private RecyclerView categoriesRecyclerView;
    private TestCategoriesRecyclerAdapter categoriesRecyclerAdapter;
    private ArrayList<Category> categoriesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFullScreenView();

        prepareToolbar();

        prepareVariables();

        prepareCategoriesRecycler();

        getCategories();

        floatingButton();
    }

    private void prepareVariables() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(TakeTestActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        user_id = currentUser.getUid();

        //Log.d(TAG, "currentUser >>>>>>> : " + currentUser.getUid() + "   TuAANhCUIsNQGpFJNQdFVWkRJyB3");
    }

    private void prepareCategoriesData(ArrayList<Category> categoryArrayList) {
        categoriesRecyclerAdapter.notifyDataSetChanged();
        final ArrayList categoryArrayList_ = categoryArrayList;
        categoriesRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, categoriesRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Category category = (Category) categoryArrayList_.get(position);
                Intent intent = new Intent(TakeTestActivity.this, TestActivity.class);
                intent.putExtra("category_id", category.getCategory_id());
                startActivity(intent);
                //Toast.makeText(FarmDetailsActivity.this, "CLICK ON CARD", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private void getCategories() {
        db.collection("asq_categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot documentSnapshots = task.getResult();

                            if (!documentSnapshots.isEmpty()) {
                                for (DocumentSnapshot snapshot : documentSnapshots) {
                                    Category category = snapshot.toObject(Category.class);
                                    category.setCategory_id(snapshot.getId());
                                    categoriesArrayList.add(category);
                                }
                            }
                            prepareCategoriesData(categoriesArrayList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void prepareCategoriesRecycler() {
        categoriesRecyclerView = (RecyclerView) findViewById(R.id.categories_recyclerview);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        categoriesRecyclerView.setLayoutManager(mLayoutManager);
        categoriesRecyclerView.setHasFixedSize(true);
        categoriesRecyclerAdapter = new TestCategoriesRecyclerAdapter(categoriesArrayList);
        categoriesRecyclerView.setAdapter(categoriesRecyclerAdapter);
    }

    private void floatingButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void prepareToolbar() {
        setContentView(R.layout.activity_take_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    protected void setFullScreenView() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}