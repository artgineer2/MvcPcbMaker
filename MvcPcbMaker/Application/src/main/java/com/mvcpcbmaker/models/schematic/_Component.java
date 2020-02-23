package com.mvcpcbmaker.models.schematic;

import java.util.Map;


import org.jsoup.nodes.Element;

import com.mvcpcbmaker.models.schematic._ComponentImpl.ComponentPackage;

public interface _Component  {

	 public void setName(String name);
	 public void setType(String type);
	 public void setComponentPackages(Element componentBlock, Map<String,Package> packageMap);
	 public Map<String,Object> getComponentData();
	 public String getComponentType();
	 public ComponentPackage getComponentPackage(String packageName);
	 public Map<String,ComponentPackage>  getComponentPackages();
}
