package com.github.basespring.application.commons;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class StringUtils {

    private static Pattern snakeCasePattern = Pattern.compile("[a-z]+(_[a-z]+)*");

    private static Pattern cemalCasePattern = Pattern.compile("^[a-zA-Z]+([A-Z][a-z]+)+$");


    public static String toCapitalizeWord(String str) {
        str = str.trim();
        if (snakeCasePattern.matcher(str).matches()) {
            List<String> words = Arrays.stream(str.split("_")).toList();
            words.forEach(org.springframework.util.StringUtils::capitalize);

            return String.join(" ", words);
        } else if (cemalCasePattern.matcher(str).matches()) {
            List<String> words = Arrays.stream(str.split("(?=[A-Z])")).toList();
            words = words.stream().map(org.springframework.util.StringUtils::capitalize).toList();

            return String.join(" ", words);
        }

        return str;
    }
}
