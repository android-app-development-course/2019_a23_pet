package com.example.gohome.Member.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gohome.Entity.AdoptAppliment;
import com.example.gohome.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

public class MemberCheckUndoFoldingCellAdapter extends RecyclerView.Adapter<MemberCheckUndoFoldingCellAdapter.ViewHolder> {

    //数据源
    private List<AdoptAppliment> mList;
    private Context context;

    private final String s0 = "♀";
    private final String s1 = "♂";

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    //按钮请求监听
    private View.OnClickListener defaultRequestBtnClickListener;
    //点击返回
    private ItemClickCallBack clickCallBack;



    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ItemClickCallBack{
        void onItemClick(int pos);
    }


    public MemberCheckUndoFoldingCellAdapter(Context context, List<AdoptAppliment> list){
        mList = list;
        this.context = context;
    }


    //负责每一个item
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView petPhoto1, petPhoto2;   //显示申请领养动物的图片
        // title：领养动物姓名、申请人信息提示文字，申请信息类型，申请日期
        TextView petName1, applicantMessage, type, titleDate;
        //content： 申请日期、宠物姓名、宠物性别、宠物年龄、疫苗情况、绝育情况
        TextView contentDate, petName2, petGender, petAge, applicantName, applicantJob,
                applicantAddress, applicantDescription, applicantTelephone;

        ImageView  vaccine,sterilization ;
        TextView publisher, time;
        TextView contentRequestBtn;
        FoldingCell cell;


        public ViewHolder(View itemView) {
            super(itemView);

            //cell title的内容
            petPhoto1 = itemView.findViewById(R.id.iv_memberCheckUndoTitlePetPhoto);
            petName1 = itemView.findViewById(R.id.tv_memberCheckUndoTitlePetName);
            applicantMessage = itemView.findViewById(R.id.tv_memberCheckUndoApplicantMessage);
            type = itemView.findViewById(R.id.tv_memberCheckUndoType);

            titleDate = itemView.findViewById(R.id.tv_memberCheckUndoTitleDate);
            type = itemView.findViewById(R.id.tv_memberCheckUndoType);

            //cell content的内容
            petPhoto2 = itemView.findViewById(R.id.iv_memberCheckUndoContentPetPhoto);
            petName2 = itemView.findViewById(R.id.tv_memberCheckUndoContentPetName);
            contentDate = itemView.findViewById(R.id.tv_memberCheckUndoContentDate);
            petGender = itemView.findViewById(R.id.tv_memberCheckUndoPetGender);
            petAge = itemView.findViewById(R.id.tv_memberCheckUndoPetAge);
            vaccine = itemView.findViewById(R.id.iv_memberCheckUndoVaccine);
            sterilization = itemView.findViewById(R.id.iv_memberCheckUndoSterilization);
            applicantName = itemView.findViewById(R.id.tv_memberCheckUndoApplicantName);
            applicantJob = itemView.findViewById(R.id.tv_memberCheckUndoApplicantJob);
            applicantAddress = itemView.findViewById(R.id.tv_memberCheckUndoApplicantAddress);
            applicantTelephone = itemView.findViewById(R.id.tv_memberCheckUndoApplicantTel);
            applicantDescription = itemView.findViewById(R.id.tv_memberCheckUndoApplicantDescription);
            //通过审核按钮
            contentRequestBtn = itemView.findViewById(R.id.btn_memberCheckUndo);


            vaccine = itemView.findViewById(R.id.iv_memberCheckUndoVaccine);
            sterilization = itemView.findViewById(R.id.iv_memberCheckUndoSterilization);


            cell = itemView.findViewById(R.id.fc_memberCheckUndo);
        }

    }

    //创建新View，被LayoutManager所调用
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View view = (FoldingCell)LayoutInflater.from(context).inflate(R.layout.fragment_member_check_undo_item_cell,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        FoldingCell foldingCell = (FoldingCell)viewHolder.itemView;

        String gender = mList.get(position).getPetGender() == 0 ? s0 : s1;
        int icon1 = mList.get(position).getVaccine() == 0 ? R.drawable.no : R.drawable.yes;
        int icon2 = mList.get(position).getSterilization() == 0 ? R.drawable.no : R.drawable.yes;


        //title
        Glide.with(context).load(mList.get(position).getPetPhotoId()).into(viewHolder.petPhoto1);
        viewHolder.petName1.setText(mList.get(position).getPetName());
        viewHolder.applicantMessage.setText(mList.get(position).getApplyName()+"发来了申请，点击查看详情");
        viewHolder.titleDate.setText(mList.get(position).getDate().getYear()+"年"+mList.get(position).getDate().getMonth()+"月"+
                mList.get(position).getDate().getDay()+"日");
        if(mList.get(position).getPetType() == "0"){    //类型为0则表示领养申请
            viewHolder.type.setText("领养申请");
        }

        //content
        Glide.with(context).load(mList.get(position).getPetPhotoId()).into(viewHolder.petPhoto2);
        viewHolder.petName2.setText(mList.get(position).getPetName());
        viewHolder.petGender.setText(gender);
        viewHolder.petAge.setText(mList.get(position).getPetAge());
        viewHolder.contentDate.setText(mList.get(position).getDate().getYear()+"年"+mList.get(position).getDate().getMonth()+"月"+
                mList.get(position).getDate().getDay()+"日");
        viewHolder.applicantName.setText(mList.get(position).getApplyName());
        viewHolder.applicantJob.setText(mList.get(position).getJob());
        viewHolder.applicantAddress.setText(mList.get(position).getAddress());
        viewHolder.applicantTelephone.setText(mList.get(position).getTelephone());
        viewHolder.applicantDescription.setText(mList.get(position).getDescription());

        Glide.with(context).load(icon1).into(viewHolder.vaccine);
        Glide.with(context).load(icon2).into(viewHolder.sterilization);
        viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);

        viewHolder.cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickCallBack != null){
                    clickCallBack.onItemClick(position);
                    // 切换已单击的单元格状态
                    ((FoldingCell) viewHolder.itemView).toggle(false);
                    // 在适配器中注册所选单元格的状态已被切换
                    registerToggle(position);

                    if(unfoldedIndexes.contains(position)) {
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
        return mList == null ? 0 : mList.size();
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
