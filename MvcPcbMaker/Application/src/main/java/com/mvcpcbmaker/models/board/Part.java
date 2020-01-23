package com.mvcpcbmaker.models.board;

import javax.json.JsonObject;

import com.mvcpcbmaker.utilstructs.CenterPoint;
import com.mvcpcbmaker.utilstructs.BlockUnits;

public interface Part {

	void setPackage(String _package);

	String getName();

	String getType();

	String getPackage();

	BlockUnits getSize();
	int getHeight();
	int getWidth();
	
	void setPartCenterX(int centerX);
	void setPartCenterY(int centerY);
	int getPartCenterX();
	int getPartCenterY();
	JsonObject getPartDataJson();

}