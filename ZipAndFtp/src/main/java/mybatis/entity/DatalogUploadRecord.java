package mybatis.entity;


import java.io.File;
import java.util.List;

/**
 * className: DatalogUploadRecord
 * package: bean
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/8/17 14:32
 */
public class DatalogUploadRecord {
    private Long id;
    private String ip;
    private String zipFile;
    private Integer status;
    private String email;
    private String datalog1;
    private String datalog2;
    private String datalog3;
    private String datalog4;
    private String datalog5;
    private String datalog6;
    private String datalog7;
    private Long createTime;
    private Long updateTime;
    private Long deleteTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getZipFile() {
        return zipFile;
    }

    public void setZipFile(String zipFile) {
        this.zipFile = zipFile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDatalog1() {
        return datalog1;
    }

    public void setDatalog1(String datalog1) {
        this.datalog1 = datalog1;
    }

    public String getDatalog2() {
        return datalog2;
    }

    public void setDatalog2(String datalog2) {
        this.datalog2 = datalog2;
    }

    public String getDatalog3() {
        return datalog3;
    }

    public void setDatalog3(String datalog3) {
        this.datalog3 = datalog3;
    }

    public String getDatalog4() {
        return datalog4;
    }

    public void setDatalog4(String datalog4) {
        this.datalog4 = datalog4;
    }

    public String getDatalog5() {
        return datalog5;
    }

    public void setDatalog5(String datalog5) {
        this.datalog5 = datalog5;
    }

    public String getDatalog6() {
        return datalog6;
    }

    public void setDatalog6(String datalog6) {
        this.datalog6 = datalog6;
    }

    public String getDatalog7() {
        return datalog7;
    }

    public void setDatalog7(String datalog7) {
        this.datalog7 = datalog7;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Long deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Override
    public String toString() {
        return "DatalogUploadRecord{" +
                "ip='" + ip + '\'' +
                ", zipFile='" + zipFile + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", datalog1='" + datalog1 + '\'' +
                ", datalog2='" + datalog2 + '\'' +
                ", datalog3='" + datalog3 + '\'' +
                ", datalog4='" + datalog4 + '\'' +
                ", datalog5='" + datalog5 + '\'' +
                ", datalog6='" + datalog6 + '\'' +
                ", datalog7='" + datalog7 + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteTime=" + deleteTime +
                '}';
    }

    public void setDatalogList(List<File> fileList) {
        for (int i = 0; i < fileList.size(); i++) {


        }
    }
}