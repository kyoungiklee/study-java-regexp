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

    @Test

    void email_should_include_at() {
        String regExpr = "^";
    }

}