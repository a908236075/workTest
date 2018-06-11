package cn.com.chnsys.std_base_data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.com.chnsys.std_base_data.entity.MyCodeDaoImpl;
import cn.com.chnsys.std_base_data.entity.MyCodeDaoMapper;
import cn.com.chnsys.std_base_data.entity.MyCodeV0529;
import cn.com.chnsys.std_base_data.entity.MyCodeV0529Service;

public class Mytest {

	public static void main(String[] args) {
		/*
		 * AbstractApplicationContext context = new
		 * ClassPathXmlApplicationContext(
		 * "classpath:/spring/applicationMy.xml");
		 */
		/*
		 * String resource = "spring/applicationMy.xml"; InputStream inputStream
		 * = null; try { inputStream = Resources.getResourceAsStream(resource);
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } SqlSessionFactory factory = new
		 * SqlSessionFactoryBuilder().build(inputStream); SqlSession session =
		 * factory.openSession();
		 */

		// SqlSession sqlSession = std0529SqlSessionFactory.openSession();

		/*
		 * SqlSessionFactory factory = (SqlSessionFactory)
		 * context.getBean("std0529SqlSessionFactory"); SqlSession session =
		 * factory.openSession(); session.getMapper(new MyCodeV0529());
		 */
		/*
		 * MyCodeDaoMapper MyCodeDaoMapper=new MyCodeDaoImpl(); MyCodeV0529
		 * myCodeV0529 = new MyCodeV0529(); myCodeV0529.setRang(3);
		 * myCodeV0529.setName("dssfasdf"); myCodeV0529.setOwer("sdfsfdaf");
		 * MyCodeDaoMapper.insertRecords(myCodeV0529);
		 */
		test1();
	}

	public static void test1(){
		String resource = "spring/applicationMy.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SqlSessionFactory factory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		SqlSession session = factory.openSession();

		// SqlSession sqlSession = std0529SqlSessionFactory.openSession();

		final MyCodeV0529 myCodeV0529 = new MyCodeV0529();
		myCodeV0529.setRang(3);
		myCodeV0529.setName("dssfasdf");
		myCodeV0529.setOwer("sdfsfdaf");
		new ArrayList<MyCodeV0529>(){
			
			
			
			{add(myCodeV0529);}};
		

	}

}
