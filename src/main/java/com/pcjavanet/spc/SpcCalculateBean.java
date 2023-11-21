package com.pcjavanet.spc;

public class SpcCalculateBean {
  private int digitId ;
  private double value ;
  @Override
  public String toString() {
    return "SpcCalculateBean [digitId=" + digitId + ", value=" + value + "]";
  }
  public int getDigitId() {
    return digitId;
  }
  public void setDigitId(int digitId) {
    this.digitId = digitId;
  }
  public double getValue() {
    return value;
  }
  public void setValue(double value) {
    this.value = value;
  }
  
}
