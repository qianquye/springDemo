package cn.lvyjx.test.wx;

import java.util.TreeMap;

public class WxMessVo {

	//关注微信id
	private String touser;
	//模版类型
	private String templateType;
	//消息模板id
	private String templateId;
	//消息跳转的url
	private String url;
	//数据
	private TreeMap<String,String> dataMap;
	
	
	
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public TreeMap<String, String> getDataMap() {
		return dataMap;
	}
	public void setDataMap(TreeMap<String, String> dataMap) {
		this.dataMap = dataMap;
	}
	
	
}
