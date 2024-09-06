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
    @DisplayName("Step 11: Parentheses ()as Capturing Groups")
    void parentheses_as_capturing_groups() {
        String source = "The current President of Bolivia is Evo Morales .";
        Matcher matcher = Pattern.compile("([A-Z])|([a-z])").matcher(source);

        // ([A-Z])|([a-z])
        // The above regular expression defines tow capturing groups that are indexed starting from 1.
        // The first capturing group matches any single uppercase letter and the second capturing group
        // matches any single lowercase letter. By using the 'or' sign | and parentheses () as a capturing group
        // we can define a single regular expression that matches multiple kinds of strings

        List<String> list = matcher.results().filter(matchResult -> matchResult.group(1) != null)
                .map(MatchResult::group)
                .toList();
        list.forEach(log::info);
        assertThat(list).contains("T", "P", "B", "E", "M");

        // float, long 타입 찾기
        source = "42L 12 x 3.4f 6l 3.3 0F LF .2F 0.";
        Matcher matcher1 = Pattern.compile("(\\d*\\.\\d+[Ff]|\\d+\\.\\d*[Ff]|\\d+[Ff])|(\\d+[Ll])").matcher(source);
        list = matcher1.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(5);

        // Add two more capturing groups to the above regular expression so that it also classification double or ints
        // (This is another tricky question, don't be discouraged if it takes a while, see my solution as a last resort)
        //

        matcher = Pattern.compile("(\\d*\\.\\d+[Ff]|\\d+\\.\\d*[Ff]|\\d+[Ff])|(\\d+[Ll])|(\\d+\\.\\d+)|(\\d+)").matcher(source);
        list = matcher.results().map(MatchResult::group).toList();
        list.forEach(log::info);
        assertThat(list).hasSize(8);
        // Character ranges to sort the following ages: "drinkable in the US" (>=21) and "not drinkable in the USA"(< 21):
        source = "7 10 17 18 19 20 21 22 23 24 30 40 100 120";
        matcher = Pattern.compile("(2[0-9]|[3-9]\\d|1\\d\\d)|(1[0-9]|20|[0-9])").matcher(source);

        AtomicReference<ArrayList<String>> adult = new AtomicReference<>(new ArrayList<>());
        AtomicReference<ArrayList<String>> child = new AtomicReference<>(new ArrayList<>());

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                adult.get().add(matcher.group(1));
            }
            if (matcher.group(2) != null) {
                child.get().add(matcher.group(2));
            }
        }

        adult.get().forEach(log::info);
        child.get().forEach(log::info);
    }

    @Test
    @DisplayName("Step 12: Identify More Specific Matches First")
    void identify_more_specification_matches_first() {
        // By default, most regex engines use greedy matching with the basic characters we've seen so far
        // This means that the regex engine will capture the longest group defined as early as possible in the provided regex
        // So while the second group above could capture more characters in names such as "Jobs" and "Cloyed" for example
        // but since the first three characters of those names have already captured by the first capture group, they cannot be captured again by the second
        CharSequence source = "Kim Jobs Xu Cloyd Mohr Ngo Rock";
        Matcher matcher = Pattern.compile("([A-Z][a-z]?[a-z]?)|([A-Z][a-z][a-z][a-z]+)").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        list.forEach(log::info);

        // Now let's make a small fix - just change the order of the capture groups, putting the more specific(longer) group first

        matcher = Pattern.compile("([A-Z][a-z][a-z][a-z]+)|([A-Z][a-z]?[a-z]?)").matcher(source);
        list = matcher.results().map(MatchResult::group).toList();
        list.forEach(log::info);

        assertThat(list).contains("Jobs", "Cloyd");

        // "More specific" pattern almost always means "longer"
        // Suppose we want to find two kinds of "words": first those that start with vowels(more specifically), then
        // those that don't start with vowels(any other words).
        // Try writing a regular expression to capture and identity string that match those two groups
        // (Groups below are lettered, not numbered. You must determine which group should match first and which should match second.)

        source = "pds6f uub 24r2gp ewqrty l ui_op";
        matcher = Pattern.compile("(\\d+[A-Za-z0-9]+)|([A-Za-z]+\\w+|[A-Za-z])").matcher(source);

        list = matcher.results().filter(matchResult -> matchResult.group(1) != null).map(MatchResult::group).toList();
        log.info(list.toString());

        matcher = Pattern.compile("([^aeiou ]\\w*)|(\\w+)").matcher(source);


        list = matcher.results().filter(matchResult -> matchResult.group(2) != null).map(MatchResult::group).toList();
        log.info(list.toString());
    }




    @Test
    @DisplayName("Step 13: Curly Braces {}for a Specific Number of Repetitions")
    void curly_braces_for_a_specific_number_of_repetitions() {
        // For the first group, we needed surnames with four or more letters.
        // The seconds group was to capture last names with three letters of less
        CharSequence source = "Kim Jobs Xu Cloyd Mohr Ngo Rock";
        Matcher matcher = Pattern.compile("([A-Z][a-z][a-z][a-z]+)|([A-Z][a-z]?[a-z]?)").matcher(source);

        // Is there any easier way to write this than repeating these [a-z]groups over and over again?
        // Exist if you use curly braces for it.
        // Curly braces allow us to specify a minimum and (optionally) maximum number of matches for the previous character or character group.
        // There ar three use cases:
        // {X}: matches exactly X times.
        // {X,}: matches >= X times
        // {X,Y}: matches >= X and <= Y times

        source = "humuhumunuk unukuapua'a.";
        matcher = Pattern.compile("[a-z]{11}").matcher(source);

        List<String> list = matcher.results().map(MatchResult::group).toList();

        Condition<String> length11 = new Condition<>(s -> s.length() == 11, "string length");

        assertThat(list.get(0)).is(length11);

    }

    // test case 중 Condition의 사용법과 is(), doesNotHave(), haveExactly(), doNotHave(), allOf(), anyOf() 학습코드
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Member {

        private String name;
        private int age;
    }
    @Test
    @DisplayName("Using Conditions with AssertJ Assertions")
    void using_conditions_with_assertj_assertions() {

        Condition<Member> senior = new Condition<>(
                m -> m.getAge() >= 60, "senior");

        Condition<Member> nameJohn = new Condition<>(
                m -> m.getName().equalsIgnoreCase("John"),
                "name John"
        );

        Member member = new Member("John", 65);
        assertThat(member).is(senior);

        member = new Member("Jane", 60);
        assertThat(member).doesNotHave(nameJohn);

        List<Member> members = new ArrayList<>();
        members.add(new Member("Alice", 50));
        members.add(new Member("Bob", 60));

        assertThat(members).haveExactly(1, senior);
        assertThat(members).doNotHave(nameJohn);

        Member john = new Member("John", 60);
        Member jane = new Member("Jane", 50);

        assertThat(john).is(allOf(senior, nameJohn));
        assertThat(jane).is(allOf(not(nameJohn), not(senior)));

        john = new Member("John", 50);
        jane = new Member("Jane", 60);

        // not – creates a condition that is met if the specified condition is not met
        //allOf – creates a condition that is met only if all of the specified conditions are met
        //anyOf – creates a condition that is met if at least one of the specified conditions is met

        assertThat(john).is(anyOf(nameJohn, senior));
        assertThat(jane).is(anyOf(nameJohn, senior));

    }
}