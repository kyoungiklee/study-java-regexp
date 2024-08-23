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
class Chapter03Test {

    private static final String sample1 = "sales.xls\n" +
            "sales1.xls\n" +
            "order3.xls\n" +
            "sales2.xls\n" +
            "sales3.xls\n" +
            "apac1.xls\n" +
            "europe2.xls\n" +
            "na1.xls\n" +
            "na2.xls\n" +
            "sa1.xls\n" +
            "ca1.xls\n";

    private static final String sample2 =
            "The phrase \"regular expression\" is often\n" +
                    "abbreviated as RegEx or regex";

    @Test
    @DisplayName("여러 문자 중 하나와 일치시키기")
    void 여러_문자_중_하나와_일치시키기() {

        Matcher matcher = Pattern.compile(".a.\\.xls").matcher(sample1);
        List<String> list = matcher.results().map(MatchResult::group)
                .toList();
        assertThat(list).contains("na1.xls", "na2.xls", "sa1.xls", "ca1.xls");

    }

    @Test
    @DisplayName("n이나 s로 시작하는 파일명 찾기")
    void n이나_s로_시작하는_파일명_찾기() {
        // []문자집합으로 []안의 값은 문자 집합으로 취급한다.
        Matcher matcher = Pattern.compile("[ns]a.\\.xls").matcher(sample1);
        List<String> list = matcher.results().map(MatchResult::group)
                .toList();
        assertThat(list).contains("na1.xls", "na2.xls", "sa1.xls");
    }

    @Test
    @DisplayName("문자집합은 검색할 부분의 특정영역만 대소문자 구분하지 않을때 사용")
    void 검색할_부분의_특정영역만_대소문자_구분하지_않을때_사용_방법() {
        Matcher matcher = Pattern.compile("[Rr]eg[Ee]x").matcher(sample2);
        List<String> list = matcher.results().map(MatchResult::group).toList();

        assertThat(list).contains("RegEx", "regex");
    }

    private static final String sample3 = "sales.xls\n" +
            "sales1.xls\n" +
            "order3.xls\n" +
            "sales2.xls\n" +
            "sales3.xls\n" +
            "apac1.xls\n" +
            "europe2.xls\n" +
            "sam.xls\n" + // 이 파일명은 제외하기
            "na1.xls\n" +
            "na2.xls\n" +
            "sa1.xls\n" +
            "ca1.xls\n";

    @Test
    @DisplayName("문자집합 범위 사용하기")
    void 문자집합_범위_사용하기() {
        Matcher matcher = Pattern.compile("[ns]a[0-9]\\.xls").matcher(sample3);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).isNotIn("sam.xls");
        assertThat(list).contains("na1.xls", "na2.xls", "sa1.xls");

    }

    @Test
    @DisplayName("문자집합 제외하고 찾기")
    void 문자집합_제외하고_찾기() {
        Matcher matcher = Pattern.compile("[ns]a[^0-9]\\.xls").matcher(sample3);
        List<String> list = matcher.results().map(MatchResult::group).toList();

        assertThat(list).contains("sam.xls");
    }
}
