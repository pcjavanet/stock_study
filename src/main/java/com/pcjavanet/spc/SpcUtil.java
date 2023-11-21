package com.pcjavanet.spc;

import java.util.List;

public class SpcUtil {

  public static void main(String[] args) {

  }

  public static double calculateMeaning(List<Double> datas) {
    double total = 0 ; 
    for(double d: datas ) {
      total = total + d ;
    }
    return total/datas.size();
  }

//public void addClosingPrice(double price) {
//closingPrices.add(price);
//calculateControlLimits();
//}
//
//// Here, we're using the simple definition of range (max - min)
//// More sophisticated calculations can be used for larger datasets
//private void calculateControlLimits() {
//if (closingPrices.size() <= 1) return;
//
//double sum = 0.0;
//double max = Collections.max(closingPrices);
//double min = Collections.min(closingPrices);
//
//for (double price : closingPrices) {
//sum += price;
//}
//
//average = sum / closingPrices.size();
//range = max - min;
//
//// These are arbitrary control limits for demonstration
//// Adjust these according to your needs or use proper statistical methods
//upperControlLimit = average + range * 2;
//lowerControlLimit = average - range * 2;
//}
//
//public void printControlChartData() {
//System.out.println("Average: " + average);
//System.out.println("Range: " + range);
//System.out.println("Upper Control Limit: " + upperControlLimit);
//System.out.println("Lower Control Limit: " + lowerControlLimit);
//}
}
