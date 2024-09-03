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
class Chapter08Test {
    @Test
    @DisplayName("역참조 이해하기")
    void 역참조_이해하기() {

        String source = "<html>\n" +
                "<body>\n" +
                "<h1>Welcome to my Homepage</h1>\n" +
                "Content is divided into two sections:<br/>\n" +
                "<h2>SQL</h2>\n" +
                "Information about SQL.\n" +
                "<h2>RegEx</h2>\n" +
                "Information about Regular Expression.\n" +
                "<h2>This is not valid HTML</h3>\n" +
                "</body>\n" +
                "</html>";

        Matcher matcher = Pattern.compile("<[Hh]1>.*<\\/[Hh]1>").matcher(source);
        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("<h1>Welcome to my Homepage</h1>");

        // 예제의 헤더를 모두 찾으려면?
        matcher = Pattern.compile("<[hH][1-6]>.*<\\/[Hh][1-6]>").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        // <h2>This is not valid HTML</h3> 잘못된 헤더 태그에도 일치한다.
        list.forEach(log::info);

    }

    @Test
    @DisplayName("역참조로 찾기")
    void 역참조로_찾기() {
        String source = "This is a block of of text,\n" +
                "several words here are are\n" +
                "repeated, and and they\n" +
                "should not be";

        // 연이은 중복문자를 찾는 방법
        Matcher matcher = Pattern.compile("[ ]+(\\w+)[ ]+\\1").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();

        list.forEach(log::info);

        assertThat(list).hasSize(3);
        assertThat(list).contains(" of of");

        source = "<html>\n" +
                "<body>\n" +
                "<h1>Welcome to my Homepage</h1>\n" +
                "Content is divided into two sections:<br/>\n" +
                "<h2>SQL</h2>\n" +
                "Information about SQL.\n" +
                "<h2>RegEx</h2>\n" +
                "Information about Regular Expression.\n" +
                "<h2>This is not valid HTML</h3>\n" +
                "</body>\n" +
                "</html>";

        // 역참조를 사용하여 하위 표현식에서 찾은 값으로 대체한다.
        matcher = Pattern.compile("<([Hh][1-6])>.*</\\1>").matcher(source);
        List<String> list1 = matcher.results().map(MatchResult::group).toList();
        list1.forEach(log::info);

        // <h2>This is not valid HTML</h3> 매칭되지 않아 제외됨
        assertThat(list1).hasSize(3);

    }

    @Test
    @DisplayName("치환 작업 수행하기")
    void 치환_작업_수행하기() {
        String source = "Hello, ben@forta.com is my email address";
        Matcher matcher = Pattern.compile("\\w+[\\w\\.]*@[\\w\\.]+\\.\\w+").matcher(source);

        assertThat(matcher.find()).isEqualTo(true);

        String replaced = source.replaceAll("(\\w+[\\w\\.]*@[\\w\\.]+\\.\\w+)", "<a href=\"mailTo=$1\">$1</a>");
        log.info(replaced);

        assertThat(replaced).isEqualTo("Hello, <a href=\"mailTo=ben@forta.com\">ben@forta.com</a> is my email address");

        source = "313-555-1234\n" +
                "248-555-9999\n" +
                "810-555-9000";

        String replaced1 = source.replaceAll("(\\d{3})-(\\d{3})-(\\d{4})", "($1) $2-$3");
        log.info(replaced1);

        matcher = Pattern.compile("\\(\\d{3}\\) \\d{3}-\\d{4}").matcher(replaced1);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        list.forEach(log::info);

    }

    @Test
    @DisplayName("대소문자 변환하기")
    void 대소문자_변환하기() {
        String source = "<html>\n" +
                "<body>\n" +
                "<h1>Welcome to my Homepage</h1>\n" +
                "Content is divided into two sections:<br/>\n" +
                "<h2>SQL</h2>\n" +
                "Information about SQL.\n" +
                "<h2>RegEx</h2>\n" +
                "Information about Regular Expression.\n" +
                "<h2>This is not valid HTML</h3>\n" +
                "</body>\n" +
                "</html>";

        Matcher matcher = Pattern.compile("(<[Hh]1>)(.*?)(</[Hh]1>)").matcher(source);

        StringBuilder replaced = new StringBuilder();
        while (matcher.find()) {
            String upper = matcher.group(1)+matcher.group(2).toUpperCase()+matcher.group(3);
            matcher.appendReplacement(replaced, upper);
        }
        matcher.appendTail(replaced);

        log.info(replaced.toString());

        //모든 태그 대문자로 변경하기

        matcher = Pattern.compile("</?[\\s\\w]*\\s?/?>").matcher(source);
        replaced = new StringBuilder();

        while (matcher.find()) {
            String upperCase = matcher.group().toUpperCase();
            matcher.appendReplacement(replaced, upperCase);
        }
        matcher.appendTail(replaced);
        log.info(replaced.toString());



    }
}