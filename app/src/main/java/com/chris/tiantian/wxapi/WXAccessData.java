package com.chris.tiantian.wxapi;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by jianjianhong on 20-5-14
 */
public class WXAccessData implements Parcelable, Serializable {

    /**
     * access_token : 33_T12Ot0SkF0wS0vct8ukgZtCYZfX3coWV1G8y6_eN4eSW1Pvv7ilDEGAuuhTzfLT5wUanIUjM_ya78AliWw4iTmb598dOydte6mJddk45I-s
     * expires_in : 7200
     * refresh_token : 33_-urxMSkxrKMVBWqIs3PJQH4p1mKBEAWdkQdnDf1OkEVfoK6jXVlc_cLxj7tS6ZihnpaMb_P4f8jikJI0N-nnaRnvtHSHiAiQDVQpsQzX4SY
     * openid : om3Aet4sKPfXzReYGM0ggUUABwhI
     * scope : snsapi_userinfo
     * unionid : oqs9zwpnxyqWvi6QxWujcCc8Rhzg
     */
    private static final long serialVersionUID = -5809782578272943999L;
    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    protected WXAccessData(Parcel in) {
        access_token = in.readString();
        expires_in = in.readInt();
        refresh_token = in.readString();
        openid = in.readString();
        scope = in.readString();
        unionid = in.readString();
    }

    public static final Creator<WXAccessData> CREATOR = new Creator<WXAccessData>() {
        @Override
        public WXAccessData createFromParcel(Parcel in) {
            return new WXAccessData(in);
        }

        @Override
        public WXAccessData[] newArray(int size) {
            return new WXAccessData[size];
        }
    };

    @Override
    public String toString() {
        return "WXAccessData{" +
                "access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                ", openid='" + openid + '\'' +
                ", scope='" + scope + '\'' +
                ", unionid='" + unionid + '\'' +
                '}';
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(access_token);
        dest.writeInt(expires_in);
        dest.writeString(refresh_token);
        dest.writeString(openid);
        dest.writeString(scope);
        dest.writeString(unionid);
    }
}
