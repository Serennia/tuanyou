package com.tuanyou.comm.test;

public class TestSubString {
	public static void main(String[] args) {
		String url="F:/soft/apache-tomcat-6.0.18/webapps/starfile/img/huandengpian/20150212152306_755.jpg";
		url=new String(url.substring(url.indexOf("/starfile/")+10, url.length()));
		System.out.println(url);
	}
}
