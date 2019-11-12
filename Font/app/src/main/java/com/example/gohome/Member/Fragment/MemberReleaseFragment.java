package com.example.gohome.Member.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gohome.R;
import com.unstoppable.submitbuttonview.SubmitButton;

//import butterknife.BindView;

public class MemberReleaseFragment extends Fragment {

//    @BindView(R.id.btn_releaseSubmit)
    SubmitButton btn_releaseSubmit;     //提交领养信息按钮



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState ){
        View view = inflater.inflate(R.layout.fragment_member_release,null);
        return view;
    }


    private void init(){


//        btn_releaseSubmit.setProgress(100);
//
//        btn_releaseSubmit.doResult(true);



    }
}
