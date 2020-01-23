package models.schematic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Evaluator.Tag;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.mvcpcbmaker.models.schematic.Package;
import com.mvcpcbmaker.models.schematic.PackageImpl;
import com.mvcpcbmaker.models.schematic._ComponentImpl;

public class _ComponentImplTest
{
	private static _ComponentImpl comp = new _ComponentImpl();
	private static  org.jsoup.parser.Tag tag;
	private static Attributes attrs = new Attributes();
	private static String baseUri = "<gates><gate name=\"G$1\" symbol=\"27-I/O\" x=\"-2.54\" y=\"0\"/></gates>\n" + 
			"<devices><device name=\"\" package=\"TQFP32-08\">" + 
			"<connects><connect gate=\"G$1\" pin=\"AREF\" pad=\"20\"/><connect gate=\"G$1\" pin=\"AVCC\" pad=\"18\"/>\n" + 
			"<connect gate=\"G$1\" pin=\"GND\" pad=\"21\"/><connect gate=\"G$1\" pin=\"GND@1\" pad=\"5\"/>\n" + 
			"<connect gate=\"G$1\" pin=\"PB0(ICP)\" pad=\"12\"/><connect gate=\"G$1\" pin=\"PB1(OC1A)\" pad=\"13\"/>\n" + 
			"<connect gate=\"G$1\" pin=\"PB2(SS/OC1B)\" pad=\"14\"/><connect gate=\"G$1\" pin=\"PB3(MOSI/OC2)\" pad=\"15\"/>\n" + 
			"<connect gate=\"G$1\" pin=\"PB4(MISO)\" pad=\"16\"/><connect gate=\"G$1\" pin=\"PB5(SCK)\" pad=\"17\"/>\n" + 
			"<connect gate=\"G$1\" pin=\"PB6(XTAL1/TOSC1)\" pad=\"7\"/><connect gate=\"G$1\" pin=\"PB7(XTAL2/TOSC2)\" pad=\"8\"/>\n" + 
			"<connect gate=\"G$1\" pin=\"PC0(ADC0)\" pad=\"23\"/><connect gate=\"G$1\" pin=\"PC1(ADC1)\" pad=\"24\"/>\n" + 
			"<connect gate=\"G$1\" pin=\"PC2(ADC2)\" pad=\"25\"/><connect gate=\"G$1\" pin=\"PC3(ADC3)\" pad=\"26\"/>\n" + 
			"<connect gate=\"G$1\" pin=\"PC4(ADC4/SDA)\" pad=\"27\"/><connect gate=\"G$1\" pin=\"PC5(ADC5/SCL)\" pad=\"28\"/>\n" + 
			"<connect gate=\"G$1\" pin=\"PC6(/RESET)\" pad=\"29\"/><connect gate=\"G$1\" pin=\"PD0(RXD)\" pad=\"30\"/>\n" + 
			"<connect gate=\"G$1\" pin=\"PD1(TXD)\" pad=\"31\"/><connect gate=\"G$1\" pin=\"PD2(INT0)\" pad=\"32\"/>\n" + 
			"<connect gate=\"G$1\" pin=\"PD3(INT1)\" pad=\"1\"/><connect gate=\"G$1\" pin=\"PD4(XCK/T0)\" pad=\"2\"/>\n" + 
			"<connect gate=\"G$1\" pin=\"PD5(T1)\" pad=\"9\"/><connect gate=\"G$1\" pin=\"PD6(AIN0)\" pad=\"10\"/>\n" + 
			"<connect gate=\"G$1\" pin=\"PD7(AIN1)\" pad=\"11\"/><connect gate=\"G$1\" pin=\"PE0\" pad=\"3\"/>\n" + 
			"<connect gate=\"G$1\" pin=\"PE1\" pad=\"6\"/><connect gate=\"G$1\" pin=\"PE2\" pad=\"19\"/>\n" + 
			"<connect gate=\"G$1\" pin=\"PE3\" pad=\"22\"/><connect gate=\"G$1\" pin=\"VCC@1\" pad=\"4\"/>\n" + 
			"</connects><technologies><technology name=\"\"/></technologies></device></devices>";
	
	private static Map<String,Package> pkgMap = new HashMap<String,Package>();
	private static Element compBlock; 
	
	@BeforeAll
	static void setupTest()
	{
		compBlock = new Element(tag.valueOf("deviceset"),"", attrs); 
		attrs.put("name", "ATMEGA168");
		attrs.put("prefix", "IC");
		attrs.put("uservalue", "yes");
		compBlock.append(baseUri);
		

	}
	
	
	@AfterAll
	static void tearDown()
	{
		
	}
	
	@Test
	void test_Function()
	{
		List<Element> connectBlockList = compBlock.getElementsByTag("connect");
		for(Element connectBlock: connectBlockList)
		{
			System.out.println(connectBlock.toString());
		}
	}
}
