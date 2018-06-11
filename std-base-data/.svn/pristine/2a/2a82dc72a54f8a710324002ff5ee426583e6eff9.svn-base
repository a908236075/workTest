/**
 * 
 */
package cn.com.chnsys.std_base_data.business;

import java.awt.TextArea;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.chnsys.cif.base.utils.excel.ExcelException;
import cn.com.chnsys.cif.core.framework.BaseEntity;
import cn.com.chnsys.cif.core.framework.BaseService;
import cn.com.chnsys.std_base_data.pojo.ResultPojo;

/**
 * 
 * @author bianxiaozeng
 * @version 1.0
 * 
 * @date 2016年11月2日 下午3:10:24 
 */
public interface Processor<E extends BaseEntity, T extends BaseService> {

	 /**
     * logger
     */
	public static Logger log = LoggerFactory.getLogger(Processor.class.getName());
	
	/**
	 * 在excel中获取数据
	* @param path 读取文件的路径
	* @return List<E>
	* @throws ExcelException
	*/ 
	List<E> getNewItems(String path) throws ExcelException;
	
	/**
	 * 获取数据库中已经存在的数据
	* @Title: getOldItems
	* @Description: TODO
	* @param service
	* @return List<E>
	*/ 
	List<E> getOldItems(T service);
	
	/**
	 * 根据需要添加主键等其他数据
	* @Title: convert2Save
	* @param 文件位置
	* @return void
	*/ 
	ResultPojo convert2Save(String path, int courtStdVersion, TextArea textArea);
}
