package com.mvcpcbmaker.utilstructs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;


public class BoardColumnSet
{
	private int columnCount;
	private int totalSectionHeight;
	private List<SectionSizeCoords> sectSizeCoordList;
	private List<SectionSizeCoords> sortedSectSizeCoordList;
	private List<List<SectionSizeCoords>> boardColumns;
	private List<Integer> brdSectColHeightSums;
	private List<Integer> columnWidthMaxs;
	private int columnHeightMax;
	private int columnWidthSum;
	private BlockUnits blockUnits;

	public BoardColumnSet()
	{
		this.totalSectionHeight = 0;
		this.sectSizeCoordList = new ArrayList<SectionSizeCoords>();
		this.sortedSectSizeCoordList  = new ArrayList<SectionSizeCoords>();
		this.boardColumns = new ArrayList<List<SectionSizeCoords>>();
		for(int i = 0; i < this.columnCount; i++)
		{
			this.boardColumns.add(new ArrayList<SectionSizeCoords>());
		}
		this.brdSectColHeightSums = new ArrayList<Integer>();
		this.columnWidthMaxs = new ArrayList<Integer>();
		this.blockUnits = new BlockUnits();
		this.columnHeightMax = 0;
		this.columnWidthSum = 0;
	}

	public BoardColumnSet(int columnCount)
	{
		this.totalSectionHeight = 0;
		this.columnCount = columnCount;
		this.sectSizeCoordList = new ArrayList<SectionSizeCoords>();
		this.sortedSectSizeCoordList  = new ArrayList<SectionSizeCoords>();
		this.boardColumns = new ArrayList<List<SectionSizeCoords>>();
		for(int i = 0; i < this.columnCount; i++)
		{
			this.boardColumns.add(new ArrayList<SectionSizeCoords>());
		}
		this.brdSectColHeightSums = new ArrayList<Integer>();
		this.columnWidthMaxs = new ArrayList<Integer>();
		this.blockUnits = new BlockUnits();
		this.columnHeightMax = 0;
		this.columnWidthSum = 0;

	}

	public void setColumnSectionCountAndDivisor(int sectionCount, int divisor)
	{
		this.columnCount = divisor;
	}

	public void setAndSortSectionSizeCoordList(Map<String, SectionSizeCoords> sectSizeCoordsData)
	{
	 	  for(String sectSizeKey : sectSizeCoordsData.keySet())
	 	  {
	 		  SectionSizeCoords temp = sectSizeCoordsData.get(sectSizeKey);
	 		 sectSizeCoordList.add(new SectionSizeCoords(temp.name, temp.width, temp.height, temp.centerX, temp.centerY));
	 	  }

	 	 sortedSectSizeCoordList = sectSizeCoordList.stream().sorted((ss1, ss2)->
	 	 	ss2.getHeightInt().compareTo(ss1.getHeightInt())).collect(Collectors.toCollection(ArrayList::new));
	}

	public void createColumns()
	{
	 	 int sectionCount = sortedSectSizeCoordList.size();
	 	 int sectionIndex = 0;
	 	  int exitCount = 0;
	 	  while(sectionIndex < sectionCount && exitCount < 50)
	 	  {
	 	 	  for(int i = 0; i < this.columnCount && sectionIndex < sectionCount; i++)
	 	 	  {
	 	 		SectionSizeCoords tempSect = sortedSectSizeCoordList.get(sectionIndex);
	 	 		sectionIndex++;
	 	 		this.boardColumns.get(i).add(tempSect);
	 	 		exitCount++;
	 	 	  }
	 	  }
	}


	public List<SectionSizeCoords> getColumn(int index)
	{
		return this.boardColumns.get(index);
	}

	public List<List<SectionSizeCoords>> getColumns()
	{
		return  this.boardColumns;
	}

	public void setColumns(List<List<SectionSizeCoords>> columns)
	{
		this.boardColumns = columns;
	}


	public void setColumnHeightMaxAndSum()
	{
		try
		{
			this.brdSectColHeightSums = new ArrayList<Integer>();
			for(int i = 0; i < this.columnCount; i++)
			{
				int colHeightSum = this.boardColumns.get(i).stream().mapToInt(x->x.height).sum();
				this.brdSectColHeightSums.add(colHeightSum);
				this.totalSectionHeight += colHeightSum;
			}
			this.columnHeightMax = this.brdSectColHeightSums.stream().mapToInt(x -> x).max().getAsInt();
		}
		catch(Exception e)
		{
			 System.out.print("BoardColumnSet setColumnHeightMaxAndSum error: ");
			e.printStackTrace();
		}
	}

	public void setColumnWidthMaxAndSum()
	{
		try
		{
			this.columnWidthSum = 0;
			this.columnWidthMaxs = new ArrayList<Integer>();
			for(int i = 0; i < this.columnCount; i++)
			{
				int colWidthMax = this.boardColumns.get(i).stream().mapToInt(x -> x.width).max().getAsInt();
				this.columnWidthMaxs.add(colWidthMax);
				this.columnWidthSum += colWidthMax;
			}
		}
		catch(Exception e)
		{
			 System.out.print("BoardColumnSet setColumnWidthMaxAndSum error: ");
			e.printStackTrace();
		}
	}



	public void setColumnSectionCenterX(int columnIndex, int sectionIndex, int x)
	{
		this.boardColumns.get(columnIndex).get(sectionIndex).centerX = x;
	}

	public void setColumnSectionCenterY(int columnIndex, int sectionIndex, int y)
	{
		this.boardColumns.get(columnIndex).get(sectionIndex).centerY = y;
	}

	public int getColumnHeightMax()
	{
		return this.columnHeightMax;
	}

	public int getColumnWidthSum()
	{
		return this.columnWidthSum;
	}

	public int getColumnHeightAverage()
	{
		return (totalSectionHeight/this.brdSectColHeightSums.size());
	}

	public List<Integer> getColumnWidths()
	{
		return this.columnWidthMaxs;
	}

	public JsonArray getBoardSectSizeDataJson()
	{
		JsonArrayBuilder columnArrayDataJson = Json.createArrayBuilder();
		for(List<SectionSizeCoords> column : this.boardColumns)
		{
			JsonArrayBuilder sectArrayDataJson = Json.createArrayBuilder();
			for(SectionSizeCoords section : column)
			{
				JsonObjectBuilder sectJson = Json.createObjectBuilder();

				sectJson.add("name",section.name);
				sectJson.add("width",this.blockUnits.getDoubleValue(section.width));
				sectJson.add("height",this.blockUnits.getDoubleValue(section.height));
				sectJson.add("centerX",this.blockUnits.getDoubleValue(section.centerX));
				sectJson.add("centerY",this.blockUnits.getDoubleValue(section.centerY));
				sectArrayDataJson.add(sectJson.build());
			}
			columnArrayDataJson.add(sectArrayDataJson.build());
		}
		return columnArrayDataJson.build();
	}

	public void cleanOutObjectData()
	{
		  sectSizeCoordList = null;
		  sortedSectSizeCoordList = null;
		  boardColumns = null;
		  brdSectColHeightSums = null;
		  columnWidthMaxs = null;
	}
}
