package com.syject.data.concret;

import com.syject.data.IPasswordUtils;
import com.syject.data.entities.Lesspass;
import com.syject.data.entities.LongDivision;
import com.syject.data.entities.OneCharPerRule;
import com.syject.data.entities.Password;
import com.syject.data.entities.Template;

import org.androidannotations.annotations.EBean;

import java.math.BigInteger;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

@EBean
public class PasswordUtils implements IPasswordUtils {

    private final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private final String LOWERCASE = "lowercase";
    private final String UPPERCASE = "uppercase";
    private final String NUMBERS = "numbers";
    private final String SYMBOLS = "symbols";
    private final Map<String, String> characterSubsets;

    public PasswordUtils() {
        characterSubsets = new HashMap<>();
        characterSubsets.put(LOWERCASE, "abcdefghijklmnopqrstuvwxyz");
        characterSubsets.put(UPPERCASE, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        characterSubsets.put(NUMBERS, "0123456789");
        characterSubsets.put(SYMBOLS, "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");
    }

    @Override
    public Observable<String> calcEntropy(Lesspass lesspass, Template template) {
        String salt = lesspass.getSite() + lesspass.getLogin() + Integer.toString(template.getCounter(), 16);

        return Observable.just(salt)
                .flatMap(s -> {
                    switch (template.getDigest()) {
                        case Pbkdf2.SHA1:
                            try {
                                byte[] bytes = Pbkdf2.getEncryptedPasswordSHA1(lesspass.getMasterPassword(), s.getBytes(), 100000, template.getKeylen());
                                return bytesToHex(bytes);
                            } catch (InvalidKeySpecException e) {
                                return Observable.error(e);
                            }
                        default:
                            byte[] bytes = Pbkdf2.getEncryptedPasswordSHA256(lesspass.getMasterPassword(), s.getBytes(), 100000, template.getKeylen());
                            return bytesToHex(bytes);
                    }
                });
    }

    @Override
    public Observable<List<String>> getConfiguredRules(Template template) {
        List<String> rules = new ArrayList<>();

        return Observable.just(rules)
                .map(rs -> {
                    if (template.isHasLowerCaseLitters())
                        rs.add(LOWERCASE);
                    if (template.isHasAppearCaseLitters())
                        rs.add(UPPERCASE);
                    if (template.isHasNumbers())
                        rs.add(NUMBERS);
                    if (template.isHasSymbols())
                        rs.add(SYMBOLS);
                    return rs;
                });
    }

    @Override
    public Observable<String> getSetOfCharacters(List<String> rules) {

        return Observable.just(rules)
                .flatMap(rs -> {
                    if (rs.isEmpty()) {
                        String set = characterSubsets.get(LOWERCASE) + characterSubsets.get(UPPERCASE) +
                                characterSubsets.get(NUMBERS) + characterSubsets.get(SYMBOLS);
                        return Observable.just(set);
                    }
                    StringBuilder sb = new StringBuilder();
                    for (String rule : rs) {
                        sb.append(characterSubsets.get(rule));
                    }
                    return Observable.just(sb.toString());
                });
    }

    @Override
    public Password consumeEntropy(String generatedPassword, BigInteger quotient, String setOfCharacters, int maxLength) {

        if (generatedPassword.toCharArray().length >= maxLength) {
            return new Password(generatedPassword, quotient);
        }
        LongDivision longDivision = Divmod.exec(quotient, BigInteger.valueOf(setOfCharacters.length()));
        generatedPassword += setOfCharacters.charAt(longDivision.getRemainder().intValue());
        return consumeEntropy(generatedPassword, longDivision.getQuotient(), setOfCharacters, maxLength);
    }

    @Override
    public Observable<OneCharPerRule> getOneCharPerRule(BigInteger entropy, List<String> rules) {
        return Observable.just(entropy)
                .flatMap(e -> {
                    String oneCharPerRules = "";
                    for (String rule : rules) {
                        Password password = consumeEntropy("", e, characterSubsets.get(rule), 1);
                        oneCharPerRules += password.getValue();
                        e = password.getEntropy();
                    }
                    return Observable.just(new OneCharPerRule(oneCharPerRules, e));
                });
    }

    @Override
    public Observable<String> insertStringPseudoRandomly(String generatedPassword, BigInteger entropy, String string) {
        for (int i = 0; i < string.length(); i++) {
            LongDivision longDivision = Divmod.exec(entropy, BigInteger.valueOf(generatedPassword.length()));
            generatedPassword = generatedPassword.substring(0, longDivision.getRemainder().intValue()) + string.charAt(i) + generatedPassword.substring(longDivision.getRemainder().intValue());
            entropy = longDivision.getQuotient();
        }
        return Observable.just(generatedPassword);
    }

    private Observable<String> bytesToHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0)
            return Observable.just(String.format("%0" + paddingLength + "d", 0) + hex);
        else
            return Observable.just(hex);
    }

    private String bytesToHexOld(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
