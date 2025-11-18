package com.example.instagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {

    ImageView postProfile;
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    HashMap<String, String> hashMapStory;
    ArrayList<HashMap<String, String>> arrayListStory;

    HashMap<String, String> hashMapPost;
    ArrayList<HashMap<String, String>> arrayListPost;
    MyAdapter adapter;
    MyAdapter2 adapter2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView1 = myView.findViewById(R.id.recyclerView1);
        recyclerView2 = myView.findViewById(R.id.recyclerView2);


        arrayListStory = new ArrayList<>();
        loadStory();

        adapter = new MyAdapter();
        recyclerView1.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManager);


        // -------------------------------------------------------------------------------------------------------------------------


        arrayListPost = new ArrayList<>();
        loadFeed();

        adapter2 = new MyAdapter2();
        recyclerView2.setAdapter(adapter2);

        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false)
        );


        return myView;

    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder> {

        public class myViewHolder extends RecyclerView.ViewHolder {

            TextView storyUsername;
            CircleImageView storyPhoto;

            public myViewHolder(@NonNull View itemView) {
                super(itemView);
                storyUsername = itemView.findViewById(R.id.storyUsername);
                storyPhoto = itemView.findViewById(R.id.storyPhoto);
            }

        }

        //------------------------------------------------------------------------------------------------------


        @NonNull
        @Override
        public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View myView = inflater.inflate(R.layout.item_story, parent, false);

            return new myViewHolder(myView);
        }

        @Override
        public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

            hashMapStory = arrayListStory.get(position);

            String user_name = hashMapStory.get("user_name");
            String story_photo = hashMapStory.get("story_photo");


            holder.storyUsername.setText(user_name);

            Picasso.get()
                    .load(story_photo)
                    .into(holder.storyPhoto);

            holder.storyPhoto.setOnClickListener(v -> {
                String imageUrl = arrayListStory.get(position).get("story_photo");

                // StoryFullScreenFragment তৈরি
                StoryView fragment = StoryView.newInstance(imageUrl);

                // Fullscreen overlay হিসেবে add করা
                ((MainActivity) v.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .add(android.R.id.content, fragment)
                        .addToBackStack(null)
                        .commit();
            });


        }

        @Override
        public int getItemCount() {
            return arrayListStory.size();
        }


    }

    // ------------------------------------------------------------------------------------------------------------------


    public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.myViewHolder> {

        public class myViewHolder extends RecyclerView.ViewHolder {

            CircleImageView postProfile;
            ImageView postImage;
            TextView postUsername, like, comment, repost, share, postCaption, postTime;

            public myViewHolder(@NonNull View itemView) {
                super(itemView);
                postProfile = itemView.findViewById(R.id.postProfile);
                postImage = itemView.findViewById(R.id.postImage);
                postUsername = itemView.findViewById(R.id.postUsername);
                like = itemView.findViewById(R.id.like);
                comment = itemView.findViewById(R.id.comment);
                repost = itemView.findViewById(R.id.repost);
                share = itemView.findViewById(R.id.share);
                postCaption = itemView.findViewById(R.id.postCaption);
                postTime = itemView.findViewById(R.id.postTime);

            }
        }


        @NonNull
        @Override
        public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View myView = inflater.inflate(R.layout.item_post, parent, false);
            return new myViewHolder(myView);
        }

        @Override
        public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

            hashMapPost = arrayListPost.get(position);
            String user_name = hashMapPost.get("user_name");
            String user_profile = hashMapPost.get("user_profile");
            String user_image = hashMapPost.get("user_image");
            String user_like = hashMapPost.get("user_like");
            String user_comment = hashMapPost.get("user_comment");
            String user_repost = hashMapPost.get("user_repost");
            String user_share = hashMapPost.get("user_share");
            String user_caption = hashMapPost.get("user_caption");
            String user_time = hashMapPost.get("user_time");

            holder.postUsername.setText(user_name);
            holder.like.setText(user_like);
            holder.comment.setText(user_comment);
            holder.repost.setText(user_repost);
            holder.share.setText(user_share);
            holder.postCaption.setText(user_caption);
            holder.postTime.setText(user_time);

            Picasso.get()
                    .load(user_profile)
                    .into(holder.postProfile);

            Picasso.get()
                    .load(user_image)
                    .into(holder.postImage);

        }

        @Override
        public int getItemCount() {
            return arrayListPost.size();
        }

    }


    // -  ----     -------------           -------------------------          --------------------------------------------------------


    private void loadStory() {

        String url = "https://emondev.xyz/socialmedia/storyhome.json";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                arrayListStory.clear();

                for (int x = 0; x < response.length(); x++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(x);

                        String user_name = jsonObject.getString("user_name");
                        String story_photo = jsonObject.getString("story_photo");


                        hashMapStory = new HashMap<>();
                        hashMapStory.put("user_name", user_name);
                        hashMapStory.put("story_photo", story_photo);


                        arrayListStory.add(hashMapStory);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(getContext(), "Connection Lost !!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        requestQueue.add(jsonArrayRequest);

    }


    // ------------------------------- -            ----------------------------------            -----------------------------

    private void loadFeed() {


        String url = "https://emondev.xyz/socialmedia/posthome.json";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                arrayListPost.clear();

                for (int x = 0; x < response.length(); x++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(x);

                        String user_name = jsonObject.getString("user_name");
                        String user_profile = jsonObject.getString("user_profile");
                        String user_image = jsonObject.getString("user_image");
                        String user_like = jsonObject.getString("user_like");
                        String user_comment = jsonObject.getString("user_comment");
                        String user_repost = jsonObject.getString("user_repost");
                        String user_share = jsonObject.getString("user_share");
                        String user_caption = jsonObject.getString("user_caption");
                        String user_time = jsonObject.getString("user_time");


                        hashMapPost = new HashMap<>();
                        hashMapPost.put("user_name", user_name);
                        hashMapPost.put("user_profile", user_profile);
                        hashMapPost.put("user_image", user_image);
                        hashMapPost.put("user_like", user_like);
                        hashMapPost.put("user_comment", user_comment);
                        hashMapPost.put("user_repost", user_repost);
                        hashMapPost.put("user_share", user_share);
                        hashMapPost.put("user_caption", user_caption);
                        hashMapPost.put("user_time", user_time);


                        arrayListPost.add(hashMapPost);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

                adapter2.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(getContext(), "Connection Lost !!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        requestQueue.add(jsonArrayRequest);

    }


}