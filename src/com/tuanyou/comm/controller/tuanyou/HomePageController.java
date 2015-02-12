package com.tuanyou.comm.controller.tuanyou;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.tuanyou.comm.utils.BaseUtils;
public class HomePageController extends Controller{
	//幻灯片
	public void hdpview(){
		render("/pages/houtai/homepage/huandengpian.jsp");
	}
	
	public void insertHdp(){
		Record record = BaseUtils.getInsertParam(getRequest());
		record.remove("id");
		try {
			 Db.save("tuanyou_homepage_hdp", record);
			 renderJson( BaseUtils.getSuccessMessage("添加成功"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson( BaseUtils.getErrorMessage("添加失败，系统发生异常"));
		}
	}
	
	public void updateHdp() {
		Record record = BaseUtils.getUpdateParam(getRequest());
		try {
			Db.update("tuanyou_homepage_hdp",  record);
			renderJson( BaseUtils.getSuccessMessage("更新成功"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson( BaseUtils.getErrorMessage("更新失败"));
		}
	}
	
	public void delHdp() {
		String ids=getPara("id");
		try{
			Db.update("delete from tuanyou_homepage_hdp where id in ("+ids+")");
			renderJson( BaseUtils.getSuccessMessage("删除成功"));
		}catch(Exception e){
			e.printStackTrace();
			renderJson( BaseUtils.getErrorMessage("删除失败"));
		}
		
		
	}
}
