package util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;

import java.io.File;
import java.util.List;


/**
 * className: Zip
 * package: com.xijie.FT.util
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/6/21 15:15
 */
public class XJZipUtil {
    public static void zipMultiFiles(List<String> fileList, String outPath) {
        File[] files = new File[fileList.size()];
        for (int i = 0; i < fileList.size(); i++) {
            files[i] = FileUtil.file(fileList.get(i));
        }

        ZipUtil.zip(FileUtil.file(outPath),false,files);
    }
}
