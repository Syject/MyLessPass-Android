package com.syject.data;

import com.syject.data.entities.Lesspass;
import com.syject.data.entities.OneCharPerRule;
import com.syject.data.entities.Password;
import com.syject.data.entities.Template;

import java.math.BigInteger;
import java.util.List;

public interface IPasswordUtils {

    String calcEntropy(Lesspass lesspass, Template template);
    List<String> getConfiguredRules(Template template);
    String getSetOfCharacters(List<String> rules);
    Password consumeEntropy(String generatedPassword, BigInteger quotient, String setOfCharacters, int maxLength);
    OneCharPerRule getOneCharPerRule(BigInteger entropy, List<String> rules);
    String insertStringPseudoRandomly(String generatedPassword, BigInteger entropy, String string);
}
