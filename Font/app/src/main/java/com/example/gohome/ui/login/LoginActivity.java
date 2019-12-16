package com.example.gohome.ui.login;

import android.app.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gohome.Entity.AreaOrganizer;
import com.example.gohome.Entity.MemberMessage;
import com.example.gohome.Entity.UserMessage;
import com.example.gohome.Member.Activity.MemberHomeActivity;
import com.example.gohome.Organizer.OrganizerMain;
import com.example.gohome.R;
import com.example.gohome.Register.RegisterActivity;
import com.example.gohome.User.Activity.UserHomeActivity;
import com.example.gohome.data.model.LoggedInUser;
import com.example.gohome.ui.login.LoginViewModel;
import com.example.gohome.ui.login.LoginViewModelFactory;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ProgressBar loadingProgressBar;
    private TextView registerTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);
        registerTextView = findViewById(R.id.text_register);

        registerTextView.setHighlightColor(ContextCompat.getColor(this, R.color.colorAccent));

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 0x111);
            }
        });

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                registerTextView.setEnabled(true);
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(1);

                //Complete and destroy login activity once successful
//                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString(), LoginActivity.this);
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("loginBtn", "onClick...");
                loadingProgressBar.setVisibility(View.VISIBLE);
                v.setEnabled(false);
                registerTextView.setEnabled(false);
                Log.i("activity login", usernameEditText.getText().toString());
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(), LoginActivity.this);
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
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

    private void updateUiWithUser(LoggedInUserView model) {
////        String welcome = getString(R.string.welcome) + model.getDisplayName();
//        String welcome = getString(R.string.welcome) + " 张咩阿！";
//
//        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        // TODO : initiate successful logged in experience
        LoggedInUser loggedInUser =  loginViewModel.getLoginResult().getValue().getSuccess().getLoggedInUser();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        UserMessage userMessage = loggedInUser.getUserMessage();
        MemberMessage memberMessage = loggedInUser.getMemberMessage();
        AreaOrganizer areaOrganizer = loggedInUser.getAreaOrganizer();
        switch (loggedInUser.getUserType()){
            case LoggedInUser.USERTYPE_MEMBER:
                editor.putInt("memberId", memberMessage.getMessageId());
                editor.putString("memberCreated", memberMessage.getCreated().toString());
            case  LoggedInUser.USERTYPE_ORGANIZER:
                editor.putString("organizerName", areaOrganizer.getOrganizerName());
                editor.putInt("areaId", areaOrganizer.getAreaId());
                editor.putString("areaAddress", areaOrganizer.getAddress());
                editor.putString("organizerCreated", areaOrganizer.getCreated().toString());
            case LoggedInUser.USERTYPE_NORMAL:
                editor.putInt("userId", userMessage.getUserId());
                editor.putString("userName", userMessage.getUserName());
                editor.putString("address", userMessage.getAddress());
                editor.putString("protrait", userMessage.getProtrait());
                editor.putString("telephone", userMessage.getTelephone());
                break;
        }
        editor.commit();
        Intent intent;
        switch (loggedInUser.getUserType()){
            case LoggedInUser.USERTYPE_NORMAL:
                intent = new Intent(getApplicationContext(), UserHomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case LoggedInUser.USERTYPE_MEMBER:
                intent = new Intent(getApplicationContext(), MemberHomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case LoggedInUser.USERTYPE_ORGANIZER:
                intent = new Intent(getApplicationContext(), OrganizerMain.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
        registerTextView.setEnabled(true);
        loginButton.setEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //注册结束返回待补充
    }
}
