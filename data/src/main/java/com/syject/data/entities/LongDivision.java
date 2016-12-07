package com.syject.data.entities;

import java.math.BigInteger;

public class LongDivision {

    private BigInteger remainder;
    private BigInteger quotient;

    public LongDivision(BigInteger remainder, BigInteger quotient) {
        this.remainder = remainder;
        this.quotient = quotient;
    }

    public BigInteger getRemainder() {
        return remainder;
    }

    public void setRemainder(BigInteger remainder) {
        this.remainder = remainder;
    }

    public BigInteger getQuotient() {
        return quotient;
    }

    public void setQuotient(BigInteger quotient) {
        this.quotient = quotient;
    }
}
