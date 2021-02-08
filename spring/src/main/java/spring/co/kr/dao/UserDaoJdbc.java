package spring.co.kr.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import spring.co.kr.domain.Level;
import spring.co.kr.interf.UserDao;
import spring.co.kr.user.sqlService.SqlService;

public class UserDaoJdbc implements UserDao{

	private JdbcTemplate jdbcTemplate;
	
	private DataSource dataSource;

	private SqlService sqlService;

	public void setSqlService(SqlService sqlService) {
		this.sqlService = sqlService;
	}


	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}
	


	public void add(User user) throws DuplicateKeyException{
		jdbcTemplate.update(
				sqlService.getSql("userAdd"),
				user.getId(),user.getName(), user.getPassword(),user.getEmail() , user.getLevel().intValue(), user.getLogin(), user.getRecommend());
		
	}
	
	public User get(String id){
		return jdbcTemplate.queryForObject(sqlService.getSql("userGet"), new Object[] {id}, new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setLevel(Level.valueOf(rs.getInt("lev")));
				user.setLogin(rs.getInt("login"));
				user.setRecommend(rs.getInt("recommend"));
				user.setEmail(rs.getString("email"));
				return user;
			}

		});
	}
	
	public void deleteAll(){
		jdbcTemplate.update(sqlService.getSql("userDeleteAll"));
	}
	
	public int getCount(){
		return jdbcTemplate.queryForInt(sqlService.getSql("userGetCount")); 
	}


	public List<User> getAll() {
		// TODO Auto-generated method stub
		return jdbcTemplate.query(sqlService.getSql("userGetAll"),
				new RowMapper<User>() {
					public User mapRow(ResultSet rs, int rowNum) throws SQLException{
						User user = new User();
						user.setId(rs.getString("id"));
						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
						user.setLevel(Level.valueOf(rs.getInt("lev")));
						user.setLogin(rs.getInt("login"));
						user.setRecommend(rs.getInt("recommend"));
						user.setEmail(rs.getString("email"));
						return user;
					}
		});
	}


	public void update(User user) {
		// TODO Auto-generated method stub
		this.jdbcTemplate.update(sqlService.getSql("userUpdate"),
				user.getName(), user.getPassword(), user.getEmail(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId());
		
	}
	
	

}
