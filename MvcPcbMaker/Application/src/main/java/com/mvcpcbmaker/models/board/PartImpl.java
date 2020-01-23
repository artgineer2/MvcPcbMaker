package com.mvcpcbmaker.models.board;

import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.mvcpcbmaker.utilstructs.CenterPoint;
import com.mvcpcbmaker.utilstructs.BlockUnits;

public class PartImpl implements Part
{

	
	protected String name;
	protected String type;
	protected double height;
	protected double width;
	protected int blockHeight;
	protected int blockWidth;
	 
	protected String _package;
	protected BlockUnits blockUnits;
	private int partCenterX;
	private int partCenterY;

	
	
	public PartImpl()
	{
		this.name = "";
		this.type = "";
		this._package = "";
		this.height = 0.0;
		this.width = 0.0;
		this.blockWidth = 0;
		this.blockHeight = 0;
		this.blockUnits = new BlockUnits();
	}
	public PartImpl(String name, String type, double height, double width)
	{
		this.name = name;
		this.type = type;
		this._package = "";
		this.height = height;
		this.width = width;
		this.blockUnits = new BlockUnits();

		this.blockUnits = blockUnits.getBlockUnits(width, height);
		this.blockWidth = this.blockUnits.x;
		this.blockHeight = this.blockUnits.y;
	}
	
	public PartImpl(String name, String type, String _package, double height, double width)
	{
		this.name = name;
		this.type = type;
		this._package = _package;
		this.height = height;
		this.width = width;
		this.blockUnits = new BlockUnits();
		this.blockUnits = blockUnits.getBlockUnits(width, height);
		this.blockWidth = this.blockUnits.x;
		this.blockHeight = this.blockUnits.y;
		
	}
	
	@Override
	public void setPackage(String _package)
	{
		this._package = _package;
	}
	
	
		
	@Override
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public String getType()
	{
		return this.type;
	}
	
	@Override
	public String getPackage()
	{
		return this._package;
	}
	
	
	@Override
	public BlockUnits getSize()
	{
		BlockUnits blockSize = new BlockUnits();
		blockSize.x = this.blockWidth;
		blockSize.y = this.blockHeight;
		return blockSize;
	}
	
	@Override
	public int getHeight() {
		
		return this.blockHeight;
	}
	@Override
	public int getWidth() {
		
		return this.blockWidth;
	}

	@Override
	public void setPartCenterX(int centerX)
	{
		this.partCenterX = centerX;
	}
	
	@Override
	public void setPartCenterY(int centerY)
	{
		this.partCenterY = centerY;
	}
	
	@Override
	public int getPartCenterX()
	{
		return this.partCenterX;
	}
	
	@Override
	public int getPartCenterY()
	{
		return this.partCenterY;
	}

	
	
	
	@Override
	public JsonObject getPartDataJson() {
		
		return null;
	}

	
	

}