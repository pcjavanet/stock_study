package com.pcjavanet.getdata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pcjavanet.model.DayData;
import com.pcjavanet.model.Stock;
import com.pcjavanet.tool.HttpUtil;
import com.pcjavanet.tool.JdbcTool;
import com.pcjavanet.tool.ParseUtil;

public class FetchCaifudongfang {
  private static String URL_TEMPLATE_SZ = "http://push2his.eastmoney.com/api/qt/stock/kline/get?fields1=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13&fields2=f51,f52,f53,f54,f55,f56,f57,f58,f59,f60,f61&beg=0&end=20500101&ut=fa5fd1943c7b386f172d6893dbfba10b&rtntype=6&"
      + "secid=0.CODE&klt=101&fqt=1&cb=jsonp";
  private static String URL_TEMPLATE_SH = "http://push2his.eastmoney.com/api/qt/stock/kline/get?fields1=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13&fields2=f51,f52,f53,f54,f55,f56,f57,f58,f59,f60,f61&beg=0&end=20500101&ut=fa5fd1943c7b386f172d6893dbfba10b&rtntype=6&"
      + "secid=1.CODE&klt=101&fqt=1&cb=jsonp";

  // public List<Stock> loadAllStocks() {
  //   List<Stock> ret = new ArrayList<Stock>();
  //   try {
  //     Connection con = JdbcTool.getConnection();
  //     String sql = "SELECT market_type, code, name, last_update_date, status FROM stock";
  //     ResultSet rs = con.createStatement().executeQuery(sql);
  //     while (rs.next()) {
  //       Stock s = new Stock();
  //       s.setCode(rs.getString(""));
  //       ret.add(s);
  //     }
  //   } catch (Exception e) {
  //     System.out.println(e.toString());
  //   }
  //   return ret;
  // }

  public String getOneStockHistory(int type, String code) {
    String url = null;
    if (type == 0) {
      url = URL_TEMPLATE_SZ.replace("CODE", code) + System.currentTimeMillis();
    } else {
      url = URL_TEMPLATE_SH.replace("CODE", code) + System.currentTimeMillis();
    }
    System.out.println("Get data from:" + url);
    String ret = HttpUtil.getRemoteData(url);
    return ret;
  }

  public List<DayData> filterOriginDataByDate(List<DayData> datas, Stock stock, SimpleDateFormat sdf) {
    List<DayData> ret = new ArrayList<DayData>();
    Date date = new Date();
    Date d2 = null ; 
    try {
      d2 = sdf.parse(sdf.format(date));
//      System.out.println( d2);
    } catch (ParseException e) {
      System.out.println(e.toString());
    }
    for( DayData d: datas ) {
      boolean addFlag = false ;
      if ( stock.getLastUpdateDate() == null ) {
        addFlag = true ;
      } else {
        if ( d.getDay().getTime() > stock.getLastUpdateDate().getTime() ) {
          addFlag = true ;
        }
      }
      if ( addFlag ) {
        
        if ( d.getDay().getTime() < d2.getTime() ) {
          addFlag = true ;
        } else {
           if ( System.currentTimeMillis()> (d2.getTime()+ 3600000*15L)) {
             addFlag = true ;
             System.out.println(d.toString()+" is finish time");
           } else {
             System.out.println(d.toString()+" is not finish time");
             addFlag = false ;
           }
        }
        
      }
      if ( addFlag ) {
        d.setCode(stock.getCode());
        ret.add(d);
      } 
    }
    return ret ; 
  }
  
  public List<DayData> parseOriginData(String content,SimpleDateFormat sdf ) {
    List<DayData> ret = new ArrayList<DayData>();
    if (content != null && !"".equals(content)) {
      int begin = content.indexOf("(");
      int end = content.lastIndexOf(")");
      content = content.substring(begin + 1, end);
      Gson gson = new Gson();
      JsonObject object = gson.fromJson(content, JsonObject.class);
      JsonObject dataObject = (JsonObject) object.get("data");
//      System.out.println(dataObject.toString());
      Iterator<JsonElement> dayDatas = dataObject.get("klines").getAsJsonArray() .iterator();
      while (dayDatas.hasNext()) {
        JsonElement jsonDayData = dayDatas.next();
        DayData dayDataBean =  ParseUtil.pareOneData(jsonDayData.getAsString(),sdf);
//        System.out.println(dayDataBean.toString());
        if ( dayDataBean != null ) {
          ret.add(dayDataBean );
        } else {
          System.out.println( "day data is null "+ jsonDayData.toString() );
        }
      }
    }
    return ret;
  }

  public void synchronizeHistory() {
    List<Stock> stocks = JdbcTool.loadStock();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    for (Stock stock : stocks) {
      int type = stock.getType();
      String code = stock.getCode();
      String content = getOneStockHistory(type, code);
      List<DayData> datas = parseOriginData(content,sdf);
      List<DayData> filteredDatas = filterOriginDataByDate(datas, stock,sdf);
      System.out.println(code +" records="+filteredDatas.size());
      if ( filteredDatas.size() > 0 ) {
        JdbcTool.insertHistoryData(filteredDatas);
        JdbcTool.updateLastUpdateDate( filteredDatas.get(filteredDatas.size()-1).getDay(), code);
      }
    }
  }
  public static void main(String args[]) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    
    // int type = 0;
    // String code = "002166";
      FetchCaifudongfang fcf = new FetchCaifudongfang();
      fcf.synchronizeHistory();
    // System.out.println(fcf.getOneStockHistory(type, code));
  }

}
