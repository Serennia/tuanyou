
package com.tuanyou.comm.utils;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
* @ClassName: MD5Util
* @Description: TODO(MD5实现类)
* @author xf 
* @date 2014年8月19日 下午5:48:53
*
 */
public class MD5Util implements Serializable {

	
	/**
	 * 验证输入的密码是否正确 
	 * @LiCaiRong 2011-12-19
	 * @param actualPwd 真正的密码（加密后的真密码）
	 * @param inputPwd
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean authenticatePassword(String actualPwd, String inputPwd) throws Exception {
		return actualPwd.equals(encodeByMD5(Constant.MD5_PWD_BYTE_LEN,inputPwd));
	}
	
	/**	
	 * MD5算法加密 -->不可配KEY(系统所用的加密方式)
	 * @LiCaiRong 2012-01-04
	 * @param sourcePwd 未加密的密码
	 * @return 加密的密文
	 * @throws Exception
	 */
	public static String encodeByMD5(int type, String sourcePwd) {
		String mdcode = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sourcePwd.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer();
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;// 2/8
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			if (type == 16) {
				mdcode = buf.toString().substring(8, 24);
			} else if (type == 32) {
				mdcode = buf.toString();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return mdcode;
	}

	public static void main(String[] args) {
		System.out.println(encodeByMD5(32,"123"));
	}


	/** 
	 * 将一个字节转化成16进制形式的字符串
	 * @LiCaiRong 2011-12-19
	 * @param bt 字节数组
	 * @return 十六进制字串 
	 */
	public static String byteToHexString(byte bt,String[] hexDigits) {
		int n = bt;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	
	

}
