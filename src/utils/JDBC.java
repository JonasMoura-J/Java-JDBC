package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;
import db.DbException;

public class JDBC {
	
	public static void delete() {
		//atualizando salario de um vendedor
		
		Connection con = null;
		PreparedStatement st = null;
		
		try {
			con = DB.getConnection();
			st = con.prepareStatement("DELETE FROM department WHERE Id = ?");	
			
			st.setInt(1, 5);
			
			int linhasAlteradas = st.executeUpdate();
			
			System.out.println("Pronto! Linhas Afetadas: " + linhasAlteradas);
			
		}catch(SQLException e){
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
	
	public static void insert() {
			
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Connection con = null;
		PreparedStatement st = null;
		
		try {
			con = DB.getConnection();
			
			st = con.prepareStatement(
				"INSERT INTO seller  (Name, Email, BirthDate, BaseSalary, DepartmentID) VALUES (?, ?, ?, ?, ?)"	
				, Statement.RETURN_GENERATED_KEYS);
			
//				"INSERT INTO department (Name) VALUES (?)"	
//				, Statement.RETURN_GENERATED_KEYS);
//			st.setString(1, "Teste");
			
			//inserindo valores nas posições das interrogações
			st.setString(1, "Renan Moura");
			st.setString(2, "jonas@gmail.com");
			st.setDate(3, new java.sql.Date(sdf.parse("17/01/2001").getTime()));
			st.setDouble(4, 3000.00);
			st.setInt(5, 4);
			
			int linhasAlteradas = st.executeUpdate();
			
			if(linhasAlteradas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				while(rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Pronto! Id = " + id);
				}
				
			}else {
				System.out.println("Nenhuma linha alterada");
			}
			
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
			
		}catch(ParseException e) {
			e.printStackTrace();
			
		}finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
	
	public static void select() {
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
	
	public static void update() {
		//atualizando salario de um vendedor
		
		Connection con = null;
		PreparedStatement st = null;
		
		try {
			con = DB.getConnection();
			st = con.prepareStatement("UPDATE seller SET BaseSalary = BaseSalary + ? WHERE (DepartmentID = ?)");	
			
			st.setDouble(1, 200.0);
			st.setInt(2, 2);
			
			int linhasAlteradas = st.executeUpdate();
			
			System.out.println("Pronto! Linhas Afetadas: " + linhasAlteradas);
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}
