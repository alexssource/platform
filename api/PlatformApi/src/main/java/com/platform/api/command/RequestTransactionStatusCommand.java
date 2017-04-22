package com.platform.api.command;



/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 2/4/17.
 */
public class RequestTransactionStatusCommand {
    private UUID transactionId;


    public RequestTransactionStatusCommand() {
    }


    public RequestTransactionStatusCommand(UUID transactionId) {
        this.transactionId = transactionId;
    }


    public UUID getTransactionId() {
        return transactionId;
    }


    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }
}
