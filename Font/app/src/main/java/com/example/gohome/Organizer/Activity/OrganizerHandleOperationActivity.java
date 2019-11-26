package com.example.gohome.Organizer.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.gohome.R;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;
import fj.edittextcount.lib.FJEditTextCount;

public class OrganizerHandleOperationActivity extends AppCompatActivity {

    private CircularProgressButton btn_submit;
    private FJEditTextCount et_description;
    private RadioRealButtonGroup radGro_handleOperationResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_handle_operation);
    }
}
