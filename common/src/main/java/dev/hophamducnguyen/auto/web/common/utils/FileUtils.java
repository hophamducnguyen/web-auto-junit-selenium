package dev.hophamducnguyen.auto.web.common.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    public static Path addInfix(Path original, String infixString) {
        return original.resolveSibling(original.getFileName().toString()
                .replaceFirst("(.*?)(\\.[^.]+)?$", String.format("$1%s$2", infixString)));
    }

    public static <T> String getResourceLocation(String fileName, Class<T> clazz) {
        URL res = clazz.getClassLoader().getResource(fileName);
        if (res != null) {
            File file = null;
            try {
                file = Paths.get(res.toURI()).toFile();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return file.getAbsolutePath();
        }
        return null;
    }
}
