package com.arcurus.organisationlist.excepttions;

import org.springframework.expression.spel.ast.OpAnd;

public class ContactException extends IllegalArgumentException{
    public ContactException(String s) {
        super(s);
    }
}
