package org.example.delta_pdv.repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.*;

public class DB {

    private static Connection conn;

    public static Connection getConn() {
        if (conn != null) {
            return conn;
        }
        try {
            Properties props = loadProperties();
            String URL = props.getProperty("url");
            String USER = props.getProperty("user");
            String PASSWORD = props.getProperty("password");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Banco conectado com sucesso!");
            return conn;
        } catch (SQLException exception) {
            throw new RuntimeException("Não foi possivel conectar ao banco de dados" + exception);
        }
    }

    private static Properties loadProperties() {
        try(FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
             props.load(fs);
             return props;
        }catch (IOException exception) {
            throw new RuntimeException("Erro ao carregar arquivo!!: " + exception);
        }
    }

    private void closeStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            }catch (SQLException exception) {
                throw new RuntimeException("Erro ao fechar o PrepareStatement" + exception);
            }
        }
    }

    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException exception) {
            throw new RuntimeException("Erro ao fechar o resultSet");
            }
        }
    }

    public static void main(String[] args) {
        Connection conn = getConn();
    }
}
