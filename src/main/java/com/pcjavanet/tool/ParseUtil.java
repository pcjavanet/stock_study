package com.pcjavanet.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pcjavanet.model.DayData;

public class ParseUtil {
  public static DayData pareOneData(String content,SimpleDateFormat sdf) {
    DayData data = null;
    try {
      data = new DayData();
      String[] datas = content.split(",");
//      SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
      for (int i = 0; i < datas.length; i++) {
        if (i == 0) {
          try {
//            System.out.println(datas[i] );
            data.setDay(sdf.parse(datas[i]));
          } catch (ParseException e) {
            e.printStackTrace();
          }
        }
        if (i == 1) {
          data.setOpenPrice(Double.parseDouble(datas[i]));
        }
        if (i == 2) {
          data.setClosePrice(Double.parseDouble(datas[i]));
        }
        if (i == 3) {
          data.setHightPrice(Double.parseDouble(datas[i]));
        }
        if (i == 4) {
          data.setLowPrice(Double.parseDouble(datas[i]));
        }
        if (i == 5) {
          data.setVolue(Integer.parseInt(datas[i]));
        }
        if (i == 6) {
          data.setTotalComit(Double.parseDouble(datas[i]));
        }
        if (i == 7) {
          data.setChangeRange(Double.parseDouble(datas[i]));
        }
        if (i == 8) {
          data.setRiseDownRange(Double.parseDouble(datas[i]));
        }
        if (i == 9) {
          data.setRiseDownTotal(Double.parseDouble(datas[i]));
        }
        if (i == 10) {
          data.setChangeRate(Double.parseDouble(datas[i]));
        }
      }
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    return data;
  }
  public static void main(String args[] ) throws ParseException {
    String str= "2007-09-13";
    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
    Date d = sdf.parse(str);
    System.out.println(d.toString());
  }
}
