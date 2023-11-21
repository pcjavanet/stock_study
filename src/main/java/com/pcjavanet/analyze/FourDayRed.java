package com.pcjavanet.analyze;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.pcjavanet.model.DayData;
import com.pcjavanet.tool.JdbcTool;

/**
 * 三日不破：凡是“某量柱”后面三日的最低价没有跌破此量柱最低价的，此量柱就是有一定支撑力的“将军柱”；
 */
public class FourDayRed {

  /**
   * 从成交量上来看，今天的成交量高于昨天，就叫做“胜”，今天的收盘价高于昨天的收盘价就叫做“阳胜”，今天的收盘价低于昨天的收盘价就叫做“阴胜”。
   * 
   * @param descLs
   */
  public static void doubleWin(List<DayData> descLs) {
    int len = descLs.size();
    for (int i = 0; i < len; i++) {
      int nextIdex = i + 1;
      if (nextIdex < len) {
        DayData current = descLs.get(i);
        DayData preVious = descLs.get(nextIdex);
        if (current.getVolue() > preVious.getVolue() && current.getClosePrice() > preVious.getClosePrice()) {
          System.out.println(current.getCode() + " " + current.getDay() + " 成交量:" + current.getVolue() + " 收盘价:"
              + current.getClosePrice() + " 双胜阳");
          current.setDayAttribute(DayData.YANG_WIN);
        }
        if (current.getVolue() > preVious.getVolue() && current.getClosePrice() < preVious.getClosePrice()) {
          System.out.println(current.getCode() + " " + current.getDay() + " 成交量:" + current.getVolue() + " 收盘价:"
              + current.getClosePrice() + " 双胜阴");
        }
      }
    }
  }

  public static void doubleLose(List<DayData> descLs) {
    int len = descLs.size();
    for (int i = 0; i < len; i++) {
      int nextIdex = i + 1;
      if (nextIdex < len) {
        DayData current = descLs.get(i);
        DayData preVious = descLs.get(nextIdex);
        if (current.getVolue() > preVious.getVolue() && current.getClosePrice() < preVious.getClosePrice()) {
          System.out.println(current.getCode() + " " + current.getDay() + " 成交量:" + current.getVolue() + " 收盘价:"
              + current.getClosePrice() + " 双胜阴");
        }
      }
    }
  }

  /**
   *  所谓三日不破,特指基柱后三日的收盘价没有跌破基柱实底,就属于三日不破的范畴
   * @param descLs
   */
  public static void findGeneralColumn(List<DayData> descLs) {
    LinkedList<DayData>  yangWinDatas = new LinkedList<DayData>();
    doubleWin(descLs);
    for(DayData d: descLs) {
      if ( d.getDayAttribute() == DayData.YANG_WIN ) {
        yangWinDatas.addFirst(d);
      }
    }
 
    for(int i=0;i<yangWinDatas.size();i++) {
//      System.out.println( yangWinDatas.get(i).toString());
      DayData current = yangWinDatas.get(i);
      boolean findSelf = false ;
      double low = current.getOpenPrice() < current.getClosePrice() ? current.getOpenPrice(): current.getClosePrice();
      double top = current.getOpenPrice() < current.getClosePrice() ? current.getClosePrice(): current.getOpenPrice();
      
      int fourDay = 4 ;
      boolean dayOne = false ,dayTwo = false , dayThree = false ;
      boolean dayOne2 = false ,dayTwo2 = false , dayThree2 = false ;
      boolean volumnFlag = false ;
      for(int j=descLs.size()-1;j>0;j--) {
        if ( current.getDay().getTime() == descLs.get(j).getDay().getTime() ) {
          findSelf = true ;
          
          if ( j+1 < (descLs.size()-1)) {
            DayData previewDay = descLs.get(j+1);
//            System.out.println("前一天 "+previewDay.getDay() + " 当日 "+current.getDay());
//            if (  (current.getVolue() /2) >  previewDay.getVolue()) {
            int range = current.getVolue() - previewDay.getVolue() ;
            if (  (range/previewDay.getVolue()) > 0.5) {
              volumnFlag = true ;
            }
          }
          
        }
        if ( findSelf && volumnFlag ) {
          fourDay = fourDay - 1;
          if ( fourDay == 2 ) {
            if (descLs.get(j).getClosePrice()> low ) {
              dayOne = true ; 
            }
            if (descLs.get(j).getClosePrice()> top ) {
              dayOne2 = true ; 
            }
          }
          
          if ( fourDay == 1 ) {
            if (descLs.get(j).getClosePrice()> low ) {
              dayTwo = true ; 
            }
            if (descLs.get(j).getClosePrice()> top ) {
              dayTwo2 = true ; 
            }
          }
          
          if ( fourDay == 0 ) {
            if (descLs.get(j).getClosePrice()> low ) {
              dayThree = true ; 
            }
            if (descLs.get(j).getClosePrice()> top ) {
              dayThree2 = true ; 
            }
            
            if ( dayOne && dayTwo && dayThree ) {
              System.out.println(current.getCode() + " "+current.getDay()+" 找到将军柱 "+" 开盘价:"+current.getOpenPrice()+" 收盘价:"+current.getClosePrice());
            }
            if ( dayOne2 && dayTwo2 && dayThree2 ) {
              System.out.println(current.getCode() + " "+current.getDay()+" 找到黄金柱 "+" 开盘价:"+current.getOpenPrice()+" 收盘价:"+current.getClosePrice());
            }
            break;
//            findSelf = false ;
          }
        }
      }
    }
  }
  
  public static void main(String[] args) throws ParseException {
    String code = "300676";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String datestr = "2023-01-01 00:00:00";
    Date date = sdf.parse(datestr);
    List<DayData> dayDataLs = JdbcTool.loadStockWithCodeAndDay(code, date);
//    FourDayRed.doubleWin(dayDataLs);
    
    FourDayRed.findGeneralColumn(dayDataLs);
  }
}
