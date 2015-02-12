package com.tuanyou.comm;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;
import com.tuanyou.comm.controller.sys.SysAclController;
import com.tuanyou.comm.controller.sys.SysDictController;
import com.tuanyou.comm.controller.sys.SysLoginController;
import com.tuanyou.comm.controller.sys.SysRoleController;
import com.tuanyou.comm.controller.sys.SysUserRoleController;
import com.tuanyou.comm.controller.sys.SystemFavouriteController;
import com.tuanyou.comm.controller.sys.SystemMenuController;
import com.tuanyou.comm.controller.sys.SystemWebController;
import com.tuanyou.comm.controller.tuanyou.HomePageController;
import com.tuanyou.comm.handler.TuanyouActionHandler;
import com.tuanyou.comm.plugin.InitSqlPlugin;
import com.tuanyou.comm.utils.Constant;

public class TuanConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		loadPropertyFile("a_little_config.txt");				// 加载少量必要配置，随后可用getProperty(...)获取值
		me.setDevMode(getPropertyToBoolean("devMode", false));
		me.setViewType(ViewType.JSP); 							// 设置视图类型为Jsp，否则默认为FreeMarker
		me.setBaseViewPath("/pages");
		me.setUploadedFileSaveDirectory(Constant.default_upload_file_path);
		me.setMaxPostSize(1024*1000*100);
	}
	
	@Override
	public void configRoute(Routes me) {
		me.add("/", CommonController.class);
		me.add("/sysLogin", SysLoginController.class);
		me.add("/SystemWeb", SystemWebController.class);
		me.add("/systemMenu", SystemMenuController.class);
		me.add("/SystemFavorite", SystemFavouriteController.class);
		me.add("/systemDict", SysDictController.class);
		me.add("/sysRole", SysRoleController.class);
		me.add("/sysAcl",SysAclController.class);
		me.add("/sysUserRole",SysUserRoleController.class);
		
		me.add("/homepage",HomePageController.class);
	}

	@Override
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin dsMysql  = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
		dsMysql.setMaxIdleTime(60);
		me.add(dsMysql );
		// 配置ActiveRecord插件
		ActiveRecordPlugin arpMysql  = new ActiveRecordPlugin("mysql",dsMysql );
		arpMysql.setContainerFactory(new CaseInsensitiveContainerFactory(true));
		arpMysql.setShowSql(true);//ShowSql 
		me.add(arpMysql);
		//集成EhCachePlugin
		me.add(new EhCachePlugin());
		me.add(new InitSqlPlugin());
		
	}

	@Override
	public void configInterceptor(Interceptors me) {
	}

	@Override
	public void configHandler(Handlers me) {
		me.add(new TuanyouActionHandler());
	}
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
}
