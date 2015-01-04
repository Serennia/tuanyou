package com.tuanyou.comm.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;
/**
 * 
* @ClassName: SysUserInfo
* @Description: TODO(系统用户信息,登录后与Session 一起保存在缓存中)
* @author xf 
* @date 2014年8月19日 下午5:40:41
*
 */
public class SysUserInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userid; // 用户名
	private String usercode;// 用户编号
	private String username; // 用户姓名
	private String roleid; // 当前角色编号
	private String roleName;// 当前角色名称
	private String ip; // 登录IP地址
	private String logintime;// 登录时间
	private String ticket; // 票据

	private boolean adminFlag; // 是否是超级管理员
	
	private List<Record> MenuResource;
	private Map<String, ArrayList> MenuButtons;

	private String curOrgid; // 当前用户所在的组织机构(可能为年级,班级)
	private String orgid; // 用户所在一级的组织机构
	private String orgidname;//用户所在一级的组织机构的名称
	private Integer areano; // 用户所在的区域
	private String dataOrgid; // 角色数据权限orgid
	
	private String carid; // 电动车厂商id
	
	private String childorgids; // 经销商下属组织机构

	private String dominationids;//管辖orgids
	private boolean locked;
	
	private List<Map>SearchParms;
	
	
	private String usertype;
	
	private String SearchDescription;
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	

	public List<Record> getMenuResource() {
		return MenuResource;
	}

	public void setMenuResource(List<Record> menuResource) {
		MenuResource = menuResource;
	}

	public Map<String, ArrayList> getMenuButtons() {
		return MenuButtons;
	}

	public void setMenuButtons(Map<String, ArrayList> menuButtons) {
		MenuButtons = menuButtons;
	}

	public String getLogintime() {
		return logintime;
	}

	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getCurOrgid() {
		return curOrgid;
	}

	public void setCurOrgid(String curOrgid) {
		this.curOrgid = curOrgid;
	}

	public Integer getAreano() {
		return areano;
	}

	public void setAreano(Integer areano) {
		this.areano = areano;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getDataOrgid() {
		return dataOrgid;
	}

	public void setDataOrgid(String dataOrgid) {
		this.dataOrgid = dataOrgid;
	}

	public boolean isAdminFlag() {
		return adminFlag;
	}

	public void setAdminFlag(boolean adminFlag) {
		this.adminFlag = adminFlag;
	}

	public String getOrgidname() {
		return orgidname;
	}

	public void setOrgidname(String orgidname) {
		this.orgidname = orgidname;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public List<Map> getSearchParms() {
		return SearchParms;
	}

	public void setSearchParms(List<Map> searchParms) {
		SearchParms = searchParms;
	}

	public String getSearchDescription() {
		return SearchDescription;
	}

	public void setSearchDescription(String searchDescription) {
		SearchDescription = searchDescription;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getCarid() {
		return carid;
	}

	public void setCarid(String carid) {
		this.carid = carid;
	}

	public String getChildorgids() {
		return childorgids;
	}

	public void setChildorgids(String childorgids) {
		this.childorgids = childorgids;
	}

	public String getDominationids() {
		return dominationids;
	}

	public void setDominationids(String dominationids) {
		this.dominationids = dominationids;
	}

	
}
