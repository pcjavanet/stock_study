package com.pcjavanet.spc;

import java.util.List;

public class SpcUtil {

  public static void main(String[] args) {

  }

  public static double calculateMeaning(List<Double> datas) {
    double total = 0;
    for (double d : datas) {
      total = total + d;
    }
    return total / datas.size();
  }

  public static double calRange(List<Double> datas) {
    double r = 0.0;
    for (int i = 0; i < datas.size(); i++) {
      for (int j = i + 1; j < datas.size(); j++) {
        if (r < Math.abs(datas.get(i) - datas.get(j))) {
          r = Math.abs(datas.get(i) - datas.get(j));
        }
      }
    }
    return r;
  }

  public void nonGreateWallValidate() {

  }

  /** Name:Beyond Limits,例如超出3 sigma控制线 */
  public void rule1(List<SpcCalculateBean> datas, double upUcl, double lowLcl) {
    for (SpcCalculateBean d : datas) {
      if (d.getValue() > upUcl || d.getValue() < lowLcl) {
        d.setRule1ValidateResult(SpcConstant.SPC_RULE_VALIDATE_FAIL);
      }
    }
  }

  /**
   * Nmae:Zone A.2/3的点距离中心线的距离超过2个sigma(同一侧)
   * datas, order by ASC
   * CL --------------
   * *
   * L2CL -----------------
   * * *
   * L3CL-------------------
   */
  public void rule2(List<SpcCalculateBean> datas, int zoneANum, int checkNum, double upTwoSigma, double lowTwoSigma) {
    for (int i = 0, size = datas.size(); i < size; i++) {
      if (i + checkNum < size) {
        // check upTwoSigma
        boolean find = false;
        int invalidateNum = 0;
        for (int j = i; j < checkNum; j++) {
          if (datas.get(j).getValue() > upTwoSigma) {
            invalidateNum = invalidateNum + 1;
          }
        }
        if (invalidateNum >= zoneANum) {
          datas.get(i + checkNum - 1).setRule2ValidateResult(SpcConstant.SPC_RULE_VALIDATE_FAIL);
          find = true;
        }
        // check lowTwoSigma
        if (find == false) {
          invalidateNum = 0;
          for (int j = i; j < checkNum; j++) {
            if (datas.get(j).getValue() < lowTwoSigma) {
              invalidateNum = invalidateNum + 1;
            }
          }
          if (invalidateNum >= zoneANum) {
            datas.get(i + checkNum - 1).setRule2ValidateResult(SpcConstant.SPC_RULE_VALIDATE_FAIL);
          }
        }
      }
    }
  }

  /** Name:Zone B. 4/5的点距离中心线的距离超过1个sigma(同一侧) */
  public void rule3(List<SpcCalculateBean> datas, int zoneBNum, int checkNum, double upOneSigma, double lowOneSigma) {
    for (int i = 0, size = datas.size(); i < size; i++) {
      if ((i + checkNum) < size) {
        // check upTwoSigma
        boolean find = false;
        int invalidateNum = 0;
        for (int j = i; j < checkNum; j++) {
          if (datas.get(j).getValue() > upOneSigma) {
            invalidateNum = invalidateNum + 1;
          }
        }
        if (invalidateNum >= zoneBNum) {
          datas.get(i + checkNum - 1).setRule3ValidateResult(SpcConstant.SPC_RULE_VALIDATE_FAIL);
          find = true;
        }
        // check lowTwoSigma
        if (find == false) {
          invalidateNum = 0;
          for (int j = i; j < checkNum; j++) {
            if (datas.get(j).getValue() < lowOneSigma) {
              invalidateNum = invalidateNum + 1;
            }
          }
          if (invalidateNum >= zoneBNum) {
            datas.get(i + checkNum - 1).setRule3ValidateResult(SpcConstant.SPC_RULE_VALIDATE_FAIL);
          }
        }
      }
    }

  }

  /** Name:Zone C.连续7个点位于中心线的同一侧 */
  public void rule4(List<SpcCalculateBean> datas, int checkNum, double centralValue, double upLimit, double lowLimit) {
    for (int i = 0, size = datas.size(); i < size; i++) {
      if ((i + checkNum) < size) {
        boolean upSideCheckResult = false;
        int invalidateNum = 0;
        for (int j = i; j < checkNum; j++) {
          if (datas.get(j).getValue() > centralValue && datas.get(j).getValue() < upLimit) {
            invalidateNum = invalidateNum + 1;
          }
        }
        if (invalidateNum >= checkNum) {
          datas.get(i + checkNum - 1).setRule4ValidateResult(SpcConstant.SPC_RULE_VALIDATE_FAIL);
          upSideCheckResult = true;
        }
        if (upSideCheckResult == false) {
          invalidateNum = 0;
          for (int j = i; j < checkNum; j++) {
            if (datas.get(j).getValue() < centralValue && datas.get(j).getValue() > lowLimit) {
              invalidateNum = invalidateNum + 1;
            }
          }
          if (invalidateNum >= checkNum) {
            datas.get(i + checkNum - 1).setRule4ValidateResult(SpcConstant.SPC_RULE_VALIDATE_FAIL);
          }
        }
      }
    }
  }

