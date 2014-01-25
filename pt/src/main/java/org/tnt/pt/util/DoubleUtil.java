package org.tnt.pt.util;

import java.text.DecimalFormat;

public class DoubleUtil {

	//保留2位小数
	public static Double get2Double(Double a){
	    DecimalFormat df=new DecimalFormat("0.00");
	    return new Double(df.format(a).toString());
	}
	
	public static String getWeightBand(Double weight,String type){
		String weightBand = "";
		if(weight==21){
			weightBand = "21-30";
		}else if(weight==31){
			weightBand = "31-44";
		}else if(weight==45){
			weightBand = "45-70";
		}else if(weight==71){
			weightBand = "71-99";
		}else if(weight==100){
			weightBand = "100-250";
		}else if(weight==251){
			if(type.equals("15N")){
				weightBand = "250+";
			}else{
				weightBand = "251-500";
			}
		}else if(weight==501){
			weightBand = "500+";
		}else{
			
		}
		return weightBand;
	}
}
