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
        characterSubsets.put(SYMBOLS, "!\"#$%&\\'()*+,-./:;<=>?@[\\\\]^_`{|}~");
    }

    @Override
    public String calcEntropy(Lesspass lesspass, Template template) {
        String salt = lesspass.getSite() + lesspass.getLogin() + Integer.toString(template.getCounter(), 16);

        switch (template.getDigest()) {
            case Pbkdf2.SHA1:
                try {
                    byte[] bytes = Pbkdf2.getEncryptedPasswordSHA1(lesspass.getMasterPassword(), salt.getBytes(), 100000, template.getLength());
                    return bytesToHex(bytes);
                } catch (InvalidKeySpecException e) {
                    return "Error in Pbkdf2";
                }
            default:
                byte[] bytes = Pbkdf2.getEncryptedPasswordSHA256(lesspass.getMasterPassword(), salt.getBytes(), 100000, template.getLength());
                return bytesToHex(bytes);
        }
    }

    @Override
    public List<String> getConfiguredRules(Template template) {
        List<String> rules = new ArrayList<>();

        if (template.isHasAppearCaseLitters())
            rules.add(UPPERCASE);
        if (template.isHasLowerCaseLitters())
            rules.add(LOWERCASE);
        if (template.isHasNumbers())
            rules.add(NUMBERS);
        if (template.isHasSymbols())
            rules.add(SYMBOLS);

        return rules;
    }

    @Override
    public String getSetOfCharacters(List<String> rules) {
        if (rules.isEmpty()) {
            return characterSubsets.get(LOWERCASE) + characterSubsets.get(UPPERCASE) +
                    characterSubsets.get(NUMBERS) + characterSubsets.get(SYMBOLS);
        }
        StringBuilder sb = new StringBuilder();
        for (String rule : rules) {
            sb.append(characterSubsets.get(rule));
        }
        return sb.toString();
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
    public OneCharPerRule getOneCharPerRule(BigInteger entropy, List<String> rules) {
        String oneCharPerRules = "";
        for (String rule : rules) {
            Password password = consumeEntropy("", entropy, characterSubsets.get(rule), 1);
            oneCharPerRules += password.getValue();
            entropy = password.getEntropy();
        }
        return new OneCharPerRule(oneCharPerRules, entropy);
    }

    @Override
    public String insertStringPseudoRandomly(String generatedPassword, BigInteger entropy, String string) {
        for (int i = 0; i < string.length(); i++) {
            LongDivision longDivision = Divmod.exec(entropy, BigInteger.valueOf(generatedPassword.length()));
            generatedPassword = generatedPassword.substring(0, longDivision.getRemainder().intValue()) + string.charAt(i) + generatedPassword.substring(longDivision.getRemainder().intValue());
            entropy = longDivision.getQuotient();
        }
        return generatedPassword;
    }

    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
