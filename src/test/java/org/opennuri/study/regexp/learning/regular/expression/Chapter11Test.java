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

    @Test
    @DisplayName("영국 우편번호")
    void 영국_우편번호() {
        //영국 우편번호는 다섯자리, 여섯자리, 일곱자리이면 숫자와 문자로 구성된다.
        //우편번호는 두 부분으로 나뉘는데, 외부 우편번호와 내부 우편번호이다.
        //외부번호는 한자리나 두자리로 이루어진 알파벳 문자가 오고 이 뒤를 이러 한자리나 두자리 숫자가 온다.
        //또는 한 자리나 두 자리 문자뒤에 한 자리 숫자와 한 자리 문자가 차례로 온다.
        //내부 번호는 항상 한 자리 숫자 뒤에 두 자리 문자가 온다.
        //이때 C,I,K,M,O,V는 제외하는데 이 문자들은 내부 번호에서 전혀 사용하지 않는다.
        //내부 번호와 외부번호는 빈칸으로 구분한다.
        String source = "171 Kyverdale Road, London N16 6PS\n" +
                "33 Main Street, Portsmouth, P01 3AX\n" +
                "18 High Street, London, NW11 8AB";

        Matcher matcher = Pattern.compile("[A-Z]{1,2}\\d[A-Z\\d]? \\d[ABD-HJLNP-UW-Z]{2}").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(3);
        list.forEach(log::info);
    }

    @Test
    @DisplayName("미국 사회보장번호")
    void 미국_사회보장번호() {
        //미국사회보장번호는 숫자로 구성되어 있다
        //세묶음으로 나뉘는데 각 부분은 하이픈으로구분된다.
        //첫 부분은 세자리로 구성되고, 두번째 부분은 두 자리 숫자, 마지막 부분은 네 자리 숫자로 이루어진다.
        String source = "John Smith: 123-45-6789";
        Matcher matcher = Pattern.compile("\\d{3}-\\d{2}-\\d{4}").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(1);
        list.forEach(log::info);
    }

    @Test
    @DisplayName("IP 주소")
    void IP_주소() {
        //ip주소는 네바이트로 이루어진다.
        //IP주소는 숫자 네 묶음을 주로 마침표(.)로 구분한다.
        String source = "localhost is 127.0.0.1.";
        Matcher matcher = Pattern.compile("((25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(1);
        list.forEach(log::info);
    }

    @Test
    @DisplayName("URL 주소")
    void URL_주소() {
        String source = "http://www.forta.com/blog\n" +
                "https://www.forta.com/blog/index.cfm\n" +
                "http://www.forta.com\n" +
                "http://ben:password@www.forta.com/\n" +
                "http://localhost/index.php?ab=1&c=2\n" +
                "http://localhost:8500";

        Matcher matcher = Pattern.compile("https?:\\/\\/(\\w*:\\w*@)?[\\w.]+([:\\d]+)?(\\/([\\w\\/\\.]*(\\?\\S+)?)?)?").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(6);
        list.forEach(log::info);

    }

    @Test
    @DisplayName("이메일 주소")
    void 이메일_주소() {
        String source = "My name is Ben Forta, and my\n" +
                "email address is kyoung@email.forta.com, kyoungik.lee+1@gmail.com, kyoung.ik.lee+1@email.forta.com";

        Matcher matcher = Pattern.compile("(\\w+\\.)*[\\w+]+@(\\w+\\.)+[A-Za-z]+").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(3);
        list.forEach(log::info);

    }
}