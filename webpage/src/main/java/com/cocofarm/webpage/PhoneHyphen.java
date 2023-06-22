package com.cocofarm.webpage;

import java.util.regex.Pattern;

public class PhoneHyphen {

	public static String convertTelNo(String src) {
		
		String mobTelNo = src;

		if (mobTelNo != null) {
			// 일단 기존 - 전부 제거
			mobTelNo = mobTelNo.replaceAll(Pattern.quote("-"), "");

			if (mobTelNo.length() == 11) {
				// 010-1234-1234
				mobTelNo = mobTelNo.substring(0, 3) + "-" + mobTelNo.substring(3, 7) + "-" + mobTelNo.substring(7);

			} else if (mobTelNo.length() == 8) {
				// 1588-1234
				mobTelNo = mobTelNo.substring(0, 4) + "-" + mobTelNo.substring(4);
			} else {
				if (mobTelNo.startsWith("02")) { // 서울은 02-123-1234
					mobTelNo = mobTelNo.substring(0, 2) + "-" + mobTelNo.substring(2, 5) + "-" + mobTelNo.substring(5);
				} else { // 그외는 012-123-1345
					mobTelNo = mobTelNo.substring(0, 3) + "-" + mobTelNo.substring(3, 6) + "-" + mobTelNo.substring(6);
				} 
			}
		}
		return mobTelNo;
	}
}