package com.syject.domain.interactors.concrete;

import com.syject.data.entities.Lesspass;
import com.syject.data.entities.Password;
import com.syject.data.entities.Template;
import com.syject.domain.interactors.IPasswordInteractor;
import com.syject.domain.utils.IPasswordUtils;
import com.syject.domain.utils.PasswordUtils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.math.BigInteger;
import java.util.List;

import rx.Observable;

@EBean
public class PasswordInteractor implements IPasswordInteractor {

    @Bean(PasswordUtils.class)
    protected IPasswordUtils passwordUtils;

    @Override
    public Observable<String> getPassword(Lesspass lesspass, Template template) {
        PasswordDataHolder dataHolder = new PasswordDataHolder();
        return Observable.just(null)
                .flatMap(v -> passwordUtils.calcEntropy(lesspass, template))
                .map(e -> {
                    dataHolder.entropy = e;
                    return null;
                })
                .flatMap(v -> passwordUtils.getConfiguredRules(template))
                .map(configuredRules -> {
                    dataHolder.rules = configuredRules;
                    return dataHolder.rules;
                })
                .flatMap(rs -> passwordUtils.getSetOfCharacters(rs))
                .map(setOfCharacters -> {
                    dataHolder.password = passwordUtils.consumeEntropy("", new BigInteger(dataHolder.entropy, 16), setOfCharacters, template.getLength() - dataHolder.rules.size());
                    return dataHolder.password;
                })
                .flatMap(password -> passwordUtils.getOneCharPerRule(password.getEntropy(), dataHolder.rules))
                .flatMap(charactersToAdd -> passwordUtils.insertStringPseudoRandomly(dataHolder.password.getValue(), charactersToAdd.getEntropy(), charactersToAdd.getValue()));
    }

    private static class PasswordDataHolder {
        public String entropy;
        public List<String> rules;
        public Password password;
    }
}
