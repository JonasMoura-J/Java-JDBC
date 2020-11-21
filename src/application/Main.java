package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Main {

	public static void main(String[] args) {
		Connection con = null;
		
		Statement st = null;
		
		ResultSet rs = null;
		
		//connectando o banco e preparando uma consulta sql buscando todos os departamentos e o resultado sera guardado na variavel rs
		try {
			con = DB.getConnection();
			
			st = con.createStatement();//instanciando um obj do tipo Statemant
			rs = st.executeQuery("select * from department");
			
			while(rs.next()) {
				System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}
