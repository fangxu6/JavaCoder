package util;

import com.google.common.base.Splitter;

import java.util.List;

/**
 * className: XJSplitUtil
 * package: com.xijie.FT.util
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/6/25 9:42
 */
public class XJSplitUtil {
    public static List<String> split(String nameWithoutExtension,Character delimiter, int limitNumber) {
        return Splitter.on(delimiter).limit(limitNumber)
                .trimResults()
                .omitEmptyStrings()
                .splitToList(nameWithoutExtension);
    }

    public static List<String> split(String nameWithoutExtension,Character delimiter) {
        return Splitter.on(delimiter)
                .trimResults()
                .omitEmptyStrings()
                .splitToList(nameWithoutExtension);
    }
}
