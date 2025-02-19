package WordToExcelConverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * className: ExtractString
 * package: WordToExcelConverter
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2024/12/20 14:36
 */
public class ExtractString {
    public static void main(String[] args) {
        String[] inputs = {
                "trim为 MZ15_9A_6A",
                "trimMZ15_9A_6A",
                "trim MZ15_9A_6A",
                "trim为MZ15_9A_6A",
                "trim成 MZ15_9A_6A"
        };

        // 正则表达式匹配格式为 MZ15_9A_6A
        String regex = "(?<=\\btrim[为成]?\\s?)[A-Z0-9_]+";

        Pattern pattern = Pattern.compile(regex);

        for (String input : inputs) {
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                System.out.println("Extracted: " + matcher.group());
            } else {
                System.out.println("No match found in: " + input);
            }
        }
    }
}
