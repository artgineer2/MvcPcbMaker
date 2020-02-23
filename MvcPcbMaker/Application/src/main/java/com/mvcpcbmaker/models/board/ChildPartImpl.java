package com.mvcpcbmaker.models.board;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.mvcpcbmaker.utilstructs.CenterPoint;

public class ChildPartImpl extends PartImpl implements ChildPart {

	public class GridBlockBoundaries
	{
		public int x1;
		public int x2;
		public int y1;
		public int y2;

		GridBlockBoundaries()
		{
			this.x1 = 0;
			this.y1 = 0;
			this.x2 = 0;
			this.y2 = 0;
		}

		GridBlockBoundaries(int x1, int x2, int y1, int y2)
		{
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}


	}

	private int gridBlockCenterX;
	private int gridBlockCenterY;
	private GridBlockBoundaries blockBoundaries;

	public ChildPartImpl() {
		super();
		this.blockBoundaries = new GridBlockBoundaries();
		this.gridBlockCenterX = 0;
		this.gridBlockCenterY = 0;


	}

	public ChildPartImpl(String name, String type, double height, double width)
	{
		super(name,type,height,width);
		this.blockBoundaries = new GridBlockBoundaries();

	}

	public ChildPartImpl(String name, String type, String _package, double height, double width)
	{
		super(name,type,_package,height,width);
		this.blockBoundaries = new GridBlockBoundaries();
	}




	@Override
	public void setGridBlockCenterPoint(int x, int y) {
		this.gridBlockCenterX = x;
		this.gridBlockCenterY = y;

	}

	@Override
	public void setGridBlockCenterPointX(int value)
	{
		this.gridBlockCenterX = value;
	}
	@Override
	public void setGridBlockCenterPointY(int value)
	{
		this.gridBlockCenterY = value;
	}


	@Override
	public void setGridBlockBoundaries(int x1, int x2, int y1, int y2) {
		this.blockBoundaries.x1 = x1;
		this.blockBoundaries.x2 = x2;
		this.blockBoundaries.y1 = y1;
		this.blockBoundaries.y2 = y2;
	}

	@Override
	public void setGridBlockBoundaryPoint(String point, int value) {

		switch(point)
		{
		case "x1":
			this.blockBoundaries.x1 = value;
			break;
		case "x2":
			this.blockBoundaries.x2 = value;
			break;
		case "y1":
			this.blockBoundaries.y1 = value;
			break;
		case "y2":
			this.blockBoundaries.y2 = value;
			break;
		default:;
		}

	}


	@Override
	public CenterPoint getGridBlockCenterPoint()
	{
		CenterPoint centerPoint = new CenterPoint(this.gridBlockCenterX,this.gridBlockCenterY);
		return centerPoint;
	}

	@Override
	public GridBlockBoundaries getGridBlockBoundaries()
	{
		return null;
	}



	@Override
	public JsonObject getPartDataJson()
	{
		JsonObjectBuilder partDataObject = Json.createObjectBuilder();

		partDataObject.add("name", this.name);
		partDataObject.add("height", this.height);
		partDataObject.add("width", this.width);
		partDataObject.add("centerX", this.blockUnits.getDoubleValue(this.gridBlockCenterX));
		partDataObject.add("centerY", this.blockUnits.getDoubleValue(this.gridBlockCenterY));
		partDataObject.add("blockBoundaryX1", this.blockUnits.getDoubleValue(this.blockBoundaries.x1));
		partDataObject.add("blockBoundaryX2", this.blockUnits.getDoubleValue(this.blockBoundaries.x2));
		partDataObject.add("blockBoundaryY1", this.blockUnits.getDoubleValue(this.blockBoundaries.y1));
		partDataObject.add("blockBoundaryY2", this.blockUnits.getDoubleValue(this.blockBoundaries.y2));
		partDataObject.add("package", this._package);

		return partDataObject.build();
	}
}
