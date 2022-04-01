package com.example.statslegend;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    CommunityBuildList communityBuildList;
    List<Model> modelList;


    public CustomAdapter(CommunityBuildList communityBuildList, List<Model> modelList) {
        this.communityBuildList = communityBuildList;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflate layout
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_layout, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        //handle item clicks here
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //this will be called when user long click item
                //Creating AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(communityBuildList);
                //options to display in dialog
                String[] options = {"Show","Update", "Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            //get data
                            String id = modelList.get(position).getId();
                            String title = modelList.get(position).getTitle();
                            String description = modelList.get(position).getDescription();

                            //show data in toast on clicking
                            //Toast.makeText(communityBuildList, title + "\n" + description, Toast.LENGTH_SHORT).show();

                            //intent to start activity
                            Intent intent = new Intent(communityBuildList, CommunityBuildDetails.class);
                            //put data in intent
                            intent.putExtra("pId", id);
                            intent.putExtra("pTitle", title);
                            intent.putExtra("pDescription", description);
                            intent.putExtra("pPosition",position);
                            //intent.putExtra("pCommunityBuildList",communityBuildList);
                            //start activity
                            communityBuildList.startActivity(intent);
                        }
                        if (i == 1) {
                            //update is clicked
                            //get data
                            String id = modelList.get(position).getId();
                            String title = modelList.get(position).getTitle();
                            String description = modelList.get(position).getDescription();

                            //intent to start activity
                            Intent intent = new Intent(communityBuildList, CommunityBuilds.class);
                            //put data in intent
                            intent.putExtra("pId", id);
                            intent.putExtra("pTitle", title);
                            intent.putExtra("pDescription", description);
                            //start activity
                            communityBuildList.startActivity(intent);
                        }
                        if (i == 2) {
                            //delete is clicked
                            communityBuildList.deleteData(position);
                        }

                    }
                }).create().show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //this will be called when user long click item
                //Creating AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(communityBuildList);
                //options to display in dialog
                String[] options = {"Show","Update", "Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            //get data
                            String id = modelList.get(position).getId();
                            String title = modelList.get(position).getTitle();
                            String description = modelList.get(position).getDescription();

                            //show data in toast on clicking
                            //Toast.makeText(communityBuildList, title + "\n" + description, Toast.LENGTH_SHORT).show();

                            //intent to start activity
                            Intent intent = new Intent(communityBuildList, CommunityBuildDetails.class);
                            //put data in intent
                            intent.putExtra("pId", id);
                            intent.putExtra("pTitle", title);
                            intent.putExtra("pDescription", description);
                            intent.putExtra("pPosition",position);
                            //intent.putExtra("pCommunityBuildList",communityBuildList);
                            //start activity
                            communityBuildList.startActivity(intent);
                        }
                        if (i == 1) {
                            //update is clicked
                            //get data
                            String id = modelList.get(position).getId();
                            String title = modelList.get(position).getTitle();
                            String description = modelList.get(position).getDescription();

                            //intent to start activity
                            Intent intent = new Intent(communityBuildList, CommunityBuilds.class);
                            //put data in intent
                            intent.putExtra("pId", id);
                            intent.putExtra("pTitle", title);
                            intent.putExtra("pDescription", description);
                            //start activity
                            communityBuildList.startActivity(intent);
                        }
                        if (i == 2) {
                            //delete is clicked
                            communityBuildList.deleteData(position);
                        }

                    }
                }).create().show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //bind views / set data
        holder.mTitleTv.setText(modelList.get(position).getTitle());
        holder.mDescriptionTv.setText(modelList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
