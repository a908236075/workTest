package cn.com.chnsys.std_base_data.entity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MyCodeDaoImpl  implements MyCodeDaoMapper {
	
	@Autowired
	private SqlSessionFactory std0529SqlSessionFactory;
	
	public List<MyCodeV0529> getRecords(MyCodeV0529 item) {
		SqlSession sqlSession = std0529SqlSessionFactory.openSession();
		List<MyCodeV0529> selectList = sqlSession.selectList("getRecords",item);
		
		return selectList;
	}

	public void insertRecords(MyCodeV0529 myCodeV0529) {
		
		String resource = "spring/applicationMy.xml";
        InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession();
		
		//SqlSession sqlSession = std0529SqlSessionFactory.openSession();
        
			
        	session.insert("test.insertRecord", myCodeV0529);
		

	}

	public void updateRecord(MyCodeV0529 myCode) {
		SqlSession sqlSession = std0529SqlSessionFactory.openSession();
		sqlSession.update("updateRecord",myCode);

	}}


