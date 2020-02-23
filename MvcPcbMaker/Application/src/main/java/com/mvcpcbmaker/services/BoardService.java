package com.mvcpcbmaker.services;

import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvcpcbmaker.daos.ClosestParentDao;
import com.mvcpcbmaker.daos.NetDao;
import com.mvcpcbmaker.daos.PackageDao;
import com.mvcpcbmaker.daos.PartDao;
import com.mvcpcbmaker.daos.SectionsDao;
import com.mvcpcbmaker.models.board.Board;
import com.mvcpcbmaker.utilstructs.PartRowData;

@Service
public final class BoardService
{
		private String name;
		private JsonObject boardDataJsonBuilt = null;
		private JsonObject boardLayoutJson = null;
		private JsonObjectBuilder boardDataJson = null;


		@Autowired
		 Board board;
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





	  public BoardService()
	  {

	  }

	  public JsonObject createBoardLayout(String name, int columns)
	  {
		  this.name = name;
	 	  this.boardDataJsonBuilt = null;
	 	  this.boardLayoutJson = null;


		  this.boardDataJson = Json.createObjectBuilder();

		  try
		  {
		 	  this.boardDataJson.add("name", this.name);
		 	  JsonArrayBuilder sectionArrayDataJson = Json.createArrayBuilder();
		 	  for(String sectionTableKey: sectionTables.getSectionNames())
		 	  {
		 			JsonObjectBuilder sectionDataJson = Json.createObjectBuilder();
		 		 try
		 		 {
			 	 	  String parentName = sectionTableKey.split("_")[0];
			 	 	  String parentPackage = this.sectionTables.getSectionParentPackage(sectionTableKey);

			 	 	  List<String> childNameList = this.sectionTables.getSectionColumnData(sectionTableKey,"child_part");
			 	 	  Map<String,Double> parentPackageSize = this.packageTable.getPackageSizeData(parentPackage);

			 	 	  int childListSize = childNameList.size();
			 	 	  JsonArrayBuilder childPartArrayDataJson = Json.createArrayBuilder();

			 	 	  for(int i = 0; i < childListSize; i++)
			 	 	  {

			 	 		  try
			 	 		  {
			 	 			  String childPartName = childNameList.get(i);
			 	 			  PartRowData childPartData = this.partTable.getChildPartData(childPartName);
				 	 		  JsonObjectBuilder childRowDataJson = Json.createObjectBuilder();
				 	 			childRowDataJson.add("childPart",childPartName);
				 	 			childRowDataJson.add("childPartType", childPartData.type);
				 	 			Map<String,Double> childPackageSize = this.packageTable.getPackageSizeData(childPartData._package);
				 	 			childRowDataJson.add("childPartWidth", childPackageSize.get("width"));
				 	 			childRowDataJson.add("childPartHeight", childPackageSize.get("height"));
				 	 			JsonObject tempRowJson = childRowDataJson.build();
				 	 			childPartArrayDataJson.add(tempRowJson);

			 	 		  }
			 	 		  catch(Exception e)
			 	 		  {
							  System.out.print("childPartDataJson level error: ");
							  e.printStackTrace();
			 	 		  }
			 	 	  }
			 	 	  sectionDataJson.add("parentPart", parentName);
			 	 	  sectionDataJson.add("parentPackage", parentPackage);
			 	 	  sectionDataJson.add("parentPartWidth", parentPackageSize.get("width"));
				 	  sectionDataJson.add("parentPartHeight", parentPackageSize.get("height"));

			 	 	  sectionDataJson.add("sectionName", sectionTableKey);
			 	 	  JsonArray tempPartJson = childPartArrayDataJson.build();
			 	 	  sectionDataJson.add("childPartDataArray", tempPartJson);
		 		 }
		 		 catch(Exception e)
		 		 {
					  System.out.print("sectionDataJson level error: ");
					  e.printStackTrace();
		 		 }
		 		 sectionArrayDataJson.add(sectionDataJson.build());
		 	  }

		 	  this.boardDataJson.add("sectionArrayData", sectionArrayDataJson.build());
		 	  boardDataJsonBuilt = this.boardDataJson.build();
		 	  board.initBoard(this.name, columns, boardDataJsonBuilt);
		 	  this.boardLayoutJson = this.board.getBoardLayout();


		  }
		  catch(Exception e)
		  {
			  System.out.print("PlaceRouteServices::createBrdJson level error: ");
			  e.printStackTrace();
		  }



	 	  return boardLayoutJson;
	  }

	  public void cleanOutObjectData()
	  {
		  try
		  {
				boardDataJsonBuilt = null;
				boardLayoutJson = null;
				boardDataJson = null;

		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }

	  }


}
