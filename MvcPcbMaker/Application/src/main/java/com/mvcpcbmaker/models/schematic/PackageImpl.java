package com.mvcpcbmaker.models.schematic;

import java.util.HashMap;
import java.util.Map;


import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class PackageImpl implements Package {


	private String name;
	private Elements wireBlockList;
	private Elements wireBlockAttrList;
	private Element packageBlock;
	private Map<String,Map<String,Double>> pinMap;
	private double width;
	private double height;
	private int pinCount;


	public PackageImpl()
	{
		this.name = "";
		this.wireBlockList = null;
		this.wireBlockAttrList = null;
		this.packageBlock = null;
		this.pinMap = new HashMap<String,Map<String,Double>>();
		this.width = 0.0;
		this.height = 0.0;
		this.pinCount = 0;
	}

	public PackageImpl(String name)
	{
		this.name = name;
		this.wireBlockList = null;
		this.wireBlockAttrList = null;
		this.packageBlock = null;
		this.pinMap = new HashMap<String,Map<String,Double>>();
		this.width = 0.0;
		this.height = 0.0;
		this.pinCount = 0;
	}

	public PackageImpl(String name, Element packageBlock)
	{
		this.name = name;
		this.packageBlock = packageBlock;
		this.wireBlockList = null;
		this.wireBlockAttrList = null;
		this.pinMap = new HashMap<String,Map<String,Double>>();
		this.width = 0.0;
		this.height = 0.0;
		this.pinCount = 0;
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public void setPackageBlock(Element packageBlock)
	{
		this.packageBlock = packageBlock;
	}

	@Override
	public void setWireBlockList()
	{
		this.wireBlockList = this.packageBlock.getElementsByTag("wire");

		this.wireBlockAttrList = new Elements();
		for(Element wireBlock: this.wireBlockList)
		{
			this.wireBlockAttrList.addAll(wireBlock.getElementsByAttributeValue("layer","39"));
		}


		if(this.wireBlockAttrList.size() == 0)
		{
			for(Element wireBlock: this.wireBlockList)
			{
				this.wireBlockAttrList.addAll(wireBlock.getElementsByAttributeValue("layer","21"));

			}
		}

	}


	@Override
	public void setPinMap()
	{
		Elements smdBlockList = this.packageBlock.getElementsByTag("smd");
		Elements padBlockList = this.packageBlock.getElementsByTag("pad");


		for(Element smdBlock: smdBlockList)
		{
			Map<String,Double> pinCoord = new HashMap<String,Double>();
			pinCoord.put("x", Double.parseDouble(smdBlock.attr("x")));
			pinCoord.put("y", Double.parseDouble(smdBlock.attr("y")));
			this.pinMap.put(smdBlock.attr("name"), pinCoord);
		}
		for(Element padBlock: padBlockList)
		{
			Map<String,Double> pinCoord = new HashMap<String,Double>();
			pinCoord.put("x", Double.parseDouble(padBlock.attr("x")));
			pinCoord.put("y", Double.parseDouble(padBlock.attr("y")));
			this.pinMap.put(padBlock.attr("name"), pinCoord);
		}
		this.pinCount = this.pinMap.size();
	}

	@Override
	public void setSize()
	{
		for(Element wireBlockAttr: this.wireBlockAttrList)
		{
			double width = Math.abs(Double.parseDouble(wireBlockAttr.attr("x1")) - Double.parseDouble(wireBlockAttr.attr("x2")));
			if(width > this.width)
			{
				this.width = width;
			}

			double height = Math.abs(Double.parseDouble(wireBlockAttr.attr("y1"))-Double.parseDouble(wireBlockAttr.attr("y2")));
			if(height > this.height)
			{
				this.height = height;
			}
		}
	}


	@Override
	public Map<String,Object> getPackageData()
	{
		Map<String,Object> packageData = new HashMap<String,Object>();
		packageData.put("name",this.name);
		packageData.put("height",this.height);
		packageData.put("width",this.width);
		packageData.put("pinCount",this.pinCount);

		return packageData;
	}

	@Override
	public Map<String,Double> getPinCoords(String pinName)
	{
		return this.pinMap.get(pinName);
	}


}
