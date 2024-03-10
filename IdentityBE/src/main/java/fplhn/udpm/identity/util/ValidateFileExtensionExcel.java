package fplhn.udpm.identity.util;

import java.util.Optional;

public class ValidateFileExtensionExcel {

    public static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

}
