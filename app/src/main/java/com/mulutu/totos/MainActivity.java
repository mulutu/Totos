package com.mulutu.totos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mulutu.totos.models.Child;
import com.mulutu.totos.models.Clinic;
import com.mulutu.totos.utils.ChildrenRecyclerAdapter;
import com.mulutu.totos.utils.ClinicsRecyclerAdapter;
import com.mulutu.totos.utils.RecyclerTouchListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private String user_id;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private RecyclerView childrenRecyclerView, clinicRecyclerView;
    private ChildrenRecyclerAdapter childrenRecyclerAdapterAdapter;
    private ClinicsRecyclerAdapter clinicRecyclerAdapterAdapter;
    private ArrayList<Child> childArrayList = new ArrayList<>();
    private ArrayList<Clinic> clinicArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFullScreenView();

        prepareToolbar();

        prepareVariables();

        prepareChildrenRecycler();

        prepareClinicsRecycler();

        getChildren();

        getClinics();

        floatingButton();
    }

    private void prepareVariables() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        user_id = currentUser.getUid();

        //Log.d(TAG, "currentUser >>>>>>> : " + currentUser.getUid() + "   TuAANhCUIsNQGpFJNQdFVWkRJyB3");
    }

    private void prepareClinicData(ArrayList<Clinic> clinicList) {
        clinicRecyclerAdapterAdapter.notifyDataSetChanged();
        final ArrayList clinicArrayList_ = clinicList;
        clinicRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, clinicRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                /*Child child = (Child) childArrayList_.get(position);
                Intent intent = new Intent(MainActivity.this, ChildDetailsActivity.class);
                intent.putExtra("child_id", child.get_id());
                startActivity(intent);*/
                //Toast.makeText(FarmDetailsActivity.this, "CLICK ON CARD", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private void prepareChildrenData(ArrayList<Child> childList) {
        childrenRecyclerAdapterAdapter.notifyDataSetChanged();
        final ArrayList childArrayList_ = childList;
        childrenRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, childrenRecyclerView, new RecyclerTouchListener.ClickListener() {
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

    private void prepareChildrenRecycler() {
        childrenRecyclerView = (RecyclerView) findViewById(R.id.children_recyclerview);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        childrenRecyclerView.setLayoutManager(mLayoutManager);
        childrenRecyclerView.setHasFixedSize(true);
        childrenRecyclerAdapterAdapter = new ChildrenRecyclerAdapter(childArrayList);
        childrenRecyclerView.setAdapter(childrenRecyclerAdapterAdapter);
    }

    private void prepareClinicsRecycler() {
        clinicRecyclerView = (RecyclerView) findViewById(R.id.clinic_recyclerview);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        clinicRecyclerView.setLayoutManager(mLayoutManager);
        clinicRecyclerView.setHasFixedSize(true);
        clinicRecyclerAdapterAdapter = new ClinicsRecyclerAdapter(clinicArrayList);
        clinicRecyclerView.setAdapter(clinicRecyclerAdapterAdapter);
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

    private void getClinics() {
        db.collection("clinics")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot documentSnapshots = task.getResult();

                            if (!documentSnapshots.isEmpty()) {
                                for (DocumentSnapshot snapshot : documentSnapshots) {
                                    Clinic clinic = snapshot.toObject(Clinic.class);
                                    clinic.setId(snapshot.getId());
                                    clinicArrayList.add(clinic);
                                }
                            }
                            prepareClinicData(clinicArrayList);
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

    private void getChildren() {
        db.collection("children")
                .whereEqualTo("parent_id", user_id)
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
                            prepareChildrenData(childArrayList);
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