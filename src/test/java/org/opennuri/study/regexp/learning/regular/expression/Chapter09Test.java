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

    @Test
    @DisplayName("후방탐색 뒤로 찾기")
    void 후방탐색_뒤로_찾기() {
        String source = "ABC01: $23.45\n" +
                "HGG42: $5.31\n" +
                "CFMX1: $899.00\n" +
                "XTC99: $69.96\n" +
                "Total items found: 4";
        Matcher matcher = Pattern.compile("\\$[0-9\\.]").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(4);

        matcher = Pattern.compile("(?<=\\$)[0-9.]+").matcher(source);
        List<String> list1 = matcher.results().map(MatchResult::group).toList();
        assertThat(list1).contains("23.45");

    }

    @Test
    @DisplayName("부정형 전후방 탐색")
    void 부정형_전후방_탐색() {
        String source = "I paid $30 for 100 apples,\n" +
                "50 oranges, and 60 pears.\n" +
                "I saved $5 on this order.";

        // 가격을 $ 제외하고 찾고자 할때 - 긍정형 후방탐색으로 찾기
        Matcher matcher = Pattern.compile("(?<=\\$)\\d+").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(2);

        // 수량을 찾고자 할때 - 부정형 후방탐색으로 찾기
        matcher = Pattern.compile("\\b(?<!\\$)\\d+\\b").matcher(source);
        List<String> list1 = matcher.results().map(MatchResult::group).toList();
        list1.forEach(log::info);

        assertThat(list1).hasSize(3);
    }
}