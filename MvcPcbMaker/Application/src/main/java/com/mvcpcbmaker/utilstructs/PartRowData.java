package com.mvcpcbmaker.utilstructs;

public class PartRowData { 
	public String name;
	public String type;
	public String component;
	public String _package;
	public double x; 
	public double y;
	
	public PartRowData(String name, String type, String component, String _package, double x, double y)
	{
		this.name = name;
		this.type = type;
		this.component = component;
		this._package = _package;
		this.x = x;
		this.y = y;
	}

}
