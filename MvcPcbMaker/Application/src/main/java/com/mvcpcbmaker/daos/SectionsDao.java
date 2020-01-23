package com.mvcpcbmaker.daos;

import java.util.List;

import com.mvcpcbmaker.utilstructs.SectionRowData;

public interface SectionsDao {
	public void addSectionTable(String parentPart, String parentPackage);
	public List<String> getSectionNames();
	public String getSectionParentPackage(String sectName);
	public List<String> getSectionColumnData(String sectionName, String columnName);
	public SectionRowData getSectionRowData(String sectionName, String childPartName);
	public void dropSectionTables();

}
