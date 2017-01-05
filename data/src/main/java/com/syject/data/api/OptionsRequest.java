package com.syject.data.api;

import com.syject.data.entities.Options;

public class OptionsRequest {

    public String id;
    public String site;
    public String login;
    public boolean lowercase;
    public boolean uppercase;
    public boolean numbers;
    public boolean symbols;
    public int length;
    public int counter;
    public int version;

    public OptionsRequest(Options options) {
        site = options.getSite();
        login = options.getLogin();
        lowercase = options.isHasLowerCaseLitters();
        uppercase = options.isHasAppearCaseLitters();
        numbers = options.isHasNumbers();
        symbols = options.isHasSymbols();
        length = options.getLength();
        counter = options.getCounter();
        version = options.getVersion();
    }
}
