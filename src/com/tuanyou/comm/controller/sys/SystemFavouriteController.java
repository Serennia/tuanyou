package com.tuanyou.comm.controller.sys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tuanyou.comm.controller.Controller;
import com.tuanyou.comm.service.SystemService;


public class SystemFavouriteController extends Controller{
	private static SystemService systemservice=new SystemService();
	
	public void add(){
		HttpServletRequest req=getRequest();
		Map<String, Object> map=systemservice.FavouriteAdd(req);
		renderJson(map);
	}
	
	public void load(){
		HttpServletRequest req=getRequest();
		Map<String, Object> map=systemservice.Favouriteload(req);
		renderJson(map);
	}
	
	public void delete(){
		HttpServletRequest req=getRequest();
		Map<String, Object> map=systemservice.deleteFavouriteload(req);
		renderJson(map);
	}
}
