package fplhn.udpm.identity.util;


import java.util.UUID;

public class CodeGenerator {

    public static String generateRandomCode() {
        return UUID.randomUUID().toString().substring(0, 4);
    }

}
