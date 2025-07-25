package tsk;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.io.EndianUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MappingBase implements IMappingFile {
    protected List<String> _keys = new ArrayList<>();
    protected Map<String, Object> _properties = new HashMap<>();
    protected DieMatrix _dieMatrix;
    protected FileInputStream _reader;
    protected DataInputStream _dataInputStream;
    protected FileOutputStream _writer;
    protected DataOutputStream _dataOutputStream;

    private String _path;
    private String _fileType;
    private String _fileName;
    private String _fullName;

    public MappingBase(String fileType, String fullName) {
        this._fileType = fileType;
        this._fullName = fullName;
        if (StrUtil.isEmpty(fullName)) {
            this._fileName = "";
            this._fileType = "";
        }


        this._reader = null;
        this._dieMatrix = null;

        this._keys = new ArrayList<>();
        this._properties = new HashMap<>();

        initialProperties();
    }

    protected abstract void initialProperties();

    protected abstract String getFileName(String str);

    @Override
    public DieMatrix getDieMatrix() {
        return _dieMatrix;
    }

    public String getFullName() {
        return _fullName;
    }

    public void setFullName(String fullName) {
        this._fullName = fullName;
    }

    protected void openReader() throws IOException {
        _reader = new FileInputStream(_fullName);
        _dataInputStream = new DataInputStream(_reader);
    }

    protected void closeReader() throws IOException {
        if (_dataInputStream != null) _dataInputStream.close();
        if (_reader != null) _reader.close();
    }

    protected void openWriter() throws IOException {
        _writer = new FileOutputStream(_fullName);
        _dataOutputStream = new DataOutputStream(_writer);
    }

    protected void closeWriter() throws IOException {
        if (_dataOutputStream != null) _dataOutputStream.close();
        if (_writer != null) _writer.close();
    }

    protected String readString(int length) throws IOException {
        byte[] buffer = new byte[length];
        _dataInputStream.readFully(buffer);
        return new String(buffer, StandardCharsets.US_ASCII).trim();
    }

    protected byte[] readBytes(int length) throws IOException {
        byte[] buffer = new byte[length];
        _dataInputStream.readFully(buffer);
        return buffer;
    }

    protected short readInt16() throws IOException {
        return _dataInputStream.readShort();
    }

    protected int readInt32() throws IOException {
        int i = _dataInputStream.readInt();
        return i;
    }

    protected byte readByte() throws IOException {
        return _dataInputStream.readByte();
    }

    LocalDateTime readDate() throws IOException {

        byte[] yearBytes = new byte[2];
        _dataInputStream.readFully(yearBytes);
        byte[] monthBytes = new byte[2];
        _dataInputStream.readFully(monthBytes);
        byte[] dayBytes = new byte[2];
        _dataInputStream.readFully(dayBytes);
        byte[] hourBytes = new byte[2];
        _dataInputStream.readFully(hourBytes);
        byte[] minBytes = new byte[2];
        _dataInputStream.readFully(minBytes);

        _dataInputStream.skipBytes(2);

        int year = 2000 + Integer.valueOf(new String(yearBytes, StandardCharsets.US_ASCII));
        int month = Integer.valueOf(new String(monthBytes, StandardCharsets.US_ASCII));
        Integer day = Integer.valueOf(new String(dayBytes, StandardCharsets.US_ASCII));
        Integer hour = Integer.valueOf(new String(hourBytes, StandardCharsets.US_ASCII));
        Integer min = Integer.valueOf(new String(minBytes, StandardCharsets.US_ASCII));
        Integer sec = 0;
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, min, sec);

        return localDateTime;
    }

    protected void writeString(String value, int length) throws IOException {
        byte[] buffer = new byte[length];
        byte[] valueBytes = value.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(valueBytes, 0, buffer, 0, Math.min(valueBytes.length, buffer.length));
        _dataOutputStream.write(buffer);
    }

    protected void writeInt16(short value) throws IOException {
        byte[] bytes = ByteBuffer.allocate(2).putShort(value).array();
        reverse(bytes);
        _dataOutputStream.write(bytes);
    }

    protected void writeInt32(int value) throws IOException {
        byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();
        reverse(bytes);
        _dataOutputStream.write(bytes);
    }

    protected void writeDate(LocalDateTime date) throws IOException {
//        GregorianCalendar cal = new GregorianCalendar();
//        cal.setTime(date);
//        String year = String.valueOf(cal.get(GregorianCalendar.YEAR)).substring(2);
//        String month = String.format("%2s", cal.get(GregorianCalendar.MONTH) + 1);
//        String day = String.format("%2s", cal.get(GregorianCalendar.DAY_OF_MONTH));
//        String hour = String.format("%2s", cal.get(GregorianCalendar.HOUR_OF_DAY));
//        String minute = String.format("%2s", cal.get(GregorianCalendar.MINUTE));
//
//        _dataOutputStream.write(year.getBytes(StandardCharsets.US_ASCII));
//        _dataOutputStream.write(month.getBytes(StandardCharsets.US_ASCII));
//        _dataOutputStream.write(day.getBytes(StandardCharsets.US_ASCII));
//        _dataOutputStream.write(hour.getBytes(StandardCharsets.US_ASCII));
//        _dataOutputStream.write(minute.getBytes(StandardCharsets.US_ASCII));
//        _dataOutputStream.write("00".getBytes(StandardCharsets.US_ASCII));
    }


    protected void reverse(byte[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        byte tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }
}
