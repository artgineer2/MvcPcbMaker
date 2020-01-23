package com.mvcpcbmaker.utilstructs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.springframework.stereotype.Component;


public class BoardLayout
{
	public String name;
	public List<Integer> columnWidths;
	public int columnCount;
	public BoardColumnSet sectionColumns;
	public int sectionsTotalWidth;
	public int sectionsTotalHeight;
	public int boardWidth;
	public int boardHeight;
	public Map<String,JsonObject> sectionData;

	public BoardLayout()
	{
		this.columnWidths = new ArrayList<>();
		this.sectionColumns = new BoardColumnSet();
		this.sectionData = new HashMap<>();
	}


}
