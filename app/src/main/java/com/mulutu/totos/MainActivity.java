package com.mulutu.totos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mulutu.totos.models.Child;
import com.mulutu.totos.utils.ChildrenRecyclerAdapter;
import com.mulutu.totos.utils.RecyclerTouchListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private TextView message;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private ChildrenRecyclerAdapter childrenRecyclerAdapterAdapter;
    private ArrayList<Child> childArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFullScreenView();

        prepareToolbar();

        prepareRecycler();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        message = findViewById(R.id.displayMessage);

        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        ReadSingleContact();

        floatingButton();
    }

    private void prepareFarmsProjectsData(ArrayList<Child> childList) {
        childrenRecyclerAdapterAdapter.notifyDataSetChanged();
        final ArrayList childArrayList_ = childList;
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Child child = (Child) childArrayList_.get(position);
                Intent intent = new Intent(MainActivity.this, ChildDetailsActivity.class);
                intent.putExtra("child_id", child.get_id());
                startActivity(intent);
                //Toast.makeText(FarmDetailsActivity.this, "CLICK ON CARD", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private void prepareRecycler() {
        recyclerView = (RecyclerView) findViewById(R.id.children_recyclerview);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        childrenRecyclerAdapterAdapter = new ChildrenRecyclerAdapter(childArrayList);
        recyclerView.setAdapter(childrenRecyclerAdapterAdapter);
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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    private void ReadSingleContact() {
        db.collection("children")
                .whereEqualTo("parent_id", "wnhdGuMcLEhSaPvBIz4l")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot documentSnapshots = task.getResult();

                            if (!documentSnapshots.isEmpty()) {
                                for (DocumentSnapshot snapshot : documentSnapshots) {
                                    Child child = snapshot.toObject(Child.class);
                                    child.set_id(snapshot.getId());
                                    childArrayList.add(child);
                                }
                            }
                            prepareFarmsProjectsData(childArrayList);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}