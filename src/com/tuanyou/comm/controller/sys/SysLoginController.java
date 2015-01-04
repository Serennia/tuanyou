package com.tuanyou.comm.controller.sys;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tuanyou.comm.bean.SysUserInfo;
import com.tuanyou.comm.controller.Controller;
import com.tuanyou.comm.service.SystemService;
import com.tuanyou.comm.utils.Constant;


public class SysLoginController extends Controller{
	private static SystemService systemservice=new SystemService();
	
	public void doLogin(){
		HttpServletRequest request=getRequest();
		HttpSession session = request.getSession(true);
//		// 验证验证码是否正确
//		String validate_code = session.getAttribute(Constant.VALIDATE_CODE_SESS) == null ? "" : String.valueOf(session
//				.getAttribute(Constant.VALIDATE_CODE_SESS));
//		if (!validate_code.equals(getPara("validatecode"))) {
//			// 验证码错误
//			renderJson( "-1");
//		}
		// 验证账户密码是否正确
		SysUserInfo info = systemservice.validateLogin(getPara("username"), getPara("password"));
		if (info == null) {
			// 用户名或者密码错误
			renderJson( "0");
		}else{
			//如果是系统管理员
			info.setAdminFlag(Integer.parseInt(info.getRoleid())==Constant.ROLE_ID_SYSADMIN ?  true : false);
			session.setAttribute(Constant.SYS_USER_INFO, info);
			session.setMaxInactiveInterval(120*60);
			renderJson( "1");
		}
	
	}

}
