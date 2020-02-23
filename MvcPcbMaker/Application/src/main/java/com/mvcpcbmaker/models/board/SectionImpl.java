package com.mvcpcbmaker.models.board;


import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import org.springframework.stereotype.Component;

import com.mvcpcbmaker.utilstructs.*;
import com.mvcpcbmaker.utilstructs.SectionSizeCoords;


@Component
public class SectionImpl implements Section{


	private final int parentChildBuffer = 20;
	private final int parentBorderBuffer = 40;
	private final int childBorderBuffer = 40;
	private String name;
	private int width;
	private int height;
	private int centerX;
	private int centerY;
	private ChildPart[] childParts;
	private int childPartCount;
	private ParentPartImpl parentPart;
	private  int parentPartSides;
	private int childGridCellSize;		 // in BlockUnits
	private int childPartGridRowCellCount;		// row width or number of columns
	private int childPartGridColumnCellCount; //column length
	private int sectionVerticalBlockCount;
	private int sectionHorizontalBlockCount;
	private  ChildPart[][][] childPartsGrid;
	private BlockUnits blockUnits;

	public SectionImpl()
	{
		try
		{
			this.centerX = 0;
			this.centerY = 0;


			this.childParts = null;
			this.parentPart = new ParentPartImpl();
			this.sectionVerticalBlockCount = 0;
			this.sectionHorizontalBlockCount = 0;
			this.blockUnits = new BlockUnits();

		}
		catch(Exception e)
		{
			System.out.print("Section constructor error: ");
			e.printStackTrace();
		}
	}


	public SectionImpl(String name, JsonObject sectionData)
	{
		this.blockUnits = new BlockUnits();
		this.name = name;
		this.childPartCount = sectionData.getJsonArray("childPartDataArray").size();
		this.childParts = new ChildPartImpl[this.childPartCount];
		int i = 0;
		for(JsonValue childPartData : sectionData.getJsonArray("childPartDataArray"))
		{
			JsonObject childPartDataObject = (JsonObject)childPartData;
			ChildPart childPart = new ChildPartImpl(childPartDataObject.getString("childPart"),
					childPartDataObject.getString("childPartType"),
					childPartDataObject.getJsonNumber("childPartHeight").doubleValue(),
					childPartDataObject.getJsonNumber("childPartWidth").doubleValue());
			this.childParts[i++] = childPart;
		}

		this.parentPart  = new ParentPartImpl(
				this.name.split("_")[0],
				sectionData.getString("parentPackage"),
				(sectionData.getJsonNumber("parentPartHeight")).doubleValue(),
				(sectionData.getJsonNumber("parentPartWidth")).doubleValue());

		this.setChildGridBlockSize();
		if(this.childPartCount > 0)
		{
			this.setChildGridPreLayoutData();
		}

		this.setSectionSizeandGrid();




	}

	private void setChildGridBlockSize()
	{

		int maxChildWidth = 0;
		int maxChildHeight = 0;


		{
			for(ChildPart childPart : this.childParts)
			{
				if(childPart.getType().compareTo("Q") != 0)
				{
					BlockUnits partSize = childPart.getSize();
					if(maxChildWidth < partSize.x)
					{
						maxChildWidth = partSize.x;
					}
					if(maxChildHeight < partSize.y)
					{
						maxChildHeight = partSize.y;
					}
				}
			}

			this.childGridCellSize =  (maxChildWidth > maxChildHeight) ? maxChildWidth: maxChildHeight;
		}
	}


