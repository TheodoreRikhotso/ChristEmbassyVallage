package com.example.admin.christembassyvallage;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.admin.christembassyvallage.model.Video;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class VideosActivity extends Fragment {

    FloatingActionButton addVideo;
    Dialog builder;
    VideoView v;
    EditText etTitle;
    Button btnAddVideo;
    String title;
    View view;
    private StorageReference mStorageReference;
    StorageReference filePath;
    private DatabaseReference db;

    Uri uri;
    private static final int GALLERY_INTENT = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_videos, container, false);
        addVideo = (FloatingActionButton) view.findViewById(R.id.addVideo);

        db = FirebaseDatabase.getInstance().getReference("Videos");

        mStorageReference = FirebaseStorage.getInstance().getReference();
        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVideo();
            }
        });
        return view;
    }


    public void addVideo() {
        builder = new Dialog(getContext());
        builder.setTitle(" Add Video");
        builder.setContentView(R.layout.add_video);

        etTitle = builder.findViewById(R.id.etVideoTitle);
        v = builder.findViewById(R.id.etVideo);
        title = etTitle.getText().toString();


        btnAddVideo = builder.findViewById(R.id.btnAddVideo);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "xc", Toast.LENGTH_SHORT).show();

                //IMAGE
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(intent, GALLERY_INTENT);
                v.setVideoURI(uri);
                v.start();

            }
        });
        btnAddVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                StorageReference childRef = mStorageReference.child("Image").child(uri.getLastPathSegment());
                UploadTask uploadTask = childRef.putFile(uri);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri uirs = taskSnapshot.getDownloadUrl();

                        Toast.makeText(getActivity(), "aaa", Toast.LENGTH_SHORT).show();
                        Video video = new Video();
                        video.setVideoUrl(uirs.toString());
                        video.setTitle(title);


                        String id = db.push().getKey();
                        db.child(id).setValue(video);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getActivity(), "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        builder.getWindow().getAttributes();
        builder.show();

    }

    //
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            uri = data.getData();
            v.setVideoURI(uri);

        }
    }
}
