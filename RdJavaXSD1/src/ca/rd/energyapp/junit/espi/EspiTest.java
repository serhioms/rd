/**
 * 
 */
package ca.rd.energyapp.junit.espi;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.InputSource;

import ca.mss.rd.util.UtilReflection;
import ca.rd.energyapp.greenbutton.generated.espi.FeedType;
import ca.rd.energyapp.greenbutton.generated.espi.MeterReading;

/**
 * @author moskovsk
 *
 */
public class EspiTest {

	final static public String className = EspiTest.class.getName();
	final static public long serialVersionUID = className.hashCode();

	JAXBContext jaxbContext;
	Unmarshaller xmlUnmarshaller;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		jaxbContext = JAXBContext.newInstance("ca.rd.energyapp.greenbutton.generated.espi");
		xmlUnmarshaller = jaxbContext.createUnmarshaller();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void Monthly() throws Throwable {

		FeedType ft = (FeedType )((JAXBElement )xmlUnmarshaller.unmarshal(
				new InputSource(new FileInputStream("xml/Hydro-One-sample-monthly.xml")))).getValue();
		
		printOject("Monthly", ft);
	}

	public void Daily() throws Throwable {

		FeedType ft = (FeedType )((JAXBElement )xmlUnmarshaller.unmarshal(
				new InputSource(new FileInputStream("xml/Hydro-One-sample-daily.xml")))).getValue();
		
		printOject("Daily", ft);
	}

	
	
	
	private void printOject(String label, Object obj){
		if( obj != null ){
			
			if( obj instanceof JAXBElement ){
				obj = ((JAXBElement)obj).getValue();
			}

			if( obj instanceof String ){
				String str = obj.toString();
				if( str.trim().length() > 0 )
					System.out.println(label+" = "+str);
				
				if( "LocalTimeParameters".equals(str) ){
					System.out.println();
				}
				
			} else if( obj instanceof Short ){
				System.out.println(label+" = "+obj.toString());
			} else if( obj instanceof Byte ){
				System.out.println(label+" = "+obj.toString());
			} else if( obj instanceof Long ){
				System.out.println(label+" = "+obj.toString());
			} else if( obj instanceof Integer ){
				System.out.println(label+" = "+obj.toString());
			} else if( obj instanceof Double ){
				System.out.println(label+" = "+obj.toString());
			} else if( obj instanceof Float ){
				System.out.println(label+" = "+obj.toString());
			} else if( obj instanceof XMLGregorianCalendar ){
				System.out.println(label+" = "+obj.toString());
			} else if( obj instanceof List ){
				List<Object> list = (List<Object> )obj;
				for(int i=0, max=list.size(); i<max; i++){
					if( list.get(i) != null )
						printOject(label+"["+i+"]", list.get(i));
				}
			} else if( obj instanceof Map ){
				Map<Object,Object> map = (Map<Object,Object> )obj;
				if( !map.isEmpty() ){
					for(Iterator<Entry<Object,Object>> iter=map.entrySet().iterator(); iter.hasNext(); ){
						Entry<Object,Object> entry = iter.next();
						if( entry.getValue() != null )
							printOject(label+"."+entry.getKey().toString(), entry.getValue());
					}
				}
			} else {
				List<String> getters = UtilReflection.getStaticGetters(obj.getClass());
				if( getters.size() > 0 ){
					label += "."+obj.getClass().getSimpleName();
					for(int j=0, maxj=getters.size(); j<maxj; j++){
						printOject(label+"."+getters.get(j).replaceFirst("get", ""), UtilReflection.call(obj, getters.get(j)));
						System.out.print("");
					}
				} else {
					if( obj instanceof MeterReading ){
						MeterReading mr = (MeterReading )obj;
						printOject(label, mr.getBatchItemInfo());
						printOject(label, mr.getExtension());
					} else {
						System.out.println("***** "+obj.getClass().getSimpleName());
					}
				}
			}
		}
	}
	

}


