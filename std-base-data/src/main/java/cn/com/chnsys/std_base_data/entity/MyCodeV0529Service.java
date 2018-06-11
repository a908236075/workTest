package cn.com.chnsys.std_base_data.entity;


import java.util.Collection;
import java.util.List;

import cn.com.chnsys.cif.core.framework.BaseService;
import cn.com.chnsys.dtc.court.std.base.entity.CourtCode;

public interface MyCodeV0529Service extends BaseService {

	public List<MyCodeV0529> getMyCodeList(MyCodeV0529 myCodeV0529,
			int noRowOffset, int noRowLimit);

	public void insertCaseCauseCodes(Collection<MyCodeV0529> subCollection);

	public void updateCaseCauseCode(MyCodeV0529 myCode);
	
	
	
	
}
