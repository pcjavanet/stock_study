package com.pcjavanet.tool;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.pcjavanet.model.DayData;
import com.pcjavanet.model.Stock;

public class JdbcTool {
  private static LinkedList<Connection> connLs = new LinkedList<Connection>();
  public static Connection getConnection() throws Exception {
    if (connLs.size() > 0) {
      return connLs.pop();
    } else {
      Class.forName("org.postgresql.Driver");
      Connection mesConnection = DriverManager.getConnection(
          "jdbc:postgresql://127.0.0.1:5432/stock_history_data", "postgres",
          "postgres");
      return mesConnection;
    }
  }

  public static void returnConnection(Connection conn) {
    JdbcTool.connLs.add(conn);
  }

  public static List<DayData>  loadStockWithCodeAndDay(String code,java.util.Date day ) {
    List<DayData> rets = new ArrayList<DayData>();
    Connection conn = null;
    PreparedStatement stmt = null;
    try {
      conn = getConnection();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    if  ( conn != null ) {
      try {
        String sql="SELECT code, \"day\", \"open\", \"close\", low, hight, volue, totalcomit, change_range, rise_down_range, rise_down_total, change_rate\n"
            + "FROM day_history WHERE code=? AND day>=? ORDER BY  day DESC";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, code);
        stmt.setDate(2, new java.sql.Date(day.getTime()));
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
          DayData dayData= new DayData();
          dayData.setCode(rs.getString("code"));
          dayData.setDay(rs.getDate("day"));
          dayData.setOpenPrice(rs.getDouble("open"));
          dayData.setClosePrice(rs.getDouble("close"));
          dayData.setLowPrice(rs.getDouble("low"));
          dayData.setHightPrice(rs.getDouble("hight"));
          dayData.setVolue(rs.getInt("volue"));
          dayData.setTotalComit(rs.getDouble("totalcomit"));
          dayData.setChangeRange(rs.getDouble("change_range"));
          dayData.setRiseDownRange(rs.getDouble("rise_down_range"));
          dayData.setRiseDownTotal(rs.getDouble("rise_down_total"));
          dayData.setChangeRate(rs.getDouble("change_rate"));
          rets.add(dayData);
        }
        
        rs.close();
        stmt.close();
       returnConnection(conn);
      } catch (SQLException e) {
           System.out.println(e.toString());
      }
    }
    return rets ;
  }
 
    public static List<Stock>  loadStock() {
    List<Stock> rets = new ArrayList<Stock>();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = getConnection();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    if  ( conn != null ) {
      try {
        String sql="SELECT market_type, code, \"name\", last_update_date, status FROM stock WHERE status=1";
//        SELECT 
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
          Stock stock = new Stock();
          stock.setCode(rs.getString("code"));
          stock.setName(rs.getString("name"));
          stock.setLastUpdateDate(rs.getDate("last_update_date"));
          stock.setType(rs.getInt("market_type"));
          stock.setStatus(rs.getInt("status"));
          rets.add(stock);
        }
        
        rs.close();
        stmt.close();
       returnConnection(conn);
      } catch (SQLException e) {
           System.out.println(e.toString());
      }
    }
    return rets;
  }

  public static void updateLastUpdateDate(java.util.Date date ,String code) {
    Connection conn = null;
    PreparedStatement stmt = null;
    try {
      conn = getConnection();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    if  ( conn != null ) {
      try {
        String sql="update stock set last_update_date=? WHERE code=?";
        stmt = conn.prepareStatement(sql);
        stmt.setDate(1, new java.sql.Date(date.getTime()));
        stmt.setString(2, code);
        stmt.executeUpdate();
        stmt.close();
       returnConnection(conn);
      } catch (SQLException e) {
           System.out.println(e.toString());
      }
    }
//    return rets;
  }

  public static void insertHistoryData(List<DayData> historyDatas) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    String insertSql = "INSERT INTO day_history\n"
        + "(\"code\",\"day\", \"open\", \"close\", low, hight, volue, totalcomit, change_range, rise_down_range, rise_down_total, change_rate)\n"
        + "VALUES(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    try {
      conn = getConnection();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    try {
      if (conn != null) {
        conn.setAutoCommit(false);
        pstmt = conn.prepareStatement(insertSql);
        for (int i = 0; i < historyDatas.size(); i++) {
          DayData d = historyDatas.get(i);
          int k = 1;
          pstmt.setString(k++, d.getCode());
          pstmt.setDate(k++, new Date(d.getDay().getTime()));
          pstmt.setDouble(k++, d.getOpenPrice());
          pstmt.setDouble(k++, d.getClosePrice());
          pstmt.setDouble(k++, d.getLowPrice());
          pstmt.setDouble(k++, d.getHightPrice());
          pstmt.setDouble(k++, d.getVolue());
          pstmt.setDouble(k++, d.getTotalComit());
          pstmt.setDouble(k++, d.getChangeRange());
          pstmt.setDouble(k++, d.getRiseDownRange());
          pstmt.setDouble(k++, d.getRiseDownTotal());
          pstmt.setDouble(k++, d.getChangeRate());
          pstmt.addBatch();
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      if (pstmt != null) {
        pstmt.executeBatch();
      }
    } catch (SQLException e) {
      System.out.println("Error message: " + e.getMessage());
    }
    try {
      if (conn != null) {
        conn.commit();
        conn.setAutoCommit(true);
        returnConnection(conn);
      }
      if ( pstmt != null ) {
        pstmt.close();
      }
    } catch (SQLException e) {
      System.out.println(e.toString());
    }
  }
}
