package com.watchmecode.wordcounter.util;

import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    private FileUtils() {
        // private constructor to avoid initialization
    }

    @SneakyThrows
    public static String readFile(MultipartFile file) {
        OutputStream result = new ByteArrayOutputStream();
        file.getInputStream().transferTo(result);
        return result.toString();
    }

    @SneakyThrows
    public static void writeFile(String toWrite, OutputStream outputStream) {
        outputStream.write(toWrite.getBytes(StandardCharsets.UTF_8));
    }

}
