package com.tuanyou.comm.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Record;
import com.tuanyou.comm.bean.FilterGroup;
import com.tuanyou.comm.bean.SysUserInfo;
import com.tuanyou.comm.bean.rule;
/**
 * 
* @ClassName: BaseUtils
* @Description: TODO(公共方法类)
* @author xf 
* @date 2014年8月19日 下午5:48:20
*
 */
public class BaseUtils {
	public static boolean isEmpty(Object object) {
		return object == null || ((String) object).length() < 1;
	}
	/**
     * 重写tostring方法
     * @param o
     * @return
     */
    public static String toString(Object o){
    	String str="";
    	if(o!=null){
    		str=o.toString();
    	}
    	return str;
    }
	/**
	 * 获取成功信息
	 * 
	 * @param IsError
	 * @param Message
	 * @param data
	 * @return
	 */
	public static Map<String, Object> getSuccessMessage(String Message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("IsError", false);
		map.put("Message", Message);
		map.put("Data", null);
		return map;
	}
	
	
	/**
	 * 获取成功信息
	 * 
	 * @param data
	 * @return
	 */
	public static Map<String, Object> getSuccessMessage(Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("IsError", false);
		map.put("Message", "");
		map.put("Data", data);
		return map;
	}

	/**
	 * 
	 * @param Message
	 * @param data
	 * @return
	 */
	public static Map<String, Object> getErrorMessage(String Message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("IsError", true);
		map.put("Message", Message);
		return map;
	}
    /**
	 * 将List 数组转换为Tree 所需要的数据
	 * 
	 * @param list
	 * @param id
	 *            ID 列
	 * @param pid
	 *            PID 列
	 * @return
	 */
	public static List<Map<String, Object>> ArrayToTreeData(List<Record> mydata, String id, String pid) {
		Map<Object, Object> h = new HashMap<Object, Object>();// 数据索引
		List<Map<String, Object>> r = new ArrayList<Map<String, Object>>();// 数据池，返回的数据

		for (Record item : mydata) {
			if (!item.getColumns().containsKey(id)) {
				continue;
			} else {
				h.put(item.getColumns().get(id), item.getColumns());
			}
		}

		for (Record item : mydata) {
			if (!item.getColumns().containsKey(id)) {
				continue;
			}
			if (!item.getColumns().containsKey(pid) || (item.getColumns().get(pid) == null) || !h.containsKey(item.getColumns().get(pid))) {
				r.add(item.getColumns());
			} else {
				Map<String, List<Map<String, Object>>> pitem = (Map<String, List<Map<String, Object>>>) h.get(item.getColumns().get(pid));
				if (!pitem.containsKey("children")) {
					List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
					children.add(item.getColumns());
					pitem.put("children", children);
				} else {
					List<Map<String, Object>> children = pitem.get("children");
					children.add(item.getColumns());
					pitem.put("children", children);
				}
			}
		}
		return r;
	}
	
