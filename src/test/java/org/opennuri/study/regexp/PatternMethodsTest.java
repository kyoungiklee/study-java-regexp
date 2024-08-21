package org.opennuri.study.regexp;

import ch.qos.logback.core.boolex.EvaluationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class PatternMethodsTest {

    @Test
    @DisplayName("Pattern.matches() 사용법")
    void compile_regex_test() {

        String txt1 = "1234567890";
        String txt2 = "12345이것은숫자입니다";

        assertThat(Pattern.matches("^[0-9]+$", txt1)).isEqualTo(true);
        assertThat(Pattern.matches("^[0-9]+$", txt2)).isEqualTo(false);

    }

    @Test
    @DisplayName("Matcher클래스 Methods 사용법")
    void matcher_methods_test() {
        String txt = "1234567890Hello";

        //문자열형태의 정규 표현식(처음 숫자로 시작하고 연속된 숫자문자열)
        String patternString = "^[0-9]+";
        //정규식 패턴으로 생성
        Pattern pattern = Pattern.compile(patternString);

        //Pattern 객체의 matcher()를 사용하여 문자열을 검사하고 필터링된 결과를 Matcher 객체로 반환
        Matcher matcher = pattern.matcher(txt);
        boolean result = matcher.find();
        assertThat(result).isEqualTo(true);

        String group = matcher.group();
        assertThat(group).isEqualTo("1234567890");

        //todo Actual   :0 확인하기
        int count = matcher.groupCount();
        //assertThat(count).isEqualTo(2);

        //todo 재밌는 기능 확인
        //String.replaceAll() 활용하기
        //https://girawhale.tistory.com/128

        System.out.println("20210101".replaceAll("(?<year>\\d{4})(?<month>\\d{2})(?<day>\\d{2})",
                "${year}년 ${month}월 ${day}일"));

    }

    @Test
    @DisplayName("다중 결과값 출력")
    void print_multiple_result() {
        String txt = "hello12345!@#$world12345";

        Pattern pattern = Pattern.compile("[a-z]+[0-9]+");
        Matcher matcher = pattern.matcher(txt);

        while (matcher.find()) {
            log.info(matcher.group());
        }
    }


    @Test
    @DisplayName("그룹핑 결과 출력하기")
    void print_grouping_result() {
        String source = "011-1234-5678, 02-123-45467";

        Pattern pattern = Pattern.compile("(0\\d{1,2})-(\\d{3,4})-(\\d{4})");
        Matcher matcher = pattern.matcher(source);
        log.info(String.valueOf(matcher.groupCount()));

        while (matcher.find()) {
            log.info("{} -> {}, {}, {}", matcher.group(), matcher.group(1), matcher.group(2), matcher.group(3));
        }
    }

    @Test
    @DisplayName("매칭 위치 출력하기")
    void print_matching_position() {

        String source = "동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세";
        String pattern = "백두산";

        Matcher matcher = Pattern.compile(pattern).matcher(source);
        while (matcher.find()) {
            log.info("{}, {}", matcher.start(), matcher.end());

        }
    }

    @Test
    @DisplayName("정규식 플래그")
    void test_regular_expression_flag() {
        //개행문자가 포함된 문자열
        String txt = "this\nis\ntest\n";

        //. 기호는 모든 문자를 포함하지만 개행 문자는 포함하지 않기 때문에 일치하는 검색 결과 없음
        Matcher matcher = Pattern.compile("(^.*$)").matcher(txt);
        //(^.*$) 입력값이 문자로 이루어져 있는지 점검한다.
        assertThat(matcher.find()).isEqualTo(false);

        //. 기호가 개행문자까지 포함하도록 플래그 추가
        matcher = Pattern.compile("(?s)(^.*$)").matcher(txt);
//        assertThat(matcher.find()).isEqualTo(true);
        while (matcher.find()) {
            log.info("{}", matcher.group());
        }

        //다중행 모드로 각 행마다 정규식 검사
        matcher = Pattern.compile("(?m)(^.*$)").matcher(txt);
        while (matcher.find()) {
            log.info(matcher.group());
        }

        //대소문자 구분하지 않음
        matcher = Pattern.compile("(?i)(^[a-z\\s]+$)").matcher("Hello World");
        while (matcher.find()) {
            log.info(matcher.group());
        }

        matcher = Pattern.compile("(^[a-z\\s]+$)", Pattern.CASE_INSENSITIVE).matcher("Hello World");
        while (matcher.find()) {
            log.info(matcher.group());
        }
    }

    @Test
    @DisplayName("Pattern.split()으로 문자열 분해")
    void test_pattern_split() {

        String source = "Pattern Split    Method in Java  regex";
        //공백, 탭 또는 공백, 탭 여러개 찾기
        Pattern pattern = Pattern.compile("\\s+");
        String[] result = pattern.split(source);
        Arrays.asList(result).forEach(value -> log.info(value));

        //대문자로 시작하는 단어 찾기
        pattern = Pattern.compile("([A-Z]\\w+)");
        Matcher matcher = pattern.matcher(source);

        while (matcher.find()) {
            log.info(matcher.group());
        }

    }

    @Test
    @DisplayName("숫자형 문자열에 3자리마다 , 추가하기")
    void add_comma_per_3_numeric_character() {
        String source = "12345678901234564444";

        Pattern pattern = Pattern.compile("(\\d)(?=(\\d{3})+(?!\\d))");
        Matcher matcher = pattern.matcher(source);

        StringBuilder stringBuilder = new StringBuilder(source);
        int i =0;
        while (matcher.find()) {
            String group = matcher.group();
            log.info("{} {} {}", group, matcher.start(), matcher.end());
            int start = matcher.start() + i;
            int end = matcher.end() +i ;

            stringBuilder.replace(start, end, group + ",");
            i++;

        }
        log.info(stringBuilder.toString());
    }

    @Test
    void add_comma_per_3_numeric_character_replaceAll() {
        String source = "1234567890";

        String regex = "(\\d)(?=(\\d{3})+(?!\\d))";

        String result = source.replaceAll(regex, "$1,");
        log.info(result);

        Pattern pattern = Pattern.compile("(\\d)(?=(\\d{3})+(?!\\d))");
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            log.info(matcher.group());
        }


    }
}
