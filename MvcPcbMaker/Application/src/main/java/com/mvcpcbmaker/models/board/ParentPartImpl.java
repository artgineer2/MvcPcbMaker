package com.mvcpcbmaker.models.board;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;


public class ParentPartImpl extends PartImpl implements ParentPart
{



	public ParentPartImpl()
	{
		super();
	}

	public ParentPartImpl(String name, String type, double height, double width)
	{
		super(name,type,height,width);
	}

	public ParentPartImpl(String name, String type, String _package, double height, double width)
	{
		super(name,type,_package,height,width);
	}



	@Override
	public JsonObject getPartDataJson()
	{
		JsonObjectBuilder partDataObject = Json.createObjectBuilder();

		partDataObject.add("name", this.name);
		partDataObject.add("height", this.height);
		partDataObject.add("width", this.width);
		partDataObject.add("centerX", this.blockUnits.getDoubleValue(this.getPartCenterX()));
		partDataObject.add("centerY", this.blockUnits.getDoubleValue(this.getPartCenterY()));

		return partDataObject.build();
	}





}
