/**
 * 
 */
package cn.com.chnsys.std_base_data.business;

import static cn.com.chnsys.dtc.court.std.base.constants.CourtStdVersion.unsuppertStdVersion;
import static cn.com.chnsys.std_base_data.constants.STDConstants.V2005;
import static cn.com.chnsys.std_base_data.constants.STDConstants.V2009;
import static cn.com.chnsys.std_base_data.constants.STDConstants.V2015;

import java.awt.TextArea;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.chnsys.cif.base.utils.DateTimeUtil;
import cn.com.chnsys.cif.base.utils.ExcelUtil;
import cn.com.chnsys.cif.base.utils.StringUtil;
import cn.com.chnsys.cif.base.utils.excel.ExcelException;
import cn.com.chnsys.cif.base.utils.excel.model.ColumnsSetting;
import cn.com.chnsys.dtc.court.std.base.entity.CourtCode;
import cn.com.chnsys.dtc.court.std.base.service.CourtCodeService;
import cn.com.chnsys.std_base_data.constants.STDConstants;
import cn.com.chnsys.std_base_data.pojo.ResultPojo;
import cn.com.chnsys.std_base_data.util.CompareResult;
import cn.com.chnsys.std_base_data.util.ProcessorUtil;

/**
 * 
 * @author bianxiaozeng
 * @version 1.0
 * 
 * @date 2016年11月2日 上午9:28:58
 */
public class CourtCodeProcessor implements
		Processor<CourtCode, CourtCodeService> {

	public static Logger log = LoggerFactory.getLogger(Processor.class
			.getName());

	public List<CourtCode> getNewItems(String path) throws ExcelException {
		ColumnsSetting columnsSetting = new ColumnsSetting();
		columnsSetting.addColumn("code", 1);
		columnsSetting.addColumn("gradeCode", 3);
		columnsSetting.addColumn("name", 4);
//		columnsSetting.addColumn("description", 4);
		columnsSetting.addColumn("daiZi", 5);
		columnsSetting.addColumn("superiorCode", 2);
		List<CourtCode> newCourtCode = ExcelUtil.praseExcel(CourtCode.class,
				path, columnsSetting, "法院代码");
		return newCourtCode;
	}

	public List<CourtCode> getOldItems(CourtCodeService service) {
		return service.getCourtCodeList(new CourtCode(),
				RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
	}

	public ResultPojo convert2Save(String path, int courtStdVersion,
			TextArea textArea) {
		CourtCodeService courtCodeService = ProcessorUtil
				.getBean(getServiceVersion(courtStdVersion));
		ResultPojo rp = new ResultPojo();
		try {
			List<CourtCode> newCourtCode = getNewItems(path);
			//将小数格式的分级码变成整数
			for(CourtCode item:newCourtCode){
				if(!StringUtil.isEmpty(item.getGradeCode())){
					int index = item.getGradeCode().indexOf(".");
					if(index!=-1){
						item.setGradeCode(item.getGradeCode().substring(0,index)); 
					}
				}
			}
			
			if (courtStdVersion != V2015) {
				newCourtCode = getSuperiorCode(newCourtCode);
			}
			CompareResult<CourtCode> compareResult = ProcessorUtil.compre(
					newCourtCode, getOldItems(courtCodeService),
					STDConstants.COURT_CODE_COMPS, "code");
			List<CourtCode> needAddItems = compareResult.getNews();
			for (CourtCode courtCode : needAddItems) {
				courtCode.setCreateTime(DateTimeUtil.getNow());
			}
			if (needAddItems.size() > 0) {
				textArea.append("正在执行插入新添加的法院...");
				courtCodeService.insertCourtCodes(needAddItems);
			}

			List<CourtCode> needUpdateItems = compareResult.getUpdates();
			for (CourtCode courtCode : needUpdateItems) {
				textArea.append("更新法院：" + courtCode.getName() + "\n");
				courtCode.setTimestamp(DateTimeUtil.getCurrentMilliSecond());
				courtCodeService.updateCourtCode(courtCode);
			}
			List<CourtCode> needDeleteItems = compareResult.getDeletes();
			for(CourtCode courtCode:needDeleteItems){
				System.out.println("******************************");
				System.out.println(courtCode.toString()); 
				System.out.println("******************************");
				courtCode.setDeleteFlag(2);
				courtCodeService.updateCourtCode(courtCode);
			}
			System.out.println("法院代码添加：" + needAddItems.size());
			System.out.println("法院代码更新：" + needUpdateItems.size());
			System.out.println("法院代码删除更新：" + needDeleteItems.size());
			
			rp.setAddCountString("法院代码添加：" + needAddItems.size());
			rp.setUpdateCountString("法院代码更新：" + needUpdateItems.size());
			rp.setUpdateCountString("法院代码删除更新：" + needDeleteItems.size());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return rp;
	}
	
	public static void main(String[] args) throws IOException {
		String str = "110.0";
		int index = str.indexOf(".");
		if(index!=-1){
			System.out.println(str.substring(0,index)); 
		}
		
	}
	private static String getServiceVersion(int courtStdVersion) {
		String service = null;
		switch (courtStdVersion) {
		case V2009:
			service = "courtCodeV2009Service";
			break;
		case V2005:
			service = "courtCodeV2005Service";
			break;
		case V2015:
			service = "courtCodeV2015Service";
			break;

		default:
			throw unsuppertStdVersion(courtStdVersion);
		}
		return service;
	}

	private List<CourtCode> getSuperiorCode(List<CourtCode> courtCodes) {
		Map<String, Integer> superiorCodeMap = new HashMap<String, Integer>();
		for (CourtCode courtCode : courtCodes) {

			String gradeCode = courtCode.getGradeCode();
			if (StringUtils.endsWith(gradeCode, "0")) {
				superiorCodeMap.put(gradeCode, courtCode.getCode());
			}
		}
		for (CourtCode courtCode : courtCodes) {
			String gradeCode = courtCode.getGradeCode();
			String last = StringUtils.substring(gradeCode, 2, 3);
			String middle = StringUtils.substring(gradeCode, 1, 2);
			String start = StringUtils.substring(gradeCode, 0, 1);
			if (last.equals("0") && middle.equals("0")) {
				courtCode.setSuperiorCode(0);
			} else if (last.equals("0") && !middle.equals("0")) {
				courtCode.setSuperiorCode(superiorCodeMap
						.get(start + "0" + "0"));
			} else {
				courtCode.setSuperiorCode(superiorCodeMap.get(start + middle
						+ "0"));
			}
			log.info(courtCode.getGradeCode() + "<---分级码，上级代码--->"
					+ courtCode.getSuperiorCode());
		}
		return courtCodes;
	}

}
