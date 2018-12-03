package com.example.xin.pre_project;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class DogRVAdapter extends RecyclerView.Adapter<DogRVAdapter.MyViewHolder> {

    private List<Dog> dogList;
    private Context mContext;
    private ItemClickListener clickListener;

    public DogRVAdapter(Context context, List<Dog> dogs) {
        this.dogList = dogs;
        this.mContext = context;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dog_list_item, parent, false);
        final MyViewHolder vHolder = new MyViewHolder(view);

        return vHolder;
    }

    // Binds data to recycled views
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String name, year, month, day, picPath;
        year = String.valueOf(dogList.get(position).bdayYear);
        month = String.valueOf(dogList.get(position).bdayMonth);
        day = String.valueOf(dogList.get(position).bdayDay);
        String bdayString =  month + "/" + day + "/" + year;

        name = dogList.get(position).name;
        holder.dogName.setText(name);
        holder.dogBday.setText(bdayString);
        picPath = dogList.get(position).profilePicPath;

        // get dog pic from local storage
        ImageManager im = new ImageManager(HomeActivity.gContext);
        Bitmap picBM = im.loadFromStorage(picPath, name);
        if(picBM == null) {
            Toast.makeText(mContext, "Error loading " + name + "'s photo", Toast.LENGTH_SHORT).show();
            holder.dogPic.setImageResource(R.drawable.defaultdogpic);
        }
        else
            holder.dogPic.setImageBitmap(picBM);
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }

    // Stores and recycles views as they are scrolled off screen
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private de.hdodenhof.circleimageview.CircleImageView dogPic;
        private TextView dogName, dogBday;

        public MyViewHolder(View itemView) {
            super(itemView);

            dogPic = itemView.findViewById(R.id.dogPic);
            dogName = itemView.findViewById(R.id.dogName);
            dogBday = itemView.findViewById(R.id.dogBday);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //clickListener.onClick(view, getAdapterPosition()); // call the onClick in the OnItemClickListener
        }

    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onClick(View view, int position);
    }
}
