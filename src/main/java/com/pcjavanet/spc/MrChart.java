package com.pcjavanet.spc;

public class MrChart {
  private double cl = 0.0;

  private double d3 = 0.0;

  private double d4 = 0.0;

  public MrChart(int size, double cl) {
    super();
    this.cl = cl;
    this.d3 = getD3(size);
    this.d4 = getD4(size);
  }

  private double getD3(int size) {
    return ControlConstant.getD3(size);
  }

  private double getD4(int size) {
    return ControlConstant.getD4(size);
  }
  public double getCL() {
    return this.cl;
  }

  public double getLcl() {
    double cl = this.getCL();
    double lcl = d3 * cl;

    return lcl;
  }

  public double getUcl() {
    double cl = this.getCL();
    double ucl = d4 * cl;
    return ucl;
  }
  
}
