package tsk;

import java.util.Map;

/**
 * className: IMappingFile
 * package: tsk
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2025/7/24 20:38
 */
// 映射文件接口（对应IMappingFile）
public interface IMappingFile  {
    String getFileName();
    void setFileName(String fileName);
    String getFileType();
    Map<String, Object> getProperties();
    DieMatrix getDieMatrix();
    DieMatrix setDieMatrix();
    void setDieMatrix(DieMatrix dieMatrix);
    void read();
    void save();
    void deasilRotate(int degrees);
}



