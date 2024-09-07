package org.opennuri.study.regexp.learning.regular.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;

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


}