package com.tuanyou.comm.controller.sys;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.tuanyou.comm.controller.Controller;
import com.tuanyou.comm.service.SystemService;
import com.tuanyou.comm.utils.BaseUtils;

public class SysRoleController extends Controller {
	private static SystemService systemservice=new SystemService();
	public void view() {
		render("/pages/system/sysRole/sysRole.jsp");
	}
	
	
	public void insert() {
		Record record = BaseUtils.getInsertParam(getRequest());
		try {
			Db.save("sys_role", record);
			renderJson( BaseUtils.getSuccessMessage("新增成功"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson( BaseUtils.getErrorMessage("新增失败"));
		}
	}

	public void update(){
		Record record = BaseUtils.getUpdateParam(getRequest());
		try {
			Db.update("sys_role", "id", record);
			renderJson( BaseUtils.getSuccessMessage("修改成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson( BaseUtils.getErrorMessage("修改失败!"));
		}
	}
	
	public void delete(){
		String id=getPara("id");
		try {
			Db.deleteById("sys_role", id);
			renderJson( BaseUtils.getSuccessMessage("删除成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson( BaseUtils.getErrorMessage("删除失败!"));
		}
	}
	
}
