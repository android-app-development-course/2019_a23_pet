package com.example.gohome.User;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gohome.R;

public class TitleBar extends RelativeLayout {
    private ImageView iv_back;
    private TextView tv_title;
    private TextView tv_save;
    private TitleOnClick click;

    public TitleBar(Context context) {
        super(context);
    }
    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }
    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_title, this);

        iv_back = findViewById(R.id.title_back);
        tv_title = findViewById(R.id.title_title);
        tv_save = findViewById(R.id.title_save);

        iv_back.setOnClickListener(view -> click.backClick());
        tv_save.setOnClickListener(view -> click.saveClick());
    }

    public void setTitle(String title){
        if(!title.isEmpty())
            tv_title.setText(title);
    }

    public void setClick(TitleOnClick click) { this.click = click;}

    public interface TitleOnClick {
        void backClick();
        void saveClick();
    }
}
