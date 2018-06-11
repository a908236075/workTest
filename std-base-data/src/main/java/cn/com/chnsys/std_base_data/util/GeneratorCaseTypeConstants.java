package cn.com.chnsys.std_base_data.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;




public class GeneratorCaseTypeConstants {
	 /**
     * 读取txt文件的内容
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
	private static final String TYPE_LOGIC = "logic";
	private static final String TYPE_SERVICE = "service";
    public static String read(File file, String type){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	String serviceName = s.split("=")[1];
            	String lowServiceName = toLowerCaseFirstOne(serviceName);
            	String str = null;
            	if(type.equals("logic")){
            		str = tranLogicStr(lowServiceName);
            	}else if(type.equals("service")){
            		str = tranServiceStr(lowServiceName);
            	}
                result.append(System.lineSeparator()+str);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
    
    
    public static Map<String, String> read(File file){
    	Map<String, String> map = new HashMap<String,String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
//            	System.out.println(s);
            	String[] arr = s.split("\\s+");
//            	System.out.println(arr[0]);
//            	System.out.println(arr[1]);
            	String engName = arr[0];
            	String cnName = arr[1];
            	map.put(engName, cnName);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }
    
    public static void write(String str ,String path)
    {
        try
        {
        File file=new File(path);
        if(!file.exists())
            file.createNewFile();
        FileOutputStream out=new FileOutputStream(file,false); //如果追加方式用true        
//        StringBuffer sb=new StringBuffer();
//        sb.append(str+"\n");
        out.write(str.getBytes("utf-8"));//注意需要转换对应的字符集
        out.close();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getStackTrace());
        }
    } 
    //cn.com.chnsys.court.std.petition.business.xfyw.entity
//    cn.com.chnsys.dtc.court.std.trial.business.spzx
    public static String tranServiceStr(String serviceName){
    	String str = "<bean id='"+serviceName+"Service' class='cn.com.chnsys.dtc.court.std.trial.business.spzx.service.impl."+toUpperCaseFirstOne(serviceName)+"ServiceImpl'>\r\n<property name='"
    			+serviceName+"Dao' ref='"+serviceName+"Dao'></property>\r\n</bean>";
    	return str;
    }
    public static String tranLogicStr(String serviceName){
//    	<bean id="remarkInfoLogic"
//    			class="cn.com.chnsys.dtc.court.std.trial.business.sfrs.logic.impl.RemarkInfoLogicImpl">
//    			<property name="remarkInfoService" ref="remarkInfoService"></property>
//    		</bean>
    	String str = "<bean id='"+serviceName+"Logic' class='cn.com.chnsys.dtc.court.std.trial.business.spzx.logic.impl."+toUpperCaseFirstOne(serviceName)+"LogicImpl'>\r\n<property name='"
    			+serviceName+"Service' ref='"+serviceName+"Service'></property>\r\n"
    					+ "<property name='codeNameSetter' ref='simpleCodeNameSetter'></property>\r\n</bean>";
    	return str;
    }
 
	//首字母转小写
	public static String toLowerCaseFirstOne(String s){
	  if(Character.isLowerCase(s.charAt(0)))
	    return s;
	  else
	    return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}
	//首字母转大写
	public static String toUpperCaseFirstOne(String s){
	  if(Character.isUpperCase(s.charAt(0)))
	    return s;
	  else
	    return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}
	
    public static void main(String[] args){
        File file = new File("D:/tableNameMap.properties");
//        String outPath="D:/logic_petition.xml"; 
        String outPath="D:/service_petition.xml"; 
//        String result =  read(file,TransServiceAndLogicIntoXml.TYPE_LOGIC);
        String result =  read(file,GeneratorCaseTypeConstants.TYPE_SERVICE);
        write(result,outPath);
    }
}
