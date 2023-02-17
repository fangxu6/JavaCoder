package jnsw.copy.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author huzw
 *
 */
@XmlRootElement(name = "root")
public class BaseResponse {
	/**
	 * 返回代码
	 */
    private String code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private String endata;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getEndata() {
        return endata;
    }

    public void setEndata(String endata) {
        this.endata = endata;
    }
}
