package com.example.statslegend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CommunityBuilds extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextView tftName;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    //Views
    EditText mTitleEt, mDescriptionEt;
    Button mSaveBtn, mListBtn;

    //progress dialog
    ProgressDialog pd;

    String pId, pTitle, pDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_builds);

        //action bar and title
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Add Data");

        //Initialize views
        drawerLayout = findViewById(R.id.drawer_layout);
        tftName = findViewById(R.id.tftName);
        mTitleEt = findViewById(R.id.titleEt);
        mDescriptionEt = findViewById(R.id.descriptionEt);
        mSaveBtn = findViewById(R.id.saveBtn);
        mListBtn = findViewById(R.id.listBtn);

        /////////////////////////////////////////////////////
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //Update data
            actionBar.setTitle("Update Data");
            mSaveBtn.setText("Update");
            //get data
            pId = bundle.getString("pId");
            pTitle = bundle.getString("pTitle");
            pDescription = bundle.getString("pDescription");
            //set data
            mTitleEt.setText(pTitle);
            mDescriptionEt.setText(pDescription);

        } else {
            //New data
            actionBar.setTitle("Add Data");
            mSaveBtn.setText("Save");
        }

        //progress dialog
        pd = new ProgressDialog(this);

        //Initialize Firebase elements
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        //Display user name on drawer layout
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, (value, error) -> {
            //Retrieve tft name from Firebase
            assert value != null;
            tftName.setText(value.getString("tftName"));

            //click button to upload data
            mSaveBtn.setOnClickListener(view -> {
                Bundle bundle1 = getIntent().getExtras();
                if (bundle != null) {
                    //updating data
                    //input data
                    String id = pId;
                    String title = mTitleEt.getText().toString().trim();
                    String description = mDescriptionEt.getText().toString().trim();
                    // function call to upload data
                    updateData(id, title, description);
                } else {
                    //adding new data
                    //input data
                    String title = mTitleEt.getText().toString().trim();
                    String description = mDescriptionEt.getText().toString().trim();
                    //function call to upload data
                    uploadData(title, description);
                }

            });

            //click button to see all builds in list
            mListBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(CommunityBuilds.this, CommunityBuildList.class));
                    finish();
                }
            });
        });

        //
    }

    private void updateData(String id, String title, String description) {
        ///set title of progress bar
        pd.setTitle("Updating Data...");
        //show progress bar when user click save button
        pd.show();

        fStore.collection("users").document(userId).collection("builds").document(id)
                .update("title", title, "description", description)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //called when updated successfully
                        pd.dismiss();
                        Toast.makeText(CommunityBuilds.this, "Updated...", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error
                        pd.dismiss();
                        Toast.makeText(CommunityBuilds.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //Upload data to Firebase storage
    private void uploadData(String title, String description) {
        ///set title of progress bar
        pd.setTitle("Adding Data to Firestore");
        //show progress bar when user click save button
        pd.show();
        //random id for each data to be stored
        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("title", title);
        doc.put("description", description);

        //add this data
        fStore.collection("users").document(userId).collection("builds").document(id).set(doc).addOnCompleteListener(task -> {
            //Data is added successfully
            pd.dismiss();
            Toast.makeText(CommunityBuilds.this, "Uploaded...", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            //There is a error while uploading data
            pd.dismiss();
            Toast.makeText(CommunityBuilds.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        recreate();
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
        Toast.makeText(CommunityBuilds.this, "Logout Successful.", Toast.LENGTH_SHORT).show();
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