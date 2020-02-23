package com.mvcpcbmaker.daos;

import java.util.List;
import java.util.Map;

import com.mvcpcbmaker.utilstructs.PartRowData;

public interface PartDao {
	public void addPartData(List<Map<String,Object>> partListData);
	public void clearPartData();

	// Derived parent part tables
	public void createParentPartsTable();
	public List<String> getParentPartColumnData(String columnName);
	public PartRowData getParentPartData(String partName);
	public void clearParentPartData();

	// Derived child part tables
	public void createChildPartsTable();
	public List<String> getChildPartColumnData(String columnName);
	public PartRowData getChildPartData(String partName);
	public void clearChildPartData();




}
