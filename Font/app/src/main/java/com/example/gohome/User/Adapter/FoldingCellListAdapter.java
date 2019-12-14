package com.example.gohome.User.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gohome.Entity.AdoptInfo;
import com.example.gohome.R;
import com.example.gohome.User.Activity.UserAddGroupActivity;
import com.example.gohome.User.ImageDialog;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

public class FoldingCellListAdapter extends RecyclerView.Adapter{

    private Context context;
    private List<AdoptInfo> list;

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;

    private final String s0 = "♀";
    private final String s1 = "♂";


    public FoldingCellListAdapter(Context context, List<AdoptInfo> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        FoldingCell cell;
        ImageView petPhoto1, petPhoto2;
        TextView petName1, petGender1, petAge1, desc1, area1; // title
        TextView petName2, petGender2, petAge2, desc2, area2; // content
        ImageView iv_vacn, iv_strl;
        TextView publisher, time;
        TextView contentRequestBtn;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            cell = itemView.findViewById(R.id.user_adopt_cell);
            // title(外)
            petPhoto1 = itemView.findViewById(R.id.user_iv_petPhoto);
            petName1 = itemView.findViewById(R.id.user_tv_petName);
            petGender1 = itemView.findViewById(R.id.user_tv_petGender);
            petAge1 = itemView.findViewById(R.id.user_tv_petAge);
            desc1 = itemView.findViewById(R.id.user_tv_petDesc);
            area1 = itemView.findViewById(R.id.user_tv_petArea);
            // content(内)
            petPhoto2 = itemView.findViewById(R.id.user_iv_cont_petPhoto);
            petName2 = itemView.findViewById(R.id.user_tv_cont_petName);
            petGender2 = itemView.findViewById(R.id.user_tv_cont_petGender);
            petAge2 = itemView.findViewById(R.id.user_tv_cont_petAge);
            desc2 = itemView.findViewById(R.id.user_tv_cont_petDesc);
            area2 = itemView.findViewById(R.id.user_tv_cont_petArea);
            iv_vacn = itemView.findViewById(R.id.user_iv_petVacn);
            iv_strl = itemView.findViewById(R.id.user_iv_petStrl);
            publisher = itemView.findViewById(R.id.user_tv_publisher);
            time = itemView.findViewById(R.id.user_tv_pubTime);
            contentRequestBtn = itemView.findViewById(R.id.user_btn_content_want);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_user_cell, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {

        ViewHolder mholder = new ViewHolder(holder.itemView);

        String gender = list.get(pos).getGender() == 0 ? s0 : s1;
        int icon1 = !list.get(pos).getVaccinate() ? R.drawable.no : R.drawable.yes;
        int icon2 = list.get(pos).getSteriled() ? R.drawable.no : R.drawable.yes;
//        int icon1 = list.get(pos).getVaccinate() == false ? R.drawable.no : R.drawable.yes;
//        int icon2 = list.get(pos).getSteriled() == false ? R.drawable.no : R.drawable.yes;

        // title(外)
        Glide.with(context).load(list.get(pos).getPhotos()).into(mholder.petPhoto1);
        mholder.petName1.setText(list.get(pos).getPetName());
        mholder.petGender1.setText(gender);
        mholder.petAge1.setText(list.get(pos).getAge());
        mholder.desc1.setText(list.get(pos).getDescription());
        mholder.area1.setText(list.get(pos).getAddress());

        // content(内)
        Glide.with(context).load(list.get(pos).getPhotos()).into(mholder.petPhoto2);
        mholder.petName2.setText(list.get(pos).getPetName());
        mholder.petGender2.setText(gender);
        mholder.petAge2.setText(list.get(pos).getAge());
        mholder.desc2.setText(list.get(pos).getDescription());
        mholder.area2.setText(list.get(pos).getAddress());
        Glide.with(context).load(icon1).into(mholder.iv_vacn);
        Glide.with(context).load(icon2).into(mholder.iv_strl);
        mholder.publisher.setText(list.get(pos).getHandleId());
        mholder.time.setText(list.get(pos).getCreated());
        mholder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);

        mholder.petPhoto2.setOnClickListener(view -> {
            ImageDialog dialog = new ImageDialog(context, list.get(pos).getPhotos());
            dialog.show();
        });

        mholder.publisher.setOnClickListener(view -> {
            Intent intent = new Intent(context, UserAddGroupActivity.class);
            intent.putExtra("group", mholder.publisher.getText());
            context.startActivity(intent);
        });

        mholder.cell.setOnClickListener(view -> {
            if(mholder.cell.isUnfolded()) {
                mholder.cell.fold(false);
            }
            else {
                mholder.cell.unfold(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    private void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    private void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

}