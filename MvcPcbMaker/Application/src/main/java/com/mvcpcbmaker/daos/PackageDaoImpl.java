package com.mvcpcbmaker.daos;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mvcpcbmaker.daos.PackageDao;


@Repository
public class PackageDaoImpl implements PackageDao {

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

			Map<String,Double> packageSizeData = new HashMap<String,Double>();

			String getPackageRowString = "SELECT height, width FROM package WHERE name = ?";
			try
			{
				List<Map<String,Object>> packageSizeDataList = jdbcTemplate.queryForList(getPackageRowString,new Object[]{packageName});
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
		this.packageRowDataList = null;

	}

}
