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
class Chapter05Test {

    @Test
    @DisplayName("하나 이상의 문자 찾기 1")
    void 하나_이상의_문자_찾기_1() {
        String source = "Send personal email to ben@forta.com, For questions\n" +
                "about a book use support@forta.com. Feel free to send\n" +
                "unsolicited email to spam@frota.com (wouldn't it be\n" +
                "nice if it were that simple, huh?)";
        Matcher matcher = Pattern.compile("\\w+@\\w+\\.\\w+").matcher(source);

        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).contains("ben@forta.com", "support@forta.com");
    }

    @Test
    @DisplayName("하나 이상의 문자 찾기 2")
    void 하나_이상의_문자_찾기_2() {
        String source = "Send personal email to ben@forta.com or ben.forta@forta.com, \n" +
                "For questions about a book use support@forta.com. \n" +
                "If your message is urgent try ben@urgent.forta.com. Feel free to send\n" +
                "unsolicited email to spam@prota.com (wouldn't it be\n" +
                "nice if it were that simple, huh?)";

        Matcher matcher = Pattern.compile("[\\w.]+@[\\w.]+\\.\\w+").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).contains("ben.forta@forta.com", "ben@urgent.forta.com");

    }

    @Test
    @DisplayName("문자가 없거나 하나 이상 연속하는 문자 찾기")
    void 문자가_없거나_하나_이상_연속하는_문자_찾기() {

        String source = "B. Forth\n" +
                "B Forta\n" +
                "Ben Forta";
        Matcher matcher = Pattern.compile("(?m)B.* Forta$").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(3);
        //이메일 주소에 .을 쓸수는 있지만, 이메일 주소 맨 처음에 쓰지는 않는다.
        source = "Hello .ben.lee@forta.com is my email address.";
        matcher = Pattern.compile("\\w+[\\w.]+@[\\w.]+\\.\\w+").matcher(source);
        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("ben.lee@forta.com");
    }

    @Test
    @DisplayName("문자가 없거나 하나인 문자 찾기")
    void 문자가_없거나_하나인_문자_찾기() {

        String source = "The URL is http://www.forta.com/, to connect\n" +
                "securely use https://www.forta.com/ instead.";

        Matcher matcher = Pattern.compile("https?:\\/\\/[\\w.\\/]+").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(2);
    }

    @Test
    @DisplayName("정확한 구간 찾기")
    void 정확한_구간_찾기() {
        String source = "body {\n" +
                "\tbackground-color: #fefdb8;\n" +
                "}\n" +
                "h1 {\n" +
                "\tbackground-color: #0000ff;\n" +
                "}\n" +
                "div {\n" +
                "\tbackground-color: #d0f4e6;\n" +
                "}\n" +
                "span {\n" +
                "\tbackground-color: #f08970;\n" +
                "}";
        Matcher matcher = Pattern.compile("#[0-9A-Fa-f]{6}").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(4);
    }

    @Test
    @DisplayName("범위 구간 찾기")
    void 범위_구간_찾기() {
        String source = "4/8/17\n" +
                "10-6-2018\n" +
                "2/2/2\n" +
                "01-01-01";
        Matcher matcher = Pattern.compile("\\d{1,2}[\\/-]\\d{1,2}[\\/-]\\d{2,4}").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(3);
    }

    @Test
    @DisplayName("최소 구간 찾기")
    void 최소_구간_찾기() {
        String source = "1001: $496.80\n" +
                "1002: $1290.69\n" +
                "1003: $26.43\n" +
                "1004: $613.42\n" +
                "1005: $7.61\n" +
                "1006: $414.90\n" +
                "1007: $25.00";
        Matcher matcher = Pattern.compile("\\d{4}: \\$\\d{3,}\\.\\d{2}").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(4);
    }

    @Test
    @DisplayName("과하게 일치하는 상황 방지하기")
    void 과하게_일치하는_상황_방지하기() {
        String source = "This offer is not available to customers\n" +
                "living in <b>AK</b> and <b>HI</b>";

        Matcher matcher = Pattern.compile("<[Bb]>.*<\\/[Bb]>").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(1);

        matcher = Pattern.compile("<[Bb]>.*?<\\/[Bb]>").matcher(source);
        list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(2);
        assertThat(list).contains("<b>AK</b>", "<b>HI</b>");
    }
}