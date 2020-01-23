package com.mvcpcbmaker.models.board;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;
import java.lang.Thread;
import javax.json.*;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvcpcbmaker.daos.ClosestParentDao;
import com.mvcpcbmaker.daos.NetDao;
import com.mvcpcbmaker.daos.PackageDao;
import com.mvcpcbmaker.daos.PartDao;
import com.mvcpcbmaker.daos.SectionsDao;
import com.mvcpcbmaker.utilstructs.CenterPoint;
import com.mvcpcbmaker.utilstructs.BlockUnits;
import com.mvcpcbmaker.utilstructs.BoardColumnSet;
import com.mvcpcbmaker.utilstructs.SectionSizeCoords;
import com.mvcpcbmaker.utilstructs.BoardLayout;

@Component
public class BoardImpl implements Board{



	private String name;
	private int totalSectionHeight;
	private int sectionCount;
	private static final int boardEdgeBuffer = 40;
	private Map<String,SectionImpl> sections;
	private Map<String,SectionSizeCoords> sectionSizes;
	private BoardColumnSet boardColumnSet;
	private BoardLayout boardLayout;
	private BlockUnits blockUnits;
	private int columnCount;
	private int sectionsTotalWidth;
	private int sectionsMaxHeight;

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




	public BoardImpl()
	{

	}

	@Override
	 public void initBoard(String name, int columns, JsonObject boardData)
	 {
		 this.sections  = new HashMap<>();
		 this.sectionSizes = new HashMap<>();
		 this.boardLayout = new BoardLayout();
		 try
		 {
			 this.name = boardData.getString("name");
			 this.totalSectionHeight = 0;
			 this.sectionCount = 0;
			 this.columnCount = columns;


			 // Extract variables and data structures from JsonObject

			 JsonArray sectionsDataJson = boardData.getJsonArray("sectionArrayData");
			 this.sectionCount = sectionsDataJson.size();
			 for(JsonValue sectionDataJson: sectionsDataJson)
			 {
				 JsonObject tempJson = ((JsonObject)sectionDataJson);
				 String sectionName = tempJson.getString("parentPart") + "_Section";
				 this.sections.put(sectionName, new SectionImpl(sectionName,tempJson));
				 this.totalSectionHeight += this.sections.get(sectionName).getSectionSizeCoord().height;
				 this.sectionSizes.put(sectionName, this.sections.get(sectionName).getSectionSizeCoord());
			 }

		 	  //this.createBoardColumnSetMap();
			  this.createBoardColumnSet(this.columnCount);
		 	  //this.optimizeBoardColumnHeights();
		 	  this.setBoardLayoutBoardData();

			  this.setBoardSectionCenterPoints();
			  this.setBoardLayoutSectionData();

		 }
		 catch(Exception e)
		 {
			  System.out.print("Board constructor error: ");
			  e.printStackTrace();

		 }
	 }


	 private void createBoardColumnSet(int columnCount)
	 {

	 	 this.boardColumnSet = new BoardColumnSet(this.sectionCount,columnCount);

	 	 try
	 	 {
 			this.boardColumnSet.setAndSortSectionSizeCoordList(this.sectionSizes);
	 		this.boardColumnSet.createColumns();
 	 	    this.boardColumnSet.setColumnHeightMaxAndSum();
 	 	    this.boardColumnSet.setColumnWidthMaxAndSum();
	 	 }
	 	 catch(Exception e)
	 	 {
			  System.out.print("Board createBoardColumnMap error: ");
			  e.printStackTrace();
	 	 }
	 }



	 	 // # bring each board section height as close to sectionHeightAverage as possbile by popping smallest sections from higher sections and putting them on short section
//
	 private void optimizeBoardColumnHeights()
	 {

	 	  /*for(int divisor : this.boardColumnSets.keySet())
	 	  {
	 		 BoardColumnSet brdColSet = this.boardColumnSets.get(divisor);
 	 	 	 double colHgtAve = brdColSet.brdColHeightAverage;
 	 	 	 while(true)
 	 	 	 {
	 	 	 	  for(int i = 0; i < divisor; i++)
	 	 	 	  {
 	 	 	 	 	  if(brdColSet.brdSectColHeightSums.get(i) > colHgtAve)
 	 	 	 	 	  {

	 	 	 	 		SectionSizeCoords poppedSection = brdColSet.brdSectSizeCols.get(i).pop();
 	 	 	 	 		brdColSet.brdSectSizeCols.get(divisor-1).push(poppedSection);

 	 	 	 	 	  }
	 	 	 	  }
 	 	 	 	 if((brdColSet.brdSectSizeCols.get(divisor-1).stream().mapToDouble(x -> x.height).sum()) > 0.85*colHgtAve)
 	 	 	 	 {
 	 	 	 		 break;
 	 	 	 	 }
 	 	 	 }

	 	  }*/
	 }
//
	 private void setBoardLayoutBoardData()
	 {

	 	  this.blockUnits = new BlockUnits();
	 	  try
	 	  {
			  //for(int divisor : this.boardColumnSets.keySet())
			  {

		 	 	//post-optimization
		 	 	//this.boardColumnSets.get(5).setColumnHeightMaxAndSum();
		 	 	//this.boardColumnSets.get(5).setColumnWidthMaxAndSum();

			  }

			 this.boardLayout.sectionColumns.setColumnSectionCountAndDivisor(this.sectionCount, this.columnCount);

		 	  this.boardLayout.name = this.name;
		 	  this.boardLayout.columnCount =  this.columnCount;
		 	  //this.boardLayout.sectionColumns.setColumns(this.boardColumnSets.get(5).getColumns());
		 	  this.boardLayout.sectionColumns.setColumns(this.boardColumnSet.getColumns());
		 	  this.boardLayout.sectionColumns.setColumnHeightMaxAndSum();
		 	  this.boardLayout.sectionColumns.setColumnWidthMaxAndSum();
		 	  //this.boardLayout.columnWidths = this.boardColumnSets.get(5).getColumnWidths();
		 	  this.boardLayout.columnWidths = this.boardColumnSet.getColumnWidths();
		 	  this.boardLayout.sectionsTotalWidth = this.boardLayout.sectionColumns.getColumnWidthSum();
		 	  this.boardLayout.sectionsTotalHeight = this.boardLayout.sectionColumns.getColumnHeightMax();

		 	  this.boardLayout.boardWidth = this.boardLayout.sectionsTotalWidth + 2*this.boardEdgeBuffer;
		 	  this.boardLayout.boardHeight = this.boardLayout.sectionsTotalHeight + 2*this.boardEdgeBuffer;

	 	  }
	 	  catch(Exception e)
	 	  {
			  System.out.print("Board setBoardLayout error: ");
			  e.printStackTrace();
	 	  }


	 }

