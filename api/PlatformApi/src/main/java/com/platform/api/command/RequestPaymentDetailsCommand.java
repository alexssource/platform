package com.platform.api.command;



/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 12/26/16.
 */
public class RequestPaymentDetailsCommand {

    @NotNull
    private Integer tariffId;


    public RequestPaymentDetailsCommand() {
    }


    public Integer getTariffId() {
        return tariffId;
    }


    public void setTariffId(Integer tariffId) {
        this.tariffId = tariffId;
    }
}
