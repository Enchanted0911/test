package icu.junyao.fileclient.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author johnson
 * @date 2021-11-05
 */
public class RegexUtils {

    public static String gainData(String rawString, String rawPattern) throws Exception {
        Pattern pattern = Pattern.compile(rawPattern);
        Matcher matcher = pattern.matcher(rawString);
        if (!matcher.find()) {
            throw new Exception("error");
        }
        return matcher.group(1);
    }
}
