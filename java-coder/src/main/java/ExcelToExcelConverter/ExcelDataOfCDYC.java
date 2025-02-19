package ExcelToExcelConverter;

/**
 * className: ExcelDataOfCDYC
 * package: ExcelToExcelConverter
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2025/2/19 9:52
 */
public class ExcelDataOfCDYC {


    /**
     * 委外加工单
     */
    private String invOutSourceId;

    /**
     * 产品型号(客户产品型号)
     */
    private String iPN;

    /**
     * 批号
     */
    private String custLotId;

    /**
     * 片数
     */
    private String waferQty;

    /**
     * 片号
     */
    private String waferNo;

    /**
     * 流程
     */
    private String processName;

    /**
     * 要求交期
     */
    private String dueDate;

    /**
     * 测试程序
     */
    private String testProgram;

    /**
     * 出货地
     */
    private String shipTo;

    /**
     * 备注
     */
    private String remark;

    public void setInvOutSourceId(String invOutSourceId) {
        this.invOutSourceId = invOutSourceId;
    }

    public void setIPN(String iPN) {
        this.iPN = iPN;
    }

    public void setCustLotId(String custLotId) {
        this.custLotId = custLotId;
    }

    public void setWaferQty(String waferQty) {
        this.waferQty = waferQty;
    }

    public void setWaferNo(String waferNo) {
        this.waferNo = waferNo;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setTestProgram(String testProgram) {
        this.testProgram = testProgram;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
