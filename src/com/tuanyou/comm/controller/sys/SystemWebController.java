package com.tuanyou.comm.controller.sys;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.tuanyou.comm.controller.Controller;
import com.tuanyou.comm.service.SystemService;
import com.tuanyou.comm.utils.BaseUtils;

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
	
	public void uploadfile(){
		System.out.println("================文件上传=================");
		System.out.println(PathKit.getWebRootPath());
		String type=getPara("type");
		String model=getPara("model");
		String filepath=BaseUtils.getfilepath(type, model);
		 PrintWriter pw = null;
		try {
			HttpServletResponse resp=getResponse();
			UploadFile upf = getFile("fileupload",filepath);
			File file = upf.getFile();
			String fileName=file.getName();
			String fileExt = fileName.substring(
					fileName.lastIndexOf(".") + 1).toLowerCase();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newFileName = filepath+"/"+df.format(new Date()) + "_"
					+ new Random().nextInt(1000) + "." + fileExt;
			upf.getFile().renameTo(new File(newFileName));
			System.out.println("newFileName :"+newFileName);
			newFileName=new String(newFileName.substring(newFileName.indexOf("/starfile/")+10, newFileName.length()));
		    resp = getResponse();
            resp.setContentType("text/html;charset=UTF-8");
            pw = resp.getWriter();
            pw.print(newFileName);
            renderNull();
		} catch (Exception e) {
			renderJson(BaseUtils.getErrorMessage("上传失败"));
		}   finally {
            try {
                if (pw != null)
                    pw.close();
            } catch (Exception ex) {
            }}
		
	}
}
