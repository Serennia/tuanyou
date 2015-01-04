package com.tuanyou.comm.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
/**
 * 
* @ClassName: ReadProperties
* @Description: TODO(properties文件读取)
* @author xf 
* @date 2014年8月19日 下午5:49:15
*
 */
public class ReadProperties {
	private Properties propertie;
	private FileInputStream inputFile;
	private ReadProperties(){
		
	}
	/**
	 * 初始化文件读取
	 * 
	 * @param filePath
	 * @return
	 */
	public ReadProperties(String filePath) {
		propertie = new Properties();
		try {
			inputFile = new FileInputStream(this.getClass().getClassLoader().getResource(filePath).getPath());
			propertie.load(inputFile);
			inputFile.close();
		} catch (FileNotFoundException ex) {
			System.out.println("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("装载文件--->失败!");
			ex.printStackTrace();
		}
	}
	/**
	 * 获取Key值
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		if (propertie.containsKey(key)) {
			String value = propertie.getProperty(key);// 得到某一属性的值
			return value;
		} else
			return "";
	}
	
}
