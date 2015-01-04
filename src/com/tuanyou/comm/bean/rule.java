package com.tuanyou.comm.bean;
/**
 * 
* @ClassName: rule
* @Description: TODO(规则实体类
* @author xf 
* @date 2014年8月19日 下午5:41:30
*
 */
public class rule {
	private String op;
	public String field;
	public Object value;
	public String type;
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