	private void setChildPartGridArrayStructure(int sides, int row, int column)
	{
		this.parentPartSides = sides;
		this.childPartsGrid = new ChildPartImpl[sides][row][column];
		try
		{
			int childPartIndex = 0;

			for(int s = 0; s < this.parentPartSides ; s++)
			{
				for(int r = 0; r < this.childPartGridRowCellCount ; r++)
				{
					for(int c = 0; c < this.childPartGridColumnCellCount ; c++)
					{
						if(childPartIndex < this.childPartCount)
						{
							this.childPartsGrid[s][r][c] = this.childParts[childPartIndex];

							childPartIndex++;
						}
						else
						{
							this.childPartsGrid[s][r][c] = new ChildPartImpl();
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.print("Section getSectionData error: ");
			e.printStackTrace();
		}

	}

	private void setChildGridPreLayoutData()
	{

		/***********set grid side, row, and column counts***************/

		this.parentPartSides = 2;
		if(this.parentPart.getPackage().contains("QFP"))
		{
			this.parentPartSides += 2;
		}
		int childPartParentSideCellCount = this.blockUnits.divideBlockUnits1D(this.childPartCount,this.parentPartSides);

		BlockUnits parentPartSize = this.parentPart.getSize();

		this.childPartGridColumnCellCount = this.blockUnits.divideBlockUnits1D(parentPartSize.y,this.childGridCellSize);
		if(this.childPartGridColumnCellCount <= 0) this.childPartGridColumnCellCount = 1;
		this.childPartGridRowCellCount =
				this.blockUnits.divideBlockUnits1D(childPartParentSideCellCount,this.childPartGridColumnCellCount);

		if(this.childPartGridRowCellCount >= 3)
		{
			double adjustmentFactor = (double)childPartGridRowCellCount/3.00;
			this.childPartGridColumnCellCount = (int)(Math.ceil(adjustmentFactor * this.childPartGridColumnCellCount));
			this.childPartGridRowCellCount =
					this.blockUnits.divideBlockUnits1D(childPartParentSideCellCount,this.childPartGridColumnCellCount);
		}
		this.setChildPartGridArrayStructure(this.parentPartSides,
				this.childPartGridRowCellCount, this.childPartGridColumnCellCount);
	}

	private void setSectionSizeandGrid()
	{

		int childBlockGridVerticalCount = 0;
		int parentPartVerticalCount = 0;

		if(this.parentPartSides == 4)
		{
			this.sectionVerticalBlockCount = this.parentPart.getHeight()
					+ (2*this.childPartGridRowCellCount*this.childGridCellSize);
		}
		else
		{
			parentPartVerticalCount = this.parentPart.getHeight();
			childBlockGridVerticalCount = this.childPartGridColumnCellCount*this.childGridCellSize;
			if(parentPartVerticalCount > childBlockGridVerticalCount)
			{
				this.sectionVerticalBlockCount = parentPartVerticalCount;
			}
			else
			{
				this.sectionVerticalBlockCount = childBlockGridVerticalCount;
			}
		}
		this.sectionHorizontalBlockCount =
				this.parentPart.getWidth() + (2*this.childPartGridRowCellCount*this.childGridCellSize);


		if(this.childPartCount > 0)
		{
			this.width =
					this.sectionHorizontalBlockCount
					+ 2*(this.childBorderBuffer + this.parentChildBuffer);
			this.height =
					this.sectionVerticalBlockCount
					+ 2*(this.childBorderBuffer + this.parentChildBuffer);
		}
		else
		{
			BlockUnits parentSize = this.parentPart.getSize();
			this.width =
					parentSize.x + 2*this.parentBorderBuffer;
			this.height =
					parentSize.y + 2*this.parentBorderBuffer;
		}

	}






	@Override
	public SectionSizeCoords getSectionSizeCoord()
	{
		return new SectionSizeCoords(this.name, this.width, this.height, this.centerX, this.centerY);
	}

	@Override
	public void setSectionCenterPoint(int x, int y)
	{
		this.centerX = x;
		this.centerY = y;
		this.parentPart.setPartCenterX(x);
		this.parentPart.setPartCenterY(y);


		if(this.childPartCount > 0)
		{
			this.setChildGridLayout();
			this.setChildPartGridCenterPoints();
		}
	}
	/******* These methods must wait until the Board class/object has set the section centerpoint ***********/

	private void setChildGridLayout()
	{
		/************* Set child grid physical cell layout ******************/

		for(int i = 0; i < this.parentPartSides; i++)
		{
			for(int j = 0; j < this.childPartGridRowCellCount; j++)
			{
				for(int k = 0; k < this.childPartGridColumnCellCount; k++)
				{

					int x1 = this.parentPart.getPartCenterX()-this.parentPart.getWidth()/2 - this.parentChildBuffer - j*this.childGridCellSize;
					int x2 = this.parentPart.getPartCenterX()-this.parentPart.getWidth()/2 - this.parentChildBuffer - (j+1)*this.childGridCellSize;
					int y1 = this.parentPart.getPartCenterY()-this.parentPart.getHeight()/2 + k*this.childGridCellSize;
					int y2 = this.parentPart.getPartCenterY()-this.parentPart.getHeight()/2 + (k+1)*this.childGridCellSize;
					this.childPartsGrid[i][j][k].setGridBlockBoundaries(x1,x2,y1,y2);
				}
			}
		}
	}

	private void setChildPartGridCenterPoints()
	{

		int childPartIndex = 0;
		for(int s = 0; s < this.parentPartSides ; s++)
		{
			for(int r = 0; r < this.childPartGridRowCellCount ; r++)
			{
				for(int c = 0; c < this.childPartGridColumnCellCount ; c++)
				{
					if(childPartIndex < this.childPartCount)
					{
						this.setChildPartGridCenterPoint(s, r, c);

						childPartIndex++;
					}
					else
					{
						this.setChildPartGridCenterPoint(s, r, c);
					}
				}
			}
		}

	}

	private void setChildPartGridCenterPoint(int side, int row, int column)
	{

		int centerX = 0;
		int centerY = 0;
		BlockUnits parentSize = this.parentPart.getSize();

		int yColumnStartOffset = 1*(1-(this.childPartGridColumnCellCount%2));

		if(this.childPartGridRowCellCount%2 == 0) // even number of rows
		{

			switch(side)
			{
			case 0:
				centerX = this.centerX-(this.parentPart.getWidth()/2)
						-this.parentChildBuffer
						- this.childGridCellSize*(1+row);

				centerY = this.centerY+
						this.childGridCellSize*(
								(this.childPartGridColumnCellCount)/2-column
								)-(yColumnStartOffset*this.childGridCellSize)/2;

				break;
			case 1:
				centerX = this.centerX+(this.parentPart.getWidth()/2)
					+this.parentChildBuffer+ this.childGridCellSize*(1+row);

				centerY = this.centerY+
						this.childGridCellSize*(
								(this.childPartGridColumnCellCount)/2-column
								)-(yColumnStartOffset*this.childGridCellSize)/2;
				break;
			case 2:
				centerX = this.centerX-this.childGridCellSize*(
						(this.childPartGridRowCellCount-1/2)-column
						);
				centerY = this.centerY-(parentSize.y/2)
						-this.parentChildBuffer-this.childGridCellSize
						- this.childGridCellSize*row;
				break;
			case 3:
				centerX = this.centerX+this.childGridCellSize*(
						(this.childPartGridRowCellCount-1/2)-column
						);
				centerY = this.centerY+(parentSize.y/2)
						+this.parentChildBuffer
						+this.childGridCellSize*(1 + row);

				break;
			default:;
			}
		}
		else
		{
			switch(side)
			{
			case 0:
				centerX = this.centerX-(this.parentPart.getWidth()/2)
				-this.parentChildBuffer
				- this.childGridCellSize*(1 + row);

				centerY = this.centerY+this.childGridCellSize*(
						(this.childPartGridColumnCellCount)/2-column
						)-(yColumnStartOffset*this.childGridCellSize)/2;
				break;
			case 1:
				centerX = this.centerX+(this.parentPart.getWidth()/2)
						+this.parentChildBuffer
						+this.childGridCellSize*(1 + row);

				centerY = this.centerY+this.childGridCellSize*(
						(this.childPartGridColumnCellCount)/2-column
						)-(yColumnStartOffset*this.childGridCellSize)/2;
				break;
			case 2:
				centerX = this.centerX-this.childGridCellSize*(
						(this.childPartGridRowCellCount/2)-column
						);

				centerY = this.centerY-(parentSize.y/2)
						-this.parentChildBuffer
						-this.childGridCellSize*(1 + row);
				break;
			case 3:
				centerX = this.centerX+this.childGridCellSize*(
						(this.childPartGridRowCellCount/2)-column
						);

				centerY = this.centerY+(parentSize.y/2)
						+this.parentChildBuffer
						+this.childGridCellSize*(1 + row);

				break;
			default:;
			}
		}
		this.childPartsGrid[side][row][column].setGridBlockCenterPoint(centerX, centerY);

	}


	@Override
	public JsonObject getSectionDataJson()
	{
		JsonObjectBuilder sectionDataObject = Json.createObjectBuilder();
		try
		{

			JsonArrayBuilder sideArray = Json.createArrayBuilder();
			for(int s = 0; s < this.parentPartSides; s++)
			{
				JsonArrayBuilder rowArray = Json.createArrayBuilder();
				for(int r = 0; r < this.childPartGridRowCellCount ; r++)
				{
					JsonArrayBuilder columnArray = Json.createArrayBuilder();
					for(int c = 0; c < this.childPartGridColumnCellCount ; c++)
					{
						columnArray.add(this.childPartsGrid[s][r][c].getPartDataJson());
					}
					rowArray.add(columnArray.build());
				}
				sideArray.add(rowArray.build());
			}


			BlockUnits parentSize = this.parentPart.getSize();


			sectionDataObject.add("name",this.name);
			sectionDataObject.add("centerX",this.blockUnits.getDoubleValue(this.centerX));
			sectionDataObject.add("centerY",this.blockUnits.getDoubleValue(this.centerY));
			sectionDataObject.add("width",this.blockUnits.getDoubleValue(this.width));
			sectionDataObject.add("height",this.blockUnits.getDoubleValue(this.height));
			sectionDataObject.add("childBlockSize",this.blockUnits.getDoubleValue(this.childGridCellSize));
			sectionDataObject.add("sectionVerticalBlockCount",this.sectionVerticalBlockCount);
			sectionDataObject.add("sectionHorizontalBlockCount",this.sectionHorizontalBlockCount);
			sectionDataObject.add("parentPartName", this.parentPart.getName());
			sectionDataObject.add("parentPartWidth",this.blockUnits.getDoubleValue(parentSize.x));
			sectionDataObject.add("parentPartHeight",this.blockUnits.getDoubleValue(parentSize.y));
			sectionDataObject.add("parentPartSides",parentPartSides);
			sectionDataObject.add("childPartGridRowCellCount",this.childPartGridRowCellCount);
			sectionDataObject.add("childPartGridColumnCellCount",this.childPartGridColumnCellCount);
			sectionDataObject.add("childPartsGrid",sideArray.build());

		}
		catch(Exception e)
		{
			System.out.print("Section getSectionData error: ");
			e.printStackTrace();
		}

		return sectionDataObject.build();
	}



}
