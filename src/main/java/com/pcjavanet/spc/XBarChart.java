package com.pcjavanet.spc;

public class XBarChart {
    private double cl ; 
    private double rbar ;
    private double a2 ;
    public XBarChart(double cl, double rbar, int size) {
        this.cl = cl;
        this.rbar = rbar;
        this.a2 = this.getA2(size);
    }
    
    private double getA2(int size) {
        return ControlConstant.getA2(size);
    }

    /** R chart Bar value*/
    public double getMeanR() {
        return this.rbar;
    }
    /** Xbar chart Bar value*/
    public double getCL() {
        return this.cl;
    }

    public double getLCL() {
        double cl = this.getCL();
        double lcl = cl - 3 * rbar / a2;
        return lcl;
    }

    public double getUCL() {
        double sigmaNum = SpcConstant.SPC_UCL_SIGMA_NUM ;
        double ucl = getUpSigma(sigmaNum);
        return ucl;
    }

    public double getUpSigma(double sigmaNum) {
        double cl = this.getCL();
        double upThreeSigma = a2 * this.getMeanR();
        double upSigma = cl +  upThreeSigma * sigmaNum/SpcConstant.SPC_UCL_SIGMA_NUM;
        return upSigma;
    }

    public double getDownSigma(double sigmaNum) {
        double cl = this.getCL();
        double downThreeSigma = a2 * this.getMeanR();
        double downSigma = cl - downThreeSigma * sigmaNum/SpcConstant.SPC_UCL_SIGMA_NUM;
        return downSigma;
    
}
