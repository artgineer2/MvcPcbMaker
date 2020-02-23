package com.mvcpcbmaker.models.schematic;


import java.util.Map;

import java.util.HashMap;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;


@Component
public class _ComponentImpl implements _Component{


	public class Pin
	{
		public String pinName;
		public double x;
		public double y;
		Pin(){}
		Pin(String pinName, double x, double y)
		{
			this.pinName = pinName;
			this.x = x;
			this.y = y;
		}
	}

	public class ComponentPackage
	{
		public String name;
		public Map<String,Pin> pinMap;
		ComponentPackage()
		{
			this.name = "";
			this.pinMap = new HashMap<String,Pin>();
		}
	}

	private String name;
	private String type;
	private Elements connectBlockList;
	private Map<String,ComponentPackage> packages;


	public _ComponentImpl()
	{
		this.name = "";
		this.type = "";
		this.connectBlockList = null;
		this.packages = new HashMap<String,ComponentPackage>();
	}
	public _ComponentImpl(String name, String type)
	{
		this.name = name;
		this.type = type;
		this.connectBlockList = null;
		this.packages = new HashMap<String,ComponentPackage>();
	}

	public _ComponentImpl(String name, Element componentBlock, Map<String,Package> packageMap)
	{
		this.name = name;
		this.type = componentBlock.attr("prefix");
	 	Elements deviceBlockList = componentBlock.getElementsByTag("device");
	 	for(Element deviceBlock: deviceBlockList)
	 	{
	 	 	 if(deviceBlock.attributes().hasKey("package"))
	 	 	 {
	 	 		this.connectBlockList = deviceBlock.getElementsByTag("connect");
	 	 	 }
	 	}
		this.packages = new HashMap<String,ComponentPackage>();
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public void setType(String type)
	{
		this.type = type;
	}

	@Override
	public void setComponentPackages(Element componentBlock, Map<String,Package> packageMap)
	{

		this.type = componentBlock.attr("prefix");
	 	Elements deviceBlockList = componentBlock.getElementsByTag("device");
	 	for(Element deviceBlock: deviceBlockList)
	 	{
	 	 	 if(deviceBlock.attributes().hasKey("package"))
	 	 	 {
		 	 	 String componentPackageKey = "";
		 	 	 ComponentPackage componentPackageValue = new ComponentPackage();
		 	 	 componentPackageKey = componentBlock.attr("name")+"_"+deviceBlock.attr("name");
		 	 	 componentPackageValue.name = deviceBlock.attr("package").replace("'","*");

		 	 	 this.connectBlockList = deviceBlock.getElementsByTag("connect");
		 	 	 Package packageObject = packageMap.get(componentPackageValue.name);
		 	 	 for(Element connectBlock: this.connectBlockList)
		 	 	 {
		 	 		 Pin pin = new Pin();
		 	 	 	 Map<String,Double> pinCoords = packageObject.getPinCoords(connectBlock.attr("pad"));

		 	 	 	 if(connectBlock.attr("gate") == "G$1")
		 	 	 	 {
		 	 	 		pin.pinName = connectBlock.attr("pin");
		 	 	 		pin.x = pinCoords.get("x");
		 	 	 		pin.y = pinCoords.get("y");
		 	 	 	 }
		 	 	 	 else
		 	 	 	 {
		 	 	 	 	pin.pinName = connectBlock.attr("gate") + '.' + connectBlock.attr("pin");
			 	 	 	pin.x = pinCoords.get("x");
			 	 	 	pin.y = pinCoords.get("y");

		 	 	 	 }
			 	 	 componentPackageValue.pinMap.put(connectBlock.attr("pad"),pin);
		 	 	 }
		 	 	 this.packages.put(componentPackageKey, componentPackageValue);

	 	 	 }
	 	}
	}

	@Override
	  public Map<String,Object> getComponentData()
	  {
			Map<String,Object> componentData = new HashMap<String,Object>();
			componentData.put("name",this.name);
			componentData.put("type",this.type);
			componentData.put("package",this.packages);
			return componentData;
	  }

	 @Override
	 public String getComponentType()
	 {
		 return this.type;
	 }

	@Override
	 public ComponentPackage getComponentPackage(String packageName)
	 {
		 return this.packages.get(packageName);
	 }

	@Override
	 public Map<String,ComponentPackage>  getComponentPackages()
	 {
		 return this.packages;
	 }
}
