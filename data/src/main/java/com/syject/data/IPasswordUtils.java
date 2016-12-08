package com.syject.data;

import com.syject.data.entities.Lesspass;
import com.syject.data.entities.OneCharPerRule;
import com.syject.data.entities.Password;
import com.syject.data.entities.Template;

import java.math.BigInteger;
import java.util.List;

import rx.Observable;

public interface IPasswordUtils {

    Observable<String> calcEntropy(Lesspass lesspass, Template template);
    Observable<List<String>> getConfiguredRules(Template template);
    Observable<String> getSetOfCharacters(List<String> rules);
    Password consumeEntropy(String generatedPassword, BigInteger quotient, String setOfCharacters, int maxLength);
    Observable<OneCharPerRule> getOneCharPerRule(BigInteger entropy, List<String> rules);
    Observable<String> insertStringPseudoRandomly(String generatedPassword, BigInteger entropy, String string);
}
