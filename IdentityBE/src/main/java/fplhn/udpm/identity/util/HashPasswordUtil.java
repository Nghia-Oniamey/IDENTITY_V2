package fplhn.udpm.identity.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class HashPasswordUtil {

    private static final String ALGORITHM_NAME = "PBKDF2WithHmacSHA1";

    public static String[] hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance(ALGORITHM_NAME);
        byte[] hash = f.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();
        return new String[]{enc.encodeToString(salt), enc.encodeToString(hash)};
    }

    public static boolean verifyPassword(String password, String salt, String hash) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Base64.Decoder dec = Base64.getDecoder();
        byte[] saltBytes = dec.decode(salt);
        byte[] hashBytes = dec.decode(hash);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance(ALGORITHM_NAME);
        byte[] testHash = f.generateSecret(spec).getEncoded();
        return Arrays.equals(hashBytes, testHash);
    }

//    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        String password = "Nhey1123@";
//        String[] hashAndSalt = hashPassword(password);
//        System.out.println(verifyPassword(password, hashAndSalt[0], hashAndSalt[1]));
//    }

}
