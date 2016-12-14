package com.syject.data.entities;

public class Options {

    private String site;
    private String login;
    private boolean hasLowerCaseLitters;
    private boolean hasAppearCaseLitters;
    private boolean hasNumbers;
    private boolean hasSymbols;
    private int length;
    private int counter;

    public static class Builder {
        private String site;
        private String login;
        private boolean hasLowerCaseLitters = true;
        private boolean hasAppearCaseLitters = true;
        private boolean hasNumbers = true;
        private boolean hasSymbols = true;
        private int length = 16;
        private int counter = 1;

        public Builder site(String site) {
            this.site = site;
            return this;
        }

        public Builder login(String login) {
            this.login = login;
            return this;
        }

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

        public Options build() {
            return new Options(this);
        }
    }

    private Options(Builder builder) {
        hasLowerCaseLitters = builder.hasLowerCaseLitters;
        hasAppearCaseLitters = builder.hasAppearCaseLitters;
        hasNumbers = builder.hasNumbers;
        hasSymbols = builder.hasSymbols;
        length = builder.length;
        counter = builder.counter;
        site = builder.site;
        login = builder.login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Options options = (Options) o;

        if (hasLowerCaseLitters != options.hasLowerCaseLitters) return false;
        if (hasAppearCaseLitters != options.hasAppearCaseLitters) return false;
        if (hasNumbers != options.hasNumbers) return false;
        if (hasSymbols != options.hasSymbols) return false;
        if (length != options.length) return false;
        if (counter != options.counter) return false;
        if (site != null ? !site.equals(options.site) : options.site != null) return false;
        return login != null ? login.equals(options.login) : options.login == null;

    }

    @Override
    public int hashCode() {
        int result = site != null ? site.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (hasLowerCaseLitters ? 1 : 0);
        result = 31 * result + (hasAppearCaseLitters ? 1 : 0);
        result = 31 * result + (hasNumbers ? 1 : 0);
        result = 31 * result + (hasSymbols ? 1 : 0);
        result = 31 * result + length;
        result = 31 * result + counter;
        return result;
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

    public String getSite() {
        return site;
    }

    public String getLogin() {
        return login;
    }
}
