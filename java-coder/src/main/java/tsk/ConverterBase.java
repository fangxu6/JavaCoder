package tsk;

/**
 * className: ConverterBase
 * package: tsk
 * Description: 转换器基类（对应ConverterBase）
 *
 * @author fangxu6@gmail.com
 * @since 2025/7/24 20:40
 */
public abstract class ConverterBase {
    public abstract void convert(String datFile, String tmaFile);
    public abstract IMappingFile convert(IMappingFile source);
}
