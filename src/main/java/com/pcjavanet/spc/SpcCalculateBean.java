package com.pcjavanet.spc;

public class SpcCalculateBean {
  private int digitId ;
  private double value ;
  private int rule1ValidateResult ;
  private int rule2ValidateResult ;
  private int rule3ValidateResult ;
  private int rule4ValidateResult ;
  private int rule5ValidateResult ;
  private int rule6ValidateResult ;
  private int rule7ValidateResult ;
  private int rule8ValidateResult ;

  public SpcCalculateBean(int digitId, double value) {
    super();
    this.digitId = digitId;
    this.value = value;
  }
  
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

  public void setRule1ValidateResult(int rule1ValidateResult) {
    this.rule1ValidateResult = rule1ValidateResult;
  }

  public void setRule2ValidateResult(int rule2ValidateResult) {
    this.rule2ValidateResult = rule2ValidateResult;
  }

  public void setRule3ValidateResult(int rule3ValidateResult) {
    this.rule3ValidateResult = rule3ValidateResult;
  }

  public void setRule4ValidateResult(int rule4ValidateResult) {
    this.rule4ValidateResult = rule4ValidateResult;
  }

  public void setRule5ValidateResult(int rule5ValidateResult) {
    this.rule5ValidateResult = rule5ValidateResult;
  }

  public void setRule6ValidateResult(int rule6ValidateResult) {
    this.rule6ValidateResult = rule6ValidateResult;
  }

  public void setRule7ValidateResult(int rule7ValidateResult) {
    this.rule7ValidateResult = rule7ValidateResult;
  }

  public void setRule8ValidateResult(int rule8ValidateResult) {
    this.rule8ValidateResult = rule8ValidateResult;
  }

  public int getRule1ValidateResult() {
    return rule1ValidateResult;
  }

  public int getRule2ValidateResult() {
    return rule2ValidateResult;
  }

  public int getRule3ValidateResult() {
    return rule3ValidateResult;
  }

  public int getRule4ValidateResult() {
    return rule4ValidateResult;
  }

  public int getRule5ValidateResult() {
    return rule5ValidateResult;
  }

  public int getRule6ValidateResult() {
    return rule6ValidateResult;
  }

  public int getRule7ValidateResult() {
    return rule7ValidateResult;
  }

  public int getRule8ValidateResult() {
    return rule8ValidateResult;
  }
  
}
