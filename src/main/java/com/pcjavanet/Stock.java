package com.pcjavanet;

import java.util.Date;

public class Stock {
  private int type ; 
  private String code;  
  private String name ; 
  private Date lastUpdateDate ;
  private int status ;
  
  @Override
  public String toString() {
    return "Stock [type=" + type + ", code=" + code + ", name=" + name
        + ", lastUpdateDate=" + lastUpdateDate + ", status=" + status + "]";
  }
  public int getType() {
    return type;
  }
  public void setType(int type) {
    this.type = type;
  }
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Date getLastUpdateDate() {
    return lastUpdateDate;
  }
  public void setLastUpdateDate(Date lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate;
  }
  public int getStatus() {
    return status;
  }
  public void setStatus(int status) {
    this.status = status;
  } 
  
}
