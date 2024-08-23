package org.opennuri.study.regexp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class RegExpression1Test {

    //사용자 아이디는 문자로 시작하여야한다.
    @Test
    @DisplayName("사용자 아이디는 문자로 시작하여야한다.")
    void userid_should_start_with_alphabet() {

        String regExpr = "^[a-zA-Z]*$";
        assertThat("HulkRobinson").matches(regExpr);
        assertThat("Hulk1234").doesNotMatch(regExpr);
        assertThat("H").matches(regExpr);
    }

    @Test
    @DisplayName("사용자아이디는 a-z, A-Z, 0-9, _ 만 사용할 수 있다.")
    void userid_should_use_alphabet_number_underscore() {
        String regExpr = "\\w+$";
        assertThat("roadseeker_pass").matches(regExpr);
        assertThat("roadseeker*_pass").doesNotMatch(regExpr);
    }

    @Test
    @DisplayName("사용자 아이디는 문자가 2개이상 반복되어서는 않된다.")
    void userid_should_not_repeat_alphabet() {

    }

    //todo 메일 체크용 정규표현식 확인하기
    @Test
    void email_should_include_at() {
        String regExpr = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        String regex = "^[a-zA-Z0-9_+*&-]+(\\.[a-zA-Z0-9_+&*-]+)";
        String source = "aaAA09_-+*&azAZ09.";
        Matcher matcher = Pattern.compile(regex).matcher(source);
        while (matcher.find()) {
            log.info(matcher.group());
        }
    }

    @Test
    @DisplayName("h로 시작하고 a가 1개이상 반복되고 t로 끝나는 문자")
    void start_with_h_end_with_t_and_repeat_a() {
        String regex = "^ha+t$";
        assertThat("hat".matches(regex)).isEqualTo(true);
        assertThat("haaat".matches(regex)).isEqualTo((true));
        assertThat("ht".matches(regex)).isEqualTo(false);
    }

    /*public class EmailValidator {

        private static final String EMAIL_REGEX =
                "^[a-zA-Z0-9_+&*-]+(?:\\." +
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$";

        public static boolean isValidEmail(String email) {
            Pattern pattern = Pattern.compile(EMAIL_REGEX);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }*/


    @Test
    @DisplayName("Grouping")
    void test_grouping() {
        // \w 알파벳 대문자 + 소문자 + 숫자 + "_" 하나
        // \s 공백, 탭 하나
        String pattern = "(\\w)(\\s+)(\\w)";
        log.info("Hello    world".replaceAll(pattern, "-"));
        log.info("Hello    world".replaceAll(pattern, "$1-$3"));
    }

    @Test
    @DisplayName("anchors 테스트")
    void test_anchors() {
        //^부터 $까지의 모든 문자열이 패턴과 일치해야 함.
        assertThat("abc".matches("^abc$")).isEqualTo(true);
        // 문자열 시작부터 패턴과 일치해야 함.
        // (^abc)+(\w|\s)+ abc로 시작하고 문자 또는 공백이 올수 있음
        assertThat("abc def ghi".matches("(^abc)(\\w|\\s)+")).isEqualTo(true);
        assertThat("def ghi abc".matches("(\\w|\\s)+abc$")).isEqualTo(true);

    }

    @Test
    @DisplayName("전후방탐색자(Lookaround, Lookbehind")
    void test_look_around() {
        //전방탐색자(?=) 123(?=abc) 123을 찾는데 뒤에 abc가 오는 123을 찾음
        Matcher matcher = Pattern.compile("123(?=abc)").matcher("123abc");
        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("123");

        //전방탐색자(?!) 123(?!abc) 123을 찾는데 뒤에 abc가 오지 않는 123을 찾음
        matcher = Pattern.compile("123(?!abc)").matcher("123def");
        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("123");

        //후방탐색자(?<=) (?<=abc)123 123을 찾는데 앞에 abc가 오는 123을 찾음
        matcher = Pattern.compile("(?<=abc)123").matcher("abc123");
        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("123");

        //후방탐색자(?<!) (?<!abc)123 123을 찾는데 앞에 abc가 오지않는 123을 찾음
        matcher = Pattern.compile("(?<!abc)123").matcher("def123");
        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("123");
    }


}