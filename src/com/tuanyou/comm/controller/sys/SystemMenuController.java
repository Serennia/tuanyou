package com.tuanyou.comm.controller.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.tuanyou.comm.controller.Controller;
import com.tuanyou.comm.service.SystemService;
import com.tuanyou.comm.utils.BaseUtils;

public class SystemMenuController extends Controller {
	private static SystemService systemservice=new SystemService();
	public void getMenus() {
		HttpServletRequest req=getRequest();
		List<Record> menus=systemservice.getUserMenus(req);
		renderJson(menus);
	}
	public void view() {
		render("/pages/system/sysMenu/menu.jsp");
	}
	
	public void addMenu() {
		HttpServletRequest req=getRequest();
		Record record = BaseUtils.getInsertParam(req);
		record.getColumns().put("isVisible", 1);
		record.getColumns().put("isLeaf", 1);
		try {
			Db.save("sys_menu", record);
			renderJson( BaseUtils.getSuccessMessage("添加成功"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson( BaseUtils.getErrorMessage("添加失败"));
		}
	}
	public void updateMenu(){
		HttpServletRequest req=getRequest();
		Record record = BaseUtils.getInsertParam(req);
		String sql = "UPDATE	sys_menu ";
			sql += "SET menuno = ?, menuorder = ?,	menuname = ?,	menuurl = ?,	menuicon = ? ";
			sql += "WHERE  menuid=? ";
		try {
			Db.update(sql, record.get("menuno"),record.get("menuorder"),record.get("menuname"),record.get("menuurl"),record.get("menuicon"),record.get("menuid"));
			renderJson( BaseUtils.getSuccessMessage("修改成功"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson( BaseUtils.getErrorMessage("修改失败"));
		}
	}
	public void delMenu(){
		HttpServletRequest req=getRequest();
		Record record = BaseUtils.getInsertParam(req);
		try {
			int id= Integer.parseInt((String) record.get("menuid"));
			String sql = "DELETE FROM sys_menu WHERE menuid="+id+"  ";
			Db.update(sql);
			
			renderJson( BaseUtils.getSuccessMessage("删除成功"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson( BaseUtils.getErrorMessage("删除失败"));
		}
	}

	public void addButton()  {
		Record record = BaseUtils.getInsertParam(getRequest());
		record.getColumns().put("seqno", 0);
		Db.save("sys_button", record);
		renderJson( BaseUtils.getSuccessMessage("添加按钮成功"));
	}
	
	public void addCUDButtons() {
		String menuno = getPara("menuNo");
		Record r=new Record();
		Map<String, Object> record = new HashMap<String, Object>();
		record.put("btnno", "add");
		record.put("btnName", "新增");
		record.put("btnicon", "resource/icons/silkicons/add.png");
		record.put("menuno", menuno);
		r.setColumns(record);
		Db.save("sys_button", r);
		r=new Record();
		record = new HashMap<String, Object>();
		record.put("btnno", "modify");
		record.put("btnName", "修改");
		record.put("btnicon", "resource/icons/silkicons/application_edit.png");
		record.put("menuno", menuno);
		r.setColumns(record);
		Db.save("sys_button", r);
		r=new Record();
		record = new HashMap<String, Object>();
		record.put("btnno", "delete");
		record.put("btnName", "删除");
		record.put("btnicon", "resource/icons/silkicons/delete.png");
		record.put("menuno", menuno);
		r.setColumns(record);
		Db.save("sys_button", r);
		r=new Record();
		record = new HashMap<String, Object>();
		record.put("btnno", "view");
		record.put("btnName", "查看");
		record.put("btnicon", "resource/icons/silkicons/application_view_detail.png");
		record.put("menuno", menuno);
		r.setColumns(record);
		Db.save("sys_button", r);
		renderJson( BaseUtils.getSuccessMessage("批量添加按钮成功"));
	}

	public void clearButton() {
		String menuNo =getPara("menuNo"); ;
		try {
			Db.deleteById("sys_button", "menuno", menuNo);
			renderJson( BaseUtils.getSuccessMessage("清空按钮成功"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson( BaseUtils.getErrorMessage("清空按钮失败"));
		}
	}
	
	public void delButton()  {
		String ButtonId = getPara("btnID");
		try {
			Db.deleteById("sys_button", "btnid", ButtonId);
			renderJson( BaseUtils.getSuccessMessage("删除按钮成功"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson( BaseUtils.getErrorMessage("删除按钮失败"));
		}
	}
	
	public void updateButton() {
		HttpServletRequest req=getRequest();
		Record record = BaseUtils.getUpdateParam(req);
//		baseService.Update("sys_button", record, "btnid=?", request.getParameter("btnID"));
		Db.update("sys_button", "btnid", record);
		renderJson( BaseUtils.getSuccessMessage("更新按钮成功"));
	}
}
