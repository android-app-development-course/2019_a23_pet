package com.example.gohome.Member.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gohome.Entity.AdoptAppliment;
import com.example.gohome.Entity.HelpAppliment;
import com.example.gohome.Entity.ResponseAdoptAppliment;
import com.example.gohome.Entity.ResponseHelpAppliment;
import com.example.gohome.R;
import com.example.gohome.User.ImageDialog;
import com.example.gohome.Utils.GetHttpBitMap;
import com.google.gson.Gson;
import com.ramotion.foldingcell.FoldingCell;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MemberCheckUndoFoldingCellAdapter extends RecyclerView.Adapter {

    //记录提交结果
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;

    //数据源
    private List<ResponseAdoptAppliment.responseAdoptAppliment> adoptApplimentList;
    private List<ResponseHelpAppliment.responseHelpAppliment> helpApplimentList;
    private Context context;

    private final String s0 = "♀";
    private final String s1 = "♂";

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    //按钮请求监听
    private View.OnClickListener defaultRequestBtnClickListener;
    //点击返回
    private ItemClickCallBack clickCallBack;

    public int type;    //记录信息类型  ，0为领养，1位救助


    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ItemClickCallBack{
        void onItemClick(int pos);
    }


    public MemberCheckUndoFoldingCellAdapter(Context context, List list, int type){

        this.type = type;

        if(type == 0){
            this.adoptApplimentList = list;
        }
        else{
            this.helpApplimentList = list;
        }
        this.context = context;

    }


    //负责每一个item
    public class AdoptViewHolder extends RecyclerView.ViewHolder{

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


        public AdoptViewHolder(View itemView) {
            super(itemView);

            //cell title的内容
            petPhoto1 = itemView.findViewById(R.id.iv_memberCheckUndoAdoptTitlePetPhoto);
            petName1 = itemView.findViewById(R.id.tv_memberCheckUndoAdoptTitlePetName);
            applicantMessage = itemView.findViewById(R.id.tv_memberCheckUndoAdoptApplicantMessage);
            type = itemView.findViewById(R.id.tv_memberCheckUndoAdoptType);

            titleDate = itemView.findViewById(R.id.tv_memberCheckUndoAdoptTitleDate);
            type = itemView.findViewById(R.id.tv_memberCheckUndoAdoptType);

            //cell content的内容
            petPhoto2 = itemView.findViewById(R.id.iv_memberCheckUndoAdoptContentPetPhoto);
            petName2 = itemView.findViewById(R.id.tv_memberCheckUndoAdoptContentPetName);
            contentDate = itemView.findViewById(R.id.tv_memberCheckUndoAdoptContentDate);
            petGender = itemView.findViewById(R.id.tv_memberCheckUndoAdoptPetGender);
            petAge = itemView.findViewById(R.id.tv_memberCheckUndoAdoptPetAge);
            vaccine = itemView.findViewById(R.id.iv_memberCheckUndoAdoptVaccine);
            sterilization = itemView.findViewById(R.id.iv_memberCheckUndoAdoptSterilization);
            applicantName = itemView.findViewById(R.id.tv_memberCheckUndoAdoptApplicantName);
            applicantJob = itemView.findViewById(R.id.tv_memberCheckUndoAdoptApplicantJob);
            applicantAddress = itemView.findViewById(R.id.tv_memberCheckUndoAdoptApplicantAddress);
            applicantTelephone = itemView.findViewById(R.id.tv_memberCheckUndoAdoptApplicantTel);
            applicantDescription = itemView.findViewById(R.id.tv_memberCheckUndoAdoptApplicantDescription);
            //通过审核按钮
            contentRequestBtn = itemView.findViewById(R.id.btn_memberCheckUndoAdopt);


            vaccine = itemView.findViewById(R.id.iv_memberCheckUndoAdoptVaccine);
            sterilization = itemView.findViewById(R.id.iv_memberCheckUndoAdoptSterilization);


            cell = itemView.findViewById(R.id.fc_memberCheckUndoAdopt);
        }


    }

    //负责每一个求助信息item
    public class HelpViewHolder extends RecyclerView.ViewHolder{

        ImageView petPhoto1, petPhoto2;   //显示申请领养动物的图片
        // title：领养动物姓名、申请人信息提示文字，申请信息类型，申请日期
        TextView petName1, applicantMessage, type, titleDate;
        //content： 申请日期、宠物姓名、宠物性别、宠物年龄、疫苗情况、绝育情况
        TextView contentDate, petName2, petGender, petAge, applicantName,
                applicantAddress, applicantDescription, applicantTelephone;

        ImageView  vaccine,sterilization ;
        TextView contentRequestBtn;
        FoldingCell cell;


        public HelpViewHolder(View itemView) {
            super(itemView);

            //cell title的内容
            petPhoto1 = itemView.findViewById(R.id.iv_memberCheckUndoHelpTitlePetPhoto);
            petName1 = itemView.findViewById(R.id.tv_memberCheckUndoHelpTitlePetName);
            applicantMessage = itemView.findViewById(R.id.tv_memberCheckUndoHelpApplicantMessage);
            type = itemView.findViewById(R.id.tv_memberCheckUndoHelpType);

            titleDate = itemView.findViewById(R.id.tv_memberCheckUndoHelpTitleDate);
            type = itemView.findViewById(R.id.tv_memberCheckUndoHelpType);

            //cell content的内容
            petPhoto2 = itemView.findViewById(R.id.iv_memberCheckUndoHelpContentPetPhoto);
            petName2 = itemView.findViewById(R.id.tv_memberCheckUndoHelpContentPetName);
            contentDate = itemView.findViewById(R.id.tv_memberCheckUndoHelpContentDate);
            petGender = itemView.findViewById(R.id.tv_memberCheckUndoHelpPetGender);
            petAge = itemView.findViewById(R.id.tv_memberCheckUndoHelpPetAge);
            vaccine = itemView.findViewById(R.id.iv_memberCheckUndoHelpVaccine);
            sterilization = itemView.findViewById(R.id.iv_memberCheckUndoHelpSterilization);
            applicantName = itemView.findViewById(R.id.tv_memberCheckUndoHelpApplicantName);
            applicantAddress = itemView.findViewById(R.id.tv_memberCheckUndoHelpApplicantAddress);
            applicantTelephone = itemView.findViewById(R.id.tv_memberCheckUndoHelpApplicantTel);
            applicantDescription = itemView.findViewById(R.id.tv_memberCheckUndoHelpApplicantDescription);
            //通过审核按钮
            contentRequestBtn = itemView.findViewById(R.id.btn_memberCheckUndoHelp);


            vaccine = itemView.findViewById(R.id.iv_memberCheckUndoHelpVaccine);
            sterilization = itemView.findViewById(R.id.iv_memberCheckUndoHelpSterilization);


            cell = itemView.findViewById(R.id.fc_memberCheckUndoHelp);
        }


    }


    //创建新View，被LayoutManager所调用
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){

        //类型为领养申请
        if(type == 0){

            View view = LayoutInflater.from(context).inflate(R.layout.fragment_member_check_undo_adopt_item_cell,null);
            return new AdoptViewHolder(view);
        }
        else{  //类型为求助申请
            View view = LayoutInflater.from(context).inflate(R.layout.fragment_member_check_undo_help_item_cell,null);
            return new HelpViewHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {


//        //信息类型为申请领养类型
        if(type == 0) {


            AdoptViewHolder adoptViewHolder = new AdoptViewHolder(viewHolder.itemView);
            FoldingCell foldingCell = (FoldingCell) viewHolder.itemView;

            String gender = !adoptApplimentList.get(position).getPetGender() ? s0 : s1;
            int icon1 = !adoptApplimentList.get(position).getVaccine() ? R.drawable.no : R.drawable.yes;
            int icon2 = !adoptApplimentList.get(position).getSterilization() ? R.drawable.no : R.drawable.yes;


            //title

            Glide.with(context).load(adoptApplimentList.get(position).getPetPhotoId()).into(adoptViewHolder.petPhoto1);
            adoptViewHolder.petName1.setText(adoptApplimentList.get(position).getPetName());
            adoptViewHolder.applicantMessage.setText(adoptApplimentList.get(position).getApplyName() + "发来了申请，点击查看详情");
            adoptViewHolder.titleDate.setText(adoptApplimentList.get(position).getDate());
            if (type == 0) {    //类型为0则表示领养申请
                adoptViewHolder.type.setText("领养申请");
            }

            //content
            Glide.with(context).load(adoptApplimentList.get(position).getPetPhotoId()).into(adoptViewHolder.petPhoto2);
            adoptViewHolder.petName2.setText(adoptApplimentList.get(position).getPetName());
            adoptViewHolder.petGender.setText(gender);
            adoptViewHolder.petAge.setText(adoptApplimentList.get(position).getPetAge());
            adoptViewHolder.contentDate.setText(adoptApplimentList.get(position).getDate() );
            adoptViewHolder.applicantName.setText(adoptApplimentList.get(position).getApplyName());
            adoptViewHolder.applicantJob.setText(adoptApplimentList.get(position).getJob());
            adoptViewHolder.applicantAddress.setText(adoptApplimentList.get(position).getAddress());
            adoptViewHolder.applicantTelephone.setText(adoptApplimentList.get(position).getTelephone());
            adoptViewHolder.applicantDescription.setText(adoptApplimentList.get(position).getDescription());

            Glide.with(context).load(icon1).into(adoptViewHolder.vaccine);
            Glide.with(context).load(icon2).into(adoptViewHolder.sterilization);
    //            adoptViewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
            //设置审核通过点击事件
            adoptViewHolder.contentRequestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ResponseAdoptAppliment.responseAdoptAppliment responseAdoptAppliment = new ResponseAdoptAppliment.responseAdoptAppliment();
                    responseAdoptAppliment.setAdoptId(adoptApplimentList.get(position).getAdoptId());
                    responseAdoptAppliment.setApplimentId(adoptApplimentList.get(position).getApplimentId());
                    //发送自己的ID
                    responseAdoptAppliment.setHandleId(1);
                    responseAdoptAppliment.setUserId(adoptApplimentList.get(position).getUserId());

                    //创建Handler，在子线程中使用handler发message给主线程
                    @SuppressLint("HandlerLeak") Handler handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            switch (msg.what) {
                                case SUCCESS: {
                                    //更新完成
                                    adoptApplimentList.remove(position);
                                    notifyDataSetChanged();
                                    System.out.println("成功啦啦啦啦啦！！");
                                    break;
                                }
                                case FAIL:
                                {
                                    Toast.makeText(context,"通过审核失败！",Toast.LENGTH_LONG).show();
                                    break;
                                }
                            }
                        }
                    };

                    new Thread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    //建立client
                                    final OkHttpClient[] client = {new OkHttpClient()};
                                    //将传送实体类转为string类型的键值对
                                    Gson gson = new Gson();
                                    String json = gson.toJson(responseAdoptAppliment);

                                    System.out.println("json:"+json);
                                    //设置请求体并设置contentType
                                    RequestBody requestBody = FormBody.create(MediaType.parse("application/json;charset=utf-8"),json);
                                    //请求
                                    Request request=new Request.Builder()
                                            .url(context.getResources().getString(R.string.serverBasePath)+context.getResources().getString(R.string.updateAdoptApplimentToDoing))
                                            .post(requestBody)
                                            .build();
                                    //新建call联结client和request
                                    Call call= client[0].newCall(request);
                                    //新建Message通过Handle与主线程通信
                                    Message msg = new Message();
                                    call.enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            //请求失败的处理
                                            Log.i("RESPONSE:","fail"+e.getMessage());
                                            msg.what = FAIL;
                                            handler.sendMessage(msg);
                                            Log.i("result的值", String.valueOf(false));
                                        }
                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
                                            Log.i("RESPONSE:",response.body().string());
                                            msg.what = SUCCESS;
                                            handler.sendMessage(msg);
                                            Log.i("result的值", String.valueOf(true));
                                        }

                                    });
                                }
                            }).start();

                }

        });

        //控制cell的折叠与收缩
        adoptViewHolder.cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCallBack != null) {
                    clickCallBack.onItemClick(position);
                    // 切换已单击的单元格状态
                    ((FoldingCell) viewHolder.itemView).toggle(false);
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

//        adoptViewHolder.petPhoto2.setOnClickListener(view -> {
//                ImageDialog dialog = new ImageDialog(context, adoptApplimentList.get(position).getPetPhotoId());
//                dialog.show();
//            });

        }else{   //信息为求助申请

            HelpViewHolder helpViewHolder = new HelpViewHolder(viewHolder.itemView);
            FoldingCell foldingCell = (FoldingCell)viewHolder.itemView;

            String gender = !helpApplimentList.get(position).getPetGender() ? s0 : s1;
            int icon1 = !helpApplimentList.get(position).getVaccine() ? R.drawable.no : R.drawable.yes;
            int icon2 = !helpApplimentList.get(position).getSterilization() ? R.drawable.no : R.drawable.yes;

            //title
            Glide.with(context).load(helpApplimentList.get(position).getPetPhotoId()).into(helpViewHolder.petPhoto1);
            helpViewHolder.petName1.setText(helpApplimentList.get(position).getPetName());
            helpViewHolder.applicantMessage.setText(helpApplimentList.get(position).getApplicantName()+"发来了申请，点击查看详情");
            helpViewHolder.titleDate.setText(helpApplimentList.get(position).getDate());
            if(type == 1){    //类型为0则表示领养申请
                helpViewHolder.type.setText("救助申请");
            }

            //content
            Glide.with(context).load(helpApplimentList.get(position).getPetPhotoId()).into(helpViewHolder.petPhoto2);
            helpViewHolder.petName2.setText(helpApplimentList.get(position).getPetName());
            helpViewHolder.petGender.setText(gender);
            helpViewHolder.petAge.setText(helpApplimentList.get(position).getPetAge());
            helpViewHolder.contentDate.setText(helpApplimentList.get(position).getDate());
            helpViewHolder.applicantName.setText(helpApplimentList.get(position).getApplicantName());
            helpViewHolder.applicantAddress.setText(helpApplimentList.get(position).getAddress());
            helpViewHolder.applicantTelephone.setText(helpApplimentList.get(position).getApplicantTel());
            helpViewHolder.applicantDescription.setText(helpApplimentList.get(position).getDescription());

            Glide.with(context).load(icon1).into(helpViewHolder.vaccine);
            Glide.with(context).load(icon2).into(helpViewHolder.sterilization);
            //设置审核通过点击事件
            helpViewHolder.contentRequestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("数据："+helpApplimentList.get(position).getApplimentId());

                    ResponseHelpAppliment.responseHelpAppliment responseHelpAppliment = new ResponseHelpAppliment.responseHelpAppliment();
                    responseHelpAppliment.setApplimentId(helpApplimentList.get(position).getApplimentId());
                    //自己的ID
                    responseHelpAppliment.setHandleId(1);
                    responseHelpAppliment.setUserId(helpApplimentList.get(position).getUserId());

                    //创建Handler，在子线程中使用handler发message给主线程
                    @SuppressLint("HandlerLeak") Handler handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            switch (msg.what) {
                                case SUCCESS: {
                                    //更新完成
                                    helpApplimentList.remove(position);
                                    notifyDataSetChanged();
                                    System.out.println("成功啦啦啦啦啦！！");
                                    break;
                                }
                                case FAIL:
                                {
                                    Toast.makeText(context,"通过审核失败！",Toast.LENGTH_LONG).show();
                                    break;
                                }
                            }
                        }
                    };

                    new Thread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    //建立client
                                    final OkHttpClient[] client = {new OkHttpClient()};
                                    //将传送实体类转为string类型的键值对
                                    Gson gson = new Gson();
                                    String json = gson.toJson(responseHelpAppliment);

                                    System.out.println("json:"+json);
                                    //设置请求体并设置contentType
                                    RequestBody requestBody = FormBody.create(MediaType.parse("application/json;charset=utf-8"),json);
                                    //请求
                                    Request request=new Request.Builder()
                                            .url(context.getResources().getString(R.string.serverBasePath)+context.getResources().getString(R.string.updateHelpApplimentToDoing))
                                            .post(requestBody)
                                            .build();
                                    //新建call联结client和request
                                    Call call= client[0].newCall(request);
                                    //新建Message通过Handle与主线程通信
                                    Message msg = new Message();
                                    call.enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            //请求失败的处理
                                            Log.i("RESPONSE:","fail"+e.getMessage());
                                            msg.what = FAIL;
                                            handler.sendMessage(msg);
                                            Log.i("result的值", String.valueOf(false));
                                        }
                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
                                            Log.i("RESPONSE:",response.body().string());
                                            msg.what = SUCCESS;
                                            handler.sendMessage(msg);
                                            Log.i("result的值", String.valueOf(true));
                                        }

                                    });
                                }
                            }).start();


                }
            });


            //控制cell的折叠与收缩
            helpViewHolder.cell.setOnClickListener(new View.OnClickListener() {
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

//            helpViewHolder.petPhoto2.setOnClickListener(view -> {
//                ImageDialog dialog = new ImageDialog(context, helpApplimentList.get(position).getPetPhotoId());
//                dialog.show();
//            });

        }

    }



    @Override
    public int getItemCount() {
        if(type == 0){
            return adoptApplimentList == null ? 0 : adoptApplimentList.size();
        }else {
            return helpApplimentList == null ? 0 : helpApplimentList.size();
        }
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
