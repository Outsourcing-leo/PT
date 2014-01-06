package org.tnt.pt.util;

import java.text.DecimalFormat;

public class DoubleUtil {

	//保留2位小数
	public static Double get2Double(Double a){
	    DecimalFormat df=new DecimalFormat("0.00");
	    return new Double(df.format(a).toString());
	}
}
