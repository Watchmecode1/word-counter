package com.watchmecode.wordcounter.controller;

import com.watchmecode.wordcounter.service.WordCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * Controller class that handles txt file uploads.
 *
 * @author Matthew Mazzotta
 */
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class WordCounterController {

    private static final String CONTENT_DISPOSITION_HEADER_VALUE = "attachment; filename=words.txt";

    private final WordCounterService wordCounterService;

    @GetMapping
    public String index() {
        return "index";
    }

    @PostMapping
    public ResponseEntity<StreamingResponseBody> countWords(@RequestParam MultipartFile file) {
        StreamingResponseBody responseBody = outputStream -> wordCounterService.countWords(file, outputStream);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, CONTENT_DISPOSITION_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(responseBody);
    }

}
