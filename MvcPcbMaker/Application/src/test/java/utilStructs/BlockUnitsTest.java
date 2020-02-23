package utilStructs;

//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

//import org.apache.commons.math3.util.Precision;
import com.mvcpcbmaker.utilstructs.BlockUnits;

public class BlockUnitsTest
{

	private BlockUnits blkUnits = new BlockUnits();
	
	
	@Test
	void test_getBlockUnit()
	{
		int testValue = blkUnits.getBlockUnit(10.5);
		
		Assertions.assertEquals(105, testValue);
	}

	@Test
	void test_getBlockUnits()
	{
		BlockUnits testBlock = blkUnits.getBlockUnits(10.5, 9.1);
		
		Assertions.assertEquals(105, testBlock.x);
		Assertions.assertEquals(91, testBlock.y);

	}

	@Test
	void test_divideBlockUnits1D()
	{
		int testValue = blkUnits.divideBlockUnits1D(174, 33);
		Assertions.assertEquals(6, testValue);
	}

	@Test
	void test_divideIntRound()
	{
		int testValue = blkUnits.divideIntRound(174, 33);
		Assertions.assertEquals(5, testValue);

	}

	@Test
	void test_getDoubleValue()
	{
		double testValue = blkUnits.getDoubleValue(10);
		Assertions.assertEquals(1.0000000, testValue);

	}

}
