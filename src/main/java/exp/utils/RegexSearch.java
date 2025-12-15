package exp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexSearch {

    public static String findFirstAlfa(String text) {
        return findAllMatches(text, "\\bAlfa\\b").getFirst();
    }

    private static List<String> findAllMatches(String text, String regex) {
        List<String> matches = new ArrayList<>();
        if (text == null) return matches;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

}
