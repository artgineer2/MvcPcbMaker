package com.mvcpcbmaker.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mvcpcbmaker.utilstructs.PartRowData;


@Repository
public class PartDaoImpl implements PartDao {


	@Autowired
	private JdbcTemplate jdbcTemplate;


	private List<Object[]> partRowDataList;


	public PartDaoImpl()
	{

	}

	@Override
	public void addPartData(List<Map<String,Object>> partListData) {
		this.partRowDataList = new ArrayList<Object[]>();

		try
		{

			String addPartString = "INSERT INTO part VALUES (?,?,?,?,?,?)";
			for(Map<String,Object> partData : partListData)
			{
				Object[] tempObj = new Object[6];
				tempObj[0] = partData.get("name").toString();
				tempObj[1] = partData.get("type").toString();
				tempObj[2] = partData.get("component").toString();
				tempObj[3] = partData.get("package").toString();
				tempObj[4] = Double.parseDouble(partData.get("x").toString());
				tempObj[5] = Double.parseDouble(partData.get("y").toString());
				this.partRowDataList.add(tempObj);
			}

			jdbcTemplate.batchUpdate(addPartString, partRowDataList);
		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}

	}


	@Override
	public void getPartData() { // this operation is done in DB stored procedure
		// TODO Auto-generated method stub

	}

	//@Override
	public void clearPartData() {
		String clearPartTableString = "truncate table part";
		try
		{
			jdbcTemplate.execute(clearPartTableString);
		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
		this.partRowDataList = null;

	}

	/**********************************  PARENT PARTS   ****************************************/

	@Override
	public void createParentPartsTable() {


		String packagePinCountFilter = "(package = any (select name from package where pin_count >= 4))";
		String componentTypeFilter = "(type in ('IC','T','U'))";
		String addParentPartsString = "INSERT INTO parent_parts SELECT * FROM part WHERE " + packagePinCountFilter + " and " + componentTypeFilter;

		try
		{
			clearParentPartData();
			jdbcTemplate.execute(addParentPartsString);

		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
	}


	@Override
	public List<String> getParentPartColumnData(String columnName) {
		List<String> columnData = new ArrayList<String>();
		class ColumnDataList { List<String> columnData; }
		String item;
		String getParentPartColumnString = "SELECT " + columnName + " FROM parent_parts";
		try
		{
			List<String> columnDataList = jdbcTemplate.queryForList(getParentPartColumnString, String.class);
			if(columnDataList.size() > 0)
			{
				columnData = columnDataList.stream().map(x->x).collect(Collectors.toList());
			}



		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
		return columnData;
	}

	@Override
	public PartRowData getParentPartData(String partName)
	{
		PartRowData rowData = null;

		String getParentPartRowString = "SELECT * FROM parent_parts WHERE name = ?";
		try
		{
			List<PartRowData> parentPartRowDataList = jdbcTemplate.query(getParentPartRowString,new Object[]{partName},
					(rs, rowNum) -> new PartRowData(rs.getString("name"),rs.getString("type"),
							rs.getString("component"),rs.getString("package"),rs.getDouble("x"),rs.getDouble("y")));
			 rowData = parentPartRowDataList.get(0);

		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
		return rowData;

	}



	@Override
	public void clearParentPartData() {

		String clearParentPartsTable = "truncate table parent_parts";

		try
		{
			jdbcTemplate.execute(clearParentPartsTable);
		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
	}


	/***********************************  CHILD PARTS    *****************************************/

	@Override
	public void createChildPartsTable() {

		String nameFilter = "(name not in (select name from parent_parts))";
		String packagePinCountFilter = "(package = any (select name from package where pin_count > 1))";
		String addChildPartsString = "INSERT INTO child_parts SELECT * FROM part WHERE " + nameFilter + " and " + packagePinCountFilter;
		try
		{
			clearChildPartData();
			jdbcTemplate.execute(addChildPartsString);
		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
	}


	@Override
	public List<String> getChildPartColumnData(String columnName){
		List<String> columnData = new ArrayList<String>();
		List<String> columnDataList = null;
		String getChildPartColumnString = "SELECT " + columnName + " FROM child_parts";
		try
		{
			columnDataList = jdbcTemplate.queryForList(getChildPartColumnString, String.class);
			if(columnDataList.size() > 0)
			{
				columnData = columnDataList.stream().map(x->x).collect(Collectors.toList());
			}


		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
		//columnDataList = null;//new ArrayList<>();

		return columnData;

	}


	@Override
	public PartRowData getChildPartData(String partName)
	{
		PartRowData rowData = null;
		String getChildPartRowString = "SELECT * FROM child_parts WHERE name = ?";
		try
		{
			List<PartRowData> childPartDataList = jdbcTemplate.query(getChildPartRowString,new Object[]{partName},
					(rs, rowNum) -> new PartRowData(rs.getString("name"),rs.getString("type"),
							rs.getString("component"),rs.getString("package"),rs.getDouble("x"),rs.getDouble("y")));

			 rowData = childPartDataList.get(0);
		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
		return rowData;

	}


	@Override
	public void clearChildPartData() {
		String clearChildPartsTable = "truncate table child_parts";
		try
		{
			jdbcTemplate.execute(clearChildPartsTable);
		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
		//this.partRowDataList = null;//new ArrayList<>();
	}


}
