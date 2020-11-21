package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	
	//criando metodo para conectar com o banco de dados
	private static Connection connection = null;
	
	public static Connection getConnection() {
		if(connection == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				connection = DriverManager.getConnection(url, props);
				
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return connection;
	}
	
	public static void closeConnection() {
		if(connection != null) {
			try {
				connection.close();
				
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	private static Properties loadProperties() {
		try(FileInputStream fs = new FileInputStream("db.properties")){
			Properties props = new Properties();
			props.load(fs); //o load faz a leitura do arquivo properties, apontado pelo FileInputStream
			
			return props;
			
		}catch(IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	//metodos para tratamento de exceções geradas pelo .close()
	public static void closeStatement(Statement state) {
		if(state!= null) {
			try {
				state.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if(rs!= null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
