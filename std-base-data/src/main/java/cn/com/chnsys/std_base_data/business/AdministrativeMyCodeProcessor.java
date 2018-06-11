package cn.com.chnsys.std_base_data.business;

import static cn.com.chnsys.dtc.court.std.base.constants.CourtStdVersion.unsuppertStdVersion;
import static cn.com.chnsys.std_base_data.constants.STDConstants.V2005;
import static cn.com.chnsys.std_base_data.constants.STDConstants.V2009;
import static cn.com.chnsys.std_base_data.constants.STDConstants.V2015;

import java.awt.TextArea;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import cn.com.chnsys.cif.base.utils.CollectionUtil;
import cn.com.chnsys.cif.base.utils.DateTimeUtil;
import cn.com.chnsys.cif.base.utils.ExcelUtil;
import cn.com.chnsys.cif.base.utils.excel.ExcelException;
import cn.com.chnsys.cif.base.utils.excel.model.ColumnsSetting;
import cn.com.chnsys.dtc.court.std.base.entity.CaseCauseCode;
import cn.com.chnsys.dtc.court.std.base.entity.CodeDirectory;
import cn.com.chnsys.dtc.court.std.base.service.CaseCauseCodeService;
import cn.com.chnsys.std_base_data.constants.STDConstants;
import cn.com.chnsys.std_base_data.entity.MyCodeV0529;
import cn.com.chnsys.std_base_data.entity.MyCodeV0529Service;
import cn.com.chnsys.std_base_data.pojo.ResultPojo;
import cn.com.chnsys.std_base_data.util.CompareResult;
import cn.com.chnsys.std_base_data.util.ProcessorUtil;

public class AdministrativeMyCodeProcessor  implements Processor<MyCodeV0529, MyCodeV0529Service>{
	/**
	 * 案由比较字段
	 */
	public static final String[] My_CODE_COMPS = {
		"rang",
		"name",
		"ower",
	};
	
	
	
	
	public List<MyCodeV0529> getNewItems(String path) throws ExcelException{
		log.info("程序从excel中读取数据...");
		ColumnsSetting columnsSetting = new ColumnsSetting();
    	columnsSetting.addColumn("rang", 0);
    	columnsSetting.addColumn("name", 1);
    	columnsSetting.addColumn("ower", 2);
    	List<MyCodeV0529> newCaseCauseList=new ArrayList<MyCodeV0529>();
     newCaseCauseList.addAll(ExcelUtil.praseExcel(MyCodeV0529.class, path,
             columnsSetting, "武功秘籍"));
    	for(MyCodeV0529 item:newCaseCauseList){
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
	public List<MyCodeV0529> getOldItems(MyCodeV0529Service service) {
		return service.getMyCodeList(new MyCodeV0529(), RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
		
	}
	public ResultPojo convert2Save(String path, int courtStdVersion, TextArea textArea){
		//TODO
		//sql语句定义的来源
		MyCodeV0529Service myCodeService = ProcessorUtil.getBean(getServiceVersion(courtStdVersion));
		ResultPojo rp = new ResultPojo();
		CompareResult<MyCodeV0529> compareResult  = null;
		try {
			compareResult = ProcessorUtil.compre(getNewItems(path), getOldItems(myCodeService), My_CODE_COMPS, "code");
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		List<MyCodeV0529> needAddCaseCauses = compareResult.getNews();
		for (MyCodeV0529 MyCode : needAddCaseCauses) {
			MyCode.setCreateTime(DateTimeUtil.getNow());
		}
		int i = 0;
		int size = needAddCaseCauses.size();
		while(i < size){
			myCodeService.insertCaseCauseCodes(CollectionUtil.subCollection(needAddCaseCauses, i, 100));
			i += 100;
			textArea.append("正在添加新增加的案由代码...");
		}
		List<MyCodeV0529> updateList = compareResult.getUpdates();
		for (MyCodeV0529 MyCode : updateList) {
			MyCode.setTimestamp(DateTimeUtil.getCurrentMilliSecond());
			myCodeService.updateCaseCauseCode(MyCode);
			textArea.append("更新案由代码：" + MyCode.getName() + "\n");
		}
		rp.setAddCountString("案由代码添加：" + needAddCaseCauses.size());
		rp.setUpdateCountString("案由代码更新：" + updateList.size());
		log.info("------更新数据条数：" + updateList.size());
		return rp;
	}
	public static final int V0529 = 1;
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
		case V0529:
			service = "MyCodeV0529Service";
			break;

		default:
			throw unsuppertStdVersion(courtStdVersion);
		}
		log.info("获得的service为：" + service);
		return service;
	}

}
