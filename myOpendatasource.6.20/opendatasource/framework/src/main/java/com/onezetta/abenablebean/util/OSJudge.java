package com.onezetta.abenablebean.util;

public class OSJudge {
	public static boolean isWin(){
	    String os = System.getProperty("os.name");  
	    if(os.toLowerCase().startsWith("win")){  
	    	return true;
	    }else
	    	return false;
	}
}
