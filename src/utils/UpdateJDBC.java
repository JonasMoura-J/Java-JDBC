package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;

public class UpdateJDBC {
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
