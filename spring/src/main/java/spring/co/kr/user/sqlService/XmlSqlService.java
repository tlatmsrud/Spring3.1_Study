package spring.co.kr.user.sqlService;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import spring.co.kr.interf.UserDao;
import spring.co.kr.user.sqlService.jaxb.SqlType;
import spring.co.kr.user.sqlService.jaxb.Sqlmap;

public class XmlSqlService implements SqlService{

	private Map<String, String> sqlMap = new HashMap<String, String>();
	
	private String sqlmapFile;
	
	public void setSqlmapFile(String sqlmapFile) {
		this.sqlmapFile = sqlmapFile;
	}
	
	public XmlSqlService() {
		
	}
	
	@PostConstruct
	public void loadSql() {
		String contextPath = Sqlmap.class.getPackage().getName();
		try {
			JAXBContext context = JAXBContext.newInstance(contextPath);
			
			Unmarshaller unmarshaller = context.createUnmarshaller(); //언마샬러 생성
			
			InputStream is = UserDao.class.getResourceAsStream(sqlmapFile); //sqlmap.xml 스트림
			
			Sqlmap sqlmap = (Sqlmap)unmarshaller.unmarshal(is); //sqlmap.xml 파일을 언마샬링 xml - java
			for(SqlType sql : sqlmap.getSql()) {
				sqlMap.put(sql.getKey(), sql.getValue());
			}
		}catch(JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getSql(String key) throws SqlRetrievalFailureException {
		// TODO Auto-generated method stub
		String sql = sqlMap.get(key);
		if(sql == null) {
			throw new SqlRetrievalFailureException(key + "를 이용해서 SQL을 찾을 수 없습니다.");
		}else{
			return sql;
		}
		
	}
	
	
	
}
