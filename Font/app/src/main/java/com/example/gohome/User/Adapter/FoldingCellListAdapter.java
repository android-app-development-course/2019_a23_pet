package com.example.gohome.User.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.gohome.Entity.AdoptInfo;
import com.example.gohome.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

public class FoldingCellListAdapter extends ArrayAdapter<AdoptInfo> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;

    private final String s0 = "♀";
    private final String s1 = "♂";

    public FoldingCellListAdapter(Context context, List<AdoptInfo> list){
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int pos, View convertView, @NonNull ViewGroup parent){
        AdoptInfo item = getItem(pos);
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            cell = (FoldingCell) inflater.inflate(R.layout.layout_user_cell, parent, false);

            viewHolder.petPhoto1 = cell.findViewById(R.id.user_iv_petPhoto);
            viewHolder.petName1 = cell.findViewById(R.id.user_tv_petName);
            viewHolder.petGender1 = cell.findViewById(R.id.user_tv_petGender);
            viewHolder.petAge1 = cell.findViewById(R.id.user_tv_petAge);
            viewHolder.desc1 = cell.findViewById(R.id.user_tv_petDesc);
            viewHolder.area1 = cell.findViewById(R.id.user_tv_petArea);

            viewHolder.petPhoto2 = cell.findViewById(R.id.user_iv_cont_petPhoto);
            viewHolder.petName2 = cell.findViewById(R.id.user_tv_cont_petName);
            viewHolder.petGender2 = cell.findViewById(R.id.user_tv_cont_petGender);
            viewHolder.petAge2 = cell.findViewById(R.id.user_tv_cont_petAge);
            viewHolder.desc2 = cell.findViewById(R.id.user_tv_cont_petDesc);
            viewHolder.area2 = cell.findViewById(R.id.user_tv_cont_petArea);

            viewHolder.iv_vacn = cell.findViewById(R.id.user_iv_petVacn);
            viewHolder.iv_strl = cell.findViewById(R.id.user_iv_petStrl);
            viewHolder.publisher = cell.findViewById(R.id.user_tv_publisher);
            viewHolder.time = cell.findViewById(R.id.user_tv_pubTime);

            viewHolder.contentRequestBtn = cell.findViewById(R.id.user_btn_content_want);

            cell.setTag(viewHolder);
        } else {
            if(unfoldedIndexes.contains(pos)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if (item == null)
            return cell;

        String gender = item.getGender() == 0 ? s0 : s1;
        int icon1 = item.getVacn() == 0 ? R.drawable.no : R.drawable.yes;
        int icon2 = item.getStrl() == 0 ? R.drawable.no : R.drawable.yes;

        Glide.with(getContext()).load(item.getPetPhotoId()).into(viewHolder.petPhoto1);
        viewHolder.petName1.setText(item.getPetName());
        viewHolder.petGender1.setText(gender);
        viewHolder.petAge1.setText(item.getPetAge());
        viewHolder.desc1.setText(item.getDesc());
        viewHolder.area1.setText(item.getArea());

        Glide.with(getContext()).load(item.getPetPhotoId()).into(viewHolder.petPhoto2);
        viewHolder.petName2.setText(item.getPetName());
        viewHolder.petGender2.setText(gender);
        viewHolder.petAge2.setText(item.getPetAge());
        viewHolder.desc2.setText(item.getDesc());
        viewHolder.area2.setText(item.getArea());

        Glide.with(getContext()).load(icon1).into(viewHolder.iv_vacn);
        Glide.with(getContext()).load(icon2).into(viewHolder.iv_strl);
        viewHolder.publisher.setText(item.getPublisher());
        viewHolder.time.setText(item.getTime());
        viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);

        if (item.getRequestBtnClickListener() != null) {
            viewHolder.contentRequestBtn.setOnClickListener(item.getRequestBtnClickListener());
        } else {
            viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
        }

        return cell;
    }

    private static class ViewHolder {
        ImageView petPhoto1, petPhoto2;
        TextView petName1, petGender1, petAge1, desc1, area1; // title
        TextView petName2, petGender2, petAge2, desc2, area2; // content
        ImageView iv_vacn, iv_strl;
        TextView publisher, time;
        TextView contentRequestBtn;
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
