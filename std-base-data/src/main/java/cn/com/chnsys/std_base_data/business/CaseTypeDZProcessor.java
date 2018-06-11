/**
 * 
 */
package cn.com.chnsys.std_base_data.business;

import static cn.com.chnsys.dtc.court.std.base.constants.CourtStdVersion.unsuppertStdVersion;
import static cn.com.chnsys.std_base_data.constants.STDConstants.V2005;
import static cn.com.chnsys.std_base_data.constants.STDConstants.V2009;
import static cn.com.chnsys.std_base_data.constants.STDConstants.V2015;
import static cn.com.chnsys.std_base_data.util.ProcessorUtil.getBean;
import static cn.com.chnsys.std_base_data.util.ProcessorUtil.compre;

import java.awt.TextArea;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import cn.com.chnsys.cif.base.utils.DateTimeUtil;
import cn.com.chnsys.cif.base.utils.ExcelUtil;
import cn.com.chnsys.cif.base.utils.excel.ExcelException;
import cn.com.chnsys.cif.base.utils.excel.model.ColumnsSetting;
import cn.com.chnsys.dtc.court.std.base.entity.CaseTypeDZ;
import cn.com.chnsys.dtc.court.std.base.service.CaseTypeDZService;
import cn.com.chnsys.std_base_data.constants.STDConstants;
import cn.com.chnsys.std_base_data.pojo.ResultPojo;
import cn.com.chnsys.std_base_data.util.CompareResult;

/**
 * 
 * @author bianxiaozeng
 * @version 1.0
 * 
 * @date 2016年11月6日 上午8:23:59 
 */
public class CaseTypeDZProcessor implements Processor<CaseTypeDZ, CaseTypeDZService> {

	/**
	 * @see cn.com.chnsys.std_base_data.business.Processor#getNewItems(java.lang.String)
	 */
	public List<CaseTypeDZ> getNewItems(String path) throws ExcelException {
		log.info("从excel中获取案件类型代字信息！");
		ColumnsSetting columnsSetting = new ColumnsSetting();
		//代码
		columnsSetting.addColumn("code", 2);
		//名称
		columnsSetting.addColumn("name", 3);
		//说明
		columnsSetting.addColumn("description", 4);
		//上级代码
		columnsSetting.addColumn("superiorCode", 5);
		//分级码
		columnsSetting.addColumn("gradeCode", 6);
		
		return ExcelUtil.praseExcel(CaseTypeDZ.class,
				path, columnsSetting, "案件类型及其代字");
	}

	/**
	 * @see cn.com.chnsys.std_base_data.business.Processor#getOldItems(cn.com.chnsys.cif.core.framework.BaseService)
	 */
	public List<CaseTypeDZ> getOldItems(CaseTypeDZService service) {
		return service.getCaseTypeDZList(new CaseTypeDZ(), RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
	}

	/**
	 * @see cn.com.chnsys.std_base_data.business.Processor#convert2Save(java.lang.String, int)
	 */
	public ResultPojo convert2Save(String path, int courtStdVersion, TextArea textArea) {
		log.info("转换存储");
		CaseTypeDZService caseTypeDZService = getBean(getServiceVersion(courtStdVersion));
		ResultPojo rp = new ResultPojo();
		try {
			CompareResult<CaseTypeDZ> compareResult = compre(getNewItems(path), getOldItems(caseTypeDZService), STDConstants.CASE_TYPE_DZ_COMPS, "code");
			List<CaseTypeDZ> needAddItems = compareResult.getNews();
			if(needAddItems.size() > 0){
				textArea.append("正在添加新增加的案件类型代字...");
				for (CaseTypeDZ caseTypeDZ : needAddItems) {
					caseTypeDZ.setCreateTime(DateTimeUtil.getNow());
				}
				caseTypeDZService.insertCaseTypeDZs(needAddItems);
				
			}
			List<CaseTypeDZ> needUpdateItems = compareResult.getUpdates();
			log.info("需要更新的案件类型代字数量为：" + needUpdateItems.size());
			for (CaseTypeDZ caseTypeDZ : needUpdateItems) {
				caseTypeDZ.setTimestamp(DateTimeUtil.getCurrentMilliSecond());
				caseTypeDZService.updateCaseTypeDZ(caseTypeDZ);
				textArea.append("更新案件类型代字：" + caseTypeDZ.getName() + "\n");
			}
			rp.setAddCountString("案件类型代字添加：" + needAddItems.size());
			rp.setUpdateCountString("案件类型代字更新：" + needUpdateItems.size());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return rp;
		
	}
	
	private static String getServiceVersion(int courtStdVersion) {
		String service = null;
		switch (courtStdVersion) {
		case V2009:
			service = "caseTypeDZV2009Service";
			break;
		case V2005:
			service = "caseTypeDZV2005Service";
			break;
		case V2015:
			service = "caseTypeDZV2015Service";
			break;

		default:
			throw unsuppertStdVersion(courtStdVersion);
		}
		log.info("获取的service为：" + service);
		return service;
	}


}
