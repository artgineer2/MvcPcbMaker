package com.mvcpcbmaker.models.schematic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import com.mvcpcbmaker.models.schematic.Net;
import com.mvcpcbmaker.models.schematic.Part;
/*import com.mvcpcbmaker.models.schematic
import com.mvcpcbmaker.models.schematic
import com.mvcpcbmaker.models.schematic
import com.mvcpcbmaker.models.schematic
import com.mvcpcbmaker.models.schematic*/


public class NetImpl implements Net {

	private String strArray[] = {"VCC","VDD","VSS","5V","12V","18V","15V","24V","GND",
			"0V","V+","V-","+3V","+1V","+2V","P+"};
	private Set<String> devicesetExcludeList = new HashSet<String>(Arrays.asList(strArray)); 

	private class NetPart
	{
		String pinName;
		double x;
		double y;
	}
	
	private Map<String,NetPart> connectedParts;
	private String name;
	
	
	public NetImpl(String name, Element netBlock, Map<String,Part> partMap)
	{
		this.connectedParts = new HashMap<String,NetPart>();
		this.name = name;
	 	Elements pinrefBlockList = netBlock.getElementsByTag("pinref");
	 	//System.out.println(netBlock.toString());
	 	for(Element pinrefBlock: pinrefBlockList)
	 	{
	 		
	 		NetPart part = new NetPart();
	 		boolean pass = false;
	 		boolean netNamePass = false;
	 		boolean partNamePass = false;
	 		String partString = pinrefBlock.attr("part");
	 		//System.out.print("\n"+partString+"::");
	 		for(String excludeString : devicesetExcludeList)
	 		{
	 			if(partString.indexOf(excludeString) >= 0)
				{
					pass = true; 
					break;
				}
	 		}
	 		if(pass == true) continue;
	 		for(String excludeString : devicesetExcludeList)
	 		{
	 			if(this.name.indexOf(excludeString) >= 0)
				{
					pass = true; 
					break;
				}
	 		}
	 		if(pass == true) continue;
	 	 	//System.out.println(pinrefBlock.attr("part").toString()); 	  
	 	 	  Part partObject = partMap.get(pinrefBlock.attr("part"));
	 	 	 //System.out.println(this.name+": "+pinrefBlock.toString());
	 	 	  
	 	 	  if(pinrefBlock.attr("gate").compareTo("G$1") == 0)
	 	 	  {
	 	 		part.pinName = pinrefBlock.attr("pin");
	 	 	  }
	 	 	  else
	 	 	  {
	 	 		part.pinName = pinrefBlock.attr("gate") + '.' + pinrefBlock.attr("pin");
	 	 	  }
	 	 	 	  
	 	 	  Map<String,Double> partCoords = partObject.getPinNameCoords(part.pinName);
	 	 	  part.x = partCoords.get("x");
	 	 	  part.y = partCoords.get("y");
	 	 	  //System.out.println(pinrefBlock.attr("part")+"::"+this.name +":"+part.pinName+":"+ part.x+","+part.y);
	 	 	  this.connectedParts.put(pinrefBlock.attr("part"), part);

	 	}
	}
	
 	public List<Map<String,Object>> getNetData()
 	{
 		List<Map<String,Object>> netData = new ArrayList<Map<String,Object>>();
 		for(String partName : this.connectedParts.keySet())
 		{
 			Map<String,Object> netPart = new HashMap<String,Object>();
 			netPart.put("name",this.name);
 			netPart.put("part",partName);
 			netPart.put("pinName",this.connectedParts.get(partName).pinName);
 			netPart.put("x",this.connectedParts.get(partName).x);
 			netPart.put("y",this.connectedParts.get(partName).y);
 			netData.add(netPart);
 		}
		
		
		return netData;
 	}


}
