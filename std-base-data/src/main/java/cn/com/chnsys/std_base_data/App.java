package cn.com.chnsys.std_base_data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.com.chnsys.cif.base.utils.ExcelUtil;
import cn.com.chnsys.cif.base.utils.excel.ExcelException;
import cn.com.chnsys.cif.base.utils.excel.model.ColumnsSetting;
import cn.com.chnsys.dtc.court.std.base.entity.CodeInfo;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ExcelException
    {
//        System.out.println( "Hello World!" );
//        String[] locations = {"classpath*:spring/court/std/base/applicationContext-service.xml","classpath:spring/applicationContext-base-dao.xml"};  
//        ApplicationContext context = new ClassPathXmlApplicationContext(locations);
//        AdministrativeDivisionCodeV2015Service bean = (AdministrativeDivisionCodeV2015Service)context.getBean("administrativeDivisionCodeV2015Service");
//        Assert.notNull(bean, "");
//        System.out.println(bean.getName(1));
//        ((ClassPathXmlApplicationContext)context).close();
//        System.out.println(house);  
//    	String path = "E:/bian_document/数据中心/法标/09法标35/09法标_入库整理.xlsx";
  //  	String path = "E:/bian_document/数据中心/法标/09法标35/2009法标外案件_入库整理.xlsx";
   	String path="‪E:\\work\\09法标_入库整理.xlsx";
//    	String path = "E:/bian_document/数据中心/法标/15法标/2015法标代码161104 - 入库整理.xlsx";
    	Map<String, String> existInfoMap = new HashMap<String, String>();
    	StringBuilder sb = new StringBuilder();
    	ColumnsSetting columnsSetting = new ColumnsSetting();
    	columnsSetting.addColumn("codeDirectoryNo", 1);
		columnsSetting.addColumn("code", 2);
		columnsSetting.addColumn("name", 0);
		columnsSetting.addColumn("description", 4);
//    	columnsSetting.addColumn("superiorCode", 5);
//		String[] sheetArray = {"案件来源","结案方式", "一.普通代码", "二.综合业务", "三.审判执行", "四.司法政务", "五.音视频"};
		String[] sheetArray = {"审判业务标准代码"};
    	List<CodeInfo> newCodeinfoList = new ArrayList<CodeInfo>();
    	for (int i = 0; i < sheetArray.length; i++) {
    		newCodeinfoList.addAll(ExcelUtil.praseExcel(CodeInfo.class, path,
    				columnsSetting, sheetArray[i]));
		}
    	for (CodeInfo codeInfo : newCodeinfoList) {
			if(existInfoMap.containsKey(codeInfo.getCodeDirectoryNo())){
				String name = existInfoMap.get(codeInfo.getCodeDirectoryNo());
				if(name.equals(codeInfo.getName())){
					continue;
				} else {
					sb.append(codeInfo.getCodeDirectoryNo() + "~~~~" + codeInfo.getName() + " " + name + "\n");
				}
			} else {
				existInfoMap.put(codeInfo.getCodeDirectoryNo(), codeInfo.getName());
			}
		}
    	Map<String, Integer> numberCodeMap = new HashMap<String, Integer>();
    	for (CodeInfo codeInfo : newCodeinfoList) {
			if (numberCodeMap.containsKey(codeInfo.getCodeDirectoryNo())) {
				Integer code = numberCodeMap.get(codeInfo.getCodeDirectoryNo());
				if (codeInfo.getCode() == null) {
					System.out.println(codeInfo.toString());
					continue;
				}
				if (code > codeInfo.getCode()) {
					numberCodeMap.put(codeInfo.getCodeDirectoryNo(), codeInfo.getCode());
				} 
			} else {
				numberCodeMap.put(codeInfo.getCodeDirectoryNo(), codeInfo.getCode());
			}
		}
    	Set<Entry<String, Integer>> entrySet = numberCodeMap.entrySet();
    	for (Entry<String, Integer> entry : entrySet) {
			System.out.println(entry.getKey() + "~~" + entry.getValue());
		}
    	System.out.println(sb.toString());
    }
}
