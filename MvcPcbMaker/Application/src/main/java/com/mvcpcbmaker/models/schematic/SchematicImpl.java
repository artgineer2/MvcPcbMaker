package com.mvcpcbmaker.models.schematic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.mvcpcbmaker.models.schematic._Component;
import com.mvcpcbmaker.models.schematic.Package;
import com.mvcpcbmaker.models.schematic.Part;
import com.mvcpcbmaker.models.schematic.Net;



@Component
public class SchematicImpl implements Schematic{

	private String strArray[] = {"AGND","VCC","AVCC","VDD","VSS","+3V3A","+5V","GND"};
	private Set<String> devicesetExcludeList;

	private Map<String,Package> packageMap;
	private Map<String,_Component> componentMap;
	private Map<String,Part> partMap;
	private Map<String,Net> netMap;

	private List<Map<String,Object>> packageTableData;
	private List<Map<String,Object>> partTableData;
	private List<Map<String,Object>> netTableData;
	private Document schDoc;



	public SchematicImpl()
	{
		this.devicesetExcludeList = new HashSet<String>(Arrays.asList(strArray));
	}

	@Override
	public void initSchematic(String schString)
	{

		this.packageMap = new HashMap<String,Package>();
		this.componentMap = new HashMap<String,_Component>();
		this.partMap = new HashMap<String,Part>();
		this.netMap = new HashMap<String,Net>();

		this.packageTableData = new ArrayList<Map<String,Object>>();
		this.partTableData = new ArrayList<Map<String,Object>>();
		this.netTableData = new ArrayList<Map<String,Object>>();

		this.schDoc = Jsoup.parse(schString, "", Parser.xmlParser());

		Elements libraryBlockList = this.schDoc.getElementsByTag("library");
		for(Element libraryBlock: libraryBlockList)
		{
			Elements packageBlockList = libraryBlock.getElementsByTag("package");
			for(Element packageBlock: packageBlockList)
			{
				String name = packageBlock.attr("name").replace("'","*");
				Package pkg = new PackageImpl(name,packageBlock);
				pkg.setWireBlockList();
				pkg.setPinMap();
				pkg.setSize();
				this.packageMap.put(name,pkg);

				Package temp = this.packageMap.get(name);
				Map<String,Object> tempMap = temp.getPackageData();
				this.packageTableData.add(tempMap);
			}

			Elements componentBlockList = libraryBlock.getElementsByTag("deviceset");
			for(Element componentBlock: componentBlockList)
			{
				if(this.devicesetExcludeList.contains(componentBlock.attr("name")) == false)
				{
					String name = componentBlock.attr("name");
					_Component comp = new _ComponentImpl(name,componentBlock,this.packageMap);
					comp.setComponentPackages(componentBlock, this.packageMap);
					this.componentMap.put(name ,comp);
				}

			}
		}

		Elements partBlockList = this.schDoc.getElementsByTag("part");
		Elements instanceBlockList = this.schDoc.getElementsByTag("instance");
		for(Element partBlock : partBlockList)
		{
			if(partBlock.attr("library").compareTo("supply1") != 0)
			{
				String name = partBlock.attr("name");
				this.partMap.put(name, new PartImpl(name,partBlock,instanceBlockList,this.componentMap));
				this.partTableData.add(this.partMap.get(name).getPartData());
			}
		}

		Elements netBlockList = this.schDoc.getElementsByTag("net");
		for(Element netBlock: netBlockList)
		{
			String name = netBlock.attr("name");
			this.netMap.put(name, new NetImpl(name,netBlock, this.partMap));
			if(this.netTableData.isEmpty())
			{
				this.netTableData = this.netMap.get(name).getNetData();
			}
			else
			{
				this.netTableData.addAll(this.netMap.get(name).getNetData());
			}
		}
	}


	@Override
	public	List<Map<String,Object>> getPackageTable()
	{
		return this.packageTableData;
	}


	@Override
	public	List<Map<String,Object>> getPartTable()
	{
		return this.partTableData;
	}

	@Override
	public List<Map<String,Object>> getNetTable()
	{
		return this.netTableData;
	}

	@Override
	public void cleanOutObjectData()
	{
		this.packageMap = null;
		this.componentMap = null;
		this.partMap = null;
		this.netMap = null;


		 this.packageTableData = null;
		 this.partTableData = null;
		 this.netTableData = null;

		this.schDoc = null;

	}


}
