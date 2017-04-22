package com.platform.api.command;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 27.12.16.
 */
public class PaymentPaysystemResponseCommand {
    private final static Logger logger = LoggerFactory.getLogger(PaymentPaysystemResponseCommand.class);

    private String ik_am;           // amount

    private String ik_co_id;        // ID кассы

    private String ik_co_prs_id;    // ID кошелька кассы

    private String ik_co_rfn;       // Сумма зачисления на счёт кассы

    private String ik_cur;          // Валюта платежа

    private String ik_desc;         // ID транзакции в ... UUID

    private String ik_inv_crt;      // Дата создания платежа на интеркассе

    private String ik_inv_id;       // ID платежа интеркассы

    private String ik_inv_prc;      // Дата когда платеж обработан

    private String ik_inv_st;       // Состояние платежа

    private String ik_pm_no;        // Номер платежа ... Platform. Не используется, вместо него ik_desc

    private String ik_ps_price;     // Сумма платежа в платежной системе

    private String ik_pw_via;       // Способ оплаты

    private String ik_trn_id;       // ID транзакции интеркассы

    private String ik_sign;         // Цифровая подпись


    public PaymentPaysystemResponseCommand() {
    }


    public boolean isValid(PaymentSignUtil paymentSignUtil) {
        String paramsString = getParamsString();
        String computedSign = paymentSignUtil.createSign(paramsString);
        boolean signValid = computedSign.equals(ik_sign);

        if (!signValid) {
            logger.error("Error validating payment system response. Sign is not valid. Transaction id: {}", ik_desc);
            return false;
        }

        // TODO: validate payment system IP, etc.
        return true;
    }


    private String getParamsString() {
        StringBuilder builder = new StringBuilder();
        builder.append(ik_am).append(':');
        builder.append(ik_co_id).append(':');
        builder.append(ik_co_prs_id).append(':');
        builder.append(ik_co_rfn).append(':');
        builder.append(ik_cur).append(':');
        builder.append(ik_desc).append(':');
        builder.append(ik_inv_crt).append(':');
        builder.append(ik_inv_id).append(':');
        builder.append(ik_inv_prc).append(':');
        builder.append(ik_inv_st).append(':');
        builder.append(ik_pm_no).append(':');
        builder.append(ik_ps_price).append(':');
        builder.append(ik_pw_via).append(':');
        builder.append(ik_trn_id);
        return builder.toString();
    }


    public String getIk_am() {
        return ik_am;
    }


    public void setIk_am(String ik_am) {
        this.ik_am = ik_am;
    }


    public String getIk_co_id() {
        return ik_co_id;
    }


    public void setIk_co_id(String ik_co_id) {
        this.ik_co_id = ik_co_id;
    }


    public String getIk_co_prs_id() {
        return ik_co_prs_id;
    }


    public void setIk_co_prs_id(String ik_co_prs_id) {
        this.ik_co_prs_id = ik_co_prs_id;
    }


    public String getIk_co_rfn() {
        return ik_co_rfn;
    }


    public void setIk_co_rfn(String ik_co_rfn) {
        this.ik_co_rfn = ik_co_rfn;
    }


    public String getIk_cur() {
        return ik_cur;
    }


    public void setIk_cur(String ik_cur) {
        this.ik_cur = ik_cur;
    }


    public String getIk_desc() {
        return ik_desc;
    }


    public void setIk_desc(String ik_desc) {
        this.ik_desc = ik_desc;
    }


    public String getIk_inv_crt() {
        return ik_inv_crt;
    }


    public void setIk_inv_crt(String ik_inv_crt) {
        this.ik_inv_crt = ik_inv_crt;
    }


    public String getIk_inv_id() {
        return ik_inv_id;
    }


    public void setIk_inv_id(String ik_inv_id) {
        this.ik_inv_id = ik_inv_id;
    }


    public String getIk_inv_prc() {
        return ik_inv_prc;
    }


    public void setIk_inv_prc(String ik_inv_prc) {
        this.ik_inv_prc = ik_inv_prc;
    }


    public String getIk_inv_st() {
        return ik_inv_st;
    }


    public void setIk_inv_st(String ik_inv_st) {
        this.ik_inv_st = ik_inv_st;
    }


    public String getIk_pm_no() {
        return ik_pm_no;
    }


    public void setIk_pm_no(String ik_pm_no) {
        this.ik_pm_no = ik_pm_no;
    }


    public String getIk_ps_price() {
        return ik_ps_price;
    }


    public void setIk_ps_price(String ik_ps_price) {
        this.ik_ps_price = ik_ps_price;
    }


    public String getIk_pw_via() {
        return ik_pw_via;
    }


    public void setIk_pw_via(String ik_pw_via) {
        this.ik_pw_via = ik_pw_via;
    }


    public String getIk_trn_id() {
        return ik_trn_id;
    }


    public void setIk_trn_id(String ik_trn_id) {
        this.ik_trn_id = ik_trn_id;
    }


    public void setIk_sign(String ik_sign) {
        this.ik_sign = ik_sign;
    }
}
