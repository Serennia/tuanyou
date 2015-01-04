package com.tuanyou.comm.controller.sys;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.tuanyou.comm.controller.Controller;
import com.tuanyou.comm.service.SystemService;
import com.tuanyou.comm.utils.BaseUtils;

public class SysDictController extends Controller{
	private static SystemService systemservice=new SystemService();
	public void view() {
		String orgid=BaseUtils.getLoginOrgid(getRequest());
		setAttr("orgid", orgid);
		render("/pages/system/sysdict/SysDict.jsp");
	}
	
	
	public void getDictTypes() {
		String key=getPara("key");
		List<Record>  dicttypes = null;
		if(null == key || "".equals(key)){
			dicttypes = Db.find("select distinct type from sys_dict");
		}else{
			dicttypes = Db.find("select distinct type from sys_dict where type like '"+key+"%'");
		}
		
		for (Record type : dicttypes) {
			type.getColumns().put("id", type.get("type"));
			type.getColumns().put("text", type.get("type"));
		}
		renderJson(dicttypes);
	}

	
	public void insertDict()     {
		Record record = BaseUtils.getInsertParam(getRequest());
		record.remove("id");
		try {
			 Db.save("sys_dict", record);
			 renderJson( BaseUtils.getSuccessMessage("添加成功"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson( BaseUtils.getErrorMessage("添加失败，系统发生异常"));
		}
	}

	public void updateDict() {
		Record record = BaseUtils.getUpdateParam(getRequest());
		try {
//			Db.Update("sys_dict", record, "id=?", request.getParameter("id"));
			
			Db.update("sys_dict",  record);
			renderJson( BaseUtils.getSuccessMessage("更新成功"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson( BaseUtils.getErrorMessage("更新失败"));
		}
	}
	
	
	
	public void delDict() {
		String ids=getPara("id");
		try{
			Db.update("delete from sys_dict where id in ("+ids+")");
			renderJson( BaseUtils.getSuccessMessage("删除成功"));
		}catch(Exception e){
			e.printStackTrace();
			renderJson( BaseUtils.getErrorMessage("删除失败"));
		}
		
		
	}
	
}