	/**
	 * 获取当前登录信息
	 * 
	 * @param request
	 * @return
	 */
	public static SysUserInfo getCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		if (session.getAttribute(Constant.SYS_USER_INFO) != null) {
			SysUserInfo user = (SysUserInfo) session.getAttribute(Constant.SYS_USER_INFO);
			return user;
		}
		return null;
	}
	
	/**
	 * 从Request 请求中，解析Inset相关参数
	 * 
	 * @param request
	 * @return
	 */
	public static Record getInsertParam(HttpServletRequest request) {
		Record r=new Record();
		Map<String, Object> record = new HashMap<String, Object>();
		Enumeration<String> enume = request.getParameterNames();
		while (enume.hasMoreElements()) {
			String name = enume.nextElement().toString();
			if (!BaseUtils.isEmpty(request.getParameter(name))) {
				record.put(name, request.getParameter(name));
			}
		}
		r.setColumns(record);
		return r;
	}

	public static Record getUpdateParam(HttpServletRequest request, String... removeParam) {
		Record r=new Record();
		Map<String, Object> record = new HashMap<String, Object>();
		Enumeration enume = request.getParameterNames();
		while (enume.hasMoreElements()) {
			String name = enume.nextElement().toString();
			boolean istrue = false;
			for (int i = 0; i < removeParam.length; i++) {
				if (name.equalsIgnoreCase(removeParam[i])) {
					istrue = true;
					continue;
				}
			}
			if (!istrue) {
				record.put(name, request.getParameter(name));
			}
		}
		r.setColumns(record);
		return r;
	}
	/**
	 * 从Session 中获取usrId
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserCodeFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		if (session.getAttribute(Constant.SYS_USER_INFO) != null) {
			SysUserInfo user = (SysUserInfo) session.getAttribute(Constant.SYS_USER_INFO);
			return user.getUsercode();
		}
		return "";
	}
	public static FilterGroup getRuleGroup(FilterGroup oldrule, FilterGroup addRule) {

		FilterGroup dpRule = addRule;
		if (dpRule == null)
			return oldrule;
		FilterGroup resrule = new FilterGroup();
		resrule.setOp("and");
		List<FilterGroup> groups = new ArrayList<FilterGroup>();
		groups.add(oldrule);
		groups.add(dpRule);
		resrule.setGroups(groups);
		return resrule;
	}
	
	public static Map<String, Object> getGridData(int Total, Object gridData) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Total", Total);
		map.put("Rows", gridData == null ? new ArrayList<Map<String, Object>>() : gridData);
		return map;
	}
	
	public static List<Map<String, Object>> getButtons(HttpServletRequest request, String menuNo) {
		HttpSession session = request.getSession(true);
		if (session.getAttribute(Constant.SYS_USER_INFO) != null) {
			SysUserInfo user = (SysUserInfo) session.getAttribute(Constant.SYS_USER_INFO);
			
			Map<String, ArrayList> allbuttons = user.getMenuButtons();
			return allbuttons.get(menuNo);
		}
		return null;
	}
	public static Map<String,Object> GridDataForMap(List<?> list){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("Rows", list);
		map.put("Total", list.size());
		return map;
	}

	public static String getLoginOrgid(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		if (session.getAttribute(Constant.SYS_USER_INFO) != null) {
			SysUserInfo user = (SysUserInfo) session.getAttribute(Constant.SYS_USER_INFO);
			return user.getOrgid();
		}
		return null;
	}
	
	public static String getVALIDATE_CODE_SESS(int count){
		   StringBuffer sb = new StringBuffer();
	        String str = "0123456789";
	        Random r = new Random();
	        for(int i=0;i<count;i++){
	            int num = r.nextInt(str.length());
	            sb.append(str.charAt(num));
	            str = str.replace((str.charAt(num)+""), "");
	        }
	        return sb.toString();
	}
	
	public static Object[] getRequestParam(HttpServletRequest req) {
		String _pagenumber = req.getParameter("page");
		String _pagesize = req.getParameter("pagesize");
		String where = req.getParameter("where"); // 查询条件

		int pagenumber = 0, pagesize = 0;
		// 可分页
		if (!BaseUtils.isEmpty(_pagenumber) && !BaseUtils.isEmpty(_pagesize)) {
			pagenumber = Integer.valueOf(_pagenumber)-1;
			pagesize = Integer.valueOf(_pagesize);
			if (pagesize == 0)
				pagesize = 20;
		}
		FilterGroup wheres = null;
		if (!StringUtils.isBlank(where)) {
			wheres = JSON.parseObject(where, FilterGroup.class);
		}
		Map<String, Object> paramMap = new Hashtable<String, Object>();
		if (wheres != null && wheres.getRules() != null) {
			for (rule ruletemp : wheres.getRules()) {
				paramMap.put(ruletemp.getField(), ruletemp.getValue());
			}
		}
		return new Object[] { pagenumber, pagesize, paramMap };

	}
	
	
	
	  /**
	    * 使用文件通道的方式复制文件
	    * @param s
	    *  源文件
	    * @param t
	    * 复制到的新文件
	    */
	    public static void fileChannelCopy(File s, File t) {
	        FileInputStream fi = null;
	        FileOutputStream fo = null;
	        FileChannel in = null;
	        FileChannel out = null;
	        try {
	            fi = new FileInputStream(s);
	            fo = new FileOutputStream(t);
	            in = fi.getChannel();//得到对应的文件通道
	            out = fo.getChannel();//得到对应的文件通道
	            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                fi.close();
	                in.close();
	                fo.close();
	                out.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    
		/**
		 * 获取访问的IP地址
		 * 
		 * @param request
		 * @return
		 */
		public static String getIpAddr(HttpServletRequest request) {
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			return ip;
		}
	    public static  void copy(String fileFrom, String fileTo){
	    	try {  
            FileInputStream in = new java.io.FileInputStream(fileFrom);  
            FileOutputStream out = new FileOutputStream(fileTo);  
            byte[] bt = new byte[1024];  
            int count;  
            while ((count = in.read(bt)) > 0) {  
                out.write(bt, 0, count);  
            }  
            in.close();  
            out.close();  
        } catch (IOException e) {  
        	 e.printStackTrace();
        }  
	    	}
 	
	    
	    public static String getfilepath(String type,String model){
	    	String filepath=Constant.upload_file_path;
	    	if(type.equals("img")){
	    		filepath+="/img";
	    	   	if(model.equals("huandengpian")){
		    		filepath+="/huandengpian";
		    	}
		    	if(model.equals("jingdian")){
		    		filepath+="/jingdian";
		    	}
		    	if(model.equals("zhoumo")){
		    		filepath+="/zhoumo";
		    	}
		    	if(model.equals("chujing")){
		    		filepath+="/chujing";
		    	}
	    	}
	    	return filepath;
	    }
}
