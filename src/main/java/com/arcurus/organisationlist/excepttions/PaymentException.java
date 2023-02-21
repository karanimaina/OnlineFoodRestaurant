package com.arcurus.organisationlist.excepttions;

public class PaymentException  extends  IllegalArgumentException{
    public PaymentException(String s) {
        super(s);
    }
}
