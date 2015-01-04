package com.tuanyou.comm.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jfinal.plugin.IPlugin;
import com.tuanyou.comm.bean.SqlBean;
import com.tuanyou.comm.utils.BaseUtils;
import com.tuanyou.comm.utils.EhCacheUtils;
import com.tuanyou.comm.utils.ReadProperties;

public class InitSqlPlugin implements IPlugin{
	private String sqlpath = new ReadProperties("config/Global.properties").getValue("SqlPath");
	@Override
	public boolean start() {
		try {
			afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean stop() {
		return true;
	}

	public void afterPropertiesSet() throws Exception {

		if (!BaseUtils.isEmpty(sqlpath)) {
			sqlpath = sqlpath.startsWith("/") ? sqlpath : "/" + sqlpath;
			String basepath = this.getClass().getResource(sqlpath).getPath();
			File sqldirectory = new File(basepath);
			if (sqldirectory.isDirectory()) {
				File[] datasqlfiles = sqldirectory.listFiles();
				if (datasqlfiles.length > 0) {
					List<String> tempname = new ArrayList<String>();
					for (int i = 0; i < datasqlfiles.length; i++) {
						File file = datasqlfiles[i];
						SAXReader saxReader = new SAXReader();
						Document doc = saxReader.read(file);
						Element root = doc.getRootElement();
						if ("sqlConfig".equals(root.getName())) {
							List<Element> sqls = root.elements("sql");
							for (Element sqlobj : sqls) {
								String sqlname = sqlobj.attributeValue("name");
								String dataprivate = sqlobj.attributeValue("dataprivate");
								String sqlStr = sqlobj.getText();
								if (!tempname.contains(sqlname)) {
									SqlBean sqlbean = new SqlBean();
									sqlbean.setName(sqlname);
									sqlbean.setDataprivate(dataprivate);
									sqlbean.setSql(sqlStr);
									EhCacheUtils.setValue("src/ehcache.xml", "sqlConfigs",sqlname, sqlbean);
								} else {
									throw new Exception("SQL Name 重复:" + sqlname + " 请检查!");
								}
							}
						}
					}
				}
			}
		}
	
	}
}
