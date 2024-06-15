package com.boskman.elibrary.utils;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ScrapingParserService {

    public String parseField(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }

    public Integer parsePublicationYear(String publicationDate) {
        return Integer.parseInt(publicationDate.split("-")[2]);
    }

}
