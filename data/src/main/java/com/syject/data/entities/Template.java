package com.syject.data.entities;

import com.syject.data.concret.Pbkdf2;

public class Template {
    private boolean hasLowerCaseLitters;
    private boolean hasAppearCaseLitters;
    private boolean hasNumbers;
    private boolean hasSymbols;
    private int length;
    private int counter;
    private String digest;

    public static class Builder {
        private boolean hasLowerCaseLitters = true;
        private boolean hasAppearCaseLitters = true;
        private boolean hasNumbers = true;
        private boolean hasSymbols = true;
        private int length = 16;
        private int counter = 1;
        private String digest = Pbkdf2.SHA256;

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
}
