package org.opennuri.study.regexp.learning.regular.expression;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("NonAsciiCharacters")
@Slf4j
class Chapter09Test {
    @Test
    @DisplayName("전후방탐색 살펴보기")
    void 전후방탐색_살펴보기() {
        String source = "<head>\n" +
                "<title>Ben Forta's Homepage</title>\n" +
                "</head>";
        Matcher matcher = Pattern.compile("<[Tt][Ii][Tt][Ll][Ee]>.*?</[Tt][Ii][Tt][Ll][Ee]>").matcher(source);
        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("<title>Ben Forta's Homepage</title>");


        // <title> 태그의 값만 가져올려면? 전방탐색자 후방탐색자를 사용
        matcher = Pattern.compile("(?<=<[Tt][Ii][Tt][Ll][Ee]>).*?(?=</[Tt][Ii][Tt][Ll][Ee]>)").matcher(source);
        matcher.find();
        log.info(matcher.group());
    }

    @Test
    @DisplayName("전방탐색-앞으로 찾기")
    void 전방탐색_앞으로_찾기() {
        String source = "http://www.forta.com/\n" +
                "https://mail.forta.com/\n" +
                "ftp://ftp.forta.com/";
        Matcher matcher = Pattern.compile("\\w+(?=:)").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        list.forEach(log::info);
    }
}