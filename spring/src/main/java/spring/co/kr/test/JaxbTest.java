package spring.co.kr.test;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import spring.co.kr.user.sqlService.SqlService;
import spring.co.kr.user.sqlService.jaxb.SqlType;
import spring.co.kr.user.sqlService.jaxb.Sqlmap;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class JaxbTest {

	@Test
	public void readSqlmap() throws JAXBException, IOException{
		String contextPath = Sqlmap.class.getPackage().getName();
		System.out.println("contextPath : "+contextPath);
		
		JAXBContext context = JAXBContext.newInstance(contextPath);	
		System.out.println("SqlService path : "+SqlService.class.getPackage().getName());
		
		Unmarshaller unmarshaller = context.createUnmarshaller(); //언 마샬러 생성 (xml -> java) 
		
		Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(getClass().getResourceAsStream("sqlmap.xml")); 
		//sqlmap.xml 파일에 대해 언마샬링 실시, 언마샬링을 하면 매핑된 오브젝트 트리의 루트인 Sqlmap을 돌려준다.
		
		List<SqlType> sqlList = sqlmap.getSql();
		
		assertThat(sqlList.size(), is(3));
		assertThat(sqlList.get(0).getKey(), is("add"));
		assertThat(sqlList.get(0).getValue(), is("insert"));

	}
}
