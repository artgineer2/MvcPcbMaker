package com.mvcpcbmaker.utilstructs;

import com.mvcpcbmaker.models.board.BoardImpl;
import com.mvcpcbmaker.utilstructs.BlockUnits;

public class SectionSizeCoords
{
	 public String name;
	 public int width;
	 public int height;
	 public int centerX;
	 public int centerY;
	 private BlockUnits blockUnits = new BlockUnits();



	 public SectionSizeCoords(String name,double width, double height,double centerX, double centerY)
	  {
		  this.name = name;
		  BlockUnits sectionBlockSize = blockUnits.getBlockUnits(width, height);
		  this.width = sectionBlockSize.x;
		  this.height = sectionBlockSize.y;
		  BlockUnits sectionBlockCenter = blockUnits.getBlockUnits(centerX, centerY);
		  this.centerX = sectionBlockCenter.x;
		  this.centerY = sectionBlockCenter.y;
	  }

	 public SectionSizeCoords(String name,int width, int height,int centerX, int centerY)
	  {
		  this.name = name;
		  this.width = width;
		  this.height = height;
		  this.centerX = centerX;
		  this.centerY = centerY;
	  }
	 
	 public Integer getWidthInt()
	 {
		 return Integer.valueOf(this.width);
	 }
	 
	 public Integer getHeightInt()
	 {
		 return Integer.valueOf(this.height);
	 }
}
