/**
 * 
 */
package cn.com.chnsys.std_base_data.business;

import static cn.com.chnsys.dtc.court.std.base.constants.CourtStdVersion.unsuppertStdVersion;
import static cn.com.chnsys.std_base_data.constants.STDConstants.V2005;
import static cn.com.chnsys.std_base_data.constants.STDConstants.V2009;
import static cn.com.chnsys.std_base_data.constants.STDConstants.V2015;

import java.awt.TextArea;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import cn.com.chnsys.cif.base.utils.CollectionUtil;
import cn.com.chnsys.cif.base.utils.DateTimeUtil;
import cn.com.chnsys.cif.base.utils.ExcelUtil;
import cn.com.chnsys.cif.base.utils.RandomGuid;
import cn.com.chnsys.cif.base.utils.excel.ExcelException;
import cn.com.chnsys.cif.base.utils.excel.model.ColumnsSetting;
import cn.com.chnsys.dtc.court.std.base.entity.CodeInfo;
import cn.com.chnsys.dtc.court.std.base.service.CodeInfoService;
import cn.com.chnsys.std_base_data.constants.STDConstants;
import cn.com.chnsys.std_base_data.pojo.ResultPojo;
import cn.com.chnsys.std_base_data.util.CompareResult;
import cn.com.chnsys.std_base_data.util.ProcessorUtil;

/**
 * 存储代码信息
 * 
 * @author bianxiaozeng
 * @version 1.0
 * 
 * @date 2016年11月4日 下午4:10:44
 */
public class CodeInfoProcessor implements Processor<CodeInfo, CodeInfoService> {

//	private String[] sheetArray = {"一.普通代码", "二.综合业务", "三.审判执行","国家","案件类型及其代字"};
	private String[] sheetArray = {"法标外新增"};
//	private String[] sheetArrayV2015 = {"案件来源","结案方式", "一.普通代码", "二.综合业务", "三.审判执行", "四.司法政务", "五.音视频","国家"};;

	/**
	 * @see cn.com.chnsys.std_base_data.business.Processor#getNewItems(java.lang.String)
	 */
	public List<CodeInfo> getNewItems(String path) throws ExcelException {
		log.info("从excel中获取代码信息！");
//		String[] sheetArray = { "案件来源", "结案方式", "一.普通代码", "二.综合业务", "三.审判执行",
//				"四.司法政务", "五.音视频" };
		ColumnsSetting columnsSetting = new ColumnsSetting();
		columnsSetting.addColumn("codeDirectoryNo", 1);
		columnsSetting.addColumn("code", 2);
		columnsSetting.addColumn("name", 3);
		columnsSetting.addColumn("description", 4);
		columnsSetting.addColumn("gradeCode", 5);
		List<CodeInfo> newCodeinfoList = new ArrayList<CodeInfo>();
		for (int i = 0; i < sheetArray.length; i++) {
			newCodeinfoList.addAll(ExcelUtil.praseExcel(CodeInfo.class, path,
					columnsSetting, sheetArray[i]));
		}
		return newCodeinfoList;
	}

	/**
	 * @see cn.com.chnsys.std_base_data.business.Processor#getOldItems(cn.com.chnsys.cif.core.framework.BaseService)
	 */
	public List<CodeInfo> getOldItems(CodeInfoService service) {
		return service.getCodeInfoList(new CodeInfo(), RowBounds.NO_ROW_OFFSET,
				RowBounds.NO_ROW_LIMIT);
	}

