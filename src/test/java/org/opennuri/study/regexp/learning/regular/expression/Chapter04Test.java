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
class Chapter04Test {

    @Test
    @DisplayName("이스테이프 메타문자 사용하기1")
    void 이스케이프_메타문자_사용하기() {
        //
        String source = "var myArray = new Array();\n" +
                "...\n" +
                "if(myArray[0] == 0 {\n" +
                "...\n" +
                "}";
        Matcher matcher = Pattern.compile("myArray\\[0\\]").matcher(source);
        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("myArray[0]");

        //문자집합 [0-9] 사용
        matcher = Pattern.compile("myArray\\[[0-9]\\]").matcher(source);
        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("myArray[0]");

        // /d 메타문자 사용 /d는 [0-9]와 같음
        matcher = Pattern.compile("myArray\\[\\d\\]").matcher(source);
        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("myArray[0]");
    }

    @Test
    @DisplayName("슬래쉬 메타문자 이스케이프 사용")
    void 슬래쉬_메타문자_이스케이프_사용(){
        String source = "\\home\\Ben\\sales\\";

        Matcher matcher = Pattern.compile("\\\\").matcher(source);

        int index = 0;
        while (matcher.find()) {
            log.info(matcher.group());
        }
    }

    @Test
    @DisplayName("공백문자 찾기")
    void 공백문자_찾기() {

        String source = "\"101\",\"Ben\",\"Forta\"\n" +
                "\"102\",\"Jim\",\"James\"\n" +
                "\r\n" +
                "\"103\",Roberta\",\"Robertson\"\n" +
                "\"104\",\"Bob\",Bobson\"";

        Matcher matcher = Pattern.compile("\r\n").matcher(source);
        assertThat(matcher.find()).isEqualTo(true);
    }

}