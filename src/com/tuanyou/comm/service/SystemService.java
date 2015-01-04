package com.tuanyou.comm.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tuanyou.comm.bean.FilterGroup;
import com.tuanyou.comm.bean.FilterTranslator;
import com.tuanyou.comm.bean.SqlBean;
import com.tuanyou.comm.bean.SysUserInfo;
import com.tuanyou.comm.controller.Service;
import com.tuanyou.comm.utils.BaseUtils;
import com.tuanyou.comm.utils.Constant;
import com.tuanyou.comm.utils.EhCacheUtils;
import com.tuanyou.comm.utils.MD5Util;
/**
 * 
* @ClassName: SystemService
* @Description: TODO(公共方法service)
* @author xf 
* @date 2014年7月4日 上午9:05:49
*
 */
public class SystemService extends Service{
/**
 * 
* @Title: validateLogin
* @author xf 
* @Description: TODO(验证用户名密码)
* @param @param username
* @param @param password
* @param @return   
* @return SysUserInfo    返回类型
* @throws
 */
	public SysUserInfo validateLogin(String username, String password) {
		SysUserInfo info = new SysUserInfo();
		try {
			String sql = "select t1.*,t2.name rolename,t3.roleid,t3.orgid dataorgid,t3.status lockstatus,t4.orgname  from stb_customer t1,sys_role t2 ,sys_user_role t3 ,stb_org  t4 where (t1.userid=? or t1.usercode=? ) and t1.password=? "
					+ " and t1.usercode = t3.usercode and t2.id = t3.roleid and t4.ORGID=t1.orgid ";
			List<Record> list=Db.find(sql, new Object[] { username, username, MD5Util.encodeByMD5(Constant.MD5_PWD_BYTE_LEN, password) });
			if (list == null || list.isEmpty()) {
				return null;
			}else{
				Record map = list.get(0);
				info.setUserid(map.get("userid") == null ? null : String.valueOf(map.get("userid")));
				info.setUsercode(String.valueOf(map.get("usercode")));
				info.setUsername(String.valueOf(map.get("username")));
				info.setRoleid(String.valueOf(map.get("roleid")));
				info.setRoleName(String.valueOf(map.get("rolename")));
				info.setCurOrgid(String.valueOf(map.get("orgid")));
				info.setOrgid(info.getCurOrgid());
				info.setDataOrgid(map.get("dataorgid") == null ? "" : String.valueOf(map.get("dataorgid")));
				info.setLocked(map.get("lockstatus") == null ? false : Integer.parseInt(String.valueOf(map.get("lockstatus"))) == Constant.VALID_YES ? false : true);
				info.setOrgidname(BaseUtils.toString(map.get("orgname")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return info;

	}
	/**
	 * 
	* @Title: getTree
	* @author xf 
	* @Description: TODO(系统获取Tree)
	* @param @param req
	* @param @return
	* @param @throws Exception   
	* @return List<Map<String,Object>>    返回类型
	* @throws
	 */
	public List<Map<String, Object>> getTree(HttpServletRequest req) throws Exception {
		String view = req.getParameter("view");
		String where = req.getParameter("where");
		String idfield = req.getParameter("idfield");
		String pidfield = req.getParameter("pidfield");
		String textfield = req.getParameter("textfield");
		String iconfield = req.getParameter("iconfield") == null ? "" : req.getParameter("iconfield");
		String iconroot = req.getParameter("iconroot");
		String root = req.getParameter("root") == null ? "" : req.getParameter("root");
		String rooticon = req.getParameter("rooticon");
		StringBuffer sb = new StringBuffer();
		StringBuffer field = new StringBuffer();
		field.append(textfield).append(" as text");
		if (StrKit.notBlank(idfield)) {
			field.append(",").append(idfield).append(",").append(idfield).append(" as id");
		}
		if (StrKit.notBlank(pidfield)) {
			field.append(",").append(pidfield).append(",").append(pidfield).append(" as parent");
		}
		if (StrKit.notBlank(iconfield)) {
			field.append(",").append("'" + iconroot + "'").append("||").append(iconfield).append(" as icon");
		}
		String whereSql = " 1=1 ";
		Object[] parameter = null;
		FilterGroup wheres = null;
		FilterTranslator translator = null;
		if (where != null && !"".equals(where)) {
			wheres = JSON.parseObject(where, FilterGroup.class);
			translator = new FilterTranslator(wheres);
		}

		if (translator != null && wheres != null) {
			whereSql = translator.TranslateGroup(wheres, " ");
			parameter = translator.getParameters();
		}
		String SQL = sb.append("SELECT ").append(field).append(" FROM ").append(view).append(" WHERE ").append(whereSql).toString();
		List<Record> treeData = parameter==null?Db.find(SQL):Db.find(SQL, parameter);
		List<Map<String, Object>> tempTree = BaseUtils.ArrayToTreeData(treeData, idfield.toLowerCase(), pidfield.toLowerCase());
		List<Map<String, Object>> rsTree = new ArrayList<Map<String, Object>>();
		if (StrKit.notBlank(root)) {
			Map<String, Object> rootTree = new HashMap<String, Object>();
			rootTree.put("text", root);
			rootTree.put("icon", rooticon);
			rootTree.put("children", tempTree);
			rsTree.add(rootTree);
		} else {
			rsTree = tempTree;
		}
		return rsTree;
	}
	/**
	 * 
	* @Title: getAllSystemDict
	* @author xf 
	* @Description: TODO(获取系统所有字典)
	* @param @return   
	* @return Map<String,List<Record>>    返回类型
	* @throws
	 */
	public Map<String, List<Record>> getAllSystemDict() {
		Map<String, List<Record>> map = new HashMap<String, List<Record>>();
		try {

			String sql = "select value as id,value,label as text,type from sys_dict where value !='0' order by SORT";
			List<Record> record = Db.find(sql);
			List<Record> tempMap = null;
			for (Record temp : record) {
				if (map.containsKey(String.valueOf(temp.get("type")))) {
					map.get(String.valueOf(temp.get("type"))).add(temp);
				} else {
					tempMap = new ArrayList<Record>();
					map.put(String.valueOf(temp.get("type")), tempMap);
					map.get(String.valueOf(temp.get("type"))).add(temp);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 
	* @Title: getSelectOrgs
	* @author xf 
	* @Description: TODO(获取当前登录人的各组织机构)
	* @param @param request
	* @param @return   
	* @return Map<String,List<Record>>    返回类型
	* @throws
	 */
	public Map<String, List<Record>> getSelectOrgs(HttpServletRequest request) {
		Map<String, List<Record>> map = new HashMap<String, List<Record>>();
		return map;
	}
	
	
	/**
	 * 
	* @Title: getUserMenus
	* @author xf 
	* @Description: TODO(获取当前登录人所拥有的所有菜单)
	* @param @param request
	* @param @return   
	* @return List<Record>    返回类型
	* @throws
	 */
	public  List<Record> getUserMenus( HttpServletRequest request) {
		HttpSession session = request.getSession();
		SysUserInfo info = (SysUserInfo) session.getAttribute(Constant.SYS_USER_INFO);
		String sql = null;
		if (info.getMenuResource() == null) {
			
			
			sql = "select t1.*,t1.menuicon icon  from sys_menu t1 where t1.menuparentno='0' or t1.menuparentno is null ";
			List<Record> parentLs =Db.find(sql);

			//用户权限内button
			sql = "select t1.* from sys_button t1,sys_acl t2 where t2.privilegeaccess='sys_button' and t2.privilegemasterkey=? and t1.btnid=t2.privilegeaccesskey order by t1.seqno ";
			List<Record> buttonRows = Db.find(sql, info.getRoleid());
			
			//用户权限内menu
			sql = " select * from (" +
					"select t1.*,t1.menuicon icon from sys_menu  t1, sys_acl t2 where t2.privilegeaccess='sys_menu' and t2.privilegemasterkey=? and t2.privilegeaccesskey = t1.menuid " +
					" union " +
					" select t5.*,t5.menuicon icon from sys_menu t5 where exists (select 1 from (select t3.menuno from sys_button t3,sys_acl t4 where t4.privilegeaccess='sys_button' and t4.privilegemasterkey=? and t3.btnid=t4.privilegeaccesskey ) btnMenu where btnMenu.menuno=t5.menuno) " +
					")t" +
					" order by t.menuorder asc";
			List<Record> menuRows = Db.find(sql, new Object[]{info.getRoleid(),info.getRoleid()});
			
			Map<String, ArrayList> buttonMap = new HashMap<String, ArrayList>();
			for (Record r : buttonRows) {
				if (buttonMap.containsKey(r.getColumns().get("menuno"))) {
					buttonMap.get(String.valueOf(r.getColumns().get("menuno"))).add(r.getColumns());
				} else {
					ArrayList<Map<String, Object>> btLs = new ArrayList<Map<String, Object>>();
					btLs.add(r.getColumns());
					buttonMap.put(String.valueOf(r.getColumns().get("menuno")), btLs);
				}
			}

			List<Record> childrenLs = new ArrayList<Record>();

			for (Record r : menuRows) {
				if (r.getColumns().get("menuparentno") != null && String.valueOf(r.getColumns().get("menuparentno")).equals("0")) {
					//parentLs.add(map);
				} else {
					if(info.isLocked() && Constant.LOCK_MENU_LIST.contains(String.valueOf(r.getColumns().get("menuno")))){
						continue;
					}
					childrenLs.add(r);
				}
			}

			String menuno = null;

			List<Record> children = null;
			Record parent = null;
			int len = parentLs.size();
			for (int i=len-1;i>=0;i--) {
				parent = parentLs.get(i);
				children = new ArrayList<Record>();
				menuno = (String) parent.get("menuno");
				for (Record r : childrenLs) {

					if (menuno.equals(BaseUtils.toString(r.getColumns().get("menuparentno")) )) {
						children.add(r);
					}
				}
				if(children.isEmpty()){
					parentLs.remove(i);
				}else{
					parent.getColumns().put("children", children);
				}
				
			}
			
			info.setMenuResource(parentLs);
			info.setMenuButtons(buttonMap);
		}
		return info.getMenuResource();
	}
	
	/**
	 * 
	* @Title: doChangePassword
	* @author xf 
	* @Description: TODO(修改密码)
	* @param @param request
	* @param @return   
	* @return Map<String,Object>    返回类型
	* @throws
	 */
	public Map<String, Object> doChangePassword(HttpServletRequest request) {
		try {
			SysUserInfo curUser = BaseUtils.getCurrentUser(request);
			String OldPassword = request.getParameter("OldPassword");
			String NewPassword = request.getParameter("NewPassword");

			String sql = "select PASSWORD,TXTPASSWORD from stb_customer where usercode = ? ";
			List<Record> rows =Db.find(sql, new String[] { curUser.getUsercode() });
			if (rows == null || rows.isEmpty()) {
				return BaseUtils.getErrorMessage("用户不存在");
			}
			Map userMap = rows.get(0).getColumns();
			String oldpw = String.valueOf(userMap.get("password"));
			String txtpw = String.valueOf(userMap.get("txtpassword") == null ? "" : userMap.get("TXTPASSWORD"));

			if (!MD5Util.encodeByMD5(Constant.MD5_PWD_BYTE_LEN, OldPassword).equals(oldpw)) {
				return BaseUtils.getSuccessMessage("旧密码错误");
			}
			String[] params = null;
			if (txtpw.equals("")) {
				sql = "update stb_customer set password=? where usercode=? ";
				params = new String[] { MD5Util.encodeByMD5(Constant.MD5_PWD_BYTE_LEN, NewPassword), curUser.getUsercode() };
			} else {
				sql = "update stb_customer set password=?,TXTPASSWORD=?  where usercode=? ";
				params = new String[] { MD5Util.encodeByMD5(Constant.MD5_PWD_BYTE_LEN, NewPassword), NewPassword, curUser.getUsercode() };
			}
			Db.update(sql, params);
			return BaseUtils.getSuccessMessage("密码修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return BaseUtils.getSuccessMessage("密码修改失败");
		}

	}
	
	/**
	 * 
	* @Title: doLoginOut
	* @author xf 
	* @Description: TODO(注销登录)
	* @param @param request
	* @param @return   
	* @return Map<String,Object>    返回类型
	* @throws
	 */
	public Map<String, Object> doLoginOut(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			session.invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BaseUtils.getSuccessMessage("");
	}
	
	/**
	 * 
	* @Title: FavouriteAdd
	* @author xf 
	* @Description: TODO(添加收藏夹)
	* @param @param request
	* @param @return   
	* @return Map<String,Object>    返回类型
	* @throws
	 */
	public Map<String, Object> FavouriteAdd(HttpServletRequest request) {
		Record record = BaseUtils.getInsertParam(request);
		try {
			record.getColumns().put("usercode", BaseUtils.getUserCodeFromSession(request));
			Long counter = Db.queryLong("select count(1) from sys_favorite where usercode=? and menuid=?", new Object[]{record.get("usercode"),record.get("menuid")});
			if(counter>0){
				return BaseUtils.getErrorMessage("该菜单已经添加收藏");
			}
			Db.save("sys_favorite", record);
			return BaseUtils.getSuccessMessage("添加收藏成功");
		} catch (Exception e) {
			e.printStackTrace();
			return BaseUtils.getErrorMessage("添加收藏失败，系统发生异常");
		}

	}
	/**
	 * 
	* @Title: FavouriteDelete
	* @author xf 
	* @Description: TODO(删除收藏夹)
	* @param @param request
	* @param @return   
	* @return Map<String,Object>    返回类型
	* @throws
	 */
	public Map<String, Object> FavouriteDelete(HttpServletRequest request) {
		String id = request.getParameter("ID");
		try {
			Db.deleteById("sys_favorite", "id=?", id);
			return BaseUtils.getSuccessMessage("收藏已移除");
		} catch (Exception e) {
			e.printStackTrace();
			return BaseUtils.getErrorMessage("收藏移除失败，系统发生异常");
		}
	}
	/**
	 * 
	* @Title: Favouriteload
	* @author xf 
	* @Description: TODO(获取当前登录人所有的收藏夹权限)
	* @param @param request
	* @param @return   
	* @return Map<String,Object>    返回类型
	* @throws
	 */
	public Map<String, Object> Favouriteload(HttpServletRequest request) {
		List<Record> favorites=Db.find("select a.id,b.menuname as favoritetitle,a.favoritecontent,b.menuurl as url,b.menuicon as icon,b.menuno as menuno,b.menuid  from sys_favorite a,sys_menu b where a.menuid = b.menuid and usercode=?",
				BaseUtils.getUserCodeFromSession(request));
		return BaseUtils.getSuccessMessage(favorites);
	}
	
	/**
	 * 
	* @Title: getGrid
	* @author xf 
	* @Description: TODO( 获取Grid 数据 基类)
	* @param @param req
	* @param @return   
	* @return Map<String,Object>    返回类型
	* @throws
	 */
	public Map<String, Object> getGrid(HttpServletRequest req) {

		try {

			String view = req.getParameter("view"); // 表名 单表查询
			String resource = req.getParameter("resource"); // 资源名

			String sortname = req.getParameter("sortname"); // 排序列名
			String sortorder = req.getParameter("sortorder");
			String _pagenumber = req.getParameter("page");
			String _pagesize = req.getParameter("pagesize");
			String where = req.getParameter("where"); // 查询条件
			String where2 = req.getParameter("where2");// 数据权限
			SysUserInfo user = BaseUtils.getCurrentUser(req);
			int pagenumber = 1, pagesize = 20;
			// 可分页
			if (!BaseUtils.isEmpty(_pagenumber) && !BaseUtils.isEmpty(_pagesize)) {
				pagenumber = Integer.valueOf(_pagenumber);
				pagesize = Integer.valueOf(_pagesize);
			}
			// 可排序
			if (!BaseUtils.isEmpty(sortname)) {
				sortorder = BaseUtils.isEmpty(sortorder) || "asc".equals(sortorder) ? "asc" : "desc";
			}
			String whereSql = " 1=1 ";
			Object[] parameter = null;
			FilterGroup wheres = null;
			FilterTranslator translator = null;
			if (!StrKit.isBlank(where)) {
				wheres = JSON.parseObject(where, FilterGroup.class);
			}

			if (!StrKit.isBlank(where2)) {
				wheres = BaseUtils.getRuleGroup(wheres, JSON.parseObject(where2, FilterGroup.class));
			}

			translator = new FilterTranslator(wheres);
			translator.setUser(user);

			String sql = "";
			String countsql = "";

			if (!BaseUtils.isEmpty(view)) {
				StringBuffer sb = new StringBuffer();
				StringBuffer countsqlBuf = new StringBuffer();
				if (translator != null && wheres != null) {
					whereSql = translator.TranslateGroup(wheres, " ");
					parameter = translator.getParameters();
				}
				sb.append("FROM ").append(view).append(" WHERE ").append(whereSql);
				countsqlBuf.append("SELECT COUNT(1) FROM ").append(view).append("  WHERE  ").append(whereSql);
				if (!BaseUtils.isEmpty(sortname)) {
					sb.append(" ORDER BY ").append(sortname).append(" ").append(sortorder);
				}
				sql = sb.toString();
				countsql = countsqlBuf.toString();
			}
			// 以复杂SQL作为优先级最高
			if (!BaseUtils.isEmpty(resource)) {
				SqlBean sqlbean = EhCacheUtils.getValue("src/ehcache.xml", "sqlConfigs", resource);
				if (sqlbean != null) {
					String aline = "EDHDGSK";
					StringBuffer sb = new StringBuffer();
					StringBuffer countsqlBuf = new StringBuffer();
					if (translator != null && wheres != null) {
						whereSql = translator.TranslateGroup(wheres, aline + ".");
						parameter = translator.getParameters();
					}
					sb.append("from (" + replaceBlank(sqlbean.getSql()) + " ) " + aline).append("  WHERE ").append(whereSql);
					countsqlBuf.append("select count(1) from (" + replaceBlank(sqlbean.getSql()) + " ) " + aline).append(" WHERE ").append(whereSql);
					if (!BaseUtils.isEmpty(sortname)) {
						sb.append(" ORDER BY ").append(aline).append(".").append(sortname).append("  ").append(sortorder);
					}
					sql = sb.toString();
					countsql = countsqlBuf.toString();
				} else {
					return BaseUtils.getGridData(0, new ArrayList());
				}
			}

			if(StrKit.isBlank(sql)){
				throw new Exception("view and resourcr are all empty ...");
			}
			
			Long l=Long.valueOf(String.valueOf(Db.queryNumber(countsql, parameter)));
//			BigDecimal b=Db.queryBigDecimal(countsql, parameter);
			int Total= l.intValue();
			List<Record> gridData = new ArrayList<Record>();
			if (Total > 0) {
				Page<Record> p=	Db.paginate(pagenumber, pagesize, "select * ",sql ,parameter);
				gridData=p.getList();
			}
			return BaseUtils.getGridData(Total, gridData);
		} catch (Exception e) {
			e.printStackTrace();
			return BaseUtils.getGridData(0, null);
		}
	
		
	
	}
	
	
	public String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	/**
	 * 
	* @Title: getMyButtons
	* @author xf 
	* @Description: TODO(获取当前登录人该菜单的菜单按钮)
	* @param @param request
	* @param @return   
	* @return Map<String,Object>    返回类型
	* @throws
	 */
	public Map<String, Object> getMyButtons(HttpServletRequest request) {
		String menuNo = request.getParameter("MenuNo");
		List<Map<String, Object>> mybuttons = BaseUtils.getButtons(request, menuNo);
		return BaseUtils.getSuccessMessage(mybuttons);
	}
	
	public Map<String, Object> getIcons(HttpServletRequest request) {
		String rootPath = request.getRealPath("/resource/icons/32X32");
		List<String> files = this.getFiles(rootPath);
		return BaseUtils.getSuccessMessage(files);
	}
	/**
	 * 
	* @Title: getFiles
	* @author xf 
	* @Description: TODO(获取路径下的所有文件)
	* @param @param rootpath
	* @param @return   
	* @return List<String>    返回类型
	* @throws
	 */
	private List<String> getFiles(String rootpath) {
		List<String> filelist = new ArrayList<String>();
		File rootfile = new File(rootpath);
		if (rootfile.isFile()) {
			filelist.add(rootfile.getPath());
		}
		if (rootfile.isDirectory()) {
			File[] subfile = rootfile.listFiles();
			for (File file : subfile) {
				filelist.add(file.getPath());
			}
		}
		return filelist;
	}
	/**
	 * 
	* @Title: getTreeGrid
	* @author xf 
	* @Description: TODO(获取TreeGrid 权限)
	* @param @param request
	* @param @return   
	* @return Map<String,Object>    返回类型
	* @throws
	 */
	public Map<String, Object> getTreeGrid(HttpServletRequest request) {
		String view = request.getParameter("view");
		String where = request.getParameter("where");
		String where2 = request.getParameter("where2");
		String idfield = request.getParameter("idfield");
		String pidfield = request.getParameter("pidfield");
		StringBuffer sb = new StringBuffer();
		String whereSql = " 1=1 ";
		Object[] parameter = new Object[0];
		FilterGroup wheres = null;
		FilterTranslator translator = null;
		
		
		if (!StrKit.isBlank(where)) {
			wheres = JSON.parseObject(where, FilterGroup.class);
		}
		if (!StrKit.isBlank(where2)) {
			wheres = BaseUtils.getRuleGroup(wheres, JSON.parseObject(where2, FilterGroup.class));
		}
		if (where != null && !"".equals(where)) {
			wheres = JSON.parseObject(where, FilterGroup.class);
			translator = new FilterTranslator(wheres);
		}
		

		if (translator != null && wheres != null) {
			whereSql = translator.TranslateGroup(wheres, " ");
			parameter = translator.getParameters();
		}
		String SQL = sb.append("SELECT * FROM ").append(view).append(" WHERE ").append(whereSql).toString();
//		int Total = Db.queryInt(SQL,parameter);
		int Total =Db.find(SQL, parameter).size();
		List<Map<String, Object>> tempGridData = new ArrayList<Map<String, Object>>();
		if (Total > 0) {
			List<Record> treeData = Db.find(SQL,parameter);
			tempGridData = BaseUtils.ArrayToTreeData(treeData, idfield, pidfield);
		}
		return BaseUtils.getGridData(Total, tempGridData);
	}
	/**
	 * 
	* @Title: getSelect
	* @author xf 
	* @Description: TODO(获取Select 下来选择框数据)
	* @param @param request
	* @param @return   
	* @return List<Record>    返回类型
	* @throws
	 */
	public List<Record> getSelect(HttpServletRequest request) {
		String view = request.getParameter("view");
		String where = request.getParameter("where");
		String idfield = request.getParameter("idfield");
		String textfield = request.getParameter("textfield");
		String distinct = request.getParameter("distinct");
		StringBuffer sb = new StringBuffer();
		String selectStr = "";
		String distinctStr = "";
		if (!BaseUtils.isEmpty(distinct) && !"false".equalsIgnoreCase(distinct)) {
			distinctStr = "distinct";
		}
		if (!BaseUtils.isEmpty(idfield)) {
			selectStr += "," + idfield + " as id";
			selectStr += "," + idfield + " as value";
		}
		if (!BaseUtils.isEmpty(textfield)) {
			selectStr += "," + textfield + " as text";
		}
		selectStr = "".equals(selectStr.trim()) ? "*" : selectStr.substring(1);
		String whereSql = " 1=1 ";
		Object[] parameter = null;
		FilterGroup wheres = null;
		FilterTranslator translator = null;
		if (where != null && !"".equals(where)) {
			wheres = JSON.parseObject(where, FilterGroup.class);
			translator = new FilterTranslator(wheres);
		}

		if (translator != null && wheres != null) {
			whereSql = translator.TranslateGroup(wheres, " ");
			parameter = translator.getParameters();
		}
		String sql = sb.append("SELECT ").append(distinctStr).append(" ").append(selectStr).append(" FROM ").append(view).append(" WHERE ").append(whereSql)
				.toString();
//		List<Map<String, Object>> templst = baseService.Query(sql, parameter);
		
		List<Record> templst =Db.find(sql, parameter);
		return templst;
	}
	
	public Map<String, Object>  deleteFavouriteload(HttpServletRequest request){
		String id = request.getParameter("ID");
		try {
			Db.deleteById("sys_favorite", id);
			return BaseUtils.getSuccessMessage("收藏已移除");
		} catch (Exception e) {
			e.printStackTrace();
			return BaseUtils.getErrorMessage("收藏移除失败，系统发生异常");
		}
	}
}