	/**
	 * @see cn.com.chnsys.std_base_data.business.Processor#convert2Save(java.util.List)
	 */
	public ResultPojo convert2Save(String path, int courtStdVersion, TextArea textArea) {
		ResultPojo rp = new ResultPojo();
		CodeInfoService codeInfoV2009Service = ProcessorUtil
				.getBean(getServiceVersion(courtStdVersion));
		CompareResult<CodeInfo> compareResult = null;
		try {
			List<CodeInfo> newItems = getNewItems(path);
			log.info("设置代码信息id，代码目录id，创建时间");
			Iterator<CodeInfo> iterator = newItems.iterator();
			String codeDirectoryNo = "";
			while (iterator.hasNext()) {
				CodeInfo codeInfo = iterator.next();
				if (codeInfo.getCodeDirectoryNo()!=null&&codeInfo.getCodeDirectoryNo().equals("编号")) {
					log.info("删除：" + codeInfo.toString());
					iterator.remove();
					continue;
				}
				if(codeInfo.getCodeDirectoryNo()==null||codeInfo.getCodeDirectoryNo().equals("")){
					codeInfo.setCodeDirectoryNo(codeDirectoryNo);
				}else{
					codeDirectoryNo = codeInfo.getCodeDirectoryNo();
				}
				codeInfo.setCodeDirectoryID(RandomGuid.generateGuid(codeInfo
						.getCodeDirectoryNo()));
				codeInfo.setCodeOptionID(RandomGuid.generateGuid(codeInfo
						.getCodeDirectoryNo() + "_" + codeInfo.getCode()));
			}
			compareResult = ProcessorUtil.compre(newItems,
					getOldItems(codeInfoV2009Service),
					STDConstants.CODE_INFO_COMPS, "codeOptionID");
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		List<CodeInfo> needAddItems = compareResult.getNews();
		int i = 0;
		int size = needAddItems.size();
		
		// 如果数据量太大，需要分批写入
		while (i < size) {
			textArea.append("正在批量添加新增的案件信息..." + i + "\n");
//			try {
//				codeInfoV2009Service
//						.insertCodeInfos(needAddItems.subList(i, i+100));
//			} catch (Exception e) {
//				log.error("添加出错", e);
//			}
			CodeInfo info = needAddItems.get(i);
			System.out.println("#############################################No:"+info.getCodeDirectoryNo()+"    Code:"+info.getCode());
			System.out.println("primary key is :"+RandomGuid.generateGuid(info
						.getCodeDirectoryNo() + "_" + info.getCode())); 
			codeInfoV2009Service
			.insertCodeInfo(needAddItems.get(i));
			i += 1;
		}
		System.out.println("导入的数据数量为#####################："+size);
		List<CodeInfo> needUpdateItems = compareResult.getUpdates();
		for (CodeInfo codeInfo : needUpdateItems) {
			codeInfo.setTimestamp(DateTimeUtil.getCurrentMilliSecond());
			codeInfoV2009Service.updateCodeInfo(codeInfo);
			textArea.append("更新代码信息" + codeInfo.getName() + "\n");
		}
		rp.setAddCountString("代码信息添加：" + needAddItems.size());
		rp.setUpdateCountString("代码信息更新：" + needUpdateItems.size());
		return rp;
	}

	private static String getServiceVersion(int courtStdVersion) {
		String service = null;
		switch (courtStdVersion) {
		case V2009:
			service = "codeInfoV2009Service";
			break;
		case V2005:
			service = "codeInfoV2005Service";
			break;
		case V2015:
			service = "codeInfoV2015Service";
			break;

		default:
			throw unsuppertStdVersion(courtStdVersion);
		}
		return service;
	}
	/**
	 * 根据案件类型名称获取案件类型代码
	 * @param name
	 * @return
	 */
	public Integer getCaseTypeNumByCaseTypeName(String name){
		CodeInfoService codeInfoService = ProcessorUtil
				.getBean(getServiceVersion(2015));
		CodeInfo codeInfo = new CodeInfo();
		codeInfo.setName(name);
		List<CodeInfo> list = codeInfoService.getCodeInfoList(codeInfo, 0, Integer.MAX_VALUE);
		try {
			return list.get(0).getCode();
		} catch (Exception e) {
			System.out.println("---------------------------------------------------------");
			return 2048;
		}
	}
	public static void main(String[] args) {
		System.out.println(RandomGuid.generateGuid("GF2015-00004"));
		System.out.println(RandomGuid.generateGuid("GF2015-00004_10"));
	}

}
