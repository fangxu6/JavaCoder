package jnsw.copy.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author huzw
 *
 */
@XmlRootElement(name = "head")
public class BaseRequest {
	/**
	 * 交易码
	 */
    private String transcode;
    private String appsecret;
    private String appid;
    private String orgno;
    private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getOrgno() {
		return orgno;
	}

	public void setOrgno(String orgno) {
		this.orgno = orgno;
	}

	public String getTranscode() {
		return transcode;
	}

	public void setTranscode(String transcode) {
		this.transcode = transcode;
	}

	public String getAppsecret() {
		// TODO Auto-generated method stub
		return appsecret;
	}
}
