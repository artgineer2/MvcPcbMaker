package com.mvcpcbmaker.models.board;

import com.mvcpcbmaker.models.board.ChildPartImpl.GridBlockBoundaries;
import com.mvcpcbmaker.utilstructs.CenterPoint;

public interface ChildPart extends Part{

	void setGridBlockCenterPoint(int x, int y);
	void setGridBlockBoundaries(int x1, int x2, int y1, int y2);

	void setGridBlockCenterPointX(int value);
	void setGridBlockCenterPointY(int value);
	void setGridBlockBoundaryPoint(String point, int value);
	CenterPoint getGridBlockCenterPoint();
	GridBlockBoundaries getGridBlockBoundaries();
}
