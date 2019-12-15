package com.example.gohome.ui.login;

import com.example.gohome.data.model.LoggedInUser;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private LoggedInUser loggedInUser;
    //... other data fields that may be accessible to the UI


    public LoggedInUserView(LoggedInUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(LoggedInUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
