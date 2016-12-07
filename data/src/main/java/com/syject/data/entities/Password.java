package com.syject.data.entities;

import java.math.BigInteger;

public class Password {
    private String value;
    private BigInteger entropy;

    public Password(String value, BigInteger entropy) {
        this.value = value;
        this.entropy = entropy;
    }

    public BigInteger getEntropy() {
        return entropy;
    }

    public void setEntropy(BigInteger entropy) {
        this.entropy = entropy;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
