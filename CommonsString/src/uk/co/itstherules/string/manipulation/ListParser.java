package uk.co.itstherules.string.manipulation;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class ListParser {

    private ListParser() {
    }

    public static List<String> parse(String input) {
        List<String> list = new ArrayList<>();
        if (input != null && (!"".equals(input))) {
            StringTokenizer tokenizer = new StringTokenizer(input, "[],", false);
            while (tokenizer.hasMoreElements()) {
                String value = (String) tokenizer.nextElement();
                list.add(value);
            }
            return list;
        }
        return new ArrayList<>();
    }
}
