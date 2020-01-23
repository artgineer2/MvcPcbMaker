package com.mvcpcbmaker.models.schematic;

import java.util.List;
import java.util.Map;

public interface Schematic {
	public void initSchematic(String schString);
	public	List<Map<String,Object>> getPackageTable();
	public	List<Map<String,Object>> getPartTable();
	public List<Map<String,Object>> getNetTable();
	public void cleanOutObjectData();
}
