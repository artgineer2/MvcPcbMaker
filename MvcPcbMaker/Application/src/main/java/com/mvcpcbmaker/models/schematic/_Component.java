package com.mvcpcbmaker.models.schematic;

import java.util.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.mvcpcbmaker.models.schematic.PackageImpl;
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
