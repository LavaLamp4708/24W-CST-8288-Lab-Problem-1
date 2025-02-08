/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab1;

import java.io.*;
import java.nio.file.*;
import java.security.SecureRandom;
import java.sql.*;
import java.util.*;

/**
 *
 * @author peter
 */
public class Main {
    private static Connection getConnection(Properties props) throws SQLException {
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    private static ResultSet executeQuery(Connection connection, int randomYear) throws SQLException {
        String query = "SELECT * FROM Recipients WHERE Year = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, randomYear);
        return ps.executeQuery();
    }

    private static void printMetaData(ResultSetMetaData rsmd) throws SQLException {
        int columCount = rsmd.getColumnCount();
        StringBuilder builder = new StringBuilder();
        builder.append("Recipients Table - Column Attributes:\n");
        for(int i = 1; i<=columCount; i++){
            builder.append(
                String.format(
                    "%-20s%-20s%s\n",
                    rsmd.getColumnName(i),
                    rsmd.getColumnTypeName(i),
                    rsmd.getColumnClassName(i)
                )
            );
        }
        System.out.println(builder);
    }

    private static void printResults(List<Recipient> list){
        StringBuilder builder = new StringBuilder();
        String formatting = "%-15s%-40s%-10s%-15s%-40s\n";
        builder.append(String.format(formatting, "AwardID", "Name", "Year", "City", "Category"));
        for(Recipient recipient : list){
            builder.append(String.format(
                formatting, 
                String.valueOf(recipient.getAwardID()), 
                recipient.getName(),
                String.valueOf(recipient.getYear()),
                recipient.getCity(),
                recipient.getCategory()
            ));
        }
        System.out.print(builder);
    }

    private static void printIfEmpty(int year){
        System.out.println("No result(s) found for the year " + year + ".");
    }

    public static void main(String[] args){
        Properties props = new Properties();
        Connection connection = null;
        ResultSet rs = null;
        List<Recipient> recipientResultsList = new ArrayList<Recipient>();
        Boolean isEmptyRS = false;
        int randomYear = new SecureRandom().nextInt(1987, 2020);

        try {
            InputStream in = Main.class.getResourceAsStream("/lab1/database.properties");
            props.load(in);
            in.close();
            connection = getConnection(props);
            rs = executeQuery(connection, randomYear);
            if(!rs.isBeforeFirst()){
                isEmptyRS = true;
            } else {
                while(rs.next()){
                    recipientResultsList.add(
                        new Recipient(
                            rs.getInt("AwardID"),
                            rs.getString("Name"),
                            rs.getInt("Year"),
                            rs.getString("City"),
                            rs.getString("Category")
                        )
                    );
                }
            }
            ResultSetMetaData rsmd = rs.getMetaData();
            printMetaData(rsmd);
        } catch(SQLException | IOException e){
            e.printStackTrace();
        }

        if(isEmptyRS){
            printIfEmpty(randomYear);
        } else {
            printResults(recipientResultsList);
        }
    }
}
