package bean;

import java.util.List;

/**
 * className: DataLogFile
 * package: com.xijie.FT.bean
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/6/26 15:13
 */
public class AisinochipDataLogFileFormat {
    private String deviceName;

    private String batchNo;

    private String flowNo;

    private String waferID;

    private String workFlowNo;

    public AisinochipDataLogFileFormat(String deviceName, String batchNo, String flowNo, String waferID, String workFlowNo) {
        this.deviceName = deviceName;
        this.batchNo = batchNo;
        this.flowNo = flowNo;
        this.waferID = waferID;
        this.workFlowNo = workFlowNo;
    }



    public boolean checkFormat(List<String> custCodeList, List<String> workFlowCodeList) {
        //工作流校验CP1 CP2 CP3
        if (!workFlowCodeList.contains(this.flowNo.toUpperCase())) {
            return false;
        }
        return true;
    }

    public String getCustCode() {
        return deviceName;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public String getWorkFlowCode() {
        return waferID;
    }

    public String getWorkFlowNo() {
        return workFlowNo;
    }
}
