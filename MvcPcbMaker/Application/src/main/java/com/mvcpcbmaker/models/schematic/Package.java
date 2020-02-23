package com.mvcpcbmaker.models.schematic;

import java.util.Map;

import org.jsoup.nodes.Element;

public interface Package {
	public void setName(String name);
	public void setPackageBlock(Element packageBlock);
	public void setWireBlockList();
	public void setPinMap();
	public void setSize();
	public Map<String,Object> getPackageData();
	public Map<String,Double> getPinCoords(String pinName);

}
