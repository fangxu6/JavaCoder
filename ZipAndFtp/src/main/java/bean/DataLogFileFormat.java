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
public class DataLogFileFormat {
    private String custCode;

    private String batchNo;

    private String flowNo;

    private String workFlowCode;

    private String workFlowNo;

    public DataLogFileFormat(String custCode, String batchNo, String flowNo, String workFlowCode, String workFlowNo) {
        this.custCode = custCode;
        this.batchNo = batchNo;
        this.flowNo = flowNo;
        this.workFlowCode = workFlowCode;
        this.workFlowNo = workFlowNo;
    }

    public boolean checkFormat(List<String> custCodeList, List<String> workFlowCodeList) {
        if (!custCodeList.contains(custCode)) {
            return false;
        }
        //batchNo校验 pass
        //flowNo校验
        Integer flowNoCheck = null;
        try {
            flowNoCheck = Integer.valueOf(flowNo);
        } catch (NumberFormatException ex) {
//            ex.printStackTrace();
            return false;
        }
        if (flowNoCheck < 1 || flowNoCheck > 99) {
            return false;
        }
        //工作流校验
        if (!workFlowCodeList.contains(workFlowCode)) {
            return false;
        } else {
            //工作流水号校验
            if (workFlowCode.substring(0, 2) == "FT") {
                Integer workFlowNoCheck = Integer.valueOf(workFlowCode.substring(2));
                if (workFlowNoCheck < 1 || workFlowNoCheck > 99) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getCustCode() {
        return custCode;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public String getWorkFlowCode() {
        return workFlowCode;
    }

    public String getWorkFlowNo() {
        return workFlowNo;
    }
}
