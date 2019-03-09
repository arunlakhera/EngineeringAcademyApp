package com.pikchillytechnologies.engineeingacademy.Model;

public class UserModel {

    private String mFirstName;
    private String mLastName;
    private String mEmailId;
    private String mPhoneNumber;
    private String mPassword;
    private String mAddress;
    private String mCity;
    private String mState;
    private String mUserPhotoURL;

    public UserModel(String firstname, String lastname, String emailid, String phonenumber, String password, String address, String city, String state, String userphotourl){

        this.mFirstName = firstname;
        this.mLastName = lastname;
        this.mEmailId = emailid;
        this.mPhoneNumber = phonenumber;
        this.mPassword = password;
        this.mAddress = address;
        this.mCity = city;
        this.mState = state;
        this.mUserPhotoURL = userphotourl;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getmEmailId() {
        return mEmailId;
    }

    public void setmEmailId(String mEmailId) {
        this.mEmailId = mEmailId;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmState() {
        return mState;
    }

    public void setmState(String mState) {
        this.mState = mState;
    }

    public String getmUserPhotoURL() {
        return mUserPhotoURL;
    }

    public void setmUserPhotoURL(String mUserPhotoURL) {
        this.mUserPhotoURL = mUserPhotoURL;
    }
}
