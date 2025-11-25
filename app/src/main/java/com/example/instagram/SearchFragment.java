package com.example.instagram;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


public class SearchFragment extends Fragment {

    GridView gridView;
    HashMap<String, String> hashMap = new HashMap<>();
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View myView = inflater.inflate(R.layout.fragment_search, container, false);

        gridView =myView. findViewById(R.id.gridView);


        itemPhoto();

        MyAdapter myAdapter = new MyAdapter();
        gridView.setAdapter(myAdapter);



        return myView;
    }
    //-------------------------------------------------------------------------------------------


    private  class  MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View myView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = layoutInflater.inflate(R.layout.item_search, parent, false);



            ImageView imageView = myView.findViewById(R.id.imageView);


            HashMap<String, String> hashMap = arrayList.get(position);

            String image = hashMap.get("image");

            Picasso.get()
                    .load(image)
                    .into(imageView);

            return myView;
        }
    }



    // ------------------------------------------------------------------------------------------------------------
    public void itemPhoto(){



        arrayList = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put("image", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmflgZr3lK6j2r-TWZn1FXusD00KJvc-a6UQ&s");
        arrayList.add(hashMap);




        hashMap = new HashMap<>();
        hashMap.put("image", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRkTAXbtCUNAsmHDBHWmEIlYrHl960syeDIxQ&s");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("image", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQY9WZCj70EuS2tC3VMphjdc8P0YRqQ6w8LSQ&s");
        arrayList.add(hashMap);



        hashMap = new HashMap<>();
        hashMap.put("image", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSa9W2r4h5yxEa6BLSdlv1z5ONCEhouwgZi1g&s");
        arrayList.add(hashMap);






        hashMap = new HashMap<>();
        hashMap.put("image", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSkFS02Viy2Qyh61mixXtiaU3CzMG7iiSLcsQ&s");
        arrayList.add(hashMap);




    }
}