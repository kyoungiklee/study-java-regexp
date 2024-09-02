package org.opennuri.study.regexp.learning.regular.expression;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@Slf4j
class Chapter06Test {

    @Test
    @DisplayName("경계 지정하기")
    void 경계_지정하기() {
        String source = "The cat scattered his food all over the room";
        Matcher matcher = Pattern.compile("cat").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(2);
    }

    @Test
    @DisplayName("단어 경계 지정하기")
    void 단어_경계_지정하기() {
        String source = "The cat scattered his food all over the room";
        Matcher matcher = Pattern.compile("\\bcat\\b").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();

        assertThat(list).hasSize(1);

        source = "The captain wore this cap and cape proudly as\n" +
                "he sat listening to the recap of how his \n" +
                "crew saved the mem from a capsized vessel.";
        matcher = Pattern.compile("\\bcap").matcher(source);
        List<String> list1 = matcher.results().map(MatchResult::group).toList();

        assertThat(list1).hasSize(4);

        matcher = Pattern.compile("cap\\b").matcher(source);
        List<String> list2 = matcher.results().map(MatchResult::group).toList();

        assertThat(list2).hasSize(2);


        source = "Please enter the nine-digit id as is \n" +
                "appears on your color - coded pass-key.";

        matcher = Pattern.compile("\\B-\\B").matcher(source);

        List<String> list3 = matcher.results().map(MatchResult::group).toList();

        assertThat(list3).hasSize(1);
        assertThat(list3).contains("-");

    }

    @Test
    @DisplayName("문자열 경계 정의하기")
    void 문자열_경계_정의하기() {

        String source = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<wsdl:definitions targetNamespace=\"http://tips.cf\"\n" +
                "xmlns:impl=\"http://tips.cf\" xmlns:\"intf=http://tips.cf\"\n" +
                "xmlns:apachesoap=\"http://xml.apache.org/xml-soap\"";

        Matcher matcher = Pattern.compile("^\\s*<\\?xml.*\\?>").matcher(source);
        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");

        source = "<html>\n" +
                "<body>\n" +
                "<h1>Welcome to my Homepage</h1>\n" +
                "Content is divided into two sections:<br/>\n" +
                "<h2>SQL</h2>\n" +
                "Information about SQL.\n" +
                "<h2>RegEx</h2>\n" +
                "Information about Regular Expression.\n" +
                "<h2>This is not valid HTML</h3>\n" +
                "</body>\n" +
                "</html>";
        matcher = Pattern.compile("</[Hh][Tt][Mm][Ll]>\\s*$").matcher(source);

        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("</html>");

    }

    @Test
    @DisplayName("다중행 모드 사용하기")
    void 다중행_모드_사용하기() {
        String source = "<script>\n" +
                "function doSpellCheck(from, field) {\n" +
                "\t//make sure not empty\n" +
                "\tif (field.value == '') {\n" +
                "\t\treturn false;\n" +
                "\t}\n" +
                "\t\n" +
                "\t//Init\n" +
                "\tvar windowName='spellWindow';\n" +
                "\tvar spellCheckURL='spell.cfm?formname=comment&fieldname='+field.name;\n" +
                "\t...\n" +
                "\t//Done\n" +
                "\tretrun false;\n" +
                "}\n" +
                "</script>";
        Matcher matcher = Pattern.compile("(?m)^\\s*\\/\\/.*$").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();

        assertThat(list).hasSize(3);

    }
}