package org.opennuri.study.regexp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class RegExpression1 {

    //Java HotSpot(TM) 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended
    // run --> edit configurations --> Vm options --> -Xshare:off
    @Test
    @DisplayName("사용자 아이디는 문자로 시작하여야한다.")
    void userid_should_start_with_alphabet() {
        //사용자 아이디는 문자로 시작하여야한다.
        String regex = "^[a-zA-Z].*"; // ^ == 시작, [a-zA-Z] == a-z, A-Z 중 하나, .* == 어떤 문자가 0개 이상
        assertThat("a1234").matches("^[a-zA-Z].*");
        assertThat("1a234").doesNotMatch("^[a-zA-Z].*");
    }

    @Test
    @DisplayName("사용자아이디는 a-z, A-Z, 0-9, _ 만 사용할 수 있다.")
    void userid_should_use_alphabet_number_underscore() {
        //사용자아이디는 a-z, A-Z, 0-9, _ 만 사용할 수 있다.
        String regex = "^[a-zA-Z0-9_]+$"; // [a-zA-Z0-9_] == 1개 이상의 문자

        assertThat("a1234").matches(regex);
        assertThat("a1234_").matches(regex);
        assertThat("a1234_!").doesNotMatch(regex);
        assertThat("a").matches(regex);
        assertThat("").doesNotMatch(regex);
    }

    @Test
    @DisplayName("사용자 아이디는 문자가 2개이상 반복되어서는 않된다.")
    void userid_should_not_repeat_alphabet() {
        //사용자 아이디는 문자가 2개이상 반복되어서는 않된다.
        String regex = "^(?!.*([a-zA-Z]).*\\1).*$";

        assertThat("a1234").matches(regex);
        assertThat("a1234a").doesNotMatch(regex);
    }

    @Test
    @DisplayName("이메일은 @를 포함하여야한다.")
    void email_should_include_at() {
        //이메일은 @를 포함하여야한다.
        String regex = ".*@.*";

        assertThat("mail@gmail.com").matches(regex);
    }

    @Test
    @DisplayName("어떤문자하나")
    void any_character() {
        String regex = "h.t"; // . == 어떤 문자하나

        assertThat("hat").matches(regex);
        assertThat("h1t").matches(regex);
        assertThat("h t").matches(regex);
        assertThat("ht").doesNotMatch(regex);
    }

    @Test
    @DisplayName("h로 시작하고 a가 1개이상 반복되고 t로 끝나는 문자")
    void start_with_h_end_with_t_and_repeat_a() {
        //h로 시작하고 a가 1개이상 반복되고 t로 끝나는 문자
        String regex = "ha+t"; // + == 앞 문자가 1개 이상

        assertThat("hat").matches(regex);
        assertThat("haat").matches(regex);
        assertThat("haaat").matches(regex);
        assertThat("ht").doesNotMatch(regex);
    }

    @Test
    @DisplayName("h로 시작하고 h가 0개이상 반복되고 t로 끝나는 문자")
    void start_with_h_end_with_t_and_repeat_h() {
        //h로 시작하고 h가 0개이상 반복되고 t로 끝나는 문자
        String regex = "ha*t"; // * == 앞 문자가 0개 이상

        assertThat("hat").matches(regex);
        assertThat("haat").matches(regex);
        assertThat("ht").matches(regex);
    }

    @Test
    @DisplayName("h로 시작하고 t로 끝나며 그 사이에 어떤 문자가 0개 이상")
    void start_with_h_end_with_t() {
        //h로 시작하고 t로 끝나며 그 사이에 어떤 문자가 1개 이상
        String regex = "h.*t"; // .* == 어떤 문자가 0개 이상

        assertThat("hat").matches(regex);
        assertThat("h1t").matches(regex);
        assertThat("h t").matches(regex);
        assertThat("ht").matches(regex);
        assertThat("habce*&^%$#@!1234345t").matches(regex);

    }

    @Test
    @DisplayName("h로 시작하고 t로 끝나며 그 사이에 어떤 문자가 1개 이상")
    void start_with_h_end_with_t_and_repeat_any_character() {
        //h로 시작하고 t로 끝나며 그 사이에 어떤 문자가 1개 이상
        String regex = "h.+t"; // .+ == 어떤 문자가 1개 이상

        assertThat("hat").matches(regex);
        assertThat("h1t").matches(regex);
        assertThat("h t").matches(regex);
        assertThat("ht").doesNotMatch(regex);
        assertThat("habce*&^%$#@!1234345t").matches(regex);
    }

    @Test
    @DisplayName("abc 중 하나")
    void abc() {

        //abc 중 하나
        String regex = "[abc]";

        assertThat("a").matches(regex);
        assertThat("b").matches(regex);
        assertThat("c").matches(regex);
        assertThat("d").doesNotMatch(regex);
        assertThat("").doesNotMatch(regex);
    }

    @Test
    @DisplayName("a부터 z까지 중 하나")
    void a_to_z() {
        //a부터 z까지 중 하나
        String regex = "[a-z]";

        assertThat("a").matches(regex);
        assertThat("b").matches(regex);
        assertThat("z").matches(regex);
        assertThat("A").doesNotMatch(regex);
    }
    @Test
    @DisplayName("A부터 Z까지 중 하나")
    void A_to_Z() {
        //A부터 Z까지 중 하나
        String regex = "[A-Z]";

        assertThat("A").matches(regex);
        assertThat("B").matches(regex);
        assertThat("Z").matches(regex);
        assertThat("a").doesNotMatch(regex);
    }
    @Test
    @DisplayName("0부터 9까지 중 하나")
    void zero_to_nine() {
        //0부터 9까지 중 하나
        String regex = "[0-9]";

        assertThat("0").matches(regex);
        assertThat("1").matches(regex);
        assertThat("9").matches(regex);
        assertThat("a").doesNotMatch(regex);
    }
    @Test
    @DisplayName("특수문자 중 하나")
    void special_character() {
        //특수문자 중 하나
        String regex = "[!@#$%^&*()]";

        assertThat("!").matches(regex);
        assertThat("@").matches(regex);
        assertThat("#").matches(regex);
        assertThat("a").doesNotMatch(regex);
    }
    @Test
    @DisplayName("a-z, A-Z, 0-9, _ 중 하나")
    void alphabet_number_underscore() {
        //a-z, A-Z, 0-9, _ 중 하나
        String regex = "[a-zA-Z0-9_]"; // [a-zA-Z0-9_] == \w

        assertThat("a").matches(regex);
        assertThat("A").matches(regex);
        assertThat("0").matches(regex);
        assertThat("_").matches(regex);
        assertThat("!").doesNotMatch(regex);
        assertThat("ab").doesNotMatch(regex);
    }

    @Test
    @DisplayName("0-9 중 여러개")
    void zero_to_nine_multiple() {
        //0-9 중 여러개
        String regex = "[0-9]+"; // [0-9] == \d

        assertThat("0").matches(regex);
        assertThat("1").matches(regex);
        assertThat("9").matches(regex);
        assertThat("a").doesNotMatch(regex);
        assertThat("1234").matches(regex);
    }

    @Test
    @DisplayName("0-9를 제외한 문자 1개")
    void zero_to_nine_exclude() {
        //0-9를 제외한 문자 1개
        String regex = "[^0-9]"; // [^0-9] == \D

        assertThat("0").doesNotMatch(regex);
        assertThat("1").doesNotMatch(regex);
        assertThat("9").doesNotMatch(regex);
        assertThat("a").matches(regex);
        assertThat("1234").doesNotMatch(regex);
    }

    @Test
    @DisplayName("0-9 중 3개")
    void zero_to_nine_three() {
        //0-9 중 3개
        String regex = "[0-9]{3}"; // [0-9] == \d, {3} == 3개

        assertThat("0").doesNotMatch(regex);
        assertThat("1").doesNotMatch(regex);
        assertThat("9").doesNotMatch(regex);
        assertThat("a").doesNotMatch(regex);
        assertThat("123").matches(regex);
        assertThat("1234").doesNotMatch(regex);
    }

    @Test
    @DisplayName("0-9 중 3개 이상")
    void zero_to_nine_three_or_more() {
        //0-9 중 3개 이상
        //[^0-9] == 숫자를 제외한 문자 1개
        //[^a-z] == 영문 소문자를 제외한 문자 1개
        //[^A-Z] == 영문 대문자를 제외한 문자 1개
        //[^a-zA-Z] == 영문자를 제외한 문자 1개
        String regex = "[0-9]{3,}"; // [0-9] == \d, {3,} == 3개 이상

        assertThat("0").doesNotMatch(regex);
        assertThat("1").doesNotMatch(regex);
        assertThat("9").doesNotMatch(regex);
        assertThat("a").doesNotMatch(regex);
        assertThat("123").matches(regex);
        assertThat("1234").matches(regex);
    }
    @Test
    @DisplayName("앞 문자가 0또는 이상 반복")
    void zero_or_more_repeat() {
        //앞 문자가 0또는 이상 반복
        String regex = "a*"; // * == 앞 문자가 0또는 이상 반복

        assertThat("").matches(regex);
        assertThat("a").matches(regex);
        assertThat("aa").matches(regex);
        assertThat("aaa").matches(regex);
    }

    @Test
    @DisplayName("앞 문자가 1또는 이상 반복")
    void one_or_more_repeat() {
        //앞 문자가 1또는 이상 반복
        String regex = "a+"; // + == 앞 문자가 1또는 이상 반복

        assertThat("").doesNotMatch(regex);
        assertThat("a").matches(regex);
        assertThat("aa").matches(regex);
        assertThat("aaa").matches(regex);
    }

    @Test
    @DisplayName("앞 문자가 0또는 1번 반복")
    void zero_or_one_repeat() {
        //앞 문자가 0또는 1번 반복
        String regex = "a?"; // ? == 앞 문자가 0또는 1번 반복

        assertThat("").matches(regex);
        assertThat("a").matches(regex);
        assertThat("aa").doesNotMatch(regex);
        assertThat("aaa").doesNotMatch(regex);
    }

    @Test
    @DisplayName("앞 문자가 3번 반복")
    void three_repeat() {
        //앞 문자가 3번 반복
        String regex = "a{3}"; // {3} == 앞 문자가 3번 반복

        assertThat("a").doesNotMatch(regex);
        assertThat("aa").doesNotMatch(regex);
        assertThat("aaa").matches(regex);
        assertThat("aaaa").doesNotMatch(regex);
    }
    @Test
    @DisplayName("앞 문자가 3번 이상 반복")
    void three_or_more_repeat() {
        //앞 문자가 3번 이상 반복
        String regex = "a{3,}"; // {3,} == 앞 문자가 3번 이상 반복

        assertThat("a").doesNotMatch(regex);
        assertThat("aa").doesNotMatch(regex);
        assertThat("aaa").matches(regex);
        assertThat("aaaa").matches(regex);
    }

    @Test
    @DisplayName("앞 문자가 3번 이상 5번 이하 반복")
    void three_to_five_repeat() {
        //앞 문자가 3번 이상 5번 이하 반복
        String regex = "a{3,5}"; // {3,5} == 앞 문자가 3번 이상 5번 이하 반복

        assertThat("a").doesNotMatch(regex);
        assertThat("aa").doesNotMatch(regex);
        assertThat("aaa").matches(regex);
        assertThat("aaaa").matches(regex);
        assertThat("aaaaa").matches(regex);
        assertThat("aaaaaa").doesNotMatch(regex);
    }

    @Test
    @DisplayName("(na)가 0개이상 반복")
    void zero_or_more_repeat_group() {
        //(na)가 0개이상 반복
        String regex = "ba(na)*"; // * == 앞 문자가 0또는 이상 반복

        assertThat("ba").matches(regex);
        assertThat("bana").matches(regex);
        assertThat("banana").matches(regex);
        assertThat("bananana").matches(regex);
    }

    @Test
    @DisplayName("ba(na)가 1개이상 반복")
    void one_or_more_repeat_group() {
        //ba(na)가 1개이상 반복
        String regex = "ba(na)+"; // + == 앞 문자가 1또는 이상 반복

        assertThat("ba").doesNotMatch(regex);
        assertThat("bana").matches(regex);
        assertThat("banana").matches(regex);
        assertThat("bananana").matches(regex);
    }
    @Test
    @DisplayName("괄호로 묶인 그룹 내에서 | 로 나누어진 여러 패턴중 일치하는 것 중 하나")
    void or() {
        //괄호로 묶인 그룹 내에서 | 로 나누어진 여러 패턴중 일치하는 것 중 하나
        String regex = "a(b|c)d"; // (b|c) == b 또는 c

        assertThat("abd").matches(regex);
        assertThat("acd").matches(regex);
        assertThat("aed").doesNotMatch(regex);
    }

    @Test
    @DisplayName("(h.t)가 0개이상 반복")
    void zero_or_more_repeat_group_with_any_character() {
        //(h.t)가 0개이상 반복
        String regex = "a(h.t)*"; // * == 앞 문자가 0또는 이상 반복

        assertThat("a").matches(regex);
        assertThat("ahat").matches(regex);
        assertThat("ahathbt").matches(regex);
        assertThat("ahathbthct").matches(regex);
    }

    @Test
    @DisplayName("h로 시작하고 t로 끝나는 그룹 2개")
    void start_with_h_end_with_t_two_group() {
        //h로 시작하고 t로 끝나는 그룹 2개
        String regex = "(h.*t){2}"; // {2} == 앞 문자가 2번 반복

        assertThat("hat").doesNotMatch(regex);
        assertThat("h1t").doesNotMatch(regex);
        assertThat("h t").doesNotMatch(regex);
        assertThat("ht").doesNotMatch(regex);
        assertThat("habce*&^%$#@!1234345thabce*&^%$#@!1234345t").matches(regex);
    }

    @Test
    @DisplayName("연속된 문자가 있는 지 확인")
    void consecutive() {
        //연속된 문자가 있는 지 확인
        String regex = ".*(.)\\1.*"; // (.) == 어떤 문자 1개, \\1 == 앞 문자와 동일한 문자

        assertThat("a").doesNotMatch(regex);
        assertThat("aa").matches(regex);
        assertThat("ab").doesNotMatch(regex);
        assertThat("aba").matches(regex);
        assertThat("abab").matches(regex);
        assertThat("abcabc").matches(regex);
    }

    @Test
    @DisplayName("2개이상 연속된 문자가 있는지 확인")
    void consecutive_two_or_more() {
        //2개이상 연속된 문자가 있는지 확인
        String regex = ".*(.)\\1{1,}.*"; // (.) == 어떤 문자 1개, \\1{1,} == 앞 문자와 동일한 문자가 2개 이상

        assertThat("a").doesNotMatch(regex);
        assertThat("aa").doesNotMatch(regex);
        assertThat("ab").doesNotMatch(regex);
        assertThat("aba").doesNotMatch(regex);
        assertThat("abab").matches(regex);
        assertThat("abcabc").matches(regex);
    }

    @Test
    @DisplayName("특수문자가 있는지 확인")
    void special_character_exist() {
        //특수문자가 있는지 확인
        String regex = ".*[!@#$%^&*()<>?\\[\\]].*"; // [!@#$%^&*()] == 특수문자 중 하나

        assertThat("a").doesNotMatch(regex);
        assertThat("a!").matches(regex);
        assertThat("a@").matches(regex);
        assertThat("a#").matches(regex);
        assertThat("a$").matches(regex);
        assertThat("a%").matches(regex);
        assertThat("a^").matches(regex);
        assertThat("a&").matches(regex);
        assertThat("a*").matches(regex);
        assertThat("a()").matches(regex);
        assertThat("a<>").matches(regex);
        assertThat("a?").matches(regex);
        assertThat("a[").matches(regex);
        assertThat("a]").matches(regex);
    }


}
