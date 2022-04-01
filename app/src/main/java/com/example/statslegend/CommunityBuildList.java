package com.example.statslegend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommunityBuildList extends AppCompatActivity {
    DrawerLayout drawerLayout;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    List<Model> modelList = new ArrayList<>();
    RecyclerView mRecyclerView;
    //layout manager for recyclerview
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton mAddBtn;
    CustomAdapter adapter;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_build_list);

        //initialize Firebase elements
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        //initialize views
        drawerLayout = findViewById(R.id.drawer_layout);
        mRecyclerView = findViewById(R.id.recycler_view);
        mAddBtn = findViewById(R.id.addBtn);

        //set recycler view properties
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //initialize progress dialog
        pd = new ProgressDialog(this);

        //show data in recyclerView
        showData();

        mAddBtn.setOnClickListener(view -> {
            startActivity(new Intent(CommunityBuildList.this, CommunityBuilds.class));
            finish();
        });

    }

    private void showData() {
        //set title of progress dialog
        pd.setTitle("Loading Data...");
        //show progress dialog
        pd.show();

        fStore.collection("users").document(userId).collection("builds")
                .get()
                .addOnCompleteListener(task -> {
                    modelList.clear();
                    //called when data is retrieved
                    pd.dismiss();
                    //show data
                    for (DocumentSnapshot doc : task.getResult()) {
                        Model model = new Model(doc.getString("id"),
                                doc.getString("title"),
                                doc.getString("description"));
                        modelList.add(model);
                    }
                    //adapter
                    adapter = new CustomAdapter(CommunityBuildList.this, modelList);
                    //set adapter to recyclerview
                    mRecyclerView.setAdapter(adapter);
                })
                .addOnFailureListener(e -> {
                    //called when there is any error while retrieving
                    pd.dismiss();
                    Toast.makeText(CommunityBuildList.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    public void deleteData(int index){
        //set title of progress dialog
        pd.setTitle("Deleting Data...");
        //show progress dialog
        pd.show();

        fStore.collection("users").document(userId).collection("builds")
                .document(modelList.get(index).getId())
                .delete()
                .addOnCompleteListener(task -> {
                    //called when deleted successfully
                    Toast.makeText(CommunityBuildList.this, "Deleted...", Toast.LENGTH_SHORT).show();
                    //update data
                    showData();
                })
                .addOnFailureListener(e -> {
                    //called when there is any error
                    pd.dismiss();
                    Toast.makeText(CommunityBuildList.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    public void ClickMenu(View view) {
        //Open drawer
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        //Close drawer
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view) {
        //Redirect to home activity
        MainActivity.redirectActivity(this, MainActivity.class);
    }

    public void ClickPopularBuilds(View view) {
        //Redirect to Popular Builds activity
        MainActivity.redirectActivity(this, PopularBuilds.class);
    }

    public void ClickCommunityBuilds(View view) {
        //Recreate the Community Builds activity
        MainActivity.redirectActivity(this, CommunityBuilds.class);
    }

    public void ClickCurrentCharacters(View view) {
        //Redirect to Current Characters activity
        MainActivity.redirectActivity(this, CurrentCharacters.class);
    }

    public void ClickItemBuilder(View view) {
        //Redirect to Item Builder activity
        MainActivity.redirectActivity(this, ItemBuilder.class);
    }

    public void ClickLogout(View view) {
        //Logout and close app
        //Signs the user out of account
        FirebaseAuth.getInstance().signOut();
        //Returns to Login screen
        Toast.makeText(CommunityBuildList.this, "Logout Successful.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Close drawer
        MainActivity.closeDrawer(drawerLayout);
    }
}