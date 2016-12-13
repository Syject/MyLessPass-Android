package com.syject.data.entities;

public class Template {

    public static final String SHA1 = "SHA1";
    public static final String SHA256 = "SHA256";
    public static final int len = 16;

    private boolean hasLowerCaseLitters;
    private boolean hasAppearCaseLitters;
    private boolean hasNumbers;
    private boolean hasSymbols;
    private int length;
    private int keylen;
    private int counter;
    private String digest;

    public static class Builder {
        private boolean hasLowerCaseLitters = true;
        private boolean hasAppearCaseLitters = true;
        private boolean hasNumbers = true;
        private boolean hasSymbols = true;
        private int length = 16;
        private int keylen = 32;
        private int counter = 1;
        private String digest = SHA256;

        public Builder hasLowerCaseLitters(boolean b) {
            hasLowerCaseLitters = b;
            return this;
        }

        public Builder hasAppearCaseLitters(boolean b) {
            hasAppearCaseLitters = b;
            return this;
        }

        public Builder hasNumbers(boolean b) {
            hasNumbers = b;
            return this;
        }

        public Builder hasSymbols(boolean b) {
            hasSymbols = b;
            return this;
        }

        public Builder length(int len) {
            length = len;
            return this;
        }

        public Builder counter(int count) {
            counter = count;
            return this;
        }

        public Builder digest(String dig) {
            digest = dig;
            return this;
        }

        public Builder keylen(int keyl) {
            keylen = keyl;
            return this;
        }

        public Template build() {
            return new Template(this);
        }
    }

    private Template(Builder builder) {
        hasLowerCaseLitters = builder.hasLowerCaseLitters;
        hasAppearCaseLitters = builder.hasAppearCaseLitters;
        hasNumbers = builder.hasNumbers;
        hasSymbols = builder.hasSymbols;
        length = builder.length;
        counter = builder.counter;
        digest = builder.digest;
        keylen = builder.keylen;
    }

    public int getCounter() {
        return counter;
    }

    public int getLength() {
        return length;
    }

    public boolean isHasSymbols() {
        return hasSymbols;
    }

    public boolean isHasNumbers() {
        return hasNumbers;
    }

    public boolean isHasAppearCaseLitters() {
        return hasAppearCaseLitters;
    }

    public boolean isHasLowerCaseLitters() {
        return hasLowerCaseLitters;
    }

    public String getDigest() {
        return digest;
    }

    public int getKeylen() {
        return keylen;
    }
}
