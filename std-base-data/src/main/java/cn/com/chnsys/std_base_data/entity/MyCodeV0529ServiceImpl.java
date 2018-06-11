package cn.com.chnsys.std_base_data.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import cn.com.chnsys.cif.base.utils.CollectionUtil;
@Service
public class MyCodeV0529ServiceImpl implements MyCodeV0529Service {
	


	public List<MyCodeV0529> getMyCodeList(MyCodeV0529 myCodeV0529,
			int noRowOffset, int noRowLimit) {
		MyCodeDaoImpl myCodeDaoImpl=new MyCodeDaoImpl();
		List<MyCodeV0529> records =new ArrayList<MyCodeV0529>();
		myCodeV0529.setRang(1);
		myCodeV0529.setName("太极拳");
		myCodeV0529.setOwer("三丰");
		records.add(myCodeV0529);
		return records;
	}

	public void insertCaseCauseCodes(Collection<MyCodeV0529> subCollection) {
		MyCodeDaoImpl myCodeDaoImpl=new MyCodeDaoImpl();
		for (MyCodeV0529 myCodeV0529 : subCollection) {
			
			myCodeDaoImpl.insertRecords(myCodeV0529);
		}
		
	}

	public void updateCaseCauseCode(MyCodeV0529 myCode) {
		MyCodeDaoImpl myCodeDaoImpl=new MyCodeDaoImpl();
		myCodeDaoImpl.updateRecord(myCode);
		
	}

	/*@SuppressWarnings("resource")
	public List<MyCodeV0529> getMyCodeList(MyCodeV0529 item,
			int offset, int limit) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/*.xml");
		MyCodeDao myCodeDao = context.getBean(MyCodeDao.class);
		return myCodeDao.getRecords(item);
	}

	public void insertCaseCauseCodes(Collection<MyCodeV0529> items) {
		context = new ClassPathXmlApplicationContext("/*.xml");
		MyCodeDao myCodeDao = context.getBean(MyCodeDao.class);   
		 myCodeDao.insertRecords((List<MyCodeV0529>) items);
		
	}

	public void updateCaseCauseCode(MyCodeV0529 myCode) {
		context = new ClassPathXmlApplicationContext("/*.xml");
		MyCodeDao myCodeDao = context.getBean(MyCodeDao.class);
		myCodeDao.updateRecord(myCode);
		
	}
*/


	

}
