package tsk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * className: Tsk
 * package: tsk
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2025/7/24 20:42
 */
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Tsk extends MappingBase {
    // 字段定义
    private String operator;
    private String device;
    private int waferSize;
    private int machineNo;
    private int indexSizeX;
    private int indexSizeY;
    private int flatDir;
    private byte machineType;
    private byte mapVersion;
    private boolean extendHeadFlag;
    private boolean extendFlag;
    private boolean extendFlag2;
    private List<Object> extendList;
    private int rows;
    private int cols;
    private int mapDataForm;
    private String waferID;
    private byte probingNo;
    private String lotNo;
    private int cassetteNo;
    private int slotNo;
    private int refpx;
    private int refpy;
    private Date startTime;
    private Date endTime;
    private Date loadTime;
    private Date unloadTime;
    private int totalDie;
    private int passDie;
    private int failDie;

    // 构造方法
    public Tsk(String file) {
        super(ConstDefine.FileType_TSK, file);
        this.extendHeadFlag = false;
        this.extendFlag = false;
        this.extendFlag2 = false;
        this.extendList = new ArrayList<>();
    }

    // Getter和Setter方法
    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public String getDevice() { return device; }
    public void setDevice(String device) { this.device = device; }

    public int getWaferSize() { return waferSize; }
    public void setWaferSize(int waferSize) { this.waferSize = waferSize; }

    public int getMachineNo() { return machineNo; }
    public void setMachineNo(int machineNo) { this.machineNo = machineNo; }

    public int getIndexSizeX() { return indexSizeX; }
    public void setIndexSizeX(int indexSizeX) { this.indexSizeX = indexSizeX; }

    public int getIndexSizeY() { return indexSizeY; }
    public void setIndexSizeY(int indexSizeY) { this.indexSizeY = indexSizeY; }

    public int getFlatDir() { return flatDir; }
    public void setFlatDir(int flatDir) { this.flatDir = flatDir; }

    public byte getMachineType() { return machineType; }
    public void setMachineType(byte machineType) { this.machineType = machineType; }

    public byte getMapVersion() { return mapVersion; }
    public void setMapVersion(byte mapVersion) { this.mapVersion = mapVersion; }

    public boolean isExtendHeadFlag() { return extendHeadFlag; }
    public void setExtendHeadFlag(boolean extendHeadFlag) { this.extendHeadFlag = extendHeadFlag; }

    public boolean isExtendFlag() { return extendFlag; }
    public void setExtendFlag(boolean extendFlag) { this.extendFlag = extendFlag; }

    public boolean isExtendFlag2() { return extendFlag2; }
    public void setExtendFlag2(boolean extendFlag2) { this.extendFlag2 = extendFlag2; }

    public List<Object> getExtendList() { return extendList; }
    public void setExtendList(List<Object> extendList) { this.extendList = extendList; }

    public int getRows() { return rows; }
    public void setRows(int rows) { this.rows = rows; }

    public int getCols() { return cols; }
    public void setCols(int cols) { this.cols = cols; }

    public int getMapDataForm() { return mapDataForm; }
    public void setMapDataForm(int mapDataForm) { this.mapDataForm = mapDataForm; }

    @Override
    public String getWaferID() { return waferID; }
    @Override
    public void setWaferID(String waferID) { this.waferID = waferID; }

    public byte getProbingNo() { return probingNo; }
    public void setProbingNo(byte probingNo) { this.probingNo = probingNo; }

    @Override
    public String getLotNo() { return lotNo; }
    @Override
    public void setLotNo(String lotNo) { this.lotNo = lotNo; }

    public int getCassetteNo() { return cassetteNo; }
    public void setCassetteNo(int cassetteNo) { this.cassetteNo = cassetteNo; }

    public int getSlotNo() { return slotNo; }
    public void setSlotNo(int slotNo) { this.slotNo = slotNo; }

    public int getRefpx() { return refpx; }
    public void setRefpx(int refpx) { this.refpx = refpx; }

    public int getRefpy() { return refpy; }
    public void setRefpy(int refpy) { this.refpy = refpy; }

    public Date getStartTime() { return startTime; }
    public void setStartTime(Date startTime) { this.startTime = startTime; }

    public Date getEndTime() { return endTime; }
    public void setEndTime(Date endTime) { this.endTime = endTime; }

    public Date getLoadTime() { return loadTime; }
    public void setLoadTime(Date loadTime) { this.loadTime = loadTime; }

    public Date getUnloadTime() { return unloadTime; }
    public void setUnloadTime(Date unloadTime) { this.unloadTime = unloadTime; }

    public int getTotalDie() { return totalDie; }
    public void setTotalDie(int totalDie) { this.totalDie = totalDie; }

    public int getPassDie() { return passDie; }
    public void setPassDie(int passDie) { this.passDie = passDie; }

    public int getFailDie() { return failDie; }
    public void setFailDie(int failDie) { this.failDie = failDie; }

    // 从路径获取文件名
    @Override
    protected String getFileName(String str) {
        try {
            return str.substring(str.lastIndexOf(File.separator) + 1);
        } catch (Exception e) {
            return str;
        }
    }

    // 初始化属性
    @Override
    protected void initialProperties() {
        // 添加所有键
        getKeys().add("Operator");
        getKeys().add("Device");
        getKeys().add("WaferSize");
        getKeys().add("MachineNo");
        getKeys().add("IndexSizeX");
        getKeys().add("IndexSizeY");
        getKeys().add("FlatDir");

        getKeys().add("MachineType");
        getKeys().add("MapVersion");
        getKeys().add("Rows");
        getKeys().add("Cols");
        getKeys().add("MapDataForm");

        getKeys().add("WaferID");
        getKeys().add("ProbingNo");
        getKeys().add("LotNo");
        getKeys().add("CassetteNo");
        getKeys().add("SlotNo");

        getKeys().add("XCoordinates");
        getKeys().add("YCoordinates");
        getKeys().add("RefeDir");
        getKeys().add("Reserved0");
        getKeys().add("TargetX");
        getKeys().add("TargetY");

        getKeys().add("Refpx");
        getKeys().add("Refpy");

        getKeys().add("ProbingSP");
        getKeys().add("ProbingDir");
        getKeys().add("Reserved1");
        getKeys().add("DistanceX");
        getKeys().add("DistanceY");
        getKeys().add("CoordinatorX");
        getKeys().add("CoordinatorY");
        getKeys().add("FirstDirX");
        getKeys().add("FirstDirY");

        getKeys().add("StartTime");
        getKeys().add("EndTime");
        getKeys().add("LoadTime");
        getKeys().add("UnloadTime");

        getKeys().add("MachineNo1");
        getKeys().add("MachineNo2");
        getKeys().add("SpecialChar");
        getKeys().add("TestingEnd");
        getKeys().add("Reserved2");

        getKeys().add("TotalDie");
        getKeys().add("PassDie");
        getKeys().add("FailDie");

        getKeys().add("DieStartPosition");

        getKeys().add("LineCategoryNo");
        getKeys().add("LineCategoryAddr");
        getKeys().add("Configuration");
        getKeys().add("MaxMultiSite");
        getKeys().add("MaxCategories");
        getKeys().add("Reserved3");

        getKeys().add("ExtendHeadFlag");
        getKeys().add("ExtendFlag");
        getKeys().add("ExtensionHead_20");
        getKeys().add("ExtensionHead_32");
        getKeys().add("ExtensionHead_total");
        getKeys().add("ExtensionHead_pass");
        getKeys().add("ExtensionHead_fail");
        getKeys().add("ExtensionHead_44");
        getKeys().add("ExtensionHead_64");
        getKeys().add("ExtendFlag2");
        getKeys().add("ExtendList");

        // 初始化属性值
        getProperties().put("Operator", "");
        getProperties().put("Device", "");
        getProperties().put("WaferSize", 0);
        getProperties().put("MachineNo", 0);
        getProperties().put("IndexSizeX", 0);
        getProperties().put("IndexSizeY", 0);
        getProperties().put("FlatDir", 0);

        getProperties().put("MachineType", (byte) 0);
        getProperties().put("MapVersion", (byte) 0);
        getProperties().put("Rows", 0);
        getProperties().put("Cols", 0);
        getProperties().put("MapDataForm", 0);

        getProperties().put("WaferID", "");
        getProperties().put("ProbingNo", (byte) 0);
        getProperties().put("LotNo", "");
        getProperties().put("CassetteNo", 0);
        getProperties().put("SlotNo", 0);

        getProperties().put("XCoordinates", (byte) 0);
        getProperties().put("YCoordinates", (byte) 0);
        getProperties().put("RefeDir", (byte) 0);
        getProperties().put("Reserved0", (byte) 0);
        getProperties().put("TargetX", 0);
        getProperties().put("TargetY", 0);

        getProperties().put("Refpx", 0);
        getProperties().put("Refpy", 0);

        getProperties().put("ProbingSP", (byte) 0);
        getProperties().put("ProbingDir", (byte) 0);
        getProperties().put("Reserved1", (short) 0);
        getProperties().put("DistanceX", 0);
        getProperties().put("DistanceY", 0);
        getProperties().put("CoordinatorX", 0);
        getProperties().put("CoordinatorY", 0);
        getProperties().put("FirstDirX", 0);
        getProperties().put("FirstDirY", 0);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            getProperties().put("StartTime", sdf.parse("1900-01-01 00:00:00"));
            getProperties().put("EndTime", sdf.parse("1900-01-01 00:00:00"));
            getProperties().put("LoadTime", sdf.parse("1900-01-01 00:00:00"));
            getProperties().put("UnloadTime", sdf.parse("1900-01-01 00:00:00"));
        } catch (ParseException e) {
            getProperties().put("StartTime", new Date(0));
            getProperties().put("EndTime", new Date(0));
            getProperties().put("LoadTime", new Date(0));
            getProperties().put("UnloadTime", new Date(0));
        }

        getProperties().put("MachineNo1", 0);
        getProperties().put("MachineNo2", 0);
        getProperties().put("SpecialChar", 0);
        getProperties().put("TestingEnd", (byte) 0);
        getProperties().put("Reserved2", (byte) 0);

        getProperties().put("TotalDie", 0);
        getProperties().put("PassDie", 0);
        getProperties().put("FailDie", 0);

        getProperties().put("LineCategoryNo", 0);
        getProperties().put("LineCategoryAddr", 0);
        getProperties().put("Configuration", (short) 0);
        getProperties().put("MaxMultiSite", (short) 0);
        getProperties().put("MaxCategories", (short) 0);
        getProperties().put("Reserved3", (short) 0);

        getProperties().put("ExtendHeadFlag", false);
        getProperties().put("ExtendFlag", false);

        getProperties().put("ExtensionHead_20", new byte[20]);
        getProperties().put("ExtensionHead_32", new byte[32]);
        getProperties().put("ExtensionHead_total", 0);
        getProperties().put("ExtensionHead_pass", 0);
        getProperties().put("ExtensionHead_fail", 0);
        getProperties().put("ExtensionHead_44", new byte[44]);
        getProperties().put("ExtensionHead_64", new byte[64]);
    }

    @Override
    public void read() {
        try {
            // 打开读取器
            openReader();

            // 读取字段（按C#原逻辑顺序）
            setOperator(readToString(20));
            setDevice(readToString(16));
            setWaferSize(readToInt16());
            setMachineNo(readToInt16());
            setIndexSizeX(readToInt32());
            setIndexSizeY(readToInt32());
            setFlatDir(readToInt16());
            setMachineType(readToByte());
            setMapVersion(readToByte());

            int rows = readToInt16();
            int cols = readToInt16();
            setRows(rows);
            setCols(cols);

            setMapDataForm(readToInt32());
            setWaferID(readToString(21).trim());

            setProbingNo(readToByte());
            setLotNo(readToString(18));
            setCassetteNo(readToInt16());
            setSlotNo(readToInt16());

            // X坐标增长方向
            getProperties().put("XCoordinates", readToByte());
            // Y坐标增长方向
            getProperties().put("YCoordinates", readToByte());
            // 参考方向设置
            getProperties().put("RefeDir", readToByte());
            // 保留字段0
            getProperties().put("Reserved0", readToByte());
            // 目标Die X坐标
            getProperties().put("TargetX", readToInt32());
            // 目标Die Y坐标
            getProperties().put("TargetY", readToInt32());

            setRefpx(readToInt16());
            setRefpy(readToInt16());

            // 探测起始位置
            getProperties().put("ProbingSP", readToByte());
            // 探测方向
            getProperties().put("ProbingDir", readToByte());
            // 保留字段1
            getProperties().put("Reserved1", readToInt16());
            // X方向到晶圆中心距离
            getProperties().put("DistanceX", readToInt32());
            // Y方向到晶圆中心距离
            getProperties().put("DistanceY", readToInt32());
            // 晶圆中心X坐标
            getProperties().put("CoordinatorX", readToInt32());
            // 晶圆中心Y坐标
            getProperties().put("CoordinatorY", readToInt32());
            // 第一方向X坐标
            getProperties().put("FirstDirX", readToInt32());
            // 第一方向Y坐标
            getProperties().put("FirstDirY", readToInt32());

            setStartTime(readToDate());
            setEndTime(readToDate());
            setLoadTime(readToDate());
            setUnloadTime(readToDate());

            // 机器编号1
            getProperties().put("MachineNo1", readToInt32());
            // 机器编号2
            getProperties().put("MachineNo2", readToInt32());
            // 特殊字符
            getProperties().put("SpecialChar", readToInt32());
            // 测试结束信息
            getProperties().put("TestingEnd", readToByte());
            // 保留字段2
            getProperties().put("Reserved2", readToByte());

            setTotalDie(readToInt16());
            setPassDie(readToInt16());
            setFailDie(readToInt16());

            // 读取Die数据起始位置
            int dieStartPosition = readToInt32();
            getProperties().put("DieStartPosition", dieStartPosition);

            // 行分类数据数量
            getProperties().put("LineCategoryNo", readToInt32());
            // 行分类地址
            getProperties().put("LineCategoryAddr", readToInt32());
            // 映射文件配置
            getProperties().put("Configuration", readToInt16());
            // 最大多站点数
            getProperties().put("MaxMultiSite", readToInt16());
            // 最大分类数
            getProperties().put("MaxCategories", readToInt16());
            // 保留字段3
            getProperties().put("Reserved3", readToInt16());

            // 设置Die数据起始位置
            getReader().getChannel().position(dieStartPosition);

            int total = rows * cols;
            List<DieData> dieList = new ArrayList<>();

            for (int i = 0; i < total; i++) {
                dieList.add(readDie(i));
            }

            setDieMatrix(new DieMatrix(dieList, rows, cols));

            // 处理扩展头部信息
            if (getReader().getChannel().position() < getReader().getChannel().size()) {
                setExtendHeadFlag(true);

                byte[] ext20 = new byte[20];
                getReader().read(ext20);
                getProperties().put("ExtensionHead_20", ext20);

                byte[] ext32 = new byte[32];
                getReader().read(ext32);
                getProperties().put("ExtensionHead_32", ext32);

                getProperties().put("ExtensionHead_total", readToInt32());
                getProperties().put("ExtensionHead_pass", readToInt32());
                getProperties().put("ExtensionHead_fail", readToInt32());

                byte[] ext44 = new byte[44];
                getReader().read(ext44);
                getProperties().put("ExtensionHead_44", ext44);

                byte[] ext64 = new byte[64];
                getReader().read(ext64);
                getProperties().put("ExtensionHead_64", ext64);
            }

//            // 处理扩展数据
//            while (getReader().getChannel().position() < getReader().getChannel().size()) {
//                setExtendFlag(true);
//
//                for (int k = 0; k < dieList.size(); k++) {
//                    byte[] buffer = new byte[2];
//                    getReader().read(buffer);
//                    byte[] buffer2 = new byte[2];
//                    getReader().read(buffer2);
//
//                    int extSite;
//                    int extCategory;
//                    DieData die = (DieData) getDieMatrix().getItems().get(k);
//                    if (die.getAttribute() == DieCategory.FailDie || die.getAttribute() == DieCategory.PassDie) {
//                        if (getMapVersion() == 4 || getMapVersion() == 7) {
//                            extSite = buffer[1] & 0xFF;
//                            extCategory = buffer2[1] & 0xFF;
//                        } else {
//                            extSite = buffer[0] & 0xFF;
//                            // 原C#代码此处未完成，保持逻辑一致
//                            extCategory = 0; // 需根据完整逻辑补充
//                        }
//                        // 可根据需要将extSite和extCategory添加到扩展列表
//                        getExtendList().add(new int[]{extSite, extCategory});
//                    }
//                }
//            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                closeReader();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void save() {

    }

    @Override
    public void deasilRotate(int degrees) {

    }

    // 以下为需要在MappingBase中实现的抽象方法对应的调用封装
    protected void openReader() throws IOException {
        // 实现文件读取器打开逻辑，需在MappingBase中具体实现
    }

    protected String readToString(int length) throws IOException {
        byte[] buffer = new byte[length];
        getReader().read(buffer);
        return new String(buffer, StandardCharsets.UTF_8).trim();
    }

    protected short readToInt16() throws IOException {
        byte[] buffer = new byte[2];
        getReader().read(buffer);
        return (short) ((buffer[0] & 0xFF) | (buffer[1] & 0xFF) << 8);
    }

    protected int readToInt32() throws IOException {
        byte[] buffer = new byte[4];
        getReader().read(buffer);
        return (buffer[0] & 0xFF) | (buffer[1] & 0xFF) << 8 |
               (buffer[2] & 0xFF) << 16 | (buffer[3] & 0xFF) << 24;
    }

    protected byte readToByte() throws IOException {
        byte[] buffer = new byte[1];
        getReader().read(buffer);
        return buffer[0];
    }

    protected Date readToDate() throws IOException {
        // 实现日期读取逻辑，根据TSK文件日期格式处理
        // 示例：假设读取8字节表示的日期时间
        byte[] buffer = new byte[8];
        getReader().read(buffer);
        // 实际转换逻辑需根据文件格式调整
        return new Date();
    }

    protected DieData readDie(int index) throws IOException {
        // 实现Die数据读取逻辑
        // 根据TSK文件中Die的存储格式进行解析
        byte status = readToByte();
        DieCategory category = (status == 0) ? DieCategory.PassDie : DieCategory.FailDie;
        // 计算X和Y坐标（需根据实际存储逻辑调整）
        int x = index % cols;
        int y = index / cols;
        DieData die = new DieData();
        //todo 添加Die属性设置逻辑
        return die;
    }

    protected void closeReader() throws IOException {
        // 实现读取器关闭逻辑
        if (getReader() != null) {
            getReader().close();
        }
    }

    // 以下为需要在MappingBase中定义的属性和方法的getter
    protected RandomAccessFile getReader() {
        // 需在MappingBase中实现
        return null;
    }

    protected List<String> getKeys() {
        // 需在MappingBase中实现
        return new ArrayList<>();
    }

    @Override
    public String getFileName() {
        return "";
    }

    @Override
    public void setFileName(String fileName) {

    }

    @Override
    public String getFileType() {
        return "";
    }

    @Override
    public Map<String, Object> getProperties() {
        // 需在MappingBase中实现
        return new HashMap<>();
    }

    @Override
    public DieMatrix getDieMatrix() {
        // 需在MappingBase中实现
        return null;
    }

    @Override
    public DieMatrix setDieMatrix() {
        return null;
    }

    @Override
    public void setDieMatrix(DieMatrix dieMatrix) {
        // 需在MappingBase中实现
    }
}

