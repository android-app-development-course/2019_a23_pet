package com.example.gohome.User.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gohome.Entity.ProcessInfo;
import com.example.gohome.R;
import com.example.gohome.User.ImageDialog;

import java.util.List;

public class ProcessRecyclerViewAdapter extends RecyclerView.Adapter<ProcessRecyclerViewAdapter.InfoViewHolder> {
    private List<ProcessInfo> infoList;
    private Context context;

    private View.OnClickListener cardViewOnClickListener;

    public ProcessRecyclerViewAdapter(List<ProcessInfo> infoList, Context context) {
        super();
        this.infoList = infoList;
        this.context = context;
    }

    class InfoViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView petPortrait;
        TextView petNickname, userText;

        ImageView[] ivPro = new ImageView[3];
        TextView[] tvPro = new TextView[3];

        final int[] processIV = {R.drawable.user_undefined, R.drawable.user_pass, R.drawable.user_fail};
        final String[][] processTV = {{"暂未提交", "提交成功", "提交失败"},
                {"暂未审核", "审核成功", "审核失败"}, {"暂未通过申请", "申请成功", "申请失败"}};

        InfoViewHolder(View item) {
            super(item);
            cardView = item.findViewById(R.id.card_view);

            petPortrait = item.findViewById(R.id.user_iv_petPortrait);
            petNickname = item.findViewById(R.id.user_tv_petNickname);
            userText = item.findViewById(R.id.user_tv_userText);

            ivPro[0] = item.findViewById(R.id.user_iv_pro);
            ivPro[1] = item.findViewById(R.id.user_iv_pro1);
            ivPro[2] = item.findViewById(R.id.user_iv_pro2);
            tvPro[0] = item.findViewById(R.id.user_tv_pro);
            tvPro[1] = item.findViewById(R.id.user_tv_pro1);
            tvPro[2] = item.findViewById(R.id.user_tv_pro2);

        }
    }

    @NonNull
    @Override
    public ProcessRecyclerViewAdapter.InfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_process_item, null);
        return new InfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProcessRecyclerViewAdapter.InfoViewHolder infoViewHolder, int position) {
        final int pos = position;

        final int[] processState = infoList.get(pos).getProcessState();
        int process = 0;

        Glide.with(context).load(infoList.get(pos).getPetPortrait()).into(infoViewHolder.petPortrait);
        infoViewHolder.petNickname.setText(infoList.get(pos).getPetNickname());
        infoViewHolder.userText.setText(infoList.get(pos).getUserText());

        //设置进度
        while (process < 3) {
            Glide.with(context).load(infoViewHolder.processIV[processState[process]]).into(infoViewHolder.ivPro[process]);
            infoViewHolder.tvPro[process].setText(infoViewHolder.processTV[process][processState[process]]);
            process++;
        }

        infoViewHolder.cardView.setOnClickListener(cardViewOnClickListener);
        infoViewHolder.petPortrait.setOnClickListener(view -> {
            ImageDialog dialog = new ImageDialog(context, infoList.get(pos).getPetPortrait());
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return infoList == null ? 0 : infoList.size();
    }

    public void getCardViewOnClickListener(View.OnClickListener cardViewOnClickListener) {
        this.cardViewOnClickListener = cardViewOnClickListener;
    }
}
