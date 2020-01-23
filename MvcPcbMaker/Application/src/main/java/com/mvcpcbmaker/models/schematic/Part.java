package com.mvcpcbmaker.models.schematic;

import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface Part {
	public Map<String,Object> getPartData();
	public Map<String,Double> getPartCoords();
	 public Map<String,Double> getPinNumCoords(String pinNum);
	 public Map<String,Double> getPinNameCoords(String pinName);
}
