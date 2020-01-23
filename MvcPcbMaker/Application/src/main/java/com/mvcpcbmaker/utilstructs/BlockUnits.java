package com.mvcpcbmaker.utilstructs;

import org.apache.commons.math3.util.Precision;

public class BlockUnits {

		private final double gridSize = 0.10000000;
		public int x;
		public int y;


	public BlockUnits()
	{

	}

	 public int getBlockUnit(double dim)
	 {

		 return (int)(Math.ceil((dim/this.gridSize)));
	 }

	 public BlockUnits getBlockUnits(double x, double y)
	 {
		 //BlockUnits blockSize = new BlockUnits();

		 this.x = (int)(Math.ceil((x/this.gridSize)));
		 this.y = (int)(Math.ceil((y/this.gridSize)));
		 if(this.x == 0) this.x = 1;
		 if(this.y == 0) this.y = 1;

		 return this;
	 }


	 public int divideBlockUnits1D(int dividend, int divisor)
	 {
		 int result = (int)(Math.ceil((((double)dividend/(double)divisor))));
		 if(result == 0) result = 1;
		 return result;
	 }

	 public int divideIntRound(int dividend, int divisor)
	 {
		 int result = (int)(Math.floor((((double)dividend/(double)divisor))));
		 if(result == 0) result = 1;
		 return result;
	 }

	 public double getDoubleValue(int blockUnits)
	 {
		 return Precision.round(this.gridSize*((double)blockUnits),3);
	 }
}
