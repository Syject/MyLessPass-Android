package com.syject.domain.interactors.concret;

import com.syject.data.IPasswordUtils;
import com.syject.data.concret.PasswordUtils;
import com.syject.data.entities.Lesspass;
import com.syject.data.entities.OneCharPerRule;
import com.syject.data.entities.Password;
import com.syject.data.entities.Template;
import com.syject.domain.interactors.IPasswordInteractor;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.math.BigInteger;
import java.util.List;

@EBean
public class PasswordInteractor implements IPasswordInteractor {

    @Bean(PasswordUtils.class)
    protected IPasswordUtils passwordUtils;

    @Override
    public String getPassword(Lesspass lesspass, Template template) {
        String entropy = passwordUtils.calcEntropy(lesspass, template);
        List<String> rules = passwordUtils.getConfiguredRules(template);
        String setOfCharacters = passwordUtils.getSetOfCharacters(rules);
        Password password = passwordUtils.consumeEntropy("", new BigInteger(entropy, 16), setOfCharacters, template.getLength() - rules.size());
        OneCharPerRule charactersToAdd  = passwordUtils.getOneCharPerRule(password.getEntropy(), rules);
        return passwordUtils.insertStringPseudoRandomly(password.getValue(), charactersToAdd.getEntropy(), charactersToAdd.getValue());
    }
}
