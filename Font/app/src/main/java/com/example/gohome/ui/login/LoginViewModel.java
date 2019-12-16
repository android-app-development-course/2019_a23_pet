package com.example.gohome.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.app.Activity;
import android.util.Log;
import android.util.Patterns;

import com.example.gohome.data.LoginRepository;
import com.example.gohome.data.Result;
import com.example.gohome.data.model.LoggedInUser;
import com.example.gohome.R;

public class LoginViewModel extends ViewModel {

    public static final String USERNAME_REX = "^[a-zA-Z_\u4e00-\u9fa5]{2,8}$";
    public static final String PASSWORD_REX = "^[a-zA-Z0-9_]{8,16}$";

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password, Activity context) {
        Log.i("loginViewModel", "login");
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password, context);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
//            Log.i("loginResult", data.getUserMessage().getUserName());
            if(data.getUserMessage()!=null){
                loginResult.setValue(new LoginResult(new LoggedInUserView(data)));
                Log.i("loginResult", data.getUserMessage().getUserName());
            }else {
                loginResult.setValue(new LoginResult(R.string.login_failed));
            }

        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        return username.matches(USERNAME_REX);
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password.matches(PASSWORD_REX);
    }

}
