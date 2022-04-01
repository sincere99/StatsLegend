package com.example.statslegend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CommunityBuildDetails extends AppCompatActivity {
    DrawerLayout drawerLayout;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId, pId, pTitle, pDescription;
    Button mUpdateBtn;
    TextView mTitle, mDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_build_details);

        //initialize views
        drawerLayout = findViewById(R.id.drawer_layout);
        mUpdateBtn = findViewById(R.id.updateBtn);
        mTitle = findViewById(R.id.detailTitle);
        mDescription = findViewById(R.id.detailDescription);

        //set view data
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pId = bundle.getString("pId");
            pTitle = bundle.getString("pTitle");
            pDescription = bundle.getString("pDescription");
            //set data
            mTitle.setText(pTitle);
            mDescription.setText(pDescription);
        }

        //Click on update
        mUpdateBtn.setOnClickListener(view -> {
            //update is clicked
            //get data
            String id = pId;
            String title = pTitle;
            String description = pDescription;

            //intent to start activity
            Intent intent = new Intent(CommunityBuildDetails.this, CommunityBuilds.class);
            //put data in intent
            intent.putExtra("pId", id);
            intent.putExtra("pTitle", title);
            intent.putExtra("pDescription", description);
            //start activity
            CommunityBuildDetails.this.startActivity(intent);
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
        Toast.makeText(CommunityBuildDetails.this, "Logout Successful.", Toast.LENGTH_SHORT).show();
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

