package com.tuanyou.comm.controller.sys;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.plugin.activerecord.Record;
import com.tuanyou.comm.controller.Controller;
import com.tuanyou.comm.service.SystemService;

public class SystemWebController extends Controller {
	private static SystemService systemservice=new SystemService();

	public void getAllSystemDict() {
		Map<String, List<Record>> mapdict=systemservice.getAllSystemDict();
		renderJson(mapdict);
	}

	public void getSelectOrgs() {
		HttpServletRequest req=getRequest();
		Map<String, List<Record>> orgmap=systemservice.getSelectOrgs(req);
		renderJson(orgmap);
	}
	
	public void getTree() throws Exception {
		HttpServletRequest req=getRequest();
		List<Map<String, Object>> rsTree=systemservice.getTree(req);
		renderJson(rsTree);
	}
	
	public void doChangePassword(){
		HttpServletRequest req=getRequest();
		Map<String, Object> map=systemservice.doChangePassword(req);
		renderJson(map);
	}
	
	public void doLoginOut(){
		HttpServletRequest req=getRequest();
		Map<String, Object> map=systemservice.doLoginOut(req);
		renderJson(map);
		
	}
	
	public void getGrid(){
		HttpServletRequest req=getRequest();
		Map<String, Object> map=systemservice.getGrid(req);
		renderJson(map);
	}
	
	public void getMyButtons(){
		HttpServletRequest req=getRequest();
		Map<String, Object> map=systemservice.getMyButtons(req);
		renderJson(map);
	}
	

	public void getIcons(){
		HttpServletRequest req=getRequest();
		Map<String, Object> map=systemservice.getIcons(req);
		renderJson(map);
	}
	
	
	public void getTreeGrid(){
		HttpServletRequest req=getRequest();
		Map<String, Object> map=systemservice.getTreeGrid(req);
		renderJson(map);
	}
	
	public void getSelect(){
		HttpServletRequest req=getRequest();
		List<Record> l=systemservice.getSelect(req);
		renderJson(l);
	}
	
}
