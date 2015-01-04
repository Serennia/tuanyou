package com.tuanyou.comm.controller.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.tuanyou.comm.controller.Controller;
import com.tuanyou.comm.service.SystemService;
import com.tuanyou.comm.utils.BaseUtils;

public class SysAclController extends Controller{
	private static SystemService systemservice=new SystemService();
	public void view() {
		render("/pages/system/sysacl/sysacl.jsp");
	}
	
	public void getAccess() {
		List<Record> menuRecords = Db.find("select  *  from sys_menu order by menuorder");
		List<Record> buttonRecords = Db.find("select  *  from sys_button");
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		// 第一级节点数据
		List<Record> parentMenuRecord = new ArrayList<Record>();
		for (Record temp : menuRecords) {
			if ("0".equals(temp.get("menuparentno"))) {
				parentMenuRecord.add(temp);
			}
		}
		// 循环二级菜单
		for (Record parentmenu : parentMenuRecord) {
			Map<String, Object> menu = ConvertMenu(parentmenu);
			List<Map<String, Object>> submenus = new ArrayList<Map<String, Object>>();
			for (Record menurecord : menuRecords) {
				if (parentmenu.get("menuno").equals(
						menurecord.get("menuparentno"))) {
					Map<String, Object> submenu = ConvertMenu(menurecord);
					submenu.put(
							"children",
							ConvertMenuButtonssubmenu(menurecord, buttonRecords));
					submenus.add(submenu);
				}
			}
			menu.put("children", submenus);
			data.add(menu);
		}
		renderJson( BaseUtils.getSuccessMessage(data));
	}
	

	/**
	 * 转换成Menu 所需要的格式
	 * 
	 * @param menu
	 * @return
	 */
	private Map<String, Object> ConvertMenu(Record menu) {
		Map<String, Object> cmenu = new HashMap<String, Object>();
		cmenu.put("AccessName", menu.getColumns().get("menuname"));
		cmenu.put("AccessIcon", menu.getColumns().get("menuicon"));
		cmenu.put("AccessNo", menu.getColumns().get("menuno"));
		cmenu.put("MenuID", menu.getColumns().get("menuid"));
		cmenu.put("BtnID", 0);
		return cmenu;
	}
	
	/**
	 * 在菜单数据中加入按钮
	 * 
	 * @param submenu
	 * @param buttonRecords
	 * @return
	 */
	private List<Map<String, Object>> ConvertMenuButtonssubmenu(
			Record submenu, List<Record> buttons) {
		List<Map<String, Object>> mybuttons = new ArrayList<Map<String, Object>>();
		for (Record button : buttons) {
			if (button.get("menuno").equals(submenu.get("menuno"))) {
				Map<String, Object> mybutton = new HashMap<String, Object>();
				mybutton.put("AccessName", button.get("btnname"));
				mybutton.put("AccessIcon", button.get("btnicon"));
				mybutton.put("AccessNo", button.get("btnno"));
				mybutton.put("MenuID", 0);
				mybutton.put("BtnID", button.get("btnid"));
				mybuttons.add(mybutton);
			}
		}
		return mybuttons;
	}
	
	
	public void getRolePermission() {
		String roleid=getPara("roleid");
		List<Record> privilegeList = Db.find("select  * from sys_acl where privilegemaster='role' and privilegemasterkey=?", roleid);
		if(privilegeList==null||privilegeList.size()==0){
			renderJson( BaseUtils.getSuccessMessage(null));
		}
		
		List<Map<String,Object>> diclist = new ArrayList<Map<String,Object>>();
		for(Record privilege:privilegeList ){
			Map<String,Object> temp = new HashMap<String,Object>();
			if("sys_menu".equals(privilege.get("privilegeaccess"))){
				temp.put("MenuID",privilege.get("privilegeaccesskey") );
				temp.put("BtnID", 0);
			}else{
				temp.put("MenuID",0);
				temp.put("BtnID", privilege.get("privilegeaccesskey") );
			}
			diclist.add(temp);
		}
		renderJson( BaseUtils.getSuccessMessage(diclist));
	}
	
	@Before(Tx.class)
	public void saveRolePermission() {
		boolean istrue = true;
		String jsondata=getPara("DataJSON");
		String roleid=getPara("RoleID");
		List<Map> permissions = JSON.parseArray(jsondata, Map.class);
		try {
			Db.update("delete from sys_acl  where privilegemaster='role' and privilegemasterkey=?", roleid);
			for (Map item : permissions) {
				// 是否分配权限
				boolean Permit = Boolean
						.valueOf(item.get("Permit") == null ? "false" : item
								.get("Permit").toString());
				// 是否按钮
				boolean isButton = Integer
						.valueOf(item.get("BtnID") == null ? "0" : item.get(
								"BtnID").toString()) != 0;

				int MenuID = Integer.valueOf(item.get("MenuID") == null ? "0"
						: item.get("MenuID").toString());
				int BtnID = Integer.valueOf(item.get("BtnID") == null ? "0"
						: item.get("BtnID").toString());
				if (Permit) {
					Map<String, Object> record = new HashMap<String, Object>();
					record.put("privilegemaster", "role");
					record.put("privilegemasterkey", roleid);
					record.put("privilegeaccess", isButton ? "sys_button"
							: "sys_menu");
					record.put("privilegeaccesskey", isButton ? BtnID : MenuID);
					record.put("privilegeoperation", 1);
					Record r=new Record();
					r.setColumns(record);
//					baseService.Insert("sys_acl_base", record);
					Db.save("sys_acl", r);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			istrue = false;
		}
		renderJson( istrue ? BaseUtils.getSuccessMessage("授权成功") : BaseUtils
				.getErrorMessage("授权失败，系统发生异常"));
	}
}
