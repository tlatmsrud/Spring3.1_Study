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

public class UserDaoJdbc implements UserDao{

	private JdbcTemplate jdbcTemplate;
	
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}
	
	
	public void add(User user) throws DuplicateKeyException{
		jdbcTemplate.update("insert into users(id, name, password, lev, login, recommend, email) values(?,?,?,?,?,?,?)",
				user.getId(),user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(),user.getEmail());
		
	}
	
	public User get(String id){
		
		return jdbcTemplate.queryForObject("select * from users where id = ?", 
				new Object[] {id},
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
	
	public void deleteAll(){
		
		jdbcTemplate.update("delete from users");
	}
	
	public int getCount(){
		return jdbcTemplate.queryForObject("select count(*) from users", int.class); 
	}


	public List<User> getAll() {
		// TODO Auto-generated method stub
		return jdbcTemplate.query("select * from users",
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
		this.jdbcTemplate.update(
				"update users set name = ?, password = ?, lev = ?, login = ?, recommend = ?, email =? where id =?",
				user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail(), user.getId());
		
	}
	

}
