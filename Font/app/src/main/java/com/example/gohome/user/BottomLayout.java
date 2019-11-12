package com.example.gohome.User;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gohome.R;

public class BottomLayout extends LinearLayout {
    private int normalIcon;
    private int focusIcon;
    private boolean isFocused = false;
    private ImageView ivIcon;
    private TextView tvText;

    public BottomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //加载布局文件，与setContentView()效果一样
        LayoutInflater.from(context).inflate(R.layout.activity_user_bottom, this);
        ivIcon = findViewById(R.id.iv_main_bottom_icon);
        tvText = findViewById(R.id.tv_main_bottom_text);
    }

    public void setNormalIcon(int normalIcon) {
        this.normalIcon = normalIcon;
        ivIcon.setImageResource(normalIcon);
    }

    public void setFocusIcon(int focusIcon) {
        this.focusIcon = focusIcon;
    }

    public void setIconText(String text) {
        tvText.setText(text);
    }

    public void setFocused(boolean isFocused) {
        //如果已经处在设置的状态中，就不进行操作
        if (this.isFocused != isFocused) {
            this.isFocused = isFocused;
            if (isFocused) {
                //设置获得焦点后的图片
                //文字加粗
                ivIcon.setImageResource(focusIcon);
                tvText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else {
                //设置获得普通状态的图片
                //文字不加粗
                ivIcon.setImageResource(normalIcon);
                tvText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
        }
    }
}
