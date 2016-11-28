package com.syject.domain;

public class Template {
    private boolean hasLowerCaseLitters;
    private boolean hasAppearCaseLitters;
    private boolean hasNumbers;
    private boolean hasSymbols;
    private int length;
    private int counter;

    public Template(boolean hasLowerCaseLitters, boolean hasAppearCaseLitters, boolean hasNumbers, boolean hasSymbols, int length, int counter) {
        this.hasLowerCaseLitters = hasLowerCaseLitters;
        this.hasAppearCaseLitters = hasAppearCaseLitters;
        this.hasNumbers = hasNumbers;
        this.hasSymbols = hasSymbols;
        this.length = length;
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isHasSymbols() {
        return hasSymbols;
    }

    public void setHasSymbols(boolean hasSymbols) {
        this.hasSymbols = hasSymbols;
    }

    public boolean isHasNumbers() {
        return hasNumbers;
    }

    public void setHasNumbers(boolean hasNumbers) {
        this.hasNumbers = hasNumbers;
    }

    public boolean isHasAppearCaseLitters() {
        return hasAppearCaseLitters;
    }

    public void setHasAppearCaseLitters(boolean hasAppearCaseLitters) {
        this.hasAppearCaseLitters = hasAppearCaseLitters;
    }

    public boolean isHasLowerCaseLitters() {
        return hasLowerCaseLitters;
    }

    public void setHasLowerCaseLitters(boolean hasLowerCaseLitters) {
        this.hasLowerCaseLitters = hasLowerCaseLitters;
    }
}
