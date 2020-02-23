package com.mvcpcbmaker.models.schematic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.mvcpcbmaker.models.schematic._ComponentImpl.ComponentPackage;
import com.mvcpcbmaker.models.schematic._ComponentImpl.Pin;


public class PartImpl implements Part {




	private class Instance
	{
		double x;
		double y;
	}

	private String name;
	private String type;
	private String packageName;
	private String componentName;
	private double x;
	private double y;
	private Map<String,Instance> instanceMap;
	private Map<String,Pin> pinMap;



	 public PartImpl(String name, Element partBlock, Elements instanceBlockList, Map<String,_Component> componentMap)
	 {
		 this.instanceMap = new HashMap <String, Instance>();
		 this.pinMap = new HashMap <String, Pin>();
		try
		{
			this.name = name;

		 	 for(Element instanceBlock: instanceBlockList)
		 	 {
			 	 if(instanceBlock.attr("gate").compareTo("PWR") != 0 && instanceBlock.attr("part").compareTo(this.name) == 0)
			 	 {
			 		Instance inst = new Instance();
			 		inst.x = Double.parseDouble(instanceBlock.attr("x"));
			 		inst.y = Double.parseDouble(instanceBlock.attr("y"));
			 		this.instanceMap.put(instanceBlock.attr("gate"),inst);
			 	 }
		 	 }

		 	_Component componentObject = componentMap.get(partBlock.attr("deviceset"));
		 	Map<String,ComponentPackage> packages = componentObject.getComponentPackages();
		 	String packageKey = partBlock.attr("deviceset")+"_"+partBlock.attr("device");



		 	  this.x = 0.0;
		 	  this.y = 0.0;

			 	  this.componentName = partBlock.attr("deviceset");
			 	  this.packageName = packages.get(packageKey).name;
			 	  this.type = componentObject.getComponentType();
			 	  this.pinMap = packages.get(packageKey).pinMap;
			 	  List<Double> xList = new ArrayList<Double>();
			 	  List<Double> yList = new ArrayList<Double>();

			 	  for(String instanceKey: this.instanceMap.keySet())
			 	  {
			 	 	  xList.add(this.instanceMap.get(instanceKey).x);
			 	 	  yList.add(this.instanceMap.get(instanceKey).y);
			 	  }

			 	  if(xList.size() > 0)
			 	  {
				 	  double sumX = (xList.stream().mapToDouble(x -> x).sum());
				 	  double sumY = (yList.stream().mapToDouble(y -> y).sum());
				 	  int xListSize = xList.size();
				 	  int yListSize = yList.size();
				 	  this.x = sumX/xListSize;
				 	  this.y = sumY/yListSize;
			 	  }


		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("name: " + this.name);
		}


	 }



	@Override
	public Map<String,Object> getPartData()
	 {
			Map<String,Object> partData = new HashMap<String,Object>();
			partData.put("name",this.name);
			partData.put("type",this.type);
			partData.put("component",this.componentName);
			partData.put("package",this.packageName);
			partData.put("x",this.x);
			partData.put("y",this.y);

			return partData;

	 }

	@Override
	 public Map<String,Double> getPartCoords()
	 {
		 Map<String,Double> partCoords = new HashMap<String,Double>();
		 partCoords.put("x",this.x);
		 partCoords.put("y",this.y);
		 return partCoords;
	 }

	@Override
	 public Map<String,Double> getPinNumCoords(String pinNum)
	 {
		 Map<String,Double> pinNumCoords = new HashMap<String,Double>();
		 pinNumCoords.put("x",this.pinMap.get(pinNum).x);
		 pinNumCoords.put("y",this.pinMap.get(pinNum).y);

		 return pinNumCoords;
	 }

	 @Override
	 public Map<String,Double> getPinNameCoords(String pinName)
	 {
		 Map<String,Double> pinNumCoords = new HashMap<String,Double>();
		 pinNumCoords.put("x",0.0);
		 pinNumCoords.put("y",0.0);

	 	  for(String pinNum: this.pinMap.keySet())
	 	  {
	 	 	  Pin pin = this.pinMap.get(pinNum);
 	 	 	  if(pin.pinName.compareTo(pinName) == 0)
 	 	 	  {
 	 			 pinNumCoords.put("x",pin.x);
 	 			 pinNumCoords.put("y",pin.y);
 	 			 break;
	 	 	  }
	 	  }

	 	  return pinNumCoords;
	 }



}
