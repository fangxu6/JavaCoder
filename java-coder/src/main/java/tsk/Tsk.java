package tsk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Author: sky
 * Time: 2008-01-09
 * Use: Parses the Tsk file format in Mapping
 */
public class Tsk extends MappingBase {

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
    private ArrayList<Byte> extendList;
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
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime loadTime;
    private LocalDateTime unloadTime;
    private int totalDie;
    private int passDie;
    private int failDie;

    public Tsk(String file) {
        super(ConstDefine.FileType_TSK, file);
        this.extendHeadFlag = false;
        this.extendFlag = false;
        this.extendFlag2 = false;
        this.extendList = new ArrayList<>();
    }

    // Get file name from the mapping file path
    @Override
    protected String getFileName(String str) {
        try {
            return str.substring(str.lastIndexOf('\\') + 1);
        } catch (Exception e) {
            return str;
        }
    }

    @Override
    protected void initialProperties() {
        _keys.add("Operator");
        _keys.add("Device");
        _keys.add("WaferSize");
        _keys.add("MachineNo");
        _keys.add("IndexSizeX");
        _keys.add("IndexSizeY");
        _keys.add("FlatDir");

        _keys.add("MachineType");
        _keys.add("MapVersion");
        _keys.add("Rows");
        _keys.add("Cols");
        _keys.add("MapDataForm");

        _keys.add("WaferID");
        _keys.add("ProbingNo");
        _keys.add("LotNo");
        _keys.add("CassetteNo");
        _keys.add("SlotNo");

        _keys.add("XCoordinates");
        _keys.add("YCoordinates");
        _keys.add("RefeDir");
        _keys.add("Reserved0");
        _keys.add("TargetX");
        _keys.add("TargetY");

        _keys.add("Refpx");
        _keys.add("Refpy");

        _keys.add("ProbingSP");
        _keys.add("ProbingDir");
        _keys.add("Reserved1");
        _keys.add("DistanceX");
        _keys.add("DistanceY");
        _keys.add("CoordinatorX");
        _keys.add("CoordinatorY");
        _keys.add("FirstDirX");
        _keys.add("FirstDirY");

        _keys.add("StartTime");
        _keys.add("EndTime");
        _keys.add("LoadTime");
        _keys.add("UnloadTime");

        _keys.add("MachineNo1");
        _keys.add("MachineNo2");
        _keys.add("SpecialChar");
        _keys.add("TestingEnd");
        _keys.add("Reserved2");

        _keys.add("TotalDie");
        _keys.add("PassDie");
        _keys.add("FailDie");

        _keys.add("DieStartPosition");

        _keys.add("LineCategoryNo");
        _keys.add("LineCategoryAddr");
        _keys.add("Configuration");
        _keys.add("MaxMultiSite");
        _keys.add("MaxCategories");
        _keys.add("Reserved3");

        _keys.add("ExtendHeadFlag");
        _keys.add("ExtendFlag");
        _keys.add("ExtensionHead_20");
        _keys.add("ExtensionHead_32");
        _keys.add("ExtensionHead_total");
        _keys.add("ExtensionHead_pass");
        _keys.add("ExtensionHead_fail");
        _keys.add("ExtensionHead_44");
        _keys.add("ExtensionHead_64");
        _keys.add("ExtendFlag2");
        _keys.add("ExtendList");

        // 初始化属性值
        // 初始化属性值
        _properties.put("Operator", "");
        _properties.put("Device", "");
        _properties.put("WaferSize", 0);
        _properties.put("MachineNo", 0);
        _properties.put("IndexSizeX", 0);
        _properties.put("IndexSizeY", 0);
        _properties.put("FlatDir", 0);

        _properties.put("MachineType", (byte) 0);
        _properties.put("MapVersion", (byte) 0);
        _properties.put("Rows", 0);
        _properties.put("Cols", 0);
        _properties.put("MapDataForm", 0);

        _properties.put("WaferID", "");
        _properties.put("ProbingNo", (byte) 0);
        _properties.put("LotNo", "");
        _properties.put("CassetteNo", 0);
        _properties.put("SlotNo", 0);

        _properties.put("XCoordinates", (byte) 0);
        _properties.put("YCoordinates", (byte) 0);
        _properties.put("RefeDir", (byte) 0);
        _properties.put("Reserved0", (byte) 0);
        _properties.put("TargetX", 0);
        _properties.put("TargetY", 0);

        _properties.put("Refpx", 0);
        _properties.put("Refpy", 0);

        _properties.put("ProbingSP", (byte) 0);
        _properties.put("ProbingDir", (byte) 0);
        _properties.put("Reserved1", (short) 0);
        _properties.put("DistanceX", 0);
        _properties.put("DistanceY", 0);
        _properties.put("CoordinatorX", 0);
        _properties.put("CoordinatorY", 0);
        _properties.put("FirstDirX", 0);
        _properties.put("FirstDirY", 0);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            _properties.put("StartTime", sdf.parse("1900-01-01 00:00:00"));
            _properties.put("EndTime", sdf.parse("1900-01-01 00:00:00"));
            _properties.put("LoadTime", sdf.parse("1900-01-01 00:00:00"));
            _properties.put("UnloadTime", sdf.parse("1900-01-01 00:00:00"));
        } catch (ParseException e) {
            _properties.put("StartTime", new Date(0));
            _properties.put("EndTime", new Date(0));
            _properties.put("LoadTime", new Date(0));
            _properties.put("UnloadTime", new Date(0));
        }

