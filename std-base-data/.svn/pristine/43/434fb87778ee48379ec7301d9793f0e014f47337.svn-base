/**
 * 
 */
package cn.com.chnsys.std_base_data.business;

import static cn.com.chnsys.dtc.court.std.base.constants.CourtStdVersion.unsuppertStdVersion;
import static cn.com.chnsys.std_base_data.constants.STDConstants.V2005;
import static cn.com.chnsys.std_base_data.constants.STDConstants.V2009;
import static cn.com.chnsys.std_base_data.constants.STDConstants.V2015;

import java.awt.TextArea;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import cn.com.chnsys.cif.base.utils.CollectionUtil;
import cn.com.chnsys.cif.base.utils.DateTimeUtil;
import cn.com.chnsys.cif.base.utils.ExcelUtil;
import cn.com.chnsys.cif.base.utils.excel.ExcelException;
import cn.com.chnsys.cif.base.utils.excel.model.ColumnsSetting;
import cn.com.chnsys.dtc.court.std.base.entity.CaseCauseCode;
import cn.com.chnsys.dtc.court.std.base.service.CaseCauseCodeService;
import cn.com.chnsys.std_base_data.constants.STDConstants;
import cn.com.chnsys.std_base_data.pojo.ResultPojo;
import cn.com.chnsys.std_base_data.util.CompareResult;
import cn.com.chnsys.std_base_data.util.ProcessorUtil;

/**
 * 处理案由
 * @author bianxiaozeng
 * @version 1.0
 * 
 * @date 2016年11月1日 上午11:10:07 
 */
public class CaseCauseProcessor implements Processor<CaseCauseCode, CaseCauseCodeService> {
	
	public List<CaseCauseCode> getNewItems(String path) throws ExcelException{
		log.info("程序从excel中读取数据...");
		ColumnsSetting columnsSetting = new ColumnsSetting();
    	columnsSetting.addColumn("code", 2);
    	columnsSetting.addColumn("superiorCode", 3);
    	columnsSetting.addColumn("name", 5);
    	columnsSetting.addColumn("description", 6);
    	columnsSetting.addColumn("gradeCode", 4);
    	List<CaseCauseCode> newCaseCauseList = ExcelUtil.praseExcel(CaseCauseCode.class, path,
                columnsSetting, "案由代码");
    	for(CaseCauseCode item:newCaseCauseList){
    		System.out.println("################");
    		System.out.println(item.toString());
    		System.out.println("################");
    	}
    	System.out.println("总共有数据： "+newCaseCauseList.size()+" 条"); 
    	return newCaseCauseList;
	}
	/**
	 * @see cn.com.chnsys.std_base_data.business.Processor#getOldItems(cn.com.chnsys.cif.core.framework.BaseService)
	 */
	public List<CaseCauseCode> getOldItems(CaseCauseCodeService service) {
		return service.getCaseCauseCodeList(new CaseCauseCode(), RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
		
	}
	public ResultPojo convert2Save(String path, int courtStdVersion, TextArea textArea){
		CaseCauseCodeService caseCauseCodeService = ProcessorUtil.getBean(getServiceVersion(courtStdVersion));
		ResultPojo rp = new ResultPojo();
		CompareResult<CaseCauseCode> compareResult  = null;
		try {
			compareResult = ProcessorUtil.compre(getNewItems(path), getOldItems(caseCauseCodeService), STDConstants.CASE_CAUSE_CODE_COMPS, "code");
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		List<CaseCauseCode> needAddCaseCauses = compareResult.getNews();
		for (CaseCauseCode caseCauseCode : needAddCaseCauses) {
			caseCauseCode.setCreateTime(DateTimeUtil.getNow());
		}
		int i = 0;
		int size = needAddCaseCauses.size();
		while(i < size){
			caseCauseCodeService.insertCaseCauseCodes((List<CaseCauseCode>)CollectionUtil.subCollection(needAddCaseCauses, i, 100));
			i += 100;
			textArea.append("正在添加新增加的案由代码...");
		}
		List<CaseCauseCode> updateList = compareResult.getUpdates();
		for (CaseCauseCode caseCauseCode : updateList) {
			caseCauseCode.setTimestamp(DateTimeUtil.getCurrentMilliSecond());
			caseCauseCodeService.updateCaseCauseCode(caseCauseCode);
			textArea.append("更新案由代码：" + caseCauseCode.getName() + "\n");
		}
		rp.setAddCountString("案由代码添加：" + needAddCaseCauses.size());
		rp.setUpdateCountString("案由代码更新：" + updateList.size());
		log.info("------更新数据条数：" + updateList.size());
		return rp;
	}
	
	private static String getServiceVersion(int courtStdVersion){
		String service = null;
		switch (courtStdVersion) {
		case V2009:
			service = "caseCauseCodeV2009Service";
			break;
		case V2005:
			service = "caseCauseCodeV2005Service";
			break;
		case V2015:
			service = "caseCauseCodeV2015Service";
			break;

		default:
			throw unsuppertStdVersion(courtStdVersion);
		}
		log.info("获得的service为：" + service);
		return service;
	}
	
}
