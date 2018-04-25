package com.juyo.visa.admin.weixinToken.entities;

import java.io.Serializable;
import lombok.Data;

/**
 * WeixinAccessToken类
 * 
 * @author ThinkPad.H
 *
 */
@Data
public class WeixinAccessToken implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String accessToken;  
	
    private long expirationTime;  
      
    public WeixinAccessToken(){  
          
    }  
  
   public WeixinAccessToken(String accessToken,long expirationTime){  
      this.accessToken=accessToken;  
      this.expirationTime=expirationTime;  
    }  
}
