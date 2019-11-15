package com.example.gohome.User.Adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gohome.R;
import com.example.gohome.User.Activity.UserAdoptActivity;
import com.example.gohome.Entity.AdoptInfo;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.InfoViewHolder> {
    private Context context;
    private List<AdoptInfo> infoList;
    private final String s0 = "♀";
    private final String s1 = "♂";
    private final String s2 = "· ";

    public RecyclerViewAdapter(Context context, List<AdoptInfo> infoList) {
        super();
        this.context = context;
        this.infoList = infoList;
    }

    static class InfoViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView iv_petPhoto;
        TextView tv_petName, tv_petGenderAndAge, tv_desc, tv_area, tv_time;

        InfoViewHolder(final View item) {
            super(item);

            cardView = item.findViewById(R.id.user_cv_petInfo);
            iv_petPhoto = item.findViewById(R.id.user_iv_petPhoto);
            tv_petName = item.findViewById(R.id.user_tv_petName);
            tv_petGenderAndAge = item.findViewById(R.id.user_tv_petGenderAndAge);
            tv_desc = item.findViewById(R.id.user_tv_petDesc);
            tv_area = item.findViewById(R.id.user_tv_petArea);
            tv_time = item.findViewById(R.id.user_tv_pubTime);
        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.InfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_user_adopt_info_item, null);
        return new InfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.InfoViewHolder infoViewHolder, int position) {
        final int pos = position;

        AdoptInfo info = infoList.get(pos);

        String gender = info.getGender() == 0 ? s0 : s1;
        String age = info.getPetAge();
        String text = gender + s2 + age;

        Glide.with(context).load(info.getPetPhotoId()).into(infoViewHolder.iv_petPhoto);
        infoViewHolder.tv_petName.setText(info.getPetName());
        infoViewHolder.tv_petGenderAndAge.setText(text);
        infoViewHolder.tv_desc.setText(info.getDesc());
        infoViewHolder.tv_area.setText(info.getArea());
        infoViewHolder.tv_time.setText(info.getTime());

        infoViewHolder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(context, UserAdoptActivity.class);
            intent.putExtra("info", infoList.get(pos));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return infoList == null ? 0 : infoList.size();
    }
}

