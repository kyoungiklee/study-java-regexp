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
            if (matchResult.group(1) != null) result = matchResult.group(1);
            if (matchResult.group(2) != null) result = matchResult.group(2);
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

    @Test
    @DisplayName("Step 17: backreferences")
    void backreference() {
        // Although I warned you in the introduction that trying to create an HTML parser with regular expression usually leads to  heartache,
        // the last example is a nice transition to another (sometimes) useful feature of most regular expression: backreference.
        // Backlinks are like repeating groups where you can try to grab the same text twice.
        // But they differ in one important aspect - they will only capture the same text, character by character.
        // While the repeating group will allow us to capture something like this

        String source = "heyabcdefg hey heyo heyellow heyyyyyyyyy";
        Matcher matcher = Pattern.compile("(he(?:[a-z])+)").matcher(source);
        List<String> list = matcher.results().map(matchResult -> matchResult.group(1)).toList();
        list.forEach(log::info);

        matcher = Pattern.compile("(he([a-z])(\\2+))").matcher(source);
        list = matcher.results().map(matchResult -> matchResult.group(3)).toList();
        list.forEach(log::info);
        // Repeating capturing groups are useful when you want to re-match the same pattern, while backreferences are good when you want to match the same text.
        // for example, we could use a backreference to try and find the appropriate opening and closing HTML tags:
        source = "<span style=\"color: red\">hey</span>";
        matcher = Pattern.compile("<(\\w+)[^>]*>[^<]+<\\/\\1>").matcher(source);
        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("<span style=\"color: red\">hey</span>");

        // Please note that this is an extremely simplified example and strongly recommend that you do not attempt to write HTML parser based on regular expression
        // This is a very complex syntax, and you will most get likely get sick.
        // Named capturing groups are very similar to backreferences. so I'll cover them briefly here.
        // The only difference between backreferences and a named capture group is that... a named capture group has a name.

        matcher = Pattern.compile("<(?<tag>\\w+)[^>]*>[^<]+</\\k<tag>>").matcher(source);
        list = matcher.results().map(MatchResult::group).toList();
        list.forEach(log::info);


    }

    @Test
    @DisplayName("Step 18: look forward (lookahead) and look back (lookbehind)")
    void look_forward_and_look_back() {
        // We will now delve into some of advanced features of regular expressions.
        // Everything up to step 16 use quite often.
        // But these last few steps are only for people who are very serious about using regex to match very complex expressions
        // In other words, regular expression wizards. "Looking ahead" and "Looking back" may sound complicated, but they are actually not that complicated
        // They allow you to do something similar to what we did with non-capturing groups earlier - check if any text exists before or right after the actual text we want to match.
        // For example, suppose we only want to match the names of things that people love, but only if they are enthusiastic about it(only if they end their sentence with and exclamation point)
        // We could do something like:

        String source = "I like desk. I appreciate stapler. I love lamp!";
        Matcher matcher = Pattern.compile("(\\w+)(?=!)").matcher(source);

        assertThat(matcher.find()).isEqualTo(true);
        assertThat(matcher.group()).isEqualTo("lamp");

        // You can see how the above capturing group, which usually matches any of word in the passage, only matches the word "lamp"
        // A positive "look ahead"(?=!) means that we can only match sequence that end in ! but we don't actually match the exclamation mark character itself.
        // This is an important distinction because with non-capturing groups, we match a character but don't capture it.
        // With lookaheads and lookbehinds, we use a character to build our regular expression, but then we don't even match it against itself
        // They do what they sound like - positive lookahead and lookbehind allow the regex engin to continue matching only when the text contained in the lookahead/lookbehind actually matches
        // Negative lookahead and lookbehind do the opposite - they allow the regular expression to match only when the text contained in the lookahead / lookbehind does not match.
        // For example,  we want to match method names only in the method sequence chain, not the object they operate in. In this case, each method name must be preceded by symbol "."
        // A regular expression using a simple lookback can help here:

        source = "myArray.flatMap.aggregate.summarise.print";

        // In the text above, we match any sequence of characters in the word (\w+), but only if they are preceded by a "."
        matcher = Pattern.compile("(?<=\\.)(\\w+)").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        list.forEach(log::info);
        assertThat(list).containsExactly("flatMap", "aggregate", "summarise", "print");
        // We could achieve something similar using non-capturing groups, but the result is messier
        matcher = Pattern.compile("(?:\\.)(\\w+)").matcher(source);
        list = matcher.results().map(MatchResult::group).toList();

        list.forEach(log::info);
        assertThat(list).containsExactly(".flatMap", ".aggregate", ".summarise", ".print");

        // Even though it's shorter, it matches characters we don't want
        // While ths example may seem trivial, lookahead and lookbehind can really help us clean up our regular expression
        // It's not long before the finish line! The following 2 tasks will bring us one step closer to it
        // The negative lookbehind allows the regex engine to keep trying to find a match only if the text contained within the negative lookbehind is not rendered
        // until the rest of the text with which to match
        // For example, we could use a regular expression to match only last names of women attending a conference.
        // To do this, we'd like to make sure the person's last name doesn't come before Mr.
        // Can you write a regular expression for this?(You can assume last names are at least four characters long.)

        source = "Mr. Brown, Ms. Smith, Mrs. Jones, Miss. Daisy, Mr. Green";
        matcher = Pattern.compile("(?<!Mr\\. )([A-Z]\\w{4})").matcher(source);
        list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).containsExactly("Smith", "Jones", "Daisy");

        // Suppose we are cleaning the database and we have a column of information that stands for percentage.
        // Unfortunately some people have written numbers as decimal values in the range[0.0, 1.0], while others have written percentage in the range[0.0%, 100.0%]
        // and still others have witten percentage values, but forgot the literal percent sign.
        // Using negative lookahead, can you mark only those values that should be but lack the %?
        // These must be values strictly greater than 1.00 but without a trailing %
        // No number can contain more than two digits before or after the decimal point
        // Please note that this solution is extremely difficult. if you can solve this problem without looking at my answer, then you already have great skills in regular expressions

        source = "0.32 100.00 5.6 0.27 98% 12.2% 1.01 0.99% 0.99 13.13 1.10";

        matcher = Pattern.compile("(\\b[1-9]\\d*\\.\\d+\\b)(?!%)").matcher(source);
        list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).containsExactly("100.00", "5.6", "1.01", "13.13", "1.10");

    }

    @Test
    @DisplayName("Step 19: Conditions in Regular Expressions")
    void conditions_in_regular_expression() {
        // We have now reached a point where people will no longer use regular expressions.
        // We've covered probably 95% of the use cases for simple regular expressions,
        // and everything done in steps 19 to 20 is usually done by a more full-featured text manipulation language like awk or sed
        // However, let's continue, just so you know what regular expression is really capable of
        // Although regular expression are not Turing complete, some regular expression offer features that are very similar to a complete programming language
        // One such feature is the "condition".
        // Conditional Regex expressions allow if-then-else statements where the selected branch is determined by either the "lookahead" or "lookbehind" we learned about in the previous step.
        // For example, you might want to match only valid entries in list or dates:


        String source = "Dates worked: Feb 28, Feb 29, Feb 30, Mar 30, Mar 31";

        Matcher matcher = Pattern.compile("(?<=Feb )([1-2][0-9])|(?<=Mar )([1-2][0-9]|3[0-1])").matcher(source);
        List<String> list = matcher.results().map(MatchResult::group).toList();
        assertThat(list).containsExactly("28", "29", "30", "31");

        // Please note that the above groups are also indexed by month.
        // We could write a regular expression for all 12 months and capture only valid dates, which would then be grouped into groups indexed by month of the year
        // The above uses a kind of if-like structure that will only match the first group if "Feb" precedes a number(and similarly for the second).
        // But what if we wanted to use special handling for February only? Something like "if the number is preceded by 'Feb', do that, else do that other thing."
        // This is how conditional expression do it:

        //Conditionals are not supported in this regex dialect
        //matcher = Pattern.compile("(?(?<=Feb )([1-2][0-9])|([1-2][0-9]|3[0-1]))").matcher(source);

    }

    @Test
    @DisplayName("Step 20: Recursion and further training")
    void recursion_and_further_training() {

    }
}