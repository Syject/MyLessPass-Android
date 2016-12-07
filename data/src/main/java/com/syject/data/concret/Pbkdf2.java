package com.syject.data.concret;

import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Pbkdf2 {

    public static final String SHA1 = "SHA1";
    public static final String SHA256 = "SHA256";

    //SHA1
    public static byte[] getEncryptedPasswordSHA1(String password, byte[] salt, int iterations, int derivedKeyLength) throws InvalidKeySpecException {
        int keySizeInBits = derivedKeyLength * 8;
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keySizeInBits);
        SecretKeyFactory f;
        try {
            f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

        return f.generateSecret(spec).getEncoded();
    }

    //SHA256
    public static byte[] getEncryptedPasswordSHA256(String password, byte[] salt, int iterations, int derivedKeyLength) {
        int keySizeInBits = derivedKeyLength * 8;
        PKCS5S2ParametersGenerator generator = new PKCS5S2ParametersGenerator(new SHA256Digest());
        generator.init(PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(password.toCharArray()), salt, iterations);
        KeyParameter key = (KeyParameter)generator.generateDerivedMacParameters(keySizeInBits);

        return key.getKey();
    }
}
