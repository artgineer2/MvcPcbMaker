package com.mvcpcbmaker.daos;

import java.util.List;
import java.util.Map;


public interface PackageDao {
	public void addPackageData(List<Map<String,Object>> packageData);
	public Map<String,Double> getPackageSizeData(String packageName);
	public void clearPackageData();
}
