package com.mvcpcbmaker.daos;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.security.auth.x500.X500Principal;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.mvcpcbmaker.daos.PackageDao;


@Repository
public class PackageDaoImpl implements PackageDao {

	private String name;
	private double height;
	private double width;
	private int pinCount;
	private List<Object[]> packageRowDataList;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	
	public PackageDaoImpl()
	{
	}
	
	
	@Override
	public void addPackageData(List<Map<String,Object>> packageListData)
	{
		String addPackageString = "INSERT INTO package VALUES (?,?,?,?)";		
		clearPackageData();
		this.packageRowDataList = new ArrayList<Object[]>();
		try
		{
			for(Map<String,Object> packageData : packageListData)
			{
				Object[] tempObj = new Object[4];
				tempObj[0] = packageData.get("name").toString();
				tempObj[1] = Double.parseDouble(packageData.get("height").toString());
				tempObj[2] = Double.parseDouble(packageData.get("width").toString());
				tempObj[3] = Integer.parseInt(packageData.get("pinCount").toString());
				this.packageRowDataList.add(tempObj);
			}
			
			jdbcTemplate.batchUpdate(addPackageString, packageRowDataList);
		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
	}
	

	@Override
	public Map<String,Double> getPackageSizeData(String packageName) {
		// TODO Auto-generated method stub
			class PackageSize
			{
				public double width;
				public double height;
			}
			
			Map<String,Double> packageSizeData = new HashMap<String,Double>();
			
			String getPackageRowString = "SELECT height, width FROM package WHERE name = ?";
			try
			{
				List<Map<String,Object>> packageSizeDataList = jdbcTemplate.queryForList(getPackageRowString,new Object[]{packageName});//, Map<String,Double>);
				if(packageSizeDataList.size() > 0)
				{
					Map<String,Object> packageSize = packageSizeDataList.get(0);
					packageSizeData.put("width",Double.parseDouble(packageSize.get("width").toString()));
					packageSizeData.put("height",Double.parseDouble(packageSize.get("height").toString()));
				}
			}
			catch(Exception Ex)
			{
				Ex.printStackTrace();
			}
		
		
		return  packageSizeData;
	}

	@Override
	public void clearPackageData() {
		String clearPackageTableString = "truncate table package";
		try
		{
			jdbcTemplate.execute(clearPackageTableString);
		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
		this.packageRowDataList = null;//new ArrayList<>();

	}

}
