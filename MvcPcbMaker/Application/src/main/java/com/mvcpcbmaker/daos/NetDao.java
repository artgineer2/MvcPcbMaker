package com.mvcpcbmaker.daos;

import java.util.List;
import java.util.Map;

public interface NetDao {
	public void addNetData(List<Map<String,Object>> netListData);
	public void getNetData();
	public void clearNetData();
	public void addParentChildConnectionData();
	public void clearParentChildConnectionData();
}