  /* Name:Trend.连续7个点连续上升，或者下降 */
  public void rule5(List<SpcCalculateBean> datas, int checkNum) {
    for (int i = 0, size = datas.size(); i < size; i++) {
      if ((i + checkNum) < size) {
        boolean lowToUp = false;
        int invalidateNum = 1;
        for (int j = i; j < (checkNum - 1); j++) {
          if (datas.get(j).getValue() > datas.get(j + 1).getValue()) {
            invalidateNum = invalidateNum + 1;
          }
        }
        if (invalidateNum >= checkNum) {
          datas.get(i + checkNum - 1).setRule5ValidateResult(SpcConstant.SPC_RULE_VALIDATE_FAIL);
          lowToUp = true;
        }
        if (lowToUp == false) {
          invalidateNum = 1;
          for (int j = i; j < (checkNum - 1); j++) {
            if (datas.get(j).getValue() < datas.get(j + 1).getValue()) {
              invalidateNum = invalidateNum + 1;
            }
          }
          if (invalidateNum >= checkNum) {
            datas.get(i + checkNum - 1).setRule5ValidateResult(SpcConstant.SPC_RULE_VALIDATE_FAIL);
          }
        }
      }
    }
  }

  /** Name:Mixture.连续8个点距离中心线的距离大于一个sigma(任一侧) */
  public void rule6(List<SpcCalculateBean> datas, int checkNum, double upLimit, double lowLimit) {
    for (int i = 0, size = datas.size(); i < size; i++) {
      if ((i + checkNum) < size) {
        int invalidateNum = 0;
        for (int j = i; j < checkNum; j++) {
          if (datas.get(j).getValue() > upLimit || datas.get(j).getValue() < lowLimit) {
            invalidateNum = invalidateNum + 1;
          }
        }
        if (invalidateNum >= checkNum) {
          datas.get(i + checkNum - 1).setRule6ValidateResult(SpcConstant.SPC_RULE_VALIDATE_FAIL);
        }
      }
    }
  }

  /** Name:Stratification,连续15个点在1个sigma以内(任一侧) */
  public void rule7(List<SpcCalculateBean> datas, int checkNum, double upLimit, double lowLimit) {
    for (int i = 0, size = datas.size(); i < size; i++) {
      if ((i + checkNum) < size) {
        int invalidateNum = 0;
        for (int j = i; j < checkNum; j++) {
          if (datas.get(j).getValue() > upLimit || datas.get(j).getValue() < lowLimit) {
            invalidateNum = invalidateNum + 1;
          }
        }
        if (invalidateNum >= checkNum) {
          datas.get(i + checkNum - 1).setRule7ValidateResult(SpcConstant.SPC_RULE_VALIDATE_FAIL);
        }
      }
    }
  }

  /** Name:Over-control, 连续14个点交替上下变化 */
  public void rule8(List<SpcCalculateBean> datas, int checkNum) {
    for (int i = 0, size = datas.size(); i < size; i++) {
      if ((i + checkNum) < size) {
        int invalidateNum = 1;
        boolean beginLowFlag = true;

        int k = 0;
        for (int j = i; j < (checkNum - 1); j++) {
          boolean toAscFlag = false;
          if (datas.get(j).getValue() < datas.get(j + 1).getValue()) {
            toAscFlag = true;
          }
          if (k == 0) {
            if (toAscFlag) {
              beginLowFlag = true;
            } else {
              beginLowFlag = false;
            }
          }
          k = k + 1;
          int modResult = k / 2;
          if (beginLowFlag) {
            if (modResult == 0) {
              if (toAscFlag) {
                invalidateNum = invalidateNum + 1;
              }
            } else {
              if (toAscFlag == false) {
                invalidateNum = invalidateNum + 1;
              }
            }
          } else {
            if (modResult == 1) {
              if (toAscFlag) {
                invalidateNum = invalidateNum + 1;
              }
            } else {
              if (toAscFlag == false) {
                invalidateNum = invalidateNum + 1;
              }
            }
          }
        }
        if (invalidateNum >= checkNum) {
          datas.get(i + checkNum - 1).setRule8ValidateResult(SpcConstant.SPC_RULE_VALIDATE_FAIL);
        }
      }
    }
  }
  // public void addClosingPrice(double price) {
  // closingPrices.add(price);
  // calculateControlLimits();
  // }
  //
  //// Here, we're using the simple definition of range (max - min)
  //// More sophisticated calculations can be used for larger datasets
  // private void calculateControlLimits() {
  // if (closingPrices.size() <= 1) return;
  //
  // double sum = 0.0;
  // double max = Collections.max(closingPrices);
  // double min = Collections.min(closingPrices);
  //
  // for (double price : closingPrices) {
  // sum += price;
  // }
  //
  // average = sum / closingPrices.size();
  // range = max - min;
  //
  //// These are arbitrary control limits for demonstration
  //// Adjust these according to your needs or use proper statistical methods
  // upperControlLimit = average + range * 2;
  // lowerControlLimit = average - range * 2;
  // }
  //
  // public void printControlChartData() {
  // System.out.println("Average: " + average);
  // System.out.println("Range: " + range);
  // System.out.println("Upper Control Limit: " + upperControlLimit);
  // System.out.println("Lower Control Limit: " + lowerControlLimit);
  // }
}
