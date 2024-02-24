package com.watchmecode.wordcounter.service;

import com.watchmecode.wordcounter.util.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WordCounterService {

    private static final String WORD_SPLIT_REGEX = "[ \r\n]";
    private static final String REPLACE_ALL_NON_WORD_CHARACTERS_REGEX = "\\W";
    private static final String EMPTY = "";
    private static final String PRETTY_FORMAT = "%s %s";
    private static final String DELIMITER = "\n";

    public void countWords(MultipartFile file, OutputStream outputStream) {
        String words = FileUtils.readFile(file);
        Map<String, Long> wordsCount = getWordsCount(words);
        String wordsCountToWrite = sortAndFormat(wordsCount);
        FileUtils.writeFile(wordsCountToWrite, outputStream);
    }

    private Map<String, Long> getWordsCount(String words) {
        return Arrays.stream(words.split(WORD_SPLIT_REGEX))
                .map(word -> word.replaceAll(REPLACE_ALL_NON_WORD_CHARACTERS_REGEX, EMPTY))
                .filter(word -> !word.isBlank())
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private String sortAndFormat(Map<String, Long> wordsCount) {
        return wordsCount.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(entry -> String.format(PRETTY_FORMAT, entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(DELIMITER));
    }
    
}
