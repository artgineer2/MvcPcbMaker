package utilStructs;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import com.mvcpcbmaker.utilstructs.BoardColumnSet;
import com.mvcpcbmaker.utilstructs.SectionSizeCoords;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class BoardColumnSetTest
{



	private static BoardColumnSet brdColSet;
	private static Map<String, SectionSizeCoords> sectSizeCoordsData;




	@BeforeAll
	static void setupTest()
	{

		brdColSet = new BoardColumnSet(4);
		sectSizeCoordsData = new HashMap<String, SectionSizeCoords>();

		List<double[]> sizeCoordData = new ArrayList<double[]>();

		double randomNumberWidthVariation = 0.0;
		double randomNumberHeightVariation = 0.0;
		for(int i = 0; i < 12; i++)
		{
			randomNumberWidthVariation = 5.0*(Math.random() - 0.5);
			randomNumberHeightVariation = 10.0*(Math.random() - 0.5);
			double[] sizeCoordArray = {10.0+randomNumberWidthVariation,20.0+randomNumberHeightVariation,0.0,0.0};
			sizeCoordData.add(sizeCoordArray);
		}

		for(int i = 0; i < 12; i++)
		{
			try
			{
				String name = "testSizeCoordData" + i;
				SectionSizeCoords temp = new SectionSizeCoords(name,
						sizeCoordData.get(i)[0],
						sizeCoordData.get(i)[1],
						sizeCoordData.get(i)[2],
						sizeCoordData.get(i)[3]);
				sectSizeCoordsData.put(name,temp);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		brdColSet.setAndSortSectionSizeCoordList(sectSizeCoordsData);
		brdColSet.createColumns();
	}

	@AfterAll
	static void teardownTest()
	{
		brdColSet = null;
		sectSizeCoordsData = null;
	}

	@Test
	void test_getColumn()
	{
		// test: column section count = 4, columns are sorted by height
		Assertions.assertNotNull(brdColSet.getColumn(0));
		Assertions.assertFalse(brdColSet.getColumn(0).isEmpty());
		Assertions.assertNotNull(brdColSet.getColumn(1));
		Assertions.assertFalse(brdColSet.getColumn(1).isEmpty());
		Assertions.assertNotNull(brdColSet.getColumn(2));
		Assertions.assertFalse(brdColSet.getColumn(2).isEmpty());
		Assertions.assertNotNull(brdColSet.getColumn(3));
		Assertions.assertFalse(brdColSet.getColumn(3).isEmpty());
		Assertions.assertThrows(IndexOutOfBoundsException.class,
				() -> {brdColSet.getColumn(4);});

		try
		{
			for(int i = 0; i < 3; i++)
			{
				double col1 = brdColSet.getColumn(i).stream().mapToInt(x -> x.height).max().getAsInt();
				double col2 = brdColSet.getColumn(i+1).stream().mapToInt(x -> x.height).max().getAsInt();
				Assertions.assertTrue(col1 >= col2);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Test
	void test_getColumns()
	{
		// test: column count = 20, columns are sorted by height

		List<List<SectionSizeCoords>> testCols = brdColSet.getColumns();
		Assertions.assertNotNull(testCols);



		for(int i = 0; i < 3; i++)
		{
			Assertions.assertNotNull(testCols.get(i));
			double col1 = testCols.get(i).stream().mapToInt(x -> x.height).max().getAsInt();
			double col2 = testCols.get(i+1).stream().mapToInt(x -> x.height).max().getAsInt();
			Assertions.assertTrue(col1 >= col2);
		}

	}

	@Test
	void test_getColumnHeightMax()
	{
		// test: function gives correct highest column height.  Use getColumns, assuming it passed
		brdColSet.setColumnHeightMaxAndSum();
		List<List<SectionSizeCoords>> testCols = brdColSet.getColumns();

		List<Integer> testColHeights = new ArrayList<Integer>();
		for(int i = 0; i < 4; i++)
		{
			testColHeights.add(testCols.get(i).stream().mapToInt(x -> x.height).sum());
		}

		int colHeightMax = testColHeights.stream().mapToInt(x -> x).max().getAsInt();
		Assertions.assertEquals(colHeightMax, brdColSet.getColumnHeightMax());
	}

	@Test
	void test_getColumnWidthSum()
	{
		// test: function gives correct column width sum.  Use getColumns, assuming it passed

		List<List<SectionSizeCoords>> cols = brdColSet.getColumns();

		brdColSet.setColumnWidthMaxAndSum();


		int testColWidths = 0;
		for(int i = 0; i < 4; i++)
		{
			testColWidths += cols.get(i).stream().mapToInt(x -> x.width).max().getAsInt();
		}
		Assertions.assertEquals(testColWidths, brdColSet.getColumnWidthSum());

	}


	@Test
	void test_getColumnWidths()
	{
		// test: function gives correct column width values.  Use getColumns, assuming it passed

		List<List<SectionSizeCoords>> cols = brdColSet.getColumns();

		List<Integer> colWidths = brdColSet.getColumnWidths();

		for(int i = 0; i < 4; i++)
		{
			int testColWidth = cols.get(i).stream().mapToInt(x -> x.width).max().getAsInt();
			Assertions.assertEquals(testColWidth, colWidths.get(i).intValue());
		}
	}

	@Test
	void test_getBoardSectSizeDataJson()
	{
		// test: function gives non-zero values for doubles and integers
		JsonArray sectSizeData = brdColSet.getBoardSectSizeDataJson();

		try
		{
			for(JsonValue column: sectSizeData)
			{
				for(JsonValue section : (JsonArray)column)
				{
					JsonObject sectObj = (JsonObject)section;
					SectionSizeCoords targetTemp = new SectionSizeCoords(
					sectObj.getString("name"),
					sectObj.getJsonNumber("width").doubleValue(),
					sectObj.getJsonNumber("height").doubleValue(),
					sectObj.getJsonNumber("centerX").doubleValue(),
					sectObj.getJsonNumber("centerY").doubleValue()
					);

					SectionSizeCoords testTemp = sectSizeCoordsData.get(targetTemp.name);

					Assertions.assertEquals(testTemp.width, targetTemp.width);
					Assertions.assertEquals(testTemp.height, targetTemp.height);
					Assertions.assertEquals(testTemp.centerX, targetTemp.centerX);
					Assertions.assertEquals(testTemp.centerY, targetTemp.centerY);


				}

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

}
