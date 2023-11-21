package com.pcjavanet.model;

import java.util.Date;

import com.pcjavanet.tool.ParseUtil;

public class DayData {
//  "2023-11-13,7.72,7.94,7.99,7.67,193909,152457674. 34, 4.13,  2.58,  0.20,  3.82"
//  日期,开盘价，收盘价，最低价，最高价，成交量，成交额，振幅，涨跌幅，涨跌额,换手率
  private String code ;
  private Date day ; 
  private double openPrice;
  private double closePrice;
  private double lowPrice;
  private double hightPrice;
  private int volue;
  private double totalComit ;
  private double changeRange;
  private double riseDownRange ; 
  private double riseDownTotal;
  private double changeRate ;
  
  public static int YANG_WIN = 1 ; 
  public static int YIN_WIN = 0 ;
  private int dayAttribute ; 
 
  public int getDayAttribute() {
    return dayAttribute;
  }
  public void setDayAttribute(int dayAttribute) {
    this.dayAttribute = dayAttribute;
  }
  @Override
  public String toString() {
    return "DayData [code=" + code + ", day=" + day + ", openPrice=" + openPrice
        + ", closePrice=" + closePrice + ", lowPrice=" + lowPrice
        + ", hightPrice=" + hightPrice + ", volue=" + volue + ", totalComit="
        + totalComit + ", changeRange=" + changeRange + ", riseDownRange="
        + riseDownRange + ", riseDownTotal=" + riseDownTotal + ", changeRate="
        + changeRate + "]";
  }
  public Date getDay() {
    return day;
  }
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public void setDay(Date day) {
    this.day = day;
  }
  public double getOpenPrice() {
    return openPrice;
  }
  public void setOpenPrice(double openPrice) {
    this.openPrice = openPrice;
  }
  public double getClosePrice() {
    return closePrice;
  }
  public void setClosePrice(double closePrice) {
    this.closePrice = closePrice;
  }
  public double getLowPrice() {
    return lowPrice;
  }
  public void setLowPrice(double lowPrice) {
    this.lowPrice = lowPrice;
  }
  public double getHightPrice() {
    return hightPrice;
  }
  public void setHightPrice(double hightPrice) {
    this.hightPrice = hightPrice;
  }
  public int getVolue() {
    return volue;
  }
  public void setVolue(int volue) {
    this.volue = volue;
  }
  public double getTotalComit() {
    return totalComit;
  }
  public void setTotalComit(double totalComit) {
    this.totalComit = totalComit;
  }
  public double getChangeRange() {
    return changeRange;
  }
  public void setChangeRange(double changeRange) {
    this.changeRange = changeRange;
  }
  public double getRiseDownRange() {
    return riseDownRange;
  }
  public void setRiseDownRange(double riseDownRange) {
    this.riseDownRange = riseDownRange;
  }
  public double getRiseDownTotal() {
    return riseDownTotal;
  }
  public void setRiseDownTotal(double riseDownTotal) {
    this.riseDownTotal = riseDownTotal;
  }
  public double getChangeRate() {
    return changeRate;
  }
  public void setChangeRate(double changeRate) {
    this.changeRate = changeRate;
  }
  
//  public static void main(String args[]) {
//     String content =
//     "2023-11-13,7.72,7.94,7.99,7.67,193909,152457674.34,4.13,2.58,0.20,3.82";
//    DayData data = ParseUtil.pareOneData(content);
//    System.out.println( data.toString());
//    
//  }
}
