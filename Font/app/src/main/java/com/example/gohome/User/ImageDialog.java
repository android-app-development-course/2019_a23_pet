package com.example.gohome.User;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.gohome.R;

public class ImageDialog extends Dialog {
    private ImageView magnifiedImage;
    private int imageId;

    public ImageDialog(@NonNull Context context, int imageId) {
        super(context);
        this.imageId = imageId;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_image_dialog);

        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

        magnifiedImage = findViewById(R.id.image_dialog);

        Glide.with(getContext()).load(imageId).into(magnifiedImage);

        magnifiedImage.setOnClickListener(view -> dismiss());

        findViewById(R.id.image_dialog_out).setOnClickListener(view -> dismiss());
    }

}