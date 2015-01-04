package com.tuanyou.comm.bean;

import java.util.List;
/**
 * 
* @ClassName: FilterGroup
* @Description: TODO(规则集合)
* @author xf 
* @date 2014年8月19日 下午5:42:08
*
 */
public class FilterGroup {
	private String op;
	private List<rule> rules;
	private List<FilterGroup> groups;

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public List<rule> getRules() {
		return rules;
	}

	public void setRule(List<rule> rules) {
		this.rules = rules;
	}

	public List<FilterGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<FilterGroup> groups) {
		this.groups = groups;
	}

	public void setRules(List<rule> rules) {
		this.rules = rules;
	}

}
