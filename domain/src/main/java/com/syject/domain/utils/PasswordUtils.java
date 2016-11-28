package com.syject.domain.utils;

import com.syject.domain.Template;

public class PasswordUtils {

    public static String generatePassword(String site, String login, String masterPassword, Template template) {
        return site + "/" + login + "/" + masterPassword + "/" + template.getLength() + "/" + template.getCounter();
    }
}
