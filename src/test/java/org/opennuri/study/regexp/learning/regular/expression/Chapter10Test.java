package org.opennuri.study.regexp.learning.regular.expression;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("NonAsciiCharacters")
@Slf4j
class Chapter10Test {
    @Test
    @DisplayName("역참조 조건")
    void 역참조_조건() {
        String source = "<!-- Nav bar -->\n" +
                "<div>\n" +
                "    <a href=\"/home\"><img src=\"/images/home.gif\"></a>\n" +
                "    <img src=\"/images/spacer.gif>\n" +
                "    <a href=\"/search\"><img src=\"images/search.gif\"></a>\n" +
                "    <img src=\"/images/spacer.gif>\n" +
                "    <a href=\"/help\"><img src=\"/imgage/help.gif\"></a>\n" +
                "</div>";
        // 조건 참조가
        Pattern.compile("(<[Aa]\\s+[^>]+>\\s*)?<[Ii][Mm][Gg]\\s+[^>]+>(?:\1)?\\s*<\\/[Aa]>").matcher(source);


    }

    @Test
    @DisplayName("Step 11: Parentheses ()as Capturing Groups")
    void parentheses_as_capturing_groups() {
        String source = "The current President of Bolivia is Evo Morales .";
        Matcher matcher = Pattern.compile("([A-Z])|([a-z])").matcher(source);

        // ([A-Z])|([a-z])
        // The above regular expression defines tow capturing groups that are indexed starting from 1.
        // The first capturing group matches any single uppercase letter and the second capturing group
        // matches any single lowercase letter. By using the 'or' sign | and parentheses () as a capturing group
        // we can define a single regular expression that matches multiple kinds of strings

        while (matcher.find()) {
            log.info(matcher.group(1));
        }

        //
        source = "42L 12 x 3.4f 6l 3.3 0F LF .2F 0.";
    }

}