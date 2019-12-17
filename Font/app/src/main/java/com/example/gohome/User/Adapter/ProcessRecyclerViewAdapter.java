package com.example.gohome.User.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gohome.Entity.AdoptInfo;
import com.example.gohome.Entity.ProcessInfo;
import com.example.gohome.R;
import com.example.gohome.User.ImageDialog;

import java.util.List;
import java.util.Random;

public class ProcessRecyclerViewAdapter extends RecyclerView.Adapter<ProcessRecyclerViewAdapter.InfoViewHolder> {
    private List<ProcessInfo> infoList;
    private Context context;

    private View.OnClickListener cardViewOnClickListener;

    public ProcessRecyclerViewAdapter(List<ProcessInfo> infoList, Context context) {
        super();
        this.infoList = infoList;
        this.context = context;
    }

    public void setList(List<ProcessInfo> infoList) {
        this.infoList = infoList;
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
        final int processState = infoList.get(pos).getProcessState();

        Random random = new Random();

//        Glide.with(context).load(infoList.get(pos).getPetPortrait()).into(infoViewHolder.petPortrait);
        Glide.with(context).load(Uri.parse(infoList.get(pos).getPetPortrait())).into(infoViewHolder.petPortrait);
        infoViewHolder.petNickname.setText(infoList.get(pos).getPetNickname());
        infoViewHolder.userText.setText(infoList.get(pos).getUserText());

        switch (processState) {
            //提交成功
            case 1:
                Glide.with(context).load(infoViewHolder.processIV[1]).into(infoViewHolder.ivPro[0]);
                infoViewHolder.tvPro[0].setText(infoViewHolder.processTV[0][1]);
                break;
            //审核成功
            case 2:
                Glide.with(context).load(infoViewHolder.processIV[1]).into(infoViewHolder.ivPro[0]);
                infoViewHolder.tvPro[0].setText(infoViewHolder.processTV[0][1]);
                Glide.with(context).load(infoViewHolder.processIV[1]).into(infoViewHolder.ivPro[1]);
                infoViewHolder.tvPro[1].setText(infoViewHolder.processTV[1][1]);
                break;
            //审核失败
            case 3:
                Glide.with(context).load(infoViewHolder.processIV[1]).into(infoViewHolder.ivPro[0]);
                infoViewHolder.tvPro[0].setText(infoViewHolder.processTV[0][1]);
                Glide.with(context).load(infoViewHolder.processIV[2]).into(infoViewHolder.ivPro[1]);
                infoViewHolder.tvPro[1].setText(infoViewHolder.processTV[1][2]);
                break;
            //申请成功
            case 4:
                Glide.with(context).load(infoViewHolder.processIV[1]).into(infoViewHolder.ivPro[0]);
                infoViewHolder.tvPro[0].setText(infoViewHolder.processTV[0][1]);
                Glide.with(context).load(infoViewHolder.processIV[1]).into(infoViewHolder.ivPro[1]);
                infoViewHolder.tvPro[1].setText(infoViewHolder.processTV[1][1]);
                Glide.with(context).load(infoViewHolder.processIV[1]).into(infoViewHolder.ivPro[2]);
                infoViewHolder.tvPro[2].setText(infoViewHolder.processTV[2][1]);
                break;
            //申请失败
            case 5:
                Glide.with(context).load(infoViewHolder.processIV[1]).into(infoViewHolder.ivPro[0]);
                infoViewHolder.tvPro[0].setText(infoViewHolder.processTV[0][1]);
                Glide.with(context).load(infoViewHolder.processIV[1]).into(infoViewHolder.ivPro[1]);
                infoViewHolder.tvPro[1].setText(infoViewHolder.processTV[1][1]);
                Glide.with(context).load(infoViewHolder.processIV[2]).into(infoViewHolder.ivPro[2]);
                infoViewHolder.tvPro[2].setText(infoViewHolder.processTV[2][2]);
                break;
        }

        infoViewHolder.cardView.setOnClickListener(cardViewOnClickListener);
        infoViewHolder.petPortrait.setOnClickListener(view -> {
            ImageDialog dialog = new ImageDialog(context, Uri.parse(infoList.get(pos).getPetPortrait()));
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
