package org.opennuri.study.regexp.learning.regular.expression;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SuppressWarnings("NonAsciiCharacters")
class Chapter11Test {
    @Test
    @DisplayName("북미전화번호")
    void 북미전화번호() {
        String source = "J.Doe: 248-555-1234\n" +
                "B. Smith: (313) 555-1234\n" +
                "A. Lee: (810)555-1234\n" +
                "M. Jones: 734.555.9999";

        //숫자세자리 지역번호가 먼저 나오고 세자리 숫자 국번, 하이픈, 네자리 숫자가 나오는 형식이다
        //아무 숫자나 전화번호로 쓸수 있다
        //지역번호와 국번은 0이나 1로 시작할 수 없다
        //지역번호는 괄호로감싸기도하고 실제 전화번호와 하이픈으로 구분하기도 한다.
        //(555) 555-555, (555)555-555, 555-555-555
        Matcher matcher = Pattern.compile("\\(?[2-9]\\d\\d\\)?[ -.]?[2-9]\\d\\d[-.]\\d{4}").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(4);
        list.forEach(log::info);

    }

    @Test
    @DisplayName("미국 우편번호")
    void 미국_우편번호() {
        //미국 우편번호는 모두 숫자로 이루어진다.
        //최초의 숫자는 동부에서 시작하여 서부로 할당되었다. 0은 동해안 9는 서해안에 사용된다.
        //1983년 우체국에서 ZIP+4라고 부르는 확장된 우편번호를 사용하기 시작하였다.
        //숫자 4개를 추가하여 더 구체적인 위치(주로 특정 도시구역이나, 특정건물)를 제공한다.
        //유효성 검사를 하려면 다섯자리 숫자 우편번호와 ZIP+4 양쪽을 함께 확인 할 수 있어야 한다.

        String source = "999 1st Avenue, Bigtown, NY, 11222\n" +
                "123 High Street, Any City, MI 48034-1234";

        Matcher matcher = Pattern.compile("\\d{5}(-\\d{4})?").matcher(source);

        List<String> list = matcher.results().map(MatchResult::group).toList();

        assertThat(list).hasSize(2);
        list.forEach(log::info);
    }

    @Test
    @DisplayName("캐나다 우편번호")
    void 캐나다_우편번호() {
        //캐나다 우편번호는 문자 6개로 구성되며 문자와 숫자가 번갈아 가면서 나온다
        //처음에 연속해 나오는 문자와 세자리 숫자는 전달 분류 지역을 나태내고,
        //두번째에 연속해 나오는 문자와 숫자 세 개는 지역 배달 구역을 나타낸다.
        //전달 분류 지역에서 첫 글자는 주(province)를 나타내는데 18개 문자만 유효하다.(ABCEGHJKLMNPRSTVXY)
        String source = "123 4th Street, Toronto, Ontario M1A 1A1\n" +
                "567 8th Avenue, Montrial, Quebec, H9Z 9Z9";

        Matcher matcher = Pattern.compile("[ABCEGHJKLMNPRSTVXY]\\d[A-Z] \\d[A-Z]\\d").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(2);

        list.forEach(log::info);

    }
}