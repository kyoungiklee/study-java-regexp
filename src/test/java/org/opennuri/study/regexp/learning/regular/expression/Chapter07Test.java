package org.opennuri.study.regexp.learning.regular.expression;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("NonAsciiCharacters")
@Slf4j
class Chapter07Test {

    @Test
    @DisplayName("하위 표현식 이해하기")
    void 하위_표현식_이해하기() {
        String source = "Hello, my name is Ben&nbps;Forta, and I am\n" +
                "the author of multiple books on SQL (including\n" +
                "MySQL, Oracle PL/SQL, and SQL Server T-SQL),\n" +
                "Regular&nbsp;&nbsp;&nbsp;Expression, and other subjects.";
        //&nbsp;{2,}은 &nbsp다음에 ;가 두개이상 오는 문자를 찾는다.
        Matcher matcher = Pattern.compile("&nbsp;{2,}").matcher(source);

        assertThat(matcher.find()).isEqualTo(false);
    }

    @Test
    @DisplayName("하위 표현식으로 묶기")
    void 하위_표현식으로_묶기() {

        String source = "Hello, my name is Ben&nbsp;Forta, and I am\n" +
                "the author of multiple books on SQL (including\n" +
                "MySQL, Oracle PL/SQL, and SQL Server T-SQL),\n" +
                "Regular&nbsp;&nbsp;&nbsp;Expression, and other subjects.";
        Matcher matcher = Pattern.compile("(&nbsp;){2,}").matcher(source);

        assertThat(matcher.find()).isEqualTo(true);

        source = "Pinging hog.forta.com [12.159.46.200]\n" +
                "with 32 bytes of data:";
        matcher = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}").matcher(source);

        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("12.159.46.200");

        // 하위 표현식으로 묶어서 표현하면...
        matcher = Pattern.compile("(\\d{1,3}.){3}\\d{1,3}").matcher(source);

        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("12.159.46.200");

        // 1967 년도와 매칭하고자 한다.
        source = "ID: 042\n" +
                "SEX: M\n" +
                "DOB: 1967-08-17\n" +
                "STATUS: Active";
        // 19와 매칭된다. 19 OR 20\\d{2}를 찾는 정규식이다.
        matcher = Pattern.compile("19|20\\d{2}").matcher(source);
        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("19");

        matcher = Pattern.compile("(19|20)\\d{2}").matcher(source);
        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("1967");

    }

    @Test
    @DisplayName("중첩된 하위 표현식")
    void 중첩된_하위_표현식() {
        String source = "Pinging hog.forta.com [12.159.46.200]\n" +
                "with 32 bytes of data:";
        Matcher matcher = Pattern.compile("(((25[1-5])|(2[1-4]\\d)|(1\\d{2})|(\\d{1,2}))\\.){3}((25[1-5])|(2[0-4]\\d)|(1\\d{2})|(\\d{1,2}))").matcher(source);
        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("12.159.46.200");

    }
}