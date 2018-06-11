package cn.com.chnsys.std_base_data.entity;

import cn.com.chnsys.cif.core.framework.BaseEntity;


public class MyCodeV0529 extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 排名
	 */
	private  Integer rang;
	/**
	 * 名称
	 * 
	 */
	private String name;
	
	/**
	 * 拥有者
	 * 
	 */
	private String ower;
	
	/**
	 * 
	 * 创建的时间
	 */
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getRang() {
		return rang;
	}

	public void setRang(Integer rang) {
		this.rang = rang;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwer() {
		return ower;
	}

	public void setOwer(String ower) {
		this.ower = ower;
	}

	@Override
	public String toString() {
		return "AdministrativeMyCodeV0529 [rang=" + rang + ", name=" + name
				+ ", ower=" + ower + "]";
	}
	
	
}
