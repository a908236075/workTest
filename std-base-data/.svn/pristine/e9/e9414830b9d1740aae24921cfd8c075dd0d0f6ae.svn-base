/**
 * 
 */
package cn.com.chnsys.std_base_data.business;

import static cn.com.chnsys.dtc.court.std.base.constants.CourtStdVersion.unsuppertStdVersion;
import static cn.com.chnsys.std_base_data.constants.STDConstants.V2015;
import static cn.com.chnsys.std_base_data.util.ProcessorUtil.compre;
import static cn.com.chnsys.std_base_data.util.ProcessorUtil.getBean;

import java.awt.TextArea;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import cn.com.chnsys.cif.base.utils.DateTimeUtil;
import cn.com.chnsys.cif.base.utils.ExcelUtil;
import cn.com.chnsys.cif.base.utils.excel.ExcelException;
import cn.com.chnsys.cif.base.utils.excel.model.ColumnsSetting;
import cn.com.chnsys.dtc.court.std.base.entity.BusinessTypeIdentityV2015;
import cn.com.chnsys.dtc.court.std.base.service.v2015.BusinessTypeIdentityV2015Service;
import cn.com.chnsys.std_base_data.constants.STDConstants;
import cn.com.chnsys.std_base_data.pojo.ResultPojo;
import cn.com.chnsys.std_base_data.util.CompareResult;

/**
 * 业务类型标识processor
 * @author bianxiaozeng
 * @version 1.0
 * 
 * @date 2016年11月6日 上午9:01:09 
 */
public class BusinessTypeIdentityProcessor implements Processor<BusinessTypeIdentityV2015, BusinessTypeIdentityV2015Service> {

	/**
	 * @see cn.com.chnsys.std_base_data.business.Processor#getNewItems(java.lang.String)
	 */
	public List<BusinessTypeIdentityV2015> getNewItems(String path)
			throws ExcelException {
		log.info("从excel中获取业务类型标识信息！");
		ColumnsSetting columnsSetting = new ColumnsSetting();
		//代码
		columnsSetting.addColumn("code", 2);
		//上级代码
		columnsSetting.addColumn("superiorCode", 3);
		//分级码
		columnsSetting.addColumn("gradeCode", 4);
		//法标数据资源分类
		columnsSetting.addColumn("courtStandardDataSourceClassify", 5);
		//名称
		columnsSetting.addColumn("businessStandardCaseType", 6);
		//说明
		columnsSetting.addColumn("caseTypeDaiz", 7);
		
		List<BusinessTypeIdentityV2015> newItems = ExcelUtil.praseExcel(BusinessTypeIdentityV2015.class,
				path, columnsSetting, "业务类型标识");
		return newItems;
	}

	/**
	 * @see cn.com.chnsys.std_base_data.business.Processor#getOldItems(cn.com.chnsys.cif.core.framework.BaseService)
	 */
	public List<BusinessTypeIdentityV2015> getOldItems(
			BusinessTypeIdentityV2015Service service) {
		return service.getBusinessTypeIdentityList(new BusinessTypeIdentityV2015(), RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
	}

	/**
	 * @see cn.com.chnsys.std_base_data.business.Processor#convert2Save(java.lang.String, int)
	 */
	public ResultPojo convert2Save(String path, int courtStdVersion, TextArea textArea) {
		BusinessTypeIdentityV2015Service businessTypeIdentityV2015Service = getBean(getServiceVersion(courtStdVersion));
		ResultPojo rp = new ResultPojo();
		try {
			CompareResult<BusinessTypeIdentityV2015> compareResult = compre(getNewItems(path), getOldItems(businessTypeIdentityV2015Service), STDConstants.BUSINESS_TYPE_IDENTITY_COMPS, "code");
			List<BusinessTypeIdentityV2015> needAddItems = compareResult.getNews();
			if(needAddItems.size() > 0){
				for (BusinessTypeIdentityV2015 businessTypeIdentityV2015 : needAddItems) {
					businessTypeIdentityV2015.setCreateTime(DateTimeUtil.getNow());
				}
				textArea.append("正在添加新增加的业务类型标识...");
				businessTypeIdentityV2015Service.insertBusinessTypeIdentitys(needAddItems);
			}
			List<BusinessTypeIdentityV2015> needUpdateItems = compareResult.getUpdates();
			log.info("需要更新的数据为：" + needUpdateItems.size());
			for (BusinessTypeIdentityV2015 businessTypeIdentityV2015 : needUpdateItems) {
				businessTypeIdentityV2015.setTimestamp(DateTimeUtil.getCurrentMilliSecond());
//				businessTypeIdentityV2015Service.updateBusinessTypeIdentity(businessTypeIdentityV2015);
				textArea.append("更新业务类型标识" + businessTypeIdentityV2015.getName() + "\n");
			}
			rp.setAddCountString("业务类型标识添加：" + needAddItems.size());
			rp.setUpdateCountString("业务类型标识更新：" + needUpdateItems.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rp;
	}
	
	private static String getServiceVersion(int courtStdVersion) {
		String service = null;
		switch (courtStdVersion) {
		case V2015:
			service = "businessTypeIdentityV2015Service";
			break;

		default:
			throw unsuppertStdVersion(courtStdVersion);
		}
		log.info("获取的service为：" + service);
		return service;
	}


}
