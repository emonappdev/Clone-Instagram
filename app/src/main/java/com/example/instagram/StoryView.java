package com.example.instagram;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class StoryView extends Fragment {

    private static final String ARG_IMAGE_URL = "image_url";
    private String imageUrl;
    private ImageView storyImage;

    public StoryView() {
    }

    public static StoryView newInstance(String imageUrl) {
        StoryView fragment = new StoryView();
        Bundle args = new Bundle();
        args.putString(ARG_IMAGE_URL, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUrl = getArguments().getString(ARG_IMAGE_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_story_view, container, false);

        storyImage = myView.findViewById(R.id.storyImage);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(storyImage);
        }


        new Handler().postDelayed(() -> {
            getParentFragmentManager().beginTransaction()
                    .remove(StoryView.this)
                    .commit();
        }, 3000);

        return myView;
    }
}