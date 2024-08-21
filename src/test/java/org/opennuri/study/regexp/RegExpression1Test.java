package org.opennuri.study.regexp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}