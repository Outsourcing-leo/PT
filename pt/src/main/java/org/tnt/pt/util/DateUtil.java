package org.tnt.pt.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public  class DateUtil {
	
	public static String getStringFromDate(Date date,String pattern){
		return new SimpleDateFormat(pattern).format(date);
	}
	
	
}