	 private void setBoardSectionCenterPoints()
	 {
	 	  List<Integer> columnCenterLines = new ArrayList<>();
	 	  int currentCenterLine = this.boardEdgeBuffer;
	 	  //int columnCount =  this.boardLayout.columnCount;

	 	  for(int i = 0; i < this.boardLayout.columnCount; i++)
	 	  {
	 	 	  if(i == 0)
	 	 	  {
	 	 		currentCenterLine += this.boardLayout.columnWidths.get(i)/2;

	 	 	  }
	 	 	  else
	 	 	  {
	 	 		currentCenterLine +=
	 	 				(this.boardLayout.columnWidths.get(i-1)/2)
	 	 				+ (this.boardLayout.columnWidths.get(i)/2);
	 	 	  }
	 	 	  columnCenterLines.add(currentCenterLine);
	 	  }

	 	  int sectionIndex = 0;
	 	  for(int i = 0; i < this.boardLayout.columnCount; i++)
	 	  {
	 		  List<SectionSizeCoords> column = this.boardLayout.sectionColumns.getColumn(i);
	 	 	  int centerY = this.boardEdgeBuffer;
	 	 	  int centerX = columnCenterLines.get(sectionIndex);
	 	 	  for(int j = 0; j < column.size(); j++)
	 	 	  {
	 	 		  SectionSizeCoords section = column.get(j);
	 	 	 	  int halfHeight = (section.height/2);
	 	 	 	  if(sectionIndex == 0)
	 	 	 	  {
	 	 	 		centerY += halfHeight;
	 	 	 	  }
	 	 	 	  else
	 	 	 	  {
	 	 	 		centerY += halfHeight;
				  }

	 	 	 	 this.sections.get(section.name).setSectionCenterPoint( centerX, centerY);
	 	 	 	  centerY += halfHeight;
	 	 	  }
	 	 	  sectionIndex++;

	 	  }
	 }
	 private void setBoardLayoutSectionData()
	 {
	 	  try
	 	  {
		 	  for(List<SectionSizeCoords> column : this.boardLayout.sectionColumns.getColumns())
		 	  {
		 	 	  for(SectionSizeCoords section : column)
		 	 	  {

		 	 		JsonObject tempData = this.sections.get(section.name).getSectionDataJson();
		 	 		this.boardLayout.sectionData.put(section.name,tempData);
		 	 	  }
		 	  }
	 	  }
	 	  catch(Exception e)
	 	  {
			  System.out.print("Board setBoardLayout error: ");
			  e.printStackTrace();
	 	  }
	 }



	 @Override
	 public JsonObject getBoardLayout()
	 {
		 JsonObjectBuilder boardLayoutJson = Json.createObjectBuilder();

		 try
		 {

		 	JsonArrayBuilder columnWidthsJson = Json.createArrayBuilder();
		 	for(int columnWidth : this.boardLayout.columnWidths)
		 	{
		 		columnWidthsJson.add(this.blockUnits.getDoubleValue(columnWidth));
		 	}

		 	JsonObjectBuilder sectionDataJson = Json.createObjectBuilder();
		 	for(String sectionName : this.boardLayout.sectionData.keySet())
		 	{
		 		sectionDataJson.add(sectionName, (JsonValue)this.boardLayout.sectionData.get(sectionName));
		 	}
		 	boardLayoutJson.add("name", this.boardLayout.name);
		 	boardLayoutJson.add("columnWidths", columnWidthsJson.build());
		 	boardLayoutJson.add("columnCount", this.boardLayout.columnCount);
		 	boardLayoutJson.add("sectionColumns", this.boardLayout.sectionColumns.getBoardSectSizeDataJson());
			boardLayoutJson.add("sectionsTotalWidth", this.blockUnits.getDoubleValue(this.boardLayout.sectionsTotalWidth));
		 	boardLayoutJson.add("sectionsTotalHeight", this.blockUnits.getDoubleValue(this.boardLayout.sectionsTotalHeight));
			boardLayoutJson.add("boardWidth", this.blockUnits.getDoubleValue(this.boardLayout.boardWidth));
		 	boardLayoutJson.add("boardHeight", this.blockUnits.getDoubleValue(this.boardLayout.boardHeight));
		 	boardLayoutJson.add("sectionData", (JsonValue)sectionDataJson.build());


		 }
		 catch(Exception e)
		 {
			  System.out.print("Board getBoardLayout error: ");
			  e.printStackTrace();
		 }
	 	  return boardLayoutJson.build();

	 }
		@Override
		public void cleanOutObjectData()
		{
			this.sections = null;
			this.sectionSizes = null;
			 boardColumnSet = null;
			  boardLayout = null;

		}



//
//

}
