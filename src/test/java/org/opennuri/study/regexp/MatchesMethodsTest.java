package org.opennuri.study.regexp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class MatchesMethodsTest {
    //matches() 사용법 확인하기
    @Test
    @DisplayName("matches() 사용법")
    void matches_regex_test() {
        String txt = "123456";
        boolean matches = txt.matches("^[0-9]+$");
        assertThat(matches).isEqualTo(true);
    }

    @Test
    @DisplayName("replaceAll() 사용법")
    void replaceAll_regex_test() {
        String txt = "Hulk989!@#";
        String result = txt.replaceAll("[^a-zA-Z0-9]", "*");
        assertThat(result).isEqualTo("Hulk989***");
    }

    @Test
    @DisplayName("split() regex 사용법")
    void split_regex_test() {
        String txt = "Hulk989!@#";
        String[] split = txt.split("[0-9]+");
        assertThat(split).contains("Hulk", "!@#");
    }
}