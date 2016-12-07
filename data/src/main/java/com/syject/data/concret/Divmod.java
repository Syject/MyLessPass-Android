package com.syject.data.concret;

import com.syject.data.entities.LongDivision;

import java.math.BigInteger;

public class Divmod {

    public static LongDivision exec(BigInteger a, BigInteger b) {
        BigInteger remainder = a.mod(b);// a % b;
        BigInteger quotient = (a.subtract(remainder)).divide(b);
        return new LongDivision(remainder, quotient);
    }
}
