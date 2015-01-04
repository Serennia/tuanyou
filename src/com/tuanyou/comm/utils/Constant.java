package com.tuanyou.comm.utils;

import java.util.ArrayList;
import java.util.List;

public class Constant {
	public static String VALIDATE_CODE_SESS = "validate_code";
	public static String SYS_USER_INFO = "sysUserInfo";
	public static final int MD5_PWD_BYTE_LEN = 32;
	public static final int VALIDATE_CODE_LEN = 4;
	public static final int FATHER_ORGID_LENGTH = 6;
	
	public static final String DEFAULTPSW = "000000";//默认密码 
	public static final String PIC_SUFFIX = ".jpg";//图片后缀
	//有效，无效
	public static final int VALID_YES = 1;//有效
	public static final int VALID_NO= 0;//无效
	//系统角色
	public static final int ROLE_ID_SYSADMIN = 1;// 系统管理员
	//用户锁定将屏蔽的菜单menono
	public static List<String> LOCK_MENU_LIST = new ArrayList<String>();
	static{
		LOCK_MENU_LIST.add("sysdict");//字典管理
		LOCK_MENU_LIST.add("sysrole");//角色管理
		LOCK_MENU_LIST.add("sysright");//角色菜单管理
		LOCK_MENU_LIST.add("sysmenu");//菜单管理
	}
}
