package com.example.gohome.data.model;

import com.example.gohome.Entity.AreaOrganizer;
import com.example.gohome.Entity.Member;
import com.example.gohome.Entity.MemberMessage;
import com.example.gohome.Entity.UserMessage;

import java.util.Date;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    public final static int USERTYPE_NORMAL = 0;
    public final static int USERTYPE_MEMBER = 1;
    public final static int USERTYPE_ORGANIZER = 2;
    private int userType;
    private UserMessage userMessage;
    private MemberMessage memberMessage;
    private AreaOrganizer areaOrganizer;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public LoggedInUser(UserMessage userMessage, MemberMessage memberMessage, AreaOrganizer areaOrganizer) {
        this.userMessage = userMessage;
        this.memberMessage = memberMessage;
        this.areaOrganizer = areaOrganizer;
    }

    public LoggedInUser(UserMessage userMessage) {
        this.userMessage = userMessage;
    }

    public UserMessage getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(UserMessage userMessage) {
        this.userMessage = userMessage;
    }

    public MemberMessage getMemberMessage() {
        return memberMessage;
    }

    public void setMemberMessage(MemberMessage memberMessage) {
        this.memberMessage = memberMessage;
    }

    public AreaOrganizer getAreaOrganizer() {
        return areaOrganizer;
    }

    public void setAreaOrganizer(AreaOrganizer areaOrganizer) {
        this.areaOrganizer = areaOrganizer;
    }
}
