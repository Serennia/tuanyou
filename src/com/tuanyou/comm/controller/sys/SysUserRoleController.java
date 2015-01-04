package com.tuanyou.comm.controller.sys;

import com.jfinal.plugin.activerecord.Db;
import com.tuanyou.comm.controller.Controller;
import com.tuanyou.comm.utils.BaseUtils;
import com.tuanyou.comm.utils.EhCacheUtils;

public class SysUserRoleController extends Controller {
	public void view() {
		String sql="select  orgid,userid,usercode ,username ,createtime   from  stb_customer ";
		EhCacheUtils.setValue("src/ehcache.xml", "systemweb/getgrid", "getuserlist", sql);		
		render( "/pages/system/sysuserrole/SysUserRole.jsp");
	}
	
	public void init(){
		String usercode=getPara("usercode");
		String sql1="select c.userid,c.usercode, c.username,r.name,r.description,c.orgid,ur.roleid ,ur.id " +
				"from sys_user_role   ur  " +
				"right  join stb_customer  c  " +
				"on ur.orgid = c.orgid  " +
				"left join sys_role r " +
				"on r.id = ur.roleid    " +
				"where c.usercode='"+usercode+"'";
		EhCacheUtils.setValue("src/ehcache.xml", "systemweb/getgrid", "getUserRole", sql1);
		renderJson();
	}
	//给角色授权
	public void save(){
		//int id=Integer.parseInt(getPara("sort"));
		String usercode=getPara("usercode");
		int roleid=Integer.parseInt(getPara("role_id"));
		String orgid=getPara("org_id");
		if(usercode=="" || usercode==null ||orgid=="" || orgid==null || roleid==0){
			renderJson( BaseUtils.getErrorMessage("新增失败"));
			return;
		}
		try {
			Db.update(" insert sys_user_role (usercode,roleid,orgid,status) values ('"+usercode+"',"+roleid+",'"+orgid+"','1')");	
			renderJson( BaseUtils.getSuccessMessage("新增成功"));
			
		} catch (Exception e) {
			e.printStackTrace();
			renderJson( BaseUtils.getErrorMessage("新增失败"));
		}
	}
	public void nodify(){
		int roleid=Integer.parseInt(getPara("role_id"));
		int id=Integer.parseInt(getPara("id"));
		if(roleid==0 || id==0 ){
			renderJson( BaseUtils.getErrorMessage("修改失败"));
			return;
		}
		try {
			String sql ="UPDATE  sys_user_role SET  roleid = "+roleid+" WHERE id = "+id+"  ";
			Db.update(sql);	
			renderJson( BaseUtils.getSuccessMessage("修改成功"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson( BaseUtils.getErrorMessage("修改失败"));
		}
		
	}
	public void delete(){
		String usercode=getPara("usercode");
		String roleid=getPara("roleid");
		if(usercode=="" || usercode==null ||roleid=="" || roleid==null ){
			renderJson( BaseUtils.getErrorMessage("删除失败"));
			return;
		}
		try {
			String sql =" delete from sys_user_role where usercode = '"+usercode+"'  and roleid = "+roleid+"";
			Db.update(sql);	
			renderJson( BaseUtils.getSuccessMessage("删除成功"));
			
		} catch (Exception e) {
			e.printStackTrace();
			renderJson( BaseUtils.getErrorMessage("删除失败"));
		}
	}
	

}
