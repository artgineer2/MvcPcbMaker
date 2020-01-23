package com.mvcpcbmaker.models.schematic;
////package com.pcbplaceroute.schematic;


import java.util.Map;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.mvcpcbmaker.models.schematic.PackageImpl;

@Component
public class _ComponentImpl implements _Component{



	 //devicesetExcludeList = ['AGND','VCC','AVCC','VDD','VSS','+3V3A','+5V','GND']
	 //signalExcludeList = ['AGND','VCC','AVCC','VDD','VSS','+3V3A','VMID']
	 //parentPartTypeList = ['IC','T']
	 //childPartLibraryList = ['rcl','SMTRArray','jumper']


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

	//@Component
	public class ComponentPackage
	{
		public String name;
		public Map<String,Pin> pinMap;
		ComponentPackage()
		{
			this.name = "";
			this.pinMap = new HashMap<String,Pin>();
		}
		/*ComponentPackage(String name, Map<String,Pin> pinMap)
		{
			this.name = name;
			this.pinMap = pinMap;
		}*/
	}

	private String name;
	private String type;
	private Elements connectBlockList;
	private Map<String,Package> packageMap;
	private Map<String,ComponentPackage> packages;


	public _ComponentImpl()
	{
		this.name = "";
		this.type = "";
		this.connectBlockList = null;
		this.packageMap = new HashMap<String,Package>();
		this.packages = new HashMap<String,ComponentPackage>();
	}
	public _ComponentImpl(String name, String type)
	{
		this.name = name;
		this.type = type;
		this.connectBlockList = null;
		this.packageMap = new HashMap<String,Package>();
		this.packages = new HashMap<String,ComponentPackage>();
	}

	/*public _ComponentImpl(String name, Element componentBlock, Map<String,Package> packageMap)
	{
		this.name = name;
		this.type = "";
		
		this.connectBlockList = componentBlock;
		this.packageMap = new HashMap<String,Package>();
		this.packages = new HashMap<String,ComponentPackage>();
	}*/
	
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
		this.packageMap = packageMap;
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
		String component = componentBlock.attr("name");
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
	
	
	/*@Override
	public void initComponent(String name, Element componentBlock, Map<String,Package> packageMap)
	{
		

		this.name = name;
		this.type = componentBlock.attr("prefix");
		String component = componentBlock.attr("name");
	 	Elements deviceBlockList = componentBlock.getElementsByTag("device");
	 	for(Element deviceBlock: deviceBlockList)
	 	{
	 	 	 if(deviceBlock.attributes().hasKey("package"))
	 	 	 {
		 	 	 String componentPackageKey = "";
		 	 	 ComponentPackage componentPackageValue = new ComponentPackage();
		 	 	componentPackageKey = componentBlock.attr("name")+"_"+deviceBlock.attr("name");
		 	 	//System.out.println("componentPackageKey: "+componentPackageKey);
		 	 	 componentPackageValue.name = deviceBlock.attr("package").replace("'","*");

		 	 	 Elements connectBlockList = deviceBlock.getElementsByTag("connect");
		 	 	 Package packageObject = packageMap.get(componentPackageValue.name);
		 	 	 for(Element connectBlock: connectBlockList)
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
		 	 	 		//System.out.println(this.name+":"+pin.pinName);
			 	 	 	pin.x = pinCoords.get("x");
		 	 	 		//System.out.println(pin.x);
			 	 	 	pin.y = pinCoords.get("y");
		 	 	 		//System.out.println(pin.y);
		 	 	 	 }
			 	 	 componentPackageValue.pinMap.put(connectBlock.attr("pad"),pin);
		 	 	 }
		 	 	 this.packages.put(componentPackageKey, componentPackageValue);

	 	 	 }
	 	}
	}*/

	
	
	
	 
	//@Override
	  public Map<String,Object> getComponentData()
	  {
			Map<String,Object> componentData = new HashMap<String,Object>();
			componentData.put("name",this.name);
			componentData.put("type",this.type);
			componentData.put("package",this.packages);
			return componentData;
	  }


	  //@Override
	 public String getComponentType()
	 {
		 return this.type;
	 }
	 	 //
	// @Override
	 public ComponentPackage getComponentPackage(String packageName)
	 {
		 return this.packages.get(packageName);
	 }
	 	 //

	// @Override
	 public Map<String,ComponentPackage>  getComponentPackages()
	 {
		 return this.packages;
	 }

//



}
