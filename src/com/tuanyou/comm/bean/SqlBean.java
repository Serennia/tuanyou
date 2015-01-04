package com.tuanyou.comm.bean;

import java.io.Serializable;
/**
 * 
* @ClassName: SqlBean
* @Description: TODO(sql实体类,用与存储sql和数据权限)
* @author xf 
* @date 2014年8月19日 下午5:40:57
*
 */
public class SqlBean implements Serializable {
	private static final long serialVersionUID = -3664326322792515989L;
	private String name; // Sql名称
	private String dataprivate; // 数据权限resource名称
	private String sql; // 相应Sql


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataprivate() {
		return dataprivate;
	}

	public void setDataprivate(String dataprivate) {
		this.dataprivate = dataprivate;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
