package com.chris.tiantian.entity;

/**
 * Created by jianjianhong on 20-7-19
 */
public class Order {
    private String return_code;
    private String return_msg;
    private String result_code;
    private String err_code;
    private String err_code_des;
    private String trade_type;
    private String prepay_id;
    private String sign;
    private String req_sign;
    private String req_nonce_str;
    private String resp_nonce_str;

    public String getReq_nonce_str() {
        return req_nonce_str;
    }

    public void setReq_nonce_str(String req_nonce_str) {
        this.req_nonce_str = req_nonce_str;
    }

    public String getResp_nonce_str() {
        return resp_nonce_str;
    }

    public void setResp_nonce_str(String resp_nonce_str) {
        this.resp_nonce_str = resp_nonce_str;
    }

    public String getReq_sign() {
        return req_sign;
    }

    public void setReq_sign(String req_sign) {
        this.req_sign = req_sign;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }
}
