package com.example.gohome.User;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.gohome.R;

public class ImageDialog extends Dialog {
    private ImageView magnifiedImage;
    private int intImageId;
    private Uri uriImageId;

    public ImageDialog(@NonNull Context context, int intImageId) {
        super(context);
        this.intImageId = intImageId;
        uriImageId = null;
    }

    public ImageDialog(@NonNull Context context, Uri uriImageId) {
        super(context);
        this.uriImageId = uriImageId;
        intImageId = 0;
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

        if (uriImageId == null)
            Glide.with(getContext()).load(intImageId).into(magnifiedImage);
        else
            Glide.with(getContext()).load(uriImageId).into(magnifiedImage);

        magnifiedImage.setOnClickListener(view -> dismiss());

        findViewById(R.id.image_dialog_out).setOnClickListener(view -> dismiss());
    }

}