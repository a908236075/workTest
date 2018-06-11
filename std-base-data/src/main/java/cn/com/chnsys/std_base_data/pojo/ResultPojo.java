/**
 * 
 */
package cn.com.chnsys.std_base_data.pojo;

/**
 * 
 * @author bianxiaozeng
 * @version 1.0
 * 
 * @date 2016年11月14日 下午3:02:45 
 */
public class ResultPojo {

	/**
	 * 更新的数量
	 */
	private String updateCountString;
	/**
	 * 新添加的数量
	 */
	private String addCountString;
	/**  
	 * 获取updateCountString  
	 * @return updateCountString updateCountString  
	 */
	public String getUpdateCountString() {
		return updateCountString;
	}
	/**  
	 * 设置updateCountString  
	 * @param updateCountString updateCountString  
	 */
	public void setUpdateCountString(String updateCountString) {
		this.updateCountString = updateCountString;
	}
	/**  
	 * 获取addCountString  
	 * @return addCountString addCountString  
	 */
	public String getAddCountString() {
		return addCountString;
	}
	/**  
	 * 设置addCountString  
	 * @param addCountString addCountString  
	 */
	public void setAddCountString(String addCountString) {
		this.addCountString = addCountString;
	}
}
