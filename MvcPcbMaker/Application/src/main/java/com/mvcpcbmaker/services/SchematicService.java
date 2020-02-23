package com.mvcpcbmaker.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvcpcbmaker.daos.ClosestParentDao;
import com.mvcpcbmaker.daos.NetDao;
import com.mvcpcbmaker.daos.PackageDao;
import com.mvcpcbmaker.daos.PartDao;
import com.mvcpcbmaker.daos.SectionsDao;
import com.mvcpcbmaker.models.schematic.Schematic;
import com.mvcpcbmaker.utilstructs.PartRowData;

@Service
public final class SchematicService
{

	private String schematicString;
	private List<String> parentPartList;
	private List<String> childPartList;
	private List<Map<String,Object>> packageData;
	private List<Map<String,Object>> partData;
	private List<Map<String,Object>> netData;

	@Autowired
	Schematic schematic;
	@Autowired
	PackageDao packageTable;
	@Autowired
	PartDao partTable;
	@Autowired
	NetDao netTable;
	@Autowired
	ClosestParentDao closestParentTable;
	@Autowired
	SectionsDao sectionTables;

	public SchematicService()
	{

	}

	public void createSchematicMaps(String fileName, String fileData)
	{
		this.schematicString = fileData;

		try
		{
			schematic.initSchematic(this.schematicString);

			this.packageData = schematic.getPackageTable();
			this.partData = schematic.getPartTable();
			this.netData = schematic.getNetTable();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void createPrimaryDBTables()
	{
		try {
			packageTable.addPackageData(packageData);

			partTable.addPartData(partData);
			netTable.addNetData(netData);
			partTable.createParentPartsTable();
			partTable.createChildPartsTable();
			this.parentPartList = this.partTable.getParentPartColumnData("name");

			this.childPartList = this.partTable.getChildPartColumnData("name");


			try
			{
				for(String childPart : this.childPartList)
				{
					closestParentTable.runClosestParentProc(childPart);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void createSectionTables()
	{
		try
		{

			for(String parentPart: this.parentPartList)
			{
				PartRowData parentData = partTable.getParentPartData(parentPart);

				try
				{
					sectionTables.addSectionTable(parentPart, parentData._package);
				}
				catch(Exception e)
				{
					System.out.println("parentPart: " + parentPart +":"+ parentData._package);
					e.printStackTrace();
				}
			}


			System.out.println("databases filled");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void cleanOutObjectData()
	{
		this.schematic.cleanOutObjectData(); // clear all data not connected to database
		  parentPartList = new ArrayList<>();
		 childPartList = new ArrayList<>();
		  packageData = new ArrayList<>();
		  partData = new ArrayList<>();
		  netData = new ArrayList<>();
	}
	public void cleanOutDatabase()
	{
		packageTable.clearPackageData();
		partTable.clearPartData();
		partTable.clearParentPartData();
		partTable.clearChildPartData();
		netTable.clearNetData();
		closestParentTable.clearClosestParentData();
		sectionTables.dropSectionTables();
	}

}
