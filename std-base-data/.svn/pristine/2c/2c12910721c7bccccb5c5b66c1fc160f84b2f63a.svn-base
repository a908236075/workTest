/**
 * 
 */
package cn.com.chnsys.std_base_data.util;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import cn.com.chnsys.cif.base.utils.ObjectUtil;
import cn.com.chnsys.cif.core.framework.BaseEntity;

/**
 * 处理工具类
 * 
 * @author bianxiaozeng
 * @version 1.0
 * 
 * @date 2016年11月1日 上午11:15:55
 */
public class ProcessorUtil {

	/**
	 * logger
	 */
	private static Logger log = LoggerFactory.getLogger(ProcessorUtil.class.getName());
	private static final String[] locations = { "classpath*:spring/court/std/base/applicationContext-service.xml",
			"classpath:spring/applicationContext-base-dao.xml" };
	private static FileSystemXmlApplicationContext context = null;

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanId) {
		return (T) context.getBean(beanId);
	}

	// public static void loadContext(){
	// new Thread(new Runnable() {
	//
	// public void run() {
	// log.debug("初始化context！");
	// context = new FileSystemXmlApplicationContext(locations);
	// }
	// }).start();
	// }
	public static void loadContext() {
		log.debug("初始化context！");
		context = new FileSystemXmlApplicationContext(locations);
	}

	public static void closeContext() {

		// new Thread(new Runnable() {
		//
		// public void run() {
		// log.info("关闭context，程序结束");
		// if(context != null){
		// context.close();
		// }
		//
		// }
		// }).start();

	}

	/**
	 * 比较2个对象集合
	 * 
	 * @param expectList
	 *            预期的对象集合
	 * @param actulList
	 *            实际的对象集合
	 * @param props
	 *            比较的属性集合
	 * @param idName
	 *            主键属性名
	 * @return 比较结果
	 */
	public static <E extends BaseEntity> CompareResult<E> compre(List<E> expectList, List<E> actulList, String[] props,
			String idName) throws Exception {
		log.info("从文件中读取数据量：" + expectList.size() + "------从数据库读取的数据量：" + actulList.size());
		CompareResult<E> result = new CompareResult<E>();
		if (CollectionUtils.isEmpty(actulList)) {
			log.info("原数据库中数据不存在，把读取的数据全部添加到数据库！本次共添加数据条数：" + expectList.size());
			result.setNews(expectList);
		} else {
			for (int i = 0; i < actulList.size(); i++) {
				E actul = actulList.get(i);
				String actulID = BeanUtils.getProperty(actul, idName);
				for (int j = 0, n = expectList.size(); j < n; j++) {
					E expect = expectList.get(j);
					String expectID = BeanUtils.getProperty(expect, idName);
					
					if (StringUtils.equals(actulID, expectID)) {
						if (ObjectUtil.equals(actul, expect, props)) {
							expectList.remove(j);
							j--;
							n--;
							break;
						} else {
							log.info("数据不一致，需要更新，实际数据：" + expect.toString() + " 数据库中数据：" + actul.toString());
							result.addUpdate(expect);
							expectList.remove(j);
							j--;
							n--;
							break;
						}
					}
					if(j == (expectList.size()-1)){
						result.addDelete(actul);
					}
				}
			}
			log.info("数据库中新添加数据：" + expectList.size());
			result.setNews(expectList);
		}
		return result;
	}
}
