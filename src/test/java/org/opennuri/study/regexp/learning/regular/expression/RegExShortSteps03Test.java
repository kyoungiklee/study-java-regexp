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

@Slf4j
class RegExShortSteps03Test {

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

        source = "humuhumunukunukuapua 'a.";
        matcher = Pattern.compile("[a-z]{18,}").matcher(source);
        list = matcher.results().map(MatchResult::group).toList();

        Condition<String> lengthMore18 = new Condition<>(s -> {
            return s.length() >= 18;
        }, "length more than 18");

        assertThat(list.get(0)).is(lengthMore18);

        matcher = Pattern.compile("[a-z]{11,18}").matcher(source);
        list = matcher.results().map(MatchResult::group).toList();

        Condition<String> length18 = new Condition<>(s -> {
            return s.length() >= 11 && s.length() <= 18;

        }, "length is greater than or equal 11 and less than or equal 18");

        assertThat(list.get(0)).is(length18);
        assertThat(list.get(0)).is(anyOf(length18, length11));

        // 182-82-0192 찾기
        source = "113-25=1902 182-82-0192 H23-_3-9982 1I1-O0-E38B";
        matcher = Pattern.compile("\\d{3}-\\d{2}-\\d{4}").matcher(source);
        list = matcher.results().map(MatchResult::group).toList();

        assertThat(list).containsExactly("182-82-0192");

        // Assume that the password strength system of website requires user password to be between 6 and 12 characters long.
        // Write a regular expression the flags bad password in the list below.
        // Each password is enclosed in parentheses for easy matching, so make sure the regular expression stats and with literals ( and ) symbols.
        // Hint: make sure you forbid literal brackets in password with [^()] or similar, otherwise you'll end up matching the entire string

        source = "(12345) (my password) (Xanadu.2112) (su_do) (OfSalesmen!)";

        matcher = Pattern.compile("\\([^()]{1,5}\\)|\\([^()]+\\s[^()]*\\)").matcher(source);
        list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).hasSize(3);
    }

    @Test
    @DisplayName("Step 14: \\b Zero Width Border Character")
    void zero_width_border_character() {

        String source = "Ve still vant ze money, Lebowski.";

        // The sequence [^ ] must match any character that is not a literal space character.
        // So why doesn't this match a comma, after money or a period after Levoski
        // This is because the comma and period are non-word characters
        // They appear between the end of the word money and the comma that follows it, and between the word Lebowski and the dot that follows it.
        // The regular expression matches on the boundaries of these words(but not on non-word characters, which only to define them)

        Matcher matcher = Pattern.compile("\\b[^ ]+\\b").matcher(source);

        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).containsExactly("Ve", "still", "vant", "ze", "money", "Lebowski");

        // But what happened if we don't include the sequences \b to out template?

        matcher = Pattern.compile("[^ ]+").matcher(source);
        list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).containsExactly("Ve", "still", "vant", "ze", "money,", "Lebowski.");

        // Now let's use word boundaries to fix the regular expression for quoted password

        source = "\"12345\" \"my password\" \"Xanadu.2112\" \"su_do\" \"OfSalesmen!\"";

        // By placing word boundaries inside quotes, we are effectively saying the first and last characters of matching password must be "word characters"
        // So this works find here, but
        matcher = Pattern.compile("\"\\b[^\"]{0,5}\\b\"|\"\\b[^\"]+\\s[^\"]*\\b\"").matcher(source);
        list = matcher.results().map(MatchResult::group).toList();

        assertThat(list).containsExactly("\"12345\"", "\"my password\"", "\"su_do\"");

    }

    @Test
    @DisplayName("Step15: Caret ^ as beginning of line and dollar sing $ as end of line")
    void caret_as_beginning_line_dollar_sign_as_end_of_line() {
        // The word boundary sequence is not only zero-width special sequence available for use in regular expression
        // The two most popular of these ar caret-"beginning of line" and dollar sing-"end of line"
        // Including one of these in your regular expression means that the given match appear at the beginning or end of the original string

        String source = "start end start end start end start end";
        Matcher matcher = Pattern.compile("^start|end$").matcher(source);

        List<String> list = matcher.results().map(MatchResult::group).toList();

        assertThat(list).containsExactly("start", "end");

        // If your string contains line breaks, it ^start will match "start" at the beginning of any line and end$ will match "end" at the end of any line
        // These characters are especially useful when working with data that contains delimiters.
        // Let's go back to the "file size" problem from step 9 using ^ "beginning of line"
        // In this example, our file sizes are separated by spaces " ".
        // So we want each file size to start with a digit preceded by a space character or the beginning of a line

        source = "6.6KB 1..3KB 12KB 5G 3.3MB KB .6.2TB 9MB";
        matcher = Pattern.compile("(^| )(\\d+|\\d+\\.\\d+)[KMGT]B").matcher(source);

        list = matcher.results().map(MatchResult::group).toList();

        // But you may notice that we still have one small problem, we are matching a space character before the allowed fine size
        // Now we can just ignore this capturing group(1) when our regex engin find it, or we can use the non-capturing group which we will see in the next step
        assertThat(list).containsExactly("6.6KB", " 12KB", " 3.3MB", " 9MB");

        // some syntax highlights will highlight tailing spaces, that is, any spaces that are between a non-whitespaces character and the end of line.
        source = "myvec <- c(1, 2, 3, 4, 5)       ";
        matcher = Pattern.compile("\s+$").matcher(source);

        list = matcher.results().map(MatchResult::group).toList();
        assertThat(list.get(0)).isEqualTo("       ");

        // A simple comma seperated value parser will look for "token" separated by commas.
        // Generally, a space has no meaning unless it is enclosed marks "".
        // Write a simple CSV parsing regex that matches tokens between commas but ignores(does not capture) white space that is not between quotes.

        source = "a, \"b\", \"c d\",e,f,   \"g h\", dfgi,, k, \"\", l";
        matcher = Pattern.compile("(^|,)\\s*(\"[^\",]*\"|[^\",]*)").matcher(source);
        list = matcher.results().map(MatchResult::group).toList();
        list.forEach(log::info);
        assertThat(list).hasSize(11);
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

    // test case 중 Condition의 사용법과 is(), doesNotHave(), haveExactly(), doNotHave(), allOf(), anyOf() 학습코드
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Member {

        private String name;
        private int age;
    }
}