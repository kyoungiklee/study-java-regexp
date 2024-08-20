package org.opennuri.study.regexp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.replaceAll;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RegExpression2 {

    @Test
    @DisplayName("문자 1개")
    void test1() {
        String pattern = "ab.";
        assertThat("abc").matches(pattern);
        assertThat("ab1").matches(pattern);
        assertThat("abA").matches(pattern);
        assertThat("ab ").matches(pattern);
    }

    @Test
    @DisplayName("ab와 공백문자 1개")
    void with_space_and_ab() {
        String pattern = "ab\\s\\S";
        assertThat("ab c").matches(pattern);
        assertThat("ab\tc").matches(pattern);
        assertThat("ab\nc").matches(pattern);
        assertThat("ab\rc").matches(pattern);
    }

    @Test
    @DisplayName("cat으로 시작하고 cat으로 끝나는 문자열")
    void start_with_cat_and_end_with_cat() {
        String pattern = "^cat.*cat$";
        assertThat("cat").doesNotMatch(pattern);
        assertThat("catcat").matches(pattern);
        assertThat("cat cat").matches(pattern);
        assertThat("cat cat cat").matches(pattern);
    }

    @Test
    @DisplayName("The the 로 시작하는 문자열을 *로 대체")
    void replace_the_the_with_asterisk() {
        String pattern = "[Tt]he";
        String replaceAll = "The cat was the best cat".replaceAll(pattern, "*");
        assertThat(replaceAll).isEqualTo("* cat was * best cat");
    }

    @Test
    @DisplayName("The the 로 시작하는 문자열을 *로 대체")
    void replace_the_the_with_asterisk2() {
        String pattern = "\\b[Tt]he\\b";
        String replaceAll = "The cat was the best cat".replaceAll(pattern, "*");
        assertThat(replaceAll).isEqualTo("* cat was * best cat");
    }

    @Test
    @DisplayName("cat으로 끝나는 문자열 *로 대체")
    void end_with_cat_replace_with_asterisk() {
        String pattern = "cat$";
        String replaceAll = "The cat was the best cat".replaceAll(pattern, "*");
        assertThat(replaceAll).isEqualTo("The cat was the best *");
    }

    @Test
    @DisplayName("is로 시작하는 문자열 *로 대체")
    void start_with_is_replace_with_asterisk() {
        String pattern = "is";
        String replaceAll = "This island is beautiful".replaceAll(pattern, "*");
        assertThat(replaceAll).isEqualTo("Th* *land * beautiful");
    }

    @Test
    @DisplayName("is 단어를 *로 대체")
    void replace_is_with_asterisk() {
        String pattern = "\\bis\\b";
        String replaceAll = "This island is beautiful".replaceAll(pattern, "*");
        assertThat(replaceAll).isEqualTo("This island * beautiful");
    }

    @Test
    @DisplayName("abc 중 하나의 문자 다음에 vz중 하나의 문자가 오는 문자열")
    void start_with_abc_and_end_with_vz() {
        String pattern = "[abc][vz]";
        assertThat("av").matches(pattern);
        assertThat("cz").matches(pattern);
        assertThat("bv").matches(pattern);
        assertThat("cz").matches(pattern);
        assertThat("dz").doesNotMatch(pattern);
    }

    @Test
    @DisplayName("문자의 범위를 제약하는 방법")
    void range_of_characters() {
        String pattern = "Ex_[a-c1-5]";
        assertThat("Ex_a").matches(pattern);
        assertThat("Ex_z").doesNotMatch(pattern);
        assertThat("Ex_1").matches(pattern);
        assertThat("Ex_6").doesNotMatch(pattern);

    }

    @Test
    @DisplayName("첫문자는 숫자이고 두번째 문자는 알파벳인 문자열")
    void start_with_number_and_end_with_alphabet() {
        String pattern = "\\d\\D";
        assertThat("1a").matches(pattern);
        assertThat("1A").matches(pattern);
        assertThat("1z").matches(pattern);
        assertThat("1Z").matches(pattern);
        assertThat("a1").doesNotMatch(pattern);
        assertThat("A1").doesNotMatch(pattern);
        assertThat("z1").doesNotMatch(pattern);
        assertThat("Z1").doesNotMatch(pattern);
    }

    @Test
    @DisplayName("첫문자는 숫자이고 두번째 문자는 공백 문자이고 세번제는 숫자가 아닌 문자열")
    void start_with_number_and_end_with_non_number() {
        String pattern = "\\d\\s\\D";
        assertThat("1 a").matches(pattern);
        assertThat("1 A").matches(pattern);
        assertThat("1 z").matches(pattern);
        assertThat("1 Z").matches(pattern);
        assertThat("a 1").doesNotMatch(pattern);
        assertThat("A 1").doesNotMatch(pattern);
        assertThat("z 1").doesNotMatch(pattern);
        assertThat("Z 1").doesNotMatch(pattern);
    }

    @Test
    @DisplayName("첫번째는 알파벳이고 두번째는 알파벳이 아닌 문자열")
    void start_with_alphabet_and_end_with_non_alphabet() {
        String pattern = "\\w\\W";
        assertThat("a!").matches(pattern);
        assertThat("A!").matches(pattern);
        assertThat("z!").matches(pattern);
        assertThat("Z!").matches(pattern);
    }

    @Test
    @DisplayName("a 가 0이번이상 반복")
    void zero_or_more_a() {
        String pattern = "a*";
        assertThat("").matches(pattern);
        assertThat("a").matches(pattern);
        assertThat("aa").matches(pattern);
        assertThat("aaa").matches(pattern);
        assertThat("aaaa").matches(pattern);
    }

    @Test
    @DisplayName("a 가 1번이상 반복")
    void one_or_more_a() {
        String pattern = "a+";
        assertThat("").doesNotMatch(pattern);
        assertThat("a").matches(pattern);
        assertThat("aa").matches(pattern);
        assertThat("aaa").matches(pattern);
        assertThat("aaaa").matches(pattern);
    }
    @Test
    @DisplayName("a 가 0번 또는 1번 반복")
    void zero_or_one_a() {
        String pattern = "a?";
        assertThat("").matches(pattern);
        assertThat("a").matches(pattern);
        assertThat("aa").doesNotMatch(pattern);
        assertThat("aaa").doesNotMatch(pattern);
        assertThat("aaaa").doesNotMatch(pattern);
    }

    @Test
    @DisplayName("(abc)가 0번 이상 반복")
    void zero_or_more_abc() {
        String pattern = "(abc)*";
        assertThat("").matches(pattern);
        assertThat("abc").matches(pattern);
        assertThat("abcabc").matches(pattern);
        assertThat("abcabcabc").matches(pattern);
        assertThat("abcabcabcabc").matches(pattern);
    }

    @Test
    @DisplayName(" a 또는 b 또는 c가 1번 이상 반복")
    void one_or_more_abc() {
        String pattern = "[abc]+";
        assertThat("a").matches(pattern);
        assertThat("b").matches(pattern);
        assertThat("c").matches(pattern);
        assertThat("ab").matches(pattern);
        assertThat("abc").matches(pattern);
        assertThat("abcabc").matches(pattern);
        assertThat("abcabcabc").matches(pattern);
        assertThat("abcabcabcabc").matches(pattern);
        assertThat("abcd").doesNotMatch(pattern);
    }

    @Test
    @DisplayName("a가 두번 반복")
    void exactly_two_a() {
        String pattern = "a{2}";
        assertThat("a").doesNotMatch(pattern);
        assertThat("aa").matches(pattern);
        assertThat("aaa").doesNotMatch(pattern);
        assertThat("aaaa").doesNotMatch(pattern);
    }

    @Test
    @DisplayName("문자가 2번 이상 반복")
    void two_or_more_characters() {
        String pattern = ".{2,}"; // .은 모든 문자 하나를 의미
        assertThat("a").doesNotMatch(pattern);
        assertThat("aa").matches(pattern);
        assertThat("aaa").matches(pattern);
        assertThat("aaaa").matches(pattern);
        assertThat("!@#$%^&*()").matches(pattern);
        assertThat("!!").matches(pattern);
    }

    @Test
    @DisplayName("a는 첫글자로 0번 이상 반복하고 다음 숫자로 1번이상 반복하는 문자열")
    void zero_or_more_a_and_one_or_more_number() {
        String pattern = "a*\\d+";
        assertThat("1").matches(pattern);
        assertThat("a1").matches(pattern);
        assertThat("aa1").matches(pattern);
        assertThat("aaa1").matches(pattern);
        assertThat("aaaa1").matches(pattern);
        assertThat("aaaaa123456789").matches(pattern);
    }

    @Test
    @DisplayName("a는 첫글자로 0번 이상 반복하고 b는 0또는 1번 반복하고 c는 1번이상 반복하는 문자열")
    void zero_or_more_a_and_zero_or_one_b_and_one_or_more_c() {
        String pattern = "a*b?c+";
        assertThat("ac").matches(pattern);
        assertThat("abc").matches(pattern);
        assertThat("aabc").matches(pattern);
        assertThat("aabcc").matches(pattern);
        assertThat("bccccc").matches(pattern);
        assertThat("abbc").doesNotMatch(pattern);
    }

    @Test
    @DisplayName("1 character + whitespaces + 1 character 문자열")
    void one_character_plus_whitespaces_plus_one_character() {
        String pattern = "(\\w)(\\s+)([\\w])";
        System.out.println("Hello           World".replaceAll(pattern, "-"));

        System.out.println("Hello           World".replaceAll(pattern, "$1-$3"));

    }

    @Test
    @DisplayName("a 첫글자 0번 이상 반복 두번째 숫자 0번이상 가능")
    void zero_or_more_a_and_zero_or_more_number() {
        String pattern = "a*[0-9]*";
        assertThat("").matches(pattern);
        assertThat("a").matches(pattern);
        assertThat("1").matches(pattern);
        assertThat("a1").matches(pattern);
        assertThat("aa1").matches(pattern);
        assertThat("aaa1").matches(pattern);
        assertThat("aaaa1").matches(pattern);
        assertThat("aaaaa123456789").matches(pattern);
    }

    @Test
    @DisplayName("Pattern.matches() 메소드를 사용한 예제")
    void pattern_matches() {
        String pattern = "\\bcat\\b";
        Pattern compiled = Pattern.compile(pattern);
        Matcher matcher = compiled.matcher("cat cattie cat cat");
        while (matcher.find()) {
            System.out.println("Start index: " + matcher.start());
            System.out.println("End index: " + matcher.end());
            System.out.println("Found: " + matcher.group());
        }
    }

    @Test
    @DisplayName("숫자 패턴 찾기")
    void find_number_pattern() {
        String pattern = "[\\d]{3}-[\\d]{5}";
        Pattern compiled = Pattern.compile(pattern);
        Matcher matcher = compiled.matcher("123-12345");
        assertThat(matcher.find()).isTrue();
    }

    @Test
    @DisplayName("back reference")
    void back_reference() {
        String pattern = "c(..) s\\1";
        Pattern compiled = Pattern.compile(pattern);
        Matcher matcher = compiled.matcher("The cat sat on the mat.");
        assertThat(matcher.find()).isTrue();
        assertThat(matcher.group()).isEqualTo("cat sat");
    }

    @Test
    @DisplayName("중복된 문자열 찾기")
    void find_duplicate_string() {
        String pattern = "\\b(\\w+)\\s+\\1\\b"; // \\w 문자와 숫자를 의미 \\w+ 문자와 숫자가 1번이상 반복 \\s 공백문자 \\s+ 공백문자가 1번이상 반복
        Pattern compiled = Pattern.compile(pattern);
        Matcher matcher = compiled.matcher("The the cat sat on the the mat.");
        assertThat(matcher.find()).isTrue();
        assertThat(matcher.group()).isEqualTo("the the");
    }

    @Test
    @DisplayName("중복된 문자열 찾기")
    void find_duplicate_string2() {
        String pattern = "\\b(\\w+)\\s+\\1\\b"; // \\w 문자와 숫자를 의미 \\w+ 문자와 숫자가 1번이상 반복 \\s 공백문자 \\s+ 공백문자가 1번이상 반복
        Pattern compiled = Pattern.compile(pattern);
        Matcher matcher = compiled.matcher("Hello world world world");
        assertThat(matcher.find()).isTrue();
        assertThat(matcher.group()).isEqualTo("world world");
    }

    @Test
    @DisplayName("replace 예제")
    void replace() {
        Pattern compiled = Pattern.compile("dog");
        Matcher matcher = compiled.matcher("The dog says meow. All dogs say meow.");
        String replaceAll = matcher.replaceAll("cat");
        assertThat(replaceAll).isEqualTo("The cat says meow. All cats say meow.");

        String replaceAll1 = "The cat sat on the mat.".replaceAll("at[.]", "*");
        assertThat(replaceAll1).isEqualTo("The cat sat on the m*");

        String replaceAll2 = "The cat sat on the mat.".replaceAll("at\\b", "*");
        assertThat(replaceAll2).isEqualTo("The c* s* on the m*.");

        String replaceAll3 = "The cat sat on the mat.".replaceAll("at[.]?", "*");
        assertThat(replaceAll3).isEqualTo("The c* s* on the m*");

        String replaceAll4 = "The cat sat on the mat.".replaceAll("at[.]*", "*");
        assertThat(replaceAll4).isEqualTo("The c* s* on the m*");

        String replaceAll5 = "The cat sat on the mat.".replaceAll("[a-z]", "*");
        assertThat(replaceAll5).isEqualTo("T** *** *** ** *** ***.");

        String replaceAll6 = "The cat sat on the mat.".replaceAll("[a-z]+", "*");
        assertThat(replaceAll6).isEqualTo("T* * * * * *.");
    }

    @Test
    @DisplayName(" 가장 적은 조건으로 매칭되는 문자열을 찾는다")
    void find_least_match() {
        Pattern compiled = Pattern.compile("c.+t");
        Matcher matcher = compiled.matcher("The cat sat on the mat.");
        assertThat(matcher.find()).isTrue();
        assertThat(matcher.group()).isEqualTo("cat sat on the mat");

        String replaceAll = matcher.replaceAll("*");
        assertThat(replaceAll).isEqualTo("The *.");

        Pattern compiled2 = Pattern.compile("c.+?t");
        Matcher matcher2 = compiled2.matcher("The cat sat on the mat.");
        assertThat(matcher2.find()).isTrue();
        assertThat(matcher2.group()).isEqualTo("cat");

        String replaceAll2 = matcher2.replaceAll("*");
        assertThat(replaceAll2).isEqualTo("The * sat on the mat.");
    }

}
