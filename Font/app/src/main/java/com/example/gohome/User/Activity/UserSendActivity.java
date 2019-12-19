package com.example.gohome.User.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.alertview.AlertView;
import com.example.gohome.Member.Adapter.GridImageAdapter;
import com.example.gohome.Member.FullyGridLayoutManager;
import com.example.gohome.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;
import fj.edittextcount.lib.FJEditTextCount;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class UserSendActivity extends AppCompatActivity {

    public static final int SUCCESS = 1;
    public static final int FAIL = 0;

    Integer userId;
    String phone, address;

    private EditText et_sendUserName;
    private EditText et_sendUserPhone;
    private EditText et_sendUserAddress;
    private EditText et_sendPetName;
    private EditText et_sendAge;
    private EditText et_sendType;
    private FJEditTextCount et_sendDesc;
    private RadioRealButtonGroup radGro_sendGender;
    private RadioRealButtonGroup radGro_sendStl;
    private RadioRealButtonGroup radGro_sendVcn;
    private CircularProgressButton btn_submit;

    //向上箭头、向下箭头
    private int upResId, downResId;
    //主题风格
    private int themeId;
    //状态栏背景色
    private int statusBarColorPrimaryDark;
    //弹出对话框 选择照片选择方式
    NiftyDialogBuilder dialogBuilderSelect;

    //记录用户选择，拍照或从相册选择
    boolean mode;
    private int chooseMode = PictureMimeType.ofAll();
    //最大照片数
    final private int maxSelectNum = 9;
    //照片存储列表
    private List<LocalMedia> selectList;

    private GridImageAdapter adapter;

    //性别、绝育、疫苗
    int Gender, Stl, Vcn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_send);
        setTitle("填写送养信息");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // user_back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        phone = sharedPreferences.getString("telephone", "");
        address = sharedPreferences.getString("address", "");

        et_sendUserName = findViewById(R.id.et_sendUserName);
        et_sendUserPhone = findViewById(R.id.et_sendUserPhone);
        et_sendUserPhone.setText(phone);
        et_sendUserAddress = findViewById(R.id.et_sendUserAddress);
        et_sendUserAddress.setText(address);
        et_sendPetName = findViewById(R.id.et_sendPetName);
        et_sendAge = findViewById(R.id.et_sendAge);
        et_sendType = findViewById(R.id.et_sendType);
        et_sendDesc = findViewById(R.id.et_sendDesc);

        radGro_sendGender = findViewById(R.id.radGro_sendGender);
        radGro_sendStl = findViewById(R.id.radGro_sendStl);
        radGro_sendVcn = findViewById(R.id.radGro_sendVcn);

        RecyclerView recyclerViewImg = findViewById(R.id.rv_sendImg);
        btn_submit = findViewById(R.id.btn_sendSubmit);

        upResId = R.drawable.orange_arrow_up;
        downResId = R.drawable.orange_arrow_down;
        themeId = R.style.picture_default_style;
        statusBarColorPrimaryDark = R.color.yellow;
        dialogBuilderSelect = NiftyDialogBuilder.getInstance(this);

        //每行显示4张照片
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4,
                GridLayoutManager.VERTICAL, false);
        recyclerViewImg.setLayoutManager(manager);

        selectList = new ArrayList<>();

        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recyclerViewImg.setAdapter(adapter);

        initListener();
    }

    private void initListener() {
        radGro_sendGender.setOnClickedButtonListener((btn, pos) -> Gender = pos);

        radGro_sendGender.setOnPositionChangedListener((btn, curPos, lastPos) -> Gender = curPos);

        radGro_sendStl.setOnClickedButtonListener((btn, pos) -> Stl = pos);

        radGro_sendStl.setOnPositionChangedListener((btn, curPos, lastPos) -> Stl = curPos);

        radGro_sendVcn.setOnClickedButtonListener((btn, pos) -> Vcn = pos);

        radGro_sendVcn.setOnPositionChangedListener((btn, curPos, lastPos) -> Vcn = curPos);

        btn_submit.setOnClickListener(view -> {
            btn_submit.startAnimation();

            Bitmap bitmapDone = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_action_done);
            Bitmap bitmapFail = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_action_fail);

            //设置提交成功的图标和颜色
            btn_submit.doneLoadingAnimation(getResources().getColor(R.color.green), bitmapDone);
            //设置提交失败的图标和颜色
//            btn_submit.doneLoadingAnimation(getResources().getColor(R.color.red), bitmapFail);

            //提示提交成功toast
            Toast toast = TastyToast.makeText(this, "提交成功!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            //设置toast显示位置
            toast.setGravity(Gravity.CENTER,0,0);
            //调用show使得toast得以显示
            toast.show();

            cleanUp();
        });

        adapter.setOnItemClickListener((position, v) -> {
            if (selectList.size() > 0) {
                LocalMedia media = selectList.get(position);
                String mimeType = media.getMimeType();
                int mediaType = PictureMimeType.getMimeType(mimeType);
                switch (mediaType) {
                    case PictureConfig.TYPE_VIDEO:
                        // 预览视频
                        PictureSelector.create(this).externalPictureVideo(media.getPath());
                        break;
                    case PictureConfig.TYPE_AUDIO:
                        // 预览音频
                        PictureSelector.create(this).externalPictureAudio(media.getPath());
                        break;
                    default:
                        // 预览图片 可自定长按保存路径
                        PictureSelector.create(this).themeStyle(themeId).openExternalPreview(position, selectList);
                        break;
                }
            }
        });
    }

    private void cleanUp(){
        //清空editText和选择框
        et_sendUserName.setText("");
        et_sendUserPhone.setText("");
        et_sendUserAddress.setText("");
        et_sendPetName.setText("");
        et_sendAge.setText("");
        et_sendType.setText("");
        et_sendDesc.setText("");

        //还原提交按钮
//        btn_submit.revertAnimation();
    }

    //给上传图片添加点击事件
    private GridImageAdapter.onAddPicClickListener
            onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            //弹出对话框 选择拍照或从相册选择
            new AlertView.Builder().setContext(UserSendActivity.this)
                    .setStyle(AlertView.Style.ActionSheet)
                    .setTitle("选择操作")
                    .setMessage(null)
                    .setCancelText("取消")
                    .setDestructive("拍照", "从相册中选择")
                    .setOthers(null)
                    .setOnItemClickListener((object, position) -> {
                        switch (position){
                            case 0: mode = false;
                                selectPhotos(); break;
                            case 1: mode = true;
                                selectPhotos(); break;
                        }
                    })
                    .build()
                    .show();
        }
    };

    public void selectPhotos(){
        if (mode) {
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(UserSendActivity.this)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(maxSelectNum)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .cameraFileName("")// 使用相机时保存至本地的文件名称,注意这个只在拍照时可以使用，选图时不要用
                    .selectionMode(PictureConfig.MULTIPLE)// PictureConfig.MULTIPLE : PictureConfig.SINGLE 多选 or 单选
                    .isSingleDirectReturn(false)// 单选模式下是否直接返回
                    .previewImage(true)// 是否可预览图片
                    .isCamera(true)// 是否显示拍照按钮
//                        .isChangeStatusBarFontColor(isChangeStatusBarFontColor)// 是否关闭白色状态栏字体颜色
                    .setStatusBarColorPrimaryDark(statusBarColorPrimaryDark)// 状态栏背景色
                    .setUpArrowDrawable(upResId)// 设置标题栏右侧箭头图标
                    .setDownArrowDrawable(downResId)// 设置标题栏右侧箭头图标
                    .isOpenStyleCheckNumMode(true)// 是否开启数字选择模式 类似QQ相册
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
//                        .enableCrop(cb_crop.isChecked())// 是否裁剪
//                        .compress(cb_compress.isChecked())// 是否压缩
                    .synOrAsy(false)//同步true或异步false 压缩 默认同步
                    //.compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                        .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
//                        .isGif(cb_isGif.isChecked())// 是否显示gif图片
//                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
//                        .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
//                        .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                        .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .openClickSound(false)// 是否开启点击声音
                    .selectionMedia(selectList)// 是否传入已选图片
                    //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.cropCompressQuality(90)// 废弃 改用cutOutQuality()
                    .cutOutQuality(90)// 裁剪输出质量 默认100
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    //.rotateEnabled(true) // 裁剪是否可旋转图片
                    //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                    //.videoQuality()// 视频录制质量 0 or 1
                    //.videoSecond()//显示多少秒以内的视频or音频也可适用
                    //.recordVideoSecond()//录制视频秒数 默认60s
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        } else {
            // 单独拍照
            PictureSelector.create(UserSendActivity.this)
                    .openCamera(chooseMode)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                    .theme(themeId)// 主题样式设置 具体参考 values/styles
                    .maxSelectNum(maxSelectNum)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    //.querySpecifiedFormatSuffix(PictureMimeType.ofPNG())// 查询指定后缀格式资源
                    .selectionMode(PictureConfig.MULTIPLE)// PictureConfig.MULTIPLE : PictureConfig.SINGLE  多选 or 单选
                    .previewImage(true)// 是否可预览图片
//                        .previewVideo(cb_preview_video.isChecked())// 是否可预览视频
//                        .enablePreviewAudio(cb_preview_audio.isChecked()) // 是否可播放音频
                    .isCamera(true)// 是否显示拍照按钮
//                        .isChangeStatusBarFontColor(isChangeStatusBarFontColor)// 是否关闭白色状态栏字体颜色
                    .setStatusBarColorPrimaryDark(statusBarColorPrimaryDark)// 状态栏背景色
                    .isOpenStyleCheckNumMode(true)// 是否开启数字选择模式 类似QQ相册
                    .setUpArrowDrawable(upResId)// 设置标题栏右侧箭头图标
                    .setDownArrowDrawable(downResId)// 设置标题栏右侧箭头图标
//                        .enableCrop(cb_crop.isChecked())// 是否裁剪
//                        .compress(cb_compress.isChecked())// 是否压缩
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                        .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
//                        .isGif(cb_isGif.isChecked())// 是否显示gif图片
//                        .freeStyleCropEnabled(cb_styleCrop.isChecked())// 裁剪框是否可拖拽
//                        .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
//                        .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                        .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .openClickSound(true)// 是否开启点击声音
                    .selectionMedia(selectList)// 是否传入已选图片
                    .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.cropCompressQuality(90)// 废弃 改用cutOutQuality()
                    .cutOutQuality(90)// 裁剪输出质量 默认100
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    //.rotateEnabled() // 裁剪是否可旋转图片
                    //.scaleEnabled()// 裁剪是否可放大缩小图片
                    //.videoQuality()// 视频录制质量 0 or 1
                    //.videoSecond()////显示多少秒以内的视频or音频也可适用
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }
    }

    //返回结果并显示
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    // 4.media.getAndroidQToPath();为Android Q版本特有返回的字段，此字段有值就用来做上传使用
                    for (LocalMedia media : selectList) {
                        Log.i(TAG, "原图---->" + media.getPath());
                        Log.i(TAG, "裁剪---->" + media.getCutPath());
                        Log.i(TAG, "压缩---->" + media.getCompressPath());
                        Log.i(TAG, "Android Q 特有Path---->" + media.getAndroidQToPath());
                    }
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
/*


            Integer petGender = Gender;
            Boolean vaccinate = Vcn == 0;
            Boolean sterilize = Stl == 0;

            Bitmap bitmapDone = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_action_done);
            Bitmap bitmapFail = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_action_fail);

            //设置提交成功的图标和颜色
            btn_submit.doneLoadingAnimation(getResources().getColor(R.color.green), bitmapDone);
            //设置提交失败的图标和颜色
            btn_submit.doneLoadingAnimation(getResources().getColor(R.color.red), bitmapFail);

            String userName = et_sendUserName.getText().toString();
            String phone = et_sendUserPhone.getText().toString();
            String address = et_sendUserAddress.getText().toString();
            String petName = et_sendPetName.getText().toString();
            String petAge = et_sendAge.getText().toString();
            String type = et_sendType.getText().toString();
            String description = et_sendDesc.getText();

            if ("".equals(userName) || "".equals(phone) || "".equals(address) ||
                    "".equals(petName) || "".equals(petAge) || "".equals(type) ||
                    "".equals(description)){
                btn_submit.doneLoadingAnimation(getResources().getColor(R.color.red), bitmapFail);
                Toast.makeText(UserSendActivity.this, "请不要留空", Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(this::cleanUp, 3000);
                return;
            }
            if(type.contains("猫")) petType = 0;
            if (type.contains("狗")) petType = 1;

            Handler handler = new Handler() {
                public void handleMessage(Message message){
                    switch (message.what){
                        case SUCCESS:
                            btn_submit.doneLoadingAnimation(getResources().getColor(R.color.green), bitmapDone);
                            Toast.makeText(UserSendActivity.this,"提交成功，请耐心等候！",Toast.LENGTH_LONG).show();
                            break;

                        case FAIL:
                            btn_submit.doneLoadingAnimation(getResources().getColor(R.color.red), bitmapFail);
                            Toast.makeText(UserSendActivity.this,"提交失败，请重新提交！",Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            };

            new Thread(()->
            {
                Gson gson = new Gson();
                String json;
//                Log.i("表单json: ", json);
                RequestBody requestBody = FormBody.create(MediaType.parse("application/json;charset=utf-8"),json);

                Request request = new Request.Builder()
                        .url(getResources().getString(R.string.serverPath) +
                                getResources().getString(R.string.insertHelpApplication))
                        .post(requestBody)
                        .build();
                Message msg = new Message();
                OkHttpClient okHttpClient = new OkHttpClient();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("提交失败:", e.getMessage());
                        msg.what = FAIL;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("提交成功:", response.body().string());
                        msg.what = SUCCESS;
                        handler.sendMessage(msg);
                    }
                });
            }).start();

            Handler mHandler = new Handler();
            mHandler.postDelayed(this::cleanUp, 3000);
        });


*/