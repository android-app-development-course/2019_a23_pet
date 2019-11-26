package com.example.gohome.Organizer.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gohome.Entity.JoinAppliment;
import com.example.gohome.R;
import com.ramotion.foldingcell.FoldingCell;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;

public class OrganizationUnProcessFoldingCellAdapter extends RecyclerView.Adapter{

    // data source
    private List<JoinAppliment> joinApplimentList;
    private Context context;

    //记录cell的折叠情况
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    //按钮请求监听
    private View.OnClickListener defaultRequestBtnClickListener;
    //点击的返回

    private OrganizationUnProcessFoldingCellAdapter.ItemClickCallBack clickCallBack;

    public void setClickCallBack(OrganizationUnProcessFoldingCellAdapter.ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ItemClickCallBack{
        void onItemClick(int pos);
    }

    public OrganizationUnProcessFoldingCellAdapter(Context context, List list){
        this.context = context;
        this.joinApplimentList = list;
    }

    public class ApplyViewHolder extends RecyclerView.ViewHolder{

        ImageView titleApplyProtrait;
        TextView contentApplyDate, contentUsername, contentApplyName, contentGender, contentTelephone, contentAddress, contentDescription;
        TextView titleTitle, titleInfoMessage, titleApplyDate;
        TextView contentBtn;
        FoldingCell cell;

        public ApplyViewHolder(View itemView){
            super(itemView);

            //set title
            titleApplyProtrait = itemView.findViewById(R.id.image_view_apply_protrait);
            titleTitle = itemView.findViewById(R.id.text_view_apply_title);
            titleInfoMessage = itemView.findViewById(R.id.text_view_apply_info_message);
            titleApplyDate = itemView.findViewById(R.id.text_view_apply_date);

            //set content
            contentApplyDate =itemView.findViewById(R.id.text_view_apply_content_date);
            contentUsername = itemView.findViewById(R.id.text_view_apply_username);
            contentApplyName = itemView.findViewById(R.id.text_view_apply_name);
            contentGender = itemView.findViewById(R.id.text_view_apply_gender);
            contentTelephone = itemView.findViewById(R.id.text_view_apply_telephone);
            contentAddress =itemView.findViewById(R.id.text_view_apply_address);
            contentDescription = itemView.findViewById(R.id.text_view_apply_description);
            contentBtn = itemView.findViewById(R.id.text_view_apply_handle);

            cell = itemView.findViewById(R.id.foldingcell_unprocess);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_organizer_organization_unprocess_item_cell, null);
        return new ApplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ApplyViewHolder applyViewHolder = new ApplyViewHolder(holder.itemView);
        FoldingCell foldingCell = (FoldingCell)holder.itemView;

        JoinAppliment joinAppliment = joinApplimentList.get(position);
        String gender = joinAppliment.getGender().equals(Integer.valueOf(1))? "男":"女";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String applyDate = simpleDateFormat.format(joinAppliment.getApplyDate());


        //set title
        Glide.with(context).load(joinAppliment.getPhotoId()).into(applyViewHolder.titleApplyProtrait);
        applyViewHolder.titleInfoMessage.setText(joinAppliment.getUsername() + "的申请，点击处理");
        applyViewHolder.titleApplyDate.setText(applyDate);

        //set content
        applyViewHolder.contentUsername.setText(joinAppliment.getUsername());
        applyViewHolder.contentApplyName.setText(joinAppliment.getApplyName());
        applyViewHolder.contentGender.setText(gender);
        applyViewHolder.contentAddress.setText(joinAppliment.getAddress());
        applyViewHolder.contentTelephone.setText(joinAppliment.getTelephone());
        applyViewHolder.contentDescription.setText(joinAppliment.getDescription());
        applyViewHolder.contentApplyDate.setText(applyDate);

        applyViewHolder.contentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("点击了第" + position + "个处理按钮");
                Toast.makeText(context, "点击了第" + position + "个处理按钮", Toast.LENGTH_LONG).show();
                //处理+刷新
            }
        });

        //控制cell的折叠与收缩
        applyViewHolder.cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCallBack != null) {
                    clickCallBack.onItemClick(position);
                    // 切换已单击的单元格状态
                    ((FoldingCell) holder.itemView).toggle(false);
                    // 在适配器中注册所选单元格的状态已被切换
                    registerToggle(position);

                    if (unfoldedIndexes.contains(position)) {
                        foldingCell.unfold(true);
                    } else {
                        foldingCell.fold(true);
                    }

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return joinApplimentList == null ? 0 : joinApplimentList.size();
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
