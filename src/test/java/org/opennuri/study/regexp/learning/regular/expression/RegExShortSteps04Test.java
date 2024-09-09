package org.opennuri.study.regexp.learning.regular.expression;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class RegExShortSteps04Test {

    @Test
            @DisplayName("Step 16: No capturing groups")
            void no_capturing_group() {
        // In the two examples in the previous step, we were capturing text that we don't really need.
        // In the "File Size" task, we captured the spaces before the first digit of the first digit of the file size.
        // and in the "CSV" task, we captured the commas between each token. We don't need to capture these characters,
        // but we do need to use them to structure our regular expression. These are ideal option for using a non-capturing group (?:)
        // The non-capturing group does exactly what it sounds like in meaning
        // it allows characters to be grouped and used in regular expression. but does not capture them in a numbered group

        String source = "I only want \"the text inside these quotes\".";
        Matcher matcher = Pattern.compile("(?:\")([^\"]+)(?:\")").matcher(source);
        List<String> list = matcher.results().map(matchResult -> matchResult.group(1)).toList();
        list.forEach(log::info);

        // The regular expression now matches the quoted text as well as the quote characters themselves,
        // but the capturing group only captured the quoted text. Why should we do that?
        // The point is that most regular expression engines allow you to recover text from capturing groups defined in your regular expressions.
        // if we can trim the extra characters we don't want without including them in our capturing groups,
        // then it will make it easier to parse and manipulate the text later. Here's how to clean the CSV parser from the previous step:

        source = "a, \"b\", \"c d\",e,f,   \"g h\", dfgi,, k, \"\", l";

        matcher = Pattern.compile("(?:^|,)\\s*(?:\"([^\",]*)\"|([^\", ]*))").matcher(source);
        //list = matcher.results().map(matchResult -> matchResult.group(1) + " : " + matchResult.group(2)).toList();

        list = matcher.results().map(matchResult -> {
            String result = "";
            if(matchResult.group(1) != null) result = matchResult.group(1);
            if(matchResult.group(2) != null) result = matchResult.group(2);
            return result;
        }).toList();
        list.forEach(log::info);
        assertThat(list).hasSize(11);

        // There are a few thinks to note here: First, we no longer capture commas, since we changed the capturing group (^|,) to a non-capturing group (?:^|,)
        // Secondly, we nested a capturing group within a non-capturing group.
        // This is useful when, for example, you need a group of characters to appear in a particular order, but you only care about subset of those characters.
        // In our cases, we want non-quote characters and non-commas to appear within quotes, but we don't really need the quote characters themselves, so they didn't need to be captured
        // Finally, pay attention "k", "l" the quotes("") are the substring you are looking for, but there are no characters between the quotes, so the corresponding substring contains no characters(has zero length)
        // Shall we consolidate our knowledge? Here are two and a half problems to help us with this:
        // Using non-capturing groups (and capturing groups, and character classes, etc.), Write a regular expression that only captures properly formatted file sizes on the line below.


        source = "6.6KB 1..3KB 12KB 5G 3.3MB KB .6.2TB 9MB";

        matcher = Pattern.compile("(?:^| )(\\d+(?:\\.\\d+)?[KMGT]B)").matcher(source);
        list = matcher.results().map(matchResult -> matchResult.group(1)).toList();
        list.forEach(log::info);

        // Opening HTML tags start with a character <and end with a character >.
        // HTML closing tags begin with a sequence of characters </and end with a >.
        // The tag name is contained between these characters.
        // Can you write a regex to only grab the names in the following tags?
        // (You may be able to solve this problem without using non-capturing groups. Try this two ways! Once with groups and once without them.)

        source = "<p> </span> <div> </kbd> <link>";
        matcher = Pattern.compile("(?:<\\/?)(\\w+)(?:>)").matcher(source);

        list = matcher.results().map(matchResult -> matchResult.group(1)).toList();
        list.forEach(log::info);

        matcher = Pattern.compile("<\\/?(\\w+)>").matcher(source);
        list = matcher.results().map(matchResult -> matchResult.group(1)).toList();
        list.forEach(log::info);
    }

}