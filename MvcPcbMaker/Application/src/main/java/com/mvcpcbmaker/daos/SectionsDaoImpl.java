package com.mvcpcbmaker.daos;

//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.lang.String;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mvcpcbmaker.utilstructs.PartRowData;
import com.mvcpcbmaker.utilstructs.SectionRowData;

@Repository
public class SectionsDaoImpl implements SectionsDao {

	
	private Map<String,Map<String,String>> sectionParentDataMap; 
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	

	public SectionsDaoImpl()
	{
		this.sectionParentDataMap = new HashMap<String,Map<String,String>>();
	}
	
	@Override
	public void addSectionTable(String parentPart, String parentPackage) 
	{
		Map<String,String> sectionParentData = new HashMap<String,String>();
		sectionParentData.put("part",parentPart);
		sectionParentData.put("package",parentPackage);
		String sectName = parentPart + "_Section";
		this.sectionParentDataMap.put(sectName, sectionParentData);
		
		String createSectionTableString = "CREATE TABLE " + sectName + "(child_part varchar(20),child_package varchar(20),closest_parent_part varchar(20),closest_parent_package varchar(20))";
		String addSectionTableString = "INSERT INTO " + sectName + " SELECT * FROM closest_parent WHERE closest_parent_part = ?";

		try
		{					
			jdbcTemplate.execute(createSectionTableString);

			this.jdbcTemplate.update(addSectionTableString, new Object[]{parentPart});
		}
		catch(Exception Ex)
		{
			System.out.println("SectionsDaoImpl::addSection error: " + sectName);
			Ex.printStackTrace();
		}
		
		//sectionParentData = new HashMap<>();

	}
	
	@Override
	public List<String> getSectionNames()
	{
		List<String> sectionNames = this.sectionParentDataMap.keySet().stream().collect(Collectors.toList());
		return sectionNames;
	}
	
	
	
	@Override
	public String getSectionParentPackage(String sectName)
	{
		return this.sectionParentDataMap.get(sectName).get("package");
	}
	
	
	
	@Override
	public List<String> getSectionColumnData(String sectionName, String columnName){
		List<String> columnData = new ArrayList<String>();
		//class ColumnDataList { List<String> columnData; }
		List<String> tempColumnData;
		String getSectionColumnString = "SELECT " + columnName + " FROM " + sectionName;
		try
		{
			tempColumnData = jdbcTemplate.queryForList(getSectionColumnString, String.class);
			if(tempColumnData.size() > 0)
			{
				columnData = tempColumnData;
			}
		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
		return columnData;

	}
	
	
	@Override
	public SectionRowData getSectionRowData(String sectionName, String childPartName)
	{
		SectionRowData sectionRowData = null;
		
		String getSectionRowString = "SELECT * FROM " + sectionName+ " WHERE child_part = ?";
		try
		{
			sectionRowData = jdbcTemplate.queryForObject(getSectionRowString,new Object[]{childPartName},SectionRowData.class);//,
					/*(rs, rowNum) -> new SectionRowData(rs.getString("child_part"),rs.getString("child_package"),
							rs.getString("parent_part"),rs.getString("parent_package")));*/
			
			
		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
		return sectionRowData;

	}

	@Override
	public void dropSectionTables() {
		
		List<String> tableNames = jdbcTemplate.queryForList("show tables",String.class);
		List<String> sectNames = tableNames.stream().filter(t -> t.contains("_Section")).collect(Collectors.toList());
		
		for(String sectName: sectNames)
		{
			try
			{
				String clearSectionTable = "drop table if exists " + sectName;
				jdbcTemplate.execute(clearSectionTable);				
			}
			catch(Exception Ex)
			{
				Ex.printStackTrace();
			}
						
		}
		
		this.sectionParentDataMap = new HashMap<>();
		
	}

}
