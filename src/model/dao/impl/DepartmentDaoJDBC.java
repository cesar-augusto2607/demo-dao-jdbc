package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO department " 
										+ "(name) " 
										+ "VALUES (?) ", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());	
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0){
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! ");
			}
		}
		catch(SQLException e ) {
			throw new DbException(e.getMessage());
		}
		
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE department "
										+ "SET name = ? "
										+ "WHERE id = ? ");
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			
			st.execute();
			
		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deletebyId(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ? ");
			st.setInt(1, id);
			st.execute();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try  {
				st = conn.prepareStatement("Select * FROM department WHERE id = ? ");
				
				st.setInt(1, id);
				rs = st.executeQuery();
				
				if(rs.next()) {
					String name = rs.getString("name");
					Department dep = new Department(id, name);
					return dep;
				}
				return null;
		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM department " 
										+ "ORDER BY name ");
			rs = st.executeQuery();
			List<Department> list = new ArrayList<>();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				list.add(new Department(id, name));
			}
			
			
		return list;
		}
		catch(SQLException	e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		
	}
	
	
	
}