        _properties.put("MachineNo1", 0);
        _properties.put("MachineNo2", 0);
        _properties.put("SpecialChar", 0);
        _properties.put("TestingEnd", (byte) 0);
        _properties.put("Reserved2", (byte) 0);

        _properties.put("TotalDie", 0);
        _properties.put("PassDie", 0);
        _properties.put("FailDie", 0);

        _properties.put("LineCategoryNo", 0);
        _properties.put("LineCategoryAddr", 0);
        _properties.put("Configuration", (short) 0);
        _properties.put("MaxMultiSite", (short) 0);
        _properties.put("MaxCategories", (short) 0);
        _properties.put("Reserved3", (short) 0);

        _properties.put("ExtendHeadFlag", false);
        _properties.put("ExtendFlag", false);

        _properties.put("ExtensionHead_20", new byte[20]);
        _properties.put("ExtensionHead_32", new byte[32]);
        _properties.put("ExtensionHead_total", 0);
        _properties.put("ExtensionHead_pass", 0);
        _properties.put("ExtensionHead_fail", 0);
        _properties.put("ExtensionHead_44", new byte[44]);
        _properties.put("ExtensionHead_64", new byte[64]);
    }

    @Override
    public void read() throws IOException {
        try {
            openReader();

            this.setOperator(readString(20));
            this.setDevice(readString(16));
            this.setWaferSize(readInt16());
            this.setMachineNo(readInt16());
            this.setIndexSizeX(readInt32());
            this.setIndexSizeY(readInt32());
            this.setFlatDir(readInt16());
            this.setMachineType(readByte());
            this.setMapVersion(readByte());

            int fileRows = readInt16();
            int fileCols = readInt16();
            this.setRows(fileRows);
            this.setCols(fileCols);

            this.setMapDataForm(readInt32());
            this.setWaferID(readString(21).trim());
            this.setProbingNo(readByte());
            this.setLotNo(readString(18));
            this.setCassetteNo(readInt16());
            this.setSlotNo(readInt16());

            _properties.put("XCoordinates", readByte());
            _properties.put("YCoordinates", readByte());
            _properties.put("RefeDir", readByte());
            _properties.put("Reserved0", readByte());
            _properties.put("TargetX", readInt32());
            _properties.put("TargetY", readInt32());

            this.setRefpx(readInt16());
            this.setRefpy(readInt16());

            _properties.put("ProbingSP", readByte());
            _properties.put("ProbingDir", readByte());
            _properties.put("Reserved1", readInt16());
            _properties.put("DistanceX", readInt32());
            _properties.put("DistanceY", readInt32());
            _properties.put("CoordinatorX", readInt32());
            _properties.put("CoordinatorY", readInt32());
            _properties.put("FirstDirX", readInt32());
            _properties.put("FirstDirY", readInt32());

//            byte[] bytesStartTime = new byte[12];
//            _dataInputStream.read(bytesStartTime, 0, 12);
            LocalDateTime startTime = readDate();
            this.setStartTime(startTime);

//            byte[] bytesEndTime = new byte[12];
//            _dataInputStream.read(bytesEndTime, 0, 12);
            LocalDateTime endTime = readDate();
            this.setEndTime(endTime);
//
//            byte[] bytesLoadTime = new byte[12];
//            _dataInputStream.read(bytesLoadTime, 0, 12);
            LocalDateTime loadTime = readDate();
            this.setLoadTime(loadTime);
//
//            byte[] bytesUnloadTime = new byte[12];
//            _dataInputStream.read(bytesUnloadTime, 0, 12);
            LocalDateTime unloadTime = readDate();
            this.setUnloadTime(unloadTime);

            _properties.put("MachineNo1", readInt32());
            _properties.put("MachineNo2", readInt32());
            _properties.put("SpecialChar", readInt32());
            _properties.put("TestingEnd", readByte());
            _properties.put("Reserved2", readByte());

            this.setTotalDie(readInt16());
//            this.setPassDie(readInt16());
            this.passDie = readInt16();
            this.setFailDie(readInt16());

            int dieStartPosition = readInt32();
            _properties.put("DieStartPosition", dieStartPosition);

            _properties.put("LineCategoryNo", readInt32());
            _properties.put("LineCategoryAddr", readInt32());
            _properties.put("Configuration", readInt16());
            _properties.put("MaxMultiSite", readInt16());
            _properties.put("MaxCategories", readInt16());
            _properties.put("Reserved3", readInt16());

            _reader.getChannel().position(dieStartPosition);

            int total = fileRows * fileCols;
            ArrayList<DieData> dieList = new ArrayList<>();

            for (int i = 0; i < total; i++) {
                dieList.add(readDie(i));
            }

            this._dieMatrix = new DieMatrix(dieList, fileRows, fileCols);

            if (_reader.getChannel().position() < _reader.getChannel().size()) {
                this.setExtendHeadFlag(true);
                _properties.put("ExtensionHead_20", readBytes(20));
                _properties.put("ExtensionHead_32", readBytes(32));
                _properties.put("ExtensionHead_total", readInt32());
                _properties.put("ExtensionHead_pass", readInt32());
                _properties.put("ExtensionHead_fail", readInt32());
                _properties.put("ExtensionHead_44", readBytes(44));
                _properties.put("ExtensionHead_64", readBytes(64));
            }

            if (_reader.getChannel().position() < _reader.getChannel().size()) {
                this.setExtendFlag(true);
                for (int k = 0; k < dieList.size(); k++) {
                    if (_reader.getChannel().position() >= _reader.getChannel().size()) {
                        break;
                    }

                    byte[] buffer = readBytes(2);
                    byte[] buffer2 = readBytes(2);
                    int extSite;
                    int extCategory;
                    DieData currentDie = this.getDieMatrix().getDie(k);
                    if (currentDie.getAttribute() == DieCategory.FailDie || currentDie.getAttribute() == DieCategory.PassDie) {
                        if (this.getMapVersion() == 4 || this.getMapVersion() == 7) {
                            extSite = buffer[1];
                            extCategory = buffer2[1];
                        } else {
                            extSite = buffer[0];
                            extCategory = buffer[1];
                        }
                        if (currentDie.getAttribute() == DieCategory.FailDie) {
                            if (extCategory == 0 || extCategory == 1) {
                                System.out.println("error");
                                continue;
                            }
                        }
                        if (currentDie.getAttribute() == DieCategory.PassDie) {
                            if (extCategory != 1) {
                                System.out.println("error");
                                continue;
                            }
                        }
                        currentDie.setBin(extCategory);
                        currentDie.setSite(extSite);
                    }
                }
            }

            while (_reader.getChannel().position() < _reader.getChannel().size()) {
                this.setExtendFlag2(true);
                getExtendList().add(readByte());
            }

        } catch (IOException e) {
            throw e;
        } finally {
            closeReader();
        }
    }

    private DieData readDie(int index) throws IOException {
        // First word
        byte[] buffer = readBytes(2);
        int dieTestResult = (buffer[0] >> 6) & 0x3;
        buffer[0] = (byte) (buffer[0] & 0x1);
        reverse(buffer);
        int f6 = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getShort();

        // Second word
        buffer = readBytes(2);
        int s6 = (buffer[0] >> 1) & 0x1;
        int dieProperty = (buffer[0] >> 6) & 0x3;
        buffer[0] = (byte) (buffer[0] & 0x1);
        reverse(buffer);

        // Third word
        buffer = readBytes(2);
        int t3 = buffer[0] & 0x3f;
        int binNum = buffer[1] & 0x3f;

        DieData die = new DieData();

        switch (dieProperty) {
            case 0:
                if (s6 == 1) {
                    die.setAttribute(DieCategory.SkipDie);
                } else {
                    die.setAttribute(DieCategory.SkipDie2);
                }
                break;
            case 1:
                switch (dieTestResult) {
                    case 0:
                        die.setAttribute(DieCategory.NoneDie);
                        break;
                    case 1:
                        die.setAttribute(DieCategory.PassDie);
                        die.setBin(binNum);
                        die.setSite(t3);
                        if (binNum != 1) {
                            System.out.println("error");
                        }
                        break;
                    case 2:
                    case 3:
                        die.setAttribute(DieCategory.FailDie);
                        die.setBin(binNum);
                        die.setSite(t3);
                        if (binNum == 0 || binNum == 1) {
                            System.out.println("error");
                        }
                        break;
                    default:
                        die.setAttribute(DieCategory.Unknow);
                        break;
                }
                break;
            case 2:
                die.setAttribute(DieCategory.MarkDie);
                break;
            default:
                die.setAttribute(DieCategory.Unknow);
                break;
        }

        die.setX((int) _properties.get("FirstDirX") +
                (((byte) _properties.get("XCoordinates") == 2) ? index % this.getRows() : -index % this.getRows()));
        die.setY((int) _properties.get("FirstDirY") +
                (((byte) _properties.get("YCoordinates") == 1) ? index / this.getRows() : -index / this.getRows()));

        return die;
    }


    @Override
    public void save() throws IOException {
        try {
            openWriter();

            writeString(String.format("%-20s", this.getOperator()), 20);
            writeString(String.format("%-16s", this.getDevice()), 16);

            writeInt16((short) this.getWaferSize());
            writeInt16((short) this.getMachineNo());
            writeInt32(this.getIndexSizeX());
            writeInt32(this.getIndexSizeY());
            writeInt16((short) this.getFlatDir());

            _writer.write(this.getMachineType());
            _writer.write(this.getMapVersion());

            writeInt16((short) this.getDieMatrix().getXMax());
            writeInt16((short) this.getDieMatrix().getYMax());

            writeInt32(this.getMapDataForm());

            writeString(String.format("%-21s", this.getWaferID()), 21);
            _writer.write(this.getProbingNo());
            writeString(String.format("%-18s", this.getLotNo()), 18);

            writeInt16((short) this.getCassetteNo());
            writeInt16((short) this.getSlotNo());

            _writer.write((byte) _properties.get("XCoordinates"));
            _writer.write((byte) _properties.get("YCoordinates"));
            _writer.write((byte) _properties.get("RefeDir"));
            _writer.write((byte) _properties.get("Reserved0"));

            writeInt32((int) _properties.get("TargetX"));
            writeInt32((int) _properties.get("TargetY"));

            writeInt16((short) this.getRefpx());
            writeInt16((short) this.getRefpy());

            _writer.write((byte) _properties.get("ProbingSP"));
            _writer.write((byte) _properties.get("ProbingDir"));
            writeInt16((short) _properties.get("Reserved1"));
            writeInt32((int) _properties.get("DistanceX"));
            writeInt32((int) _properties.get("DistanceY"));
            writeInt32((int) _properties.get("CoordinatorX"));
            writeInt32((int) _properties.get("CoordinatorY"));
            writeInt32((int) _properties.get("FirstDirX"));
            writeInt32((int) _properties.get("FirstDirY"));

            writeDate(this.getStartTime());
            writeDate(this.getEndTime());
            writeDate(this.getLoadTime());
            writeDate(this.getUnloadTime());

            writeInt32((int) _properties.get("MachineNo1"));
            writeInt32((int) _properties.get("MachineNo2"));
            writeInt32((int) _properties.get("SpecialChar"));
            _writer.write((byte) _properties.get("TestingEnd"));
            _writer.write((byte) _properties.get("Reserved2"));

            writeInt16((short) this.getTotalDie());
            writeInt16((short) this.getPassDie());
            writeInt16((short) this.getFailDie());

            writeInt32((int) _properties.get("DieStartPosition"));
            writeInt32((int) _properties.get("LineCategoryNo"));
            writeInt32((int) _properties.get("LineCategoryAddr"));
            writeInt16((short) _properties.get("Configuration"));
            writeInt16((short) _properties.get("MaxMultiSite"));
            writeInt16((short) _properties.get("MaxCategories"));
            writeInt16((short) _properties.get("Reserved3"));

            for (int i = 0; i < this.getDieMatrix().getYMax(); i++) {
                for (int j = 0; j < this.getDieMatrix().getXMax(); j++) {
                    writeDie(this.getDieMatrix().getDie(j, i));
                }
            }

            if (this.isExtendHeadFlag()) {
                _writer.write((byte[]) _properties.get("ExtensionHead_20"));
                _writer.write((byte[]) _properties.get("ExtensionHead_32"));
                writeInt32(this.getTotalDie());
                writeInt32(this.getPassDie());
                writeInt32(this.getFailDie());
                _writer.write((byte[]) _properties.get("ExtensionHead_44"));
                _writer.write((byte[]) _properties.get("ExtensionHead_64"));
            }

            if (this.isExtendFlag()) {
                for (int i = 0; i < this.getDieMatrix().getYMax(); i++) {
                    for (int j = 0; j < this.getDieMatrix().getXMax(); j++) {
                        writeDieExtension(this.getDieMatrix().getDie(j, i));
                    }
                }
            }

            if (this.isExtendFlag2()) {
                for (byte b : this.getExtendList()) {
                    _writer.write(b);
                }
            }

        } catch (IOException e) {
            throw e;
        } finally {
            closeWriter();
        }
    }

    private void writeDie(DieData d) throws IOException {
        short f = (short) Math.abs(d.getX());
        f = (short) (f & 0x01ff);
        short s = (short) Math.abs(d.getY());
        s = (short) (s & 0x01ff);
        short t = (short) d.getBin();

        switch (d.getAttribute()) {
            case FailDie:
            case TIRefFail:
                f |= 0x8000; // Fail
                s |= 0x4000; // Probing
                break;
            case MarkDie:
                s |= 0x8000; // Mark
                break;
            case NoneDie:
                f |= 0x0000; // None
                s |= 0x4000; // None probing
                break;
            case PassDie:
            case TIRefPass:
                f |= 0x4000; // Pass
                s |= 0x4000; // Probing
                break;
            case SkipDie:
                s |= 0x0000; // Skip
                s |= 0x0200; // Skip2
                break;
            case SkipDie2:
                s |= 0x0000; // Skip
                s |= 0x0000; // Skip
                break;
            case Unknow:
                s |= 0xc000;
                break;
        }

        if (d.getX() < 0) {
            s |= 0x0800;
        }
        if (d.getY() < 0) {
            s |= 0x0400;
        }

        writeInt16(f);
        writeInt16(s);
        writeInt16(t);
    }

    private void writeDieExtension(DieData d) throws IOException {
        short binNo = (short) d.getBin();
        if (this.getMapVersion() == 2) {
            writeInt16(binNo);
            writeInt16((short) 0);
        } else {
            writeInt16((short) 0);
            writeInt16(binNo);
        }
    }

    @Override
    public boolean isEmptyDie(DieData die) {
        return die.getAttribute() == DieCategory.NoneDie;
    }

    @Override
    public IMappingFile merge(IMappingFile map, String newfile) throws Exception {
        if (!(map instanceof Tsk)) {
            throw new Exception("Tsk file can only be merged with Tsk file.");
        }

        Tsk tskMap = (Tsk) map;
        for (int i = 0; i < this.getRows() * this.getCols(); i++) {
            DieData die = tskMap.getDieMatrix().getDie(i);
            DieData mergeDie = this.getDieMatrix().getDie(i);
            if (die.getAttribute() == DieCategory.FailDie) {
                mergeDie.setAttribute(DieCategory.FailDie);
                mergeDie.setBin(die.getBin());
            }
        }

        this.setPassDie(0);
        this.setFailDie(0);
        for (int k = 0; k < this.getRows() * this.getCols(); k++) {
            if (this.getDieMatrix().getDie(k).getAttribute() == DieCategory.PassDie) {
                this.setPassDie(this.getPassDie() + 1);
            } else if (this.getDieMatrix().getDie(k).getAttribute() == DieCategory.FailDie) {
                this.setFailDie(this.getFailDie() + 1);
            }
        }
        this.setTotalDie(this.getPassDie() + this.getFailDie());

        this.setFullName(newfile);
        this.save();
        return this;
    }

    // Getters and Setters
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public int getWaferSize() {
        return waferSize;
    }

    public void setWaferSize(int waferSize) {
        this.waferSize = waferSize;
    }

    public int getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(int machineNo) {
        this.machineNo = machineNo;
    }

    public int getIndexSizeX() {
        return indexSizeX;
    }

    public void setIndexSizeX(int indexSizeX) {
        this.indexSizeX = indexSizeX;
    }

    public int getIndexSizeY() {
        return indexSizeY;
    }

    public void setIndexSizeY(int indexSizeY) {
        this.indexSizeY = indexSizeY;
    }

    public int getFlatDir() {
        return flatDir;
    }

    public void setFlatDir(int flatDir) {
        this.flatDir = flatDir;
    }

    public byte getMachineType() {
        return machineType;
    }

    public void setMachineType(byte machineType) {
        this.machineType = machineType;
    }

    public byte getMapVersion() {
        return mapVersion;
    }

    public void setMapVersion(byte mapVersion) {
        this.mapVersion = mapVersion;
    }

    public boolean isExtendHeadFlag() {
        return extendHeadFlag;
    }

    public void setExtendHeadFlag(boolean extendHeadFlag) {
        this.extendHeadFlag = extendHeadFlag;
    }

    public boolean isExtendFlag() {
        return extendFlag;
    }

    public void setExtendFlag(boolean extendFlag) {
        this.extendFlag = extendFlag;
    }

    public boolean isExtendFlag2() {
        return extendFlag2;
    }

    public void setExtendFlag2(boolean extendFlag2) {
        this.extendFlag2 = extendFlag2;
    }

    public ArrayList<Byte> getExtendList() {
        return extendList;
    }

    public void setExtendList(ArrayList<Byte> extendList) {
        this.extendList = extendList;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getMapDataForm() {
        return mapDataForm;
    }

    public void setMapDataForm(int mapDataForm) {
        this.mapDataForm = mapDataForm;
    }

    @Override
    public String getWaferID() {
        return waferID;
    }

    @Override
    public void setWaferID(String waferID) {
        this.waferID = waferID;
    }

    public byte getProbingNo() {
        return probingNo;
    }

    public void setProbingNo(byte probingNo) {
        this.probingNo = probingNo;
    }

    @Override
    public String getLotNo() {
        return lotNo;
    }

    @Override
    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public int getCassetteNo() {
        return cassetteNo;
    }

    public void setCassetteNo(int cassetteNo) {
        this.cassetteNo = cassetteNo;
    }

    public int getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(int slotNo) {
        this.slotNo = slotNo;
    }

    public int getRefpx() {
        return refpx;
    }

    public void setRefpx(int refpx) {
        this.refpx = refpx;
    }

    public int getRefpy() {
        return refpy;
    }

    public void setRefpy(int refpy) {
        this.refpy = refpy;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(LocalDateTime loadTime) {
        this.loadTime = loadTime;
    }

    public LocalDateTime getUnloadTime() {
        return unloadTime;
    }

    public void setUnloadTime(LocalDateTime unloadTime) {
        this.unloadTime = unloadTime;
    }

    public int getTotalDie() {
        return totalDie;
    }

    public void setTotalDie(int totalDie) {
        this.totalDie = totalDie;
    }

    public int getPassDie() {
        return passDie;
    }

    public void setPassDie(int passDie) {
        this.passDie = passDie;
    }

    public int getFailDie() {
        return failDie;
    }

    public void setFailDie(int failDie) {
        this.failDie = failDie;
    }
}