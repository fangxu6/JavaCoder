package tsk;

/**
 * className: MappingBase
 * package: tsk
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2025/7/24 20:56
 */

abstract class MappingBase implements IMappingFile {
    protected String fileType;
    protected String filePath;

    public MappingBase(String fileType, String filePath) {
        this.fileType = fileType;
        this.filePath = filePath;
        initialProperties();
    }

    protected abstract void initialProperties();
    protected abstract String getFileName(String str);
    @Override
    public abstract void read();

    // 其他需要实现的抽象方法
    public abstract String getWaferID();
    public abstract void setWaferID(String waferID);
    public abstract String getLotNo();
    public abstract void setLotNo(String lotNo);
}