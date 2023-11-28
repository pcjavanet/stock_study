package com.pcjavanet.spc;

public class IChart {
  private double cl = 0.0;

  private double mrbar = 0.0;

  private double d2 = 0.0;

  public IChart(int size, double cl, double mrbar) {
    super();
    this.cl = cl;
    this.mrbar = mrbar;
    d2 = this.getD2(size);
  }

  private double getD2(int size) {
    return ControlConstant.getD2(size);
  }

  public double getCL() {
    return this.cl;
  }

  public double getLcl() {
    /*
    double cl = this.getCL();
    double lcl = cl - 3 * mrbar / d2;
    return lcl;
    */
    double lclNum = 3.0;
    double lcl = getDownSigma(lclNum);
    return lcl;
  }

  public double getUcl() {
    /*
    double cl = this.getCL();
    double ucl = cl + 3 * mrbar / d2;
    return ucl;*/
    double uclNum = 3.0;
    double ucl = getUpSigma(uclNum);
    return ucl;
  }

  public double getUpSigma(double sigmaNum) {
    double cl = this.getCL();
    double upSigma = cl + mrbar * sigmaNum / d2;
    return upSigma;
  }

  public double getDownSigma(double sigmaNum) {
    double cl = this.getCL();
    double downSigma = cl - mrbar * sigmaNum / d2;
    return downSigma;
  }
}
