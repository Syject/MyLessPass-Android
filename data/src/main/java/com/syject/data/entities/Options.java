package com.syject.data.entities;

import java.io.Serializable;

public class Options implements Serializable {

    private String id;
    private String site;
    private String login;
    private boolean lowercase;
    private boolean uppercase;
    private boolean numbers;
    private boolean symbols;
    private int length;
    private int counter;
    private int version;

    public static class Builder {
        private String id;
        private String site;
        private String login;
        private boolean hasLowerCaseLetters = true;
        private boolean hasUpperCaseLetters = true;
        private boolean hasNumbers = true;
        private boolean hasSymbols = true;
        private int length = 16;
        private int counter = 1;
        private int version = 2;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder site(String site) {
            this.site = site;
            return this;
        }

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder hasLowerCaseLitters(boolean b) {
            hasLowerCaseLetters = b;
            return this;
        }

        public Builder hasAppearCaseLitters(boolean b) {
            hasUpperCaseLetters = b;
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

        public Builder version(int vers) {
            version = vers;
            return this;
        }

        public Options build() {
            return new Options(this);
        }
    }

    private Options(Builder builder) {
        lowercase = builder.hasLowerCaseLetters;
        uppercase = builder.hasUpperCaseLetters;
        numbers = builder.hasNumbers;
        symbols = builder.hasSymbols;
        length = builder.length;
        counter = builder.counter;
        id = builder.id;
        site = builder.site;
        login = builder.login;
        version = builder.version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Options options = (Options) o;

        if (lowercase != options.lowercase) return false;
        if (uppercase != options.uppercase) return false;
        if (numbers != options.numbers) return false;
        if (symbols != options.symbols) return false;
        if (length != options.length) return false;
        if (counter != options.counter) return false;
        if (version != options.version) return false;
        if (id != null ? !id.equals(options.id) : options.id != null) return false;
        if (site != null ? !site.equals(options.site) : options.site != null) return false;
        return login != null ? login.equals(options.login) : options.login == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (site != null ? site.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (lowercase ? 1 : 0);
        result = 31 * result + (uppercase ? 1 : 0);
        result = 31 * result + (numbers ? 1 : 0);
        result = 31 * result + (symbols ? 1 : 0);
        result = 31 * result + length;
        result = 31 * result + counter;
        result = 31 * result + version;
        return result;
    }

    public int getCounter() {
        return counter;
    }

    public int getLength() {
        return length;
    }

    public boolean isSymbols() {
        return symbols;
    }

    public boolean isNumbers() {
        return numbers;
    }

    public boolean isUppercase() {
        return uppercase;
    }

    public boolean isLowercase() {
        return lowercase;
    }

    public String getId() {
        return id;
    }

    public String getSite() {
        return site;
    }

    public String getLogin() {
        return login;
    }

    public int getVersion() {
        return version;
    }
}
