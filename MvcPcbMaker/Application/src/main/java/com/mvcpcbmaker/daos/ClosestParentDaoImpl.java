package com.mvcpcbmaker.daos;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
////import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
////import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

@Repository
public class ClosestParentDaoImpl implements ClosestParentDao {


	@Autowired
    private JdbcTemplate jdbcTemplate;
	@Autowired
	SimpleJdbcCall simpleJdbcCall;


	public ClosestParentDaoImpl() {

	}

	@Override
	public void runClosestParentProc(String childPart) {
		try
		{
			simpleJdbcCall.withProcedureName("find_closest_parent");
			SqlParameterSource in = new MapSqlParameterSource().addValue("childPart", childPart);
			simpleJdbcCall.execute(in);
		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}

	}

	@Override
	public String getClosestParentPackage(String parentPart) {
		List<String> parentPackageList;
		String parentPackage = "";
		String getClosestParentPackageString = "SELECT closest_parent_package FROM sch_db_mvc.closest_parent WHERE closest_parent = ?";
		try
		{
			parentPackageList = jdbcTemplate.queryForList(getClosestParentPackageString, new Object[]{parentPart},String.class);
			if(parentPackageList.size() > 0)
			{
				parentPackage = parentPackageList.get(0);
			}

		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
		return parentPackage;
	}

	@Override
	public void clearClosestParentData() {
		String clearClosestParentTable = "truncate table closest_parent";
		try
		{

			int numRows = this.jdbcTemplate.update(clearClosestParentTable);
			System.out.println("ClosestParent truncate: " + numRows);
		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
	}

}
