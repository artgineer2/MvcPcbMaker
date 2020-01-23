package com.mvcpcbmaker.utilstructs;

public class CenterPoint extends BlockUnits
	{
		public int centerX;
		public int centerY;

		public CenterPoint()
		{
			this.centerX = 0;
			this.centerY = 0;
		}
		public CenterPoint(double x, double y)
		{
			BlockUnits centerPoint = this.getBlockUnits(x, y);
			this.centerX = centerPoint.x;
			this.centerY = centerPoint.y;
		}
		
		public CenterPoint(int x, int y)
		{
			this.centerX = x;
			this.centerY = y;
		}

		
	}
