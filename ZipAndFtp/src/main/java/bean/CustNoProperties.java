package bean;

import java.util.List;

/**
 * className: CustNoProperties
 * package: bean
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/8/30 15:50
 */
public class CustNoProperties {
    public String getZipType() {
        return zipType;
    }

    public List<String> getCustCode() {
        return custCode;
    }

    public List<String> getDeviceName() {
        return deviceName;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public List<String> getWorkFlow() {
        return workFlow;
    }

    public List<String> getCPWorkFlow() {
        return CPWorkFlow;
    }

    public String getDatalogfilePath() {
        return datalogfilePath;
    }

    public List<String> getExtentionName() {
        return extentionName;
    }

    private String zipType;
    private List<String> custCode;
    private List<String> deviceName;
    private String serialNo;

    private List<String> workFlow;
    private List<String> CPWorkFlow;
    private String datalogfilePath;
    private List<String> extentionName;

    public CustNoProperties(String zipType, List<String> custCode, List<String> deviceName, String serialNo, List<String> workFlow, List<String> CPWorkFlow, String datalogfilePath, List<String> extentionName) {
        this.zipType = zipType;
        this.custCode = custCode;
        this.deviceName = deviceName;
        this.serialNo = serialNo;
        this.workFlow = workFlow;
        this.CPWorkFlow = CPWorkFlow;
        this.datalogfilePath = datalogfilePath;
        this.extentionName = extentionName;
    }
}
