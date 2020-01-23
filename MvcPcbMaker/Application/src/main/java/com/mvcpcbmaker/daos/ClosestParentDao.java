package com.mvcpcbmaker.daos;


public interface ClosestParentDao {
	public void runClosestParentProc(String childPart);
	public String getClosestParentPackage(String parentPart);
	public void clearClosestParentData();

}
