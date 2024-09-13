package org.opennuri.study.regexp.learning.regular.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;

@SuppressWarnings("NonAsciiCharacters")
@Slf4j
class Chapter10Test {
    @Test
    @DisplayName("역참조 조건")
    void 역참조_조건() {
        String source = "<!-- Nav bar -->\n" +
                "<div>\n" +
                "    <a href=\"/home\"><img src=\"/images/home.gif\"></a>\n" +
                "    <img src=\"/images/spacer.gif>\n" +
                "    <a href=\"/search\"><img src=\"images/search.gif\"></a>\n" +
                "    <img src=\"/images/spacer.gif>\n" +
                "    <a href=\"/help\"><img src=\"/imgage/help.gif\"></a>\n" +
                "</div>";
        // 조건 참조가
        Pattern.compile("(<[Aa]\\s+[^>]+>\\s*)?<[Ii][Mm][Gg]\\s+[^>]+>(?:\1)?\\s*<\\/[Aa]>").matcher(source);


    }

    @Test
    @DisplayName("조건달기")
    void 조건달기() {
        String source = "123-456-7890\n" +
                "(123)456-7890\n" +
                "(123)-456-7890\n" +
                "(123-456-7890\n" +
                "1234567890\n" +
                "123 456 7890";
        Matcher matcher = Pattern.compile("(^(\\d{3}-)|^(\\(\\d{3}\\)))\\d{3}-\\d{4}",Pattern.MULTILINE).matcher(source);

        List<String> list = matcher.results().map(MatchResult::group).toList();
        list.forEach(log::info);
    }

    @Test
    @DisplayName("전후방탐색조건")
    void 전후방탐색조건() {
        String source = "11111\n" +
                "22222\n" +
                "33333-\n" +
                "44444-4444";
        Matcher matcher = Pattern.compile("").matcher(source);
    }

    @Test
    @DisplayName("그룹캡쳐")
    void 그룹_캡처() {

        String[] stringArray = {
                "나는 바보입니다",
                "나는 개입니다",
                "나는 새입니다",
                "나는 코끼리입니다",
                "너는 개구리입니다",};

        for (String s : stringArray) {
            Pattern pattern = Pattern.compile("나는 (.*)입니다");
            Matcher matcher = pattern.matcher(s);
            if (matcher.matches()) {
                log.info(matcher.group(0));
                log.info(matcher.group(1));
            }
        }

        for (String s : stringArray) {
            Pattern pattern = Pattern.compile("(?:.*)는 (.*)입니다");
            Matcher matcher = pattern.matcher(s);

            if (matcher.matches()) {
                log.info(matcher.group(0));
                log.info(matcher.group(1));
            }
        }

        for (String s : stringArray) {
            Pattern pattern = Pattern.compile("(.*)는 (.*)입니다");
            Matcher matcher = pattern.matcher(s);
            if (matcher.matches()) {
                String replaced = s.replaceAll("(.*)는 (.*)입니다", "$2는 $1입니다.");
                log.info(replaced);
            }
        }

        // 그룹이름 지정
        log.info("그룹이름 지정");
        for (String s : stringArray) {
            Pattern pattern = Pattern.compile("(?<animal>.*)는 (?<human>.*)입니다");
            Matcher matcher = pattern.matcher(s);
            if (matcher.matches()) {
                String replaced = matcher.replaceAll("${human}는 ${animal}입니다.");
                log.info(replaced);
            }
        }

    }

    @Test
    @DisplayName("중복표현제거")
    void 중복_표현_제거() {
        String[] foo = {
                "하하여여드드드ㅋㅋㅎㅎㅎ",
                "ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ",
                "웃겨ㅕㅕㅕㅕㅕㅕㅕㅕㅕㅕ",
                "빼에에에에에에에에에에엑",
                "와아아아아아아아아아아아",
                "응ㅋ어쩔어쩔어쩔어쩔어쩔",
                "어쩔티비어쩔티비어쩔티비",
        };

        log.info("반복되는 문자(모음, 자음, 음절 포함)");

        for (String s : foo) {
            String replaced = s.replaceAll("(\\S)(\\1+)", "$1");
            log.info(replaced);
        }

        log.info("반복되는 음절");
        for (String s : foo) {
            // ([가-힣]) : "가"부터 "힣"사이에 존재하는 한 자리 문자만 그룹으로 잡는다.
            // \\1+ : 그룹으로 잡힌 문자가 1개 이상 존재하는 패턴을 찾는다.
            String replaced = s.replaceAll("([가-힣])(\\1+)", "$1");
            log.info(replaced);
        }

        for (String s : foo) {
            String replaced = s.replaceAll("(\\S{1,}?)\\1+", "$1");
            log.info(replaced);
        }
    }
}