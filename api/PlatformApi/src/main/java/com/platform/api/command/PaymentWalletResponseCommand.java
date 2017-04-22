package com.platform.api.command;



/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/20/17.
 */
public class PaymentWalletResponseCommand {
    private final static Logger logger = LoggerFactory.getLogger(PaymentWalletResponseCommand.class);

    private String WMI_MERCHANT_ID;

    private String WMI_PAYMENT_AMOUNT;

    private String WMI_COMMISSION_AMOUNT;

    private String WMI_CURRENCY_ID;

    private String WMI_TO_USER_ID;

    private String WMI_PAYMENT_NO;

    private String WMI_ORDER_ID;

    private String WMI_DESCRIPTION;

    private String WMI_SUCCESS_URL;

    private String WMI_FAIL_URL;

    private String WMI_EXPIRED_DATE;

    private String WMI_CREATE_DATE;

    private String WMI_UPDATE_DATE;

    private String WMI_ORDER_STATE;

    private String WMI_SIGNATURE;


    public PaymentWalletResponseCommand() {
    }


    private String getParamsString() {
        return WMI_COMMISSION_AMOUNT
                + WMI_CREATE_DATE
                + WMI_CURRENCY_ID
                + WMI_DESCRIPTION
                + WMI_EXPIRED_DATE
                + WMI_FAIL_URL
                + WMI_MERCHANT_ID
                + WMI_ORDER_ID
                + WMI_ORDER_STATE
                + WMI_PAYMENT_AMOUNT
                + WMI_PAYMENT_NO
                + WMI_SUCCESS_URL
                + WMI_TO_USER_ID
                + WMI_UPDATE_DATE;
    }


    public String getWMI_MERCHANT_ID() {
        return WMI_MERCHANT_ID;
    }


    public void setWMI_MERCHANT_ID(String WMI_MERCHANT_ID) {
        this.WMI_MERCHANT_ID = WMI_MERCHANT_ID;
    }


    public String getWMI_PAYMENT_AMOUNT() {
        return WMI_PAYMENT_AMOUNT;
    }


    public void setWMI_PAYMENT_AMOUNT(String WMI_PAYMENT_AMOUNT) {
        this.WMI_PAYMENT_AMOUNT = WMI_PAYMENT_AMOUNT;
    }


    public String getWMI_COMMISSION_AMOUNT() {
        return WMI_COMMISSION_AMOUNT;
    }


    public void setWMI_COMMISSION_AMOUNT(String WMI_COMMISSION_AMOUNT) {
        this.WMI_COMMISSION_AMOUNT = WMI_COMMISSION_AMOUNT;
    }


    public String getWMI_CURRENCY_ID() {
        return WMI_CURRENCY_ID;
    }


    public void setWMI_CURRENCY_ID(String WMI_CURRENCY_ID) {
        this.WMI_CURRENCY_ID = WMI_CURRENCY_ID;
    }


    public String getWMI_TO_USER_ID() {
        return WMI_TO_USER_ID;
    }


    public void setWMI_TO_USER_ID(String WMI_TO_USER_ID) {
        this.WMI_TO_USER_ID = WMI_TO_USER_ID;
    }


    public String getWMI_PAYMENT_NO() {
        return WMI_PAYMENT_NO;
    }


    public void setWMI_PAYMENT_NO(String WMI_PAYMENT_NO) {
        this.WMI_PAYMENT_NO = WMI_PAYMENT_NO;
    }


    public String getWMI_ORDER_ID() {
        return WMI_ORDER_ID;
    }


    public void setWMI_ORDER_ID(String WMI_ORDER_ID) {
        this.WMI_ORDER_ID = WMI_ORDER_ID;
    }


    public String getWMI_DESCRIPTION() {
        return WMI_DESCRIPTION;
    }


    public void setWMI_DESCRIPTION(String WMI_DESCRIPTION) {
        this.WMI_DESCRIPTION = WMI_DESCRIPTION;
    }


    public String getWMI_SUCCESS_URL() {
        return WMI_SUCCESS_URL;
    }


    public void setWMI_SUCCESS_URL(String WMI_SUCCESS_URL) {
        this.WMI_SUCCESS_URL = WMI_SUCCESS_URL;
    }


    public String getWMI_FAIL_URL() {
        return WMI_FAIL_URL;
    }


    public void setWMI_FAIL_URL(String WMI_FAIL_URL) {
        this.WMI_FAIL_URL = WMI_FAIL_URL;
    }


    public String getWMI_EXPIRED_DATE() {
        return WMI_EXPIRED_DATE;
    }


    public void setWMI_EXPIRED_DATE(String WMI_EXPIRED_DATE) {
        this.WMI_EXPIRED_DATE = WMI_EXPIRED_DATE;
    }


    public String getWMI_CREATE_DATE() {
        return WMI_CREATE_DATE;
    }


    public void setWMI_CREATE_DATE(String WMI_CREATE_DATE) {
        this.WMI_CREATE_DATE = WMI_CREATE_DATE;
    }


    public String getWMI_UPDATE_DATE() {
        return WMI_UPDATE_DATE;
    }


    public void setWMI_UPDATE_DATE(String WMI_UPDATE_DATE) {
        this.WMI_UPDATE_DATE = WMI_UPDATE_DATE;
    }


    public String getWMI_ORDER_STATE() {
        return WMI_ORDER_STATE;
    }


    public void setWMI_ORDER_STATE(String WMI_ORDER_STATE) {
        this.WMI_ORDER_STATE = WMI_ORDER_STATE;
    }


    public String getWMI_SIGNATURE() {
        return WMI_SIGNATURE;
    }


    public void setWMI_SIGNATURE(String WMI_SIGNATURE) {
        this.WMI_SIGNATURE = WMI_SIGNATURE;
    }
}
