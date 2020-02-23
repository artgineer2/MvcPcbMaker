package com.mvcpcbmaker.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

@Repository
public class NetDaoImpl implements NetDao {

	private List<Object[]> netRowDataList;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	SimpleJdbcCall simpleJdbcCall;

	public NetDaoImpl()
	{
	}


	@Override
	public void addNetData(List<Map<String,Object>> netListData) {

		String addNetQueryString = "INSERT INTO net VALUES (?,?,?,?,?)";
		this.netRowDataList = new ArrayList<Object[]>();
		try
		{
			for(Map<String,Object> netData : netListData)
			{
				Object[] tempObj = new Object[5];
				tempObj[0] = netData.get("name").toString();
				tempObj[1] = netData.get("part").toString();
				tempObj[2] = netData.get("pinName").toString();
				tempObj[3] = Double.parseDouble(netData.get("x").toString());
				tempObj[4] = Double.parseDouble(netData.get("y").toString());
				this.netRowDataList.add(tempObj);
			}
			jdbcTemplate.batchUpdate(addNetQueryString, netRowDataList);
		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
		this.netRowDataList = null;
	}



	@Override
	public void getNetData() {

	}

	@Override
	public void clearNetData() {
		 String clearNetTable = "truncate table net";
		try
		{

			jdbcTemplate.execute(clearNetTable);

		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
		this.netRowDataList = null;
	}

	@Override
	public void addParentChildConnectionData()
	{
		try
		{
			simpleJdbcCall.withProcedureName("set_parent_child_connections_table");
			simpleJdbcCall.execute();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void clearParentChildConnectionData()
	{
		String clearParentChildConnectionTable = "truncate table parent_child_connections";
		try
		{
			jdbcTemplate.execute(clearParentChildConnectionTable);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
}
