/**
 * 
 */
package cn.com.chnsys.std_base_data.business;

import static cn.com.chnsys.dtc.court.std.base.constants.CourtStdVersion.unsuppertStdVersion;
import static cn.com.chnsys.std_base_data.constants.STDConstants.V2015;

import java.awt.TextArea;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import cn.com.chnsys.cif.base.utils.CollectionUtil;
import cn.com.chnsys.cif.base.utils.DateTimeUtil;
import cn.com.chnsys.cif.base.utils.ExcelUtil;
import cn.com.chnsys.cif.base.utils.excel.ExcelException;
import cn.com.chnsys.cif.base.utils.excel.model.ColumnsSetting;
import cn.com.chnsys.dtc.court.std.base.entity.AdministrativeDivisionCodeV2015;
import cn.com.chnsys.dtc.court.std.base.service.v2015.AdministrativeDivisionCodeV2015Service;
import cn.com.chnsys.std_base_data.constants.STDConstants;
import cn.com.chnsys.std_base_data.pojo.ResultPojo;
import cn.com.chnsys.std_base_data.util.CompareResult;
import cn.com.chnsys.std_base_data.util.ProcessorUtil;

/**
 * 
 * @author bianxiaozeng
 * @version 1.0
 * 
 * @date 2016年11月5日 下午6:37:02
 */
public class AdministrativeDivisionCodeProcessor
		implements
		Processor<AdministrativeDivisionCodeV2015, AdministrativeDivisionCodeV2015Service> {

	/**
	 * @see cn.com.chnsys.std_base_data.business.Processor#getNewItems(java.lang.String)
	 */
	public List<AdministrativeDivisionCodeV2015> getNewItems(String path)
			throws ExcelException {
		log.info("程序从excel中读取数据...");
		ColumnsSetting columnsSetting = new ColumnsSetting();
		columnsSetting.addColumn("code", 2);
		columnsSetting.addColumn("superiorCode", 3);
		columnsSetting.addColumn("gradeCode", 4);
		columnsSetting.addColumn("name", 5);
		columnsSetting.addColumn("description", 6);
		columnsSetting.addColumn("revokeExplain", 7);
		return ExcelUtil.praseExcel(AdministrativeDivisionCodeV2015.class,
				path, columnsSetting, "行政区划代码");
	}

	/**
	 * @see cn.com.chnsys.std_base_data.business.Processor#getOldItems(cn.com.chnsys.cif.core.framework.BaseService)
	 */
	public List<AdministrativeDivisionCodeV2015> getOldItems(
			AdministrativeDivisionCodeV2015Service service) {
		return service.getAdministrativeDivisionCodeList(
				new AdministrativeDivisionCodeV2015(), RowBounds.NO_ROW_OFFSET,
				RowBounds.NO_ROW_LIMIT);
	}

	/**
	 * @see cn.com.chnsys.std_base_data.business.Processor#convert2Save(java.lang.String,
	 *      int)
	 */
	public ResultPojo convert2Save(String path, int courtStdVersion,
			TextArea textArea) {
		AdministrativeDivisionCodeV2015Service administrativeDivisionCodeV2015Service = ProcessorUtil
				.getBean(getServiceVersion(courtStdVersion));
		ResultPojo rp = new ResultPojo();
		try {
			CompareResult<AdministrativeDivisionCodeV2015> compareResult = ProcessorUtil
					.compre(getNewItems(path),
							getOldItems(administrativeDivisionCodeV2015Service),
							STDConstants.ADMIN_DIVISION_CODE_COMPS, "code");
			List<AdministrativeDivisionCodeV2015> needAddItems = compareResult
					.getNews();
			for (AdministrativeDivisionCodeV2015 administrativeDivisionCodeV2015 : needAddItems) {
				administrativeDivisionCodeV2015.setCreateTime(DateTimeUtil
						.getNow());
			}
			int i = 0;
			int size = needAddItems.size();
			while (i < size) {
				textArea.append("正在添加新增加的行政区划代码...");
				administrativeDivisionCodeV2015Service
						.insertAdministrativeDivisionCodes((List<AdministrativeDivisionCodeV2015>) CollectionUtil
								.subCollection(needAddItems, i, 100));

				i += 100;
			}
			List<AdministrativeDivisionCodeV2015> needUpdateItems = compareResult
					.getUpdates();
			log.info("需要更新的数据量为：" + needUpdateItems.size());
			for (AdministrativeDivisionCodeV2015 administrativeDivisionCodeV2015 : needUpdateItems) {
				administrativeDivisionCodeV2015.setTimestamp(DateTimeUtil
						.getCurrentMilliSecond());
				administrativeDivisionCodeV2015Service
						.updateAdministrativeDivisionCode(administrativeDivisionCodeV2015);
				textArea.append("更新行政区划："
						+ administrativeDivisionCodeV2015.getName() + "\n");
			}
			rp.setAddCountString("行政区划代码添加：" + needAddItems.size());
			rp.setUpdateCountString("行政区划代码更新：" + needUpdateItems.size());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return rp;
	}

	public ResultPojo updateSuperiorCode(TextArea textArea){
		AdministrativeDivisionCodeV2015Service administrativeDivisionCodeV2015Service = ProcessorUtil
				.getBean(getServiceVersion(V2015));
		ResultPojo rp = new ResultPojo();
		List<AdministrativeDivisionCodeV2015> oldItems = getOldItems(administrativeDivisionCodeV2015Service);
		StringBuilder sbGradeCode = new StringBuilder();
		int updateCount = 0;
		for (AdministrativeDivisionCodeV2015 item1 : oldItems) {
			sbGradeCode.delete(0, sbGradeCode.length());
			sbGradeCode.append(item1.getGradeCode());
			if (sbGradeCode.length() <= 3 && item1.getSuperiorCode()!=0) {
				item1.setSuperiorCode(0);
				administrativeDivisionCodeV2015Service.updateAdministrativeDivisionCode(item1);
				textArea.append("更新行政区划："
						+ item1.getName() + " 上级代码："+ item1.getSuperiorCode() +"\n");
				updateCount++;
				continue;
			}
			sbGradeCode.delete(sbGradeCode.length()-3, sbGradeCode.length());
			for (AdministrativeDivisionCodeV2015 item2 : oldItems) {
				if (item2.getGradeCode().equals(sbGradeCode.toString())) {
					if (!item2.getCode().equals(item1.getSuperiorCode())) {
						item1.setSuperiorCode(item2.getCode());
						administrativeDivisionCodeV2015Service.updateAdministrativeDivisionCode(item1);
						textArea.append("更新行政区划："
								+ item1.getName() + " 上级代码："+ item1.getSuperiorCode() +"\n");
						updateCount++;
					}
					break;
				}
			}
		}
		rp.setUpdateCountString("行政区划上级代码更新数量为：" + updateCount);
		return rp;
	}

	private static String getServiceVersion(int courtStdVersion) {
		String service = null;
		switch (courtStdVersion) {

		case V2015:
			service = "administrativeDivisionCodeV2015Service";
			break;

		default:
			throw unsuppertStdVersion(courtStdVersion);
		}
		log.info("获取的service为：" + service);
		return service;
	}

}
