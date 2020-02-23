package com.mvcpcbmaker.models.board;

import javax.json.JsonObject;

import com.mvcpcbmaker.utilstructs.SectionSizeCoords;

public interface Section {
	public SectionSizeCoords getSectionSizeCoord();
	public void setSectionCenterPoint(int x, int y);
	JsonObject getSectionDataJson();
}
