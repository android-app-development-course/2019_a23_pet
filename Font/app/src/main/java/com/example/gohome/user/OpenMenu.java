package com.example.gohome.User;

import androidx.annotation.RequiresApi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gohome.R;
import com.ms_square.etsyblur.BlurringView;

public class OpenMenu extends PopupWindow implements View.OnClickListener {
    private Activity mActivity;
    private Handler mHandler;
    private int mWidth, mHeight;
    private RelativeLayout relativeLayout;
    private View bgView;
    private ImageView iv_close;

    OpenMenu(Activity activity) { mActivity = activity; }

    void init(View view){
        mHandler = new Handler();

        Rect frame = new Rect();
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);

        DisplayMetrics metrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        mWidth = metrics.widthPixels;
        mHeight = metrics.heightPixels;

        setWidth(mWidth);
        setHeight(mHeight);

        relativeLayout = (RelativeLayout) LayoutInflater.from(mActivity).inflate(R.layout.activity_open_menu, null);
        setContentView(relativeLayout);

        bgView = relativeLayout.findViewById(R.id.rel_bg);
        bgView.setOnClickListener(this);

        LinearLayout lin_bottom = relativeLayout.findViewById(R.id.lin_bottom);
        lin_bottom.setOnClickListener(this);

        iv_close = relativeLayout.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(this);

        BlurringView blurringView = relativeLayout.findViewById(R.id.blurring_view);
        blurringView.blurredView(view);//模糊背景

        setFocusable(true);
        setOutsideTouchable(true);
        setClippingEnabled(false);//使popup window全屏显示

        TextView tv_send = relativeLayout.findViewById(R.id.tv_user_send);
        TextView tv_help = relativeLayout.findViewById(R.id.tv_user_help);
        tv_send.setOnClickListener(this);
        tv_help.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.rel_bg:
            case R.id.lin_bottom:
            case R.id.iv_close:
                if (isShowing()) closeAnimation();
                break;
            case R.id.tv_user_send:
                break;
            case R.id.tv_user_help:

                break;
        }
    }

    void show(View container){
        showAtLocation(container, Gravity.TOP | Gravity.START, 0, 0);
        mHandler.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                try {
                    int x = mWidth / 2;
                    int y = (int) (mHeight - fromDp2Px(25));
                    Animator animator = ViewAnimationUtils.createCircularReveal(bgView,
                            x, y, 0, bgView.getHeight());
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
//                            bgView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
//                            relativeLayout.setVisibility(View.VISIBLE);
                        }
                    });
                    animator.setDuration(300);
                    animator.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        showAnimation(relativeLayout);
    }

    private void showAnimation(ViewGroup layout){
        try {
            LinearLayout linearLayout = layout.findViewById(R.id.lin_bottom);
            mHandler.post(new Runnable() {
                @Override
                public void run() { //加号旋转
                    iv_close.animate().rotation(90).setDuration(500);
                }
            });
            //菜单项弹出动画
            for(int i = 0; i < linearLayout.getChildCount(); i++){
                final View child = linearLayout.getChildAt(i);
                child.setOnClickListener(this);
                child.setVisibility(View.INVISIBLE);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        child.setVisibility(View.VISIBLE);
                        ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child,
                                "translationY", 600, 0);
                        fadeAnim.setDuration(500);
                        KickBackAnimator kickAnimator = new KickBackAnimator();
                        kickAnimator.setDuration(500);
                        fadeAnim.setEvaluator(kickAnimator);
                        fadeAnim.start();
                    }
                }, i * 50 + 100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void closeAnimation() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                iv_close.animate().rotation(-90).setDuration(500);
            }
        });
        try {
            int x = mWidth / 2;
            int y = (int) (mHeight - fromDp2Px(25));
            Animator animator = ViewAnimationUtils.createCircularReveal(bgView,
                    x, y, bgView.getHeight(), 0);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
//                    relativeLayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
//                    bgView.setVisibility(View.GONE);
                    dismiss();
                }
            });
            animator.setDuration(500);
            animator.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private float fromDp2Px (float dp){
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }
}
