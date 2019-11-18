package com.example.gohome.Member.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gohome.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.List;

public class MemberCheckUndoRecyclerViewAdapter extends RecyclerView.Adapter<MemberCheckUndoRecyclerViewAdapter.ViewHolder> {

    //数据源
    private List<String> mList;
    private Context context;


    public MemberCheckUndoRecyclerViewAdapter(Context context, List<String> list){
        mList = list;
        this.context = context;
    }


    //负责每一个item
    public class ViewHolder extends RecyclerView.ViewHolder{
//        TextView mView;
        FoldingCell fc_checkUndo;
        TextView tv_one;
        TextView tv_two;


        public ViewHolder(View itemView) {
            super(itemView);
//            mView = itemView.findViewById(R.id.item_tx);
            fc_checkUndo = itemView.findViewById(R.id.item_fcCheckUndo);
            tv_one = itemView.findViewById(R.id.tv_one);
            tv_two = itemView.findViewById(R.id.tv_two);
        }
    }

    @NonNull
    @Override
    public MemberCheckUndoRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_member_check_undo_item_cell,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MemberCheckUndoRecyclerViewAdapter.ViewHolder viewHolder, int position) {
//        viewHolder.mView.setText(mList.get(position));
        viewHolder.tv_one.setText(mList.get(position));
        viewHolder.tv_two.setText(mList.get(position));


    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
}
