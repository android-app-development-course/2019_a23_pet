package com.example.gohome.Organizer.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.alertview.AlertView;
import com.bumptech.glide.Glide;
import com.example.gohome.R;
import com.example.gohome.User.Activity.UserAddressActivity;
import com.example.gohome.User.Activity.UserModifyNicknameActivity;
import com.example.gohome.User.Activity.UserPersonalInforActivity;
import com.example.gohome.User.ImageDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.List;

public class OrganizerPersonalInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imPortrait;
    private Uri portrait;
    private TextView tvNickname;
    private String nickname;
    private Intent backIntent = new Intent();

    //向上箭头、向下箭头
    private int upResId = R.drawable.orange_arrow_up,
            downResId = R.drawable.orange_arrow_down;
    //主题风格
    private int themeId = R.style.picture_default_style;
    //状态栏背景色
    private int statusBarColorPrimaryDark = R.color.yellow;

    //记录用户选择，拍照或从相册选择
    boolean mode;
    private int chooseMode = PictureMimeType.ofAll();
    //最大照片数
    final private int maxSelectNum = 1;
    //照片存储列表
    private List<LocalMedia> selectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_personal_infor);

        setTitle("个人信息");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        init();
    }

    private void init() {

        findViewById(R.id.user_rel_portrait).setOnClickListener(this);
        findViewById(R.id.user_rel_nickname).setOnClickListener(this);
        findViewById(R.id.user_rel_address).setOnClickListener(this);

        //初始化头像、昵称
        Intent receivedIntent = getIntent();

        portrait = Uri.parse(receivedIntent.getStringExtra("oldPortrait"));
        imPortrait = findViewById(R.id.user_iv_portrait2);
        Glide.with(this).load(portrait).into(imPortrait);
        imPortrait.setOnClickListener(this);

        nickname = receivedIntent.getStringExtra("oldNickname");
        tvNickname = findViewById(R.id.user_tv_nickname2);
        tvNickname.setText(nickname);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_iv_portrait2:
                ImageDialog dialog = new ImageDialog(this, portrait);
                dialog.show();
                break;
            case R.id.user_rel_portrait:
                //弹出对话框 选择拍照或从相册选择
                new AlertView.Builder().setContext(OrganizerPersonalInfoActivity.this)
                        .setStyle(AlertView.Style.ActionSheet)
                        .setTitle("选择操作")
                        .setMessage(null)
                        .setCancelText("取消")
                        .setDestructive("拍照", "从相册中选择")
                        .setOthers(null)
                        .setOnItemClickListener((object, position) -> {
                            switch (position) {
                                case 0:
                                    mode = false;
                                    selectPhotos();
                                    break;
                                case 1:
                                    mode = true;
                                    selectPhotos();
                                    break;
                            }
                        })
                        .build()
                        .show();
                break;
            case R.id.user_rel_nickname:
                Intent intent = new Intent(this, UserModifyNicknameActivity.class);
                intent.putExtra("oldNickname", nickname);
                startActivityForResult(intent, 1);
                break;
            case R.id.user_rel_address:
                startActivity(new Intent(this, UserAddressActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    LocalMedia media = selectList.get(0);
                    portrait = Uri.fromFile(new File(media.getPath()));
                    Glide.with(this).load(portrait).into(imPortrait);

                    backIntent.putExtra("newPortrait", portrait.toString());
                    setResult(1, backIntent);
                    break;
            }
        }

        if (requestCode == 1) {
            if (resultCode == 1) {
                nickname = data.getStringExtra("newNickname");
                tvNickname.setText(nickname);

                backIntent.putExtra("newNickname", nickname);
                setResult(1, backIntent);
            }
        }
    }

    public void selectPhotos() {
        if (mode) {
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(OrganizerPersonalInfoActivity.this)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(maxSelectNum)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .cameraFileName("")// 使用相机时保存至本地的文件名称,注意这个只在拍照时可以使用，选图时不要用
                    .selectionMode(PictureConfig.SINGLE)// PictureConfig.MULTIPLE : PictureConfig.SINGLE 多选 or 单选
                    .isSingleDirectReturn(false)// 单选模式下是否直接返回
                    .previewImage(true)// 是否可预览图片
                    .isCamera(true)// 是否显示拍照按钮
//                  .isGif(cb_isGif.isChecked())// 是否显示gif图片
//                        .isChangeStatusBarFontColor(isChangeStatusBarFontColor)// 是否关闭白色状态栏字体颜色
                    .setStatusBarColorPrimaryDark(statusBarColorPrimaryDark)// 状态栏背景色
                    .setUpArrowDrawable(upResId)// 设置标题栏右侧箭头图标
                    .setDownArrowDrawable(downResId)// 设置标题栏右侧箭头图标
                    .isOpenStyleCheckNumMode(false)// 是否开启数字选择模式 类似QQ相册
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
//                  .compress(cb_compress.isChecked())// 是否压缩
                    .synOrAsy(false)//同步true或异步false 压缩 默认同步
                    //.compressSavePath(getPath())//压缩图片保存地址
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .openClickSound(false)// 是否开启点击声音
                    .selectionMedia(selectList)// 是否传入已选图片
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
//                    .enableCrop(true)// 是否裁剪
//                    .circleDimmedLayer(true)// 是否圆形裁剪
//                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                    .cropWH(1, 1)// 裁剪宽高比，设置如果大于图片本身宽高则无效
//                    .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                    .rotateEnabled(true) // 裁剪是否可旋转图片
//                    .scaleEnabled(true)// 裁剪是否可放大缩小图片
//                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    //.isDragFrame(true)// 是否可拖动裁剪框(固定)
                    .cutOutQuality(90)// 裁剪输出质量 默认100
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        } else {
            // 单独拍照
            PictureSelector.create(OrganizerPersonalInfoActivity.this)
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // user_back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
