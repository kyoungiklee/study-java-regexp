package org.opennuri.study.regexp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}