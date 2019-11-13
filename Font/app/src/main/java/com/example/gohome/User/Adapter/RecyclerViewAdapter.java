package com.example.gohome.User.Adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gohome.R;
import com.example.gohome.User.Activity.UserAdoptActivity;
import com.example.gohome.User.Model.AdoptInfo;

public class RecyclerViewAdapter
        extends RecyclerView.Adapter<RecyclerViewAdapter.InfoViewHolder>
{
    private Context context;
    private List<AdoptInfo> infoList;

    public RecyclerViewAdapter(Context context, List<AdoptInfo> infoList){
        super();
        this.context = context;
        this.infoList = infoList;
    }

    static class InfoViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView iv_photo;
        TextView tv_petName, tv_desc;

        InfoViewHolder(final View item){
            super(item);

            cardView = item.findViewById(R.id.user_card_view);
            iv_photo = item.findViewById(R.id.user_iv_photo);
            tv_petName = item.findViewById(R.id.user_tv_name);
            tv_desc = item.findViewById(R.id.user_tv_desc);

            // 设置TextView背景为半透明
            tv_petName.setBackgroundColor(Color.argb(20,0,0,0));
        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.InfoViewHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.activity_user_adopt_info_item,
                null);
        return new InfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.InfoViewHolder viewHolder, int position) {
        final int pos = position;

        viewHolder.iv_photo.setImageResource(infoList.get(position).getPhotoId());
        viewHolder.tv_petName.setText(infoList.get(position).getPetName());
        viewHolder.tv_desc.setText(infoList.get(position).getDesc());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserAdoptActivity.class);
                intent.putExtra("info", infoList.get(pos));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return infoList == null ? 0 : infoList.size();
    }
}

