package cn.com.chnsys.std_base_data.entity;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.com.chnsys.cif.core.framework.BaseDao;

@MapperScan
public interface MyCodeDaoMapper{
	

	List<MyCodeV0529> getRecords(MyCodeV0529 item);

	void insertRecords(MyCodeV0529 item);

	void updateRecord(MyCodeV0529 item);

}
