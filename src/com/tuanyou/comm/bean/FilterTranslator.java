package com.tuanyou.comm.bean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
/**
 * 
* @ClassName: FilterTranslator
* @Description: TODO(规则过滤处理类)
* @author xf 
* @date 2014年8月19日 下午5:41:47
*
 */
public class FilterTranslator {
	private static Map<String,String> OperatorQuery = new HashMap<String,String>();
	//几个前缀/后缀
//    protected char leftToken = '[';
    protected char paramPrefixToken = '@';
//    protected char rightToken = ']';
    protected char groupLeftToken = '(';
    protected char groupRightToken = ')';
    protected char likeToken = '%';
    /// <summary>
    /// 参数计数器
    /// </summary>
    private int paramCounter = 0;
    
    //几个主要的属性
    public FilterGroup Group ;
    public String CommandText;
    public Map<String,Object> Parms ;
	
   
	static {
		OperatorQuery.put("add", " + ");
		OperatorQuery.put("bitwiseand", " & ");
		OperatorQuery.put("bitwisenot", " ~ ");
		OperatorQuery.put("bitwiseor", " | ");
		OperatorQuery.put("bitwisexor", " ^ ");
		OperatorQuery.put("divide", " / ");
		OperatorQuery.put("equal", " = ");
		OperatorQuery.put("greater", " > ");
		OperatorQuery.put("greaterorequal", ">=");
		OperatorQuery.put("isnull", " is null ");
		OperatorQuery.put("isnotnull", " is not null ");
		OperatorQuery.put("less", " <");
		OperatorQuery.put("lessorequal", " <= ");
		OperatorQuery.put("like", " like ");
		OperatorQuery.put("startwith", "like ");
		OperatorQuery.put("endwith", " like ");
		OperatorQuery.put("modulo", " % ");
		OperatorQuery.put("multiply", " * ");
		OperatorQuery.put("notequal", " <> ");
		OperatorQuery.put("subtract", " - ");
		OperatorQuery.put("and", " and ");
		OperatorQuery.put("or", " or ");
		OperatorQuery.put("in", " in ");
		OperatorQuery.put("notin", " not in ");
		OperatorQuery.put("notlike", " not like ");
	}
	
	 public FilterTranslator(FilterGroup group)
     {
         this.Group = group;
         this.Parms = new TreeMap<String,Object>();
     }
	 
	public String TranslateGroup(FilterGroup group, String alias) {
		StringBuilder bulider = new StringBuilder();
		if (group == null)
			return " 1=1 ";
		boolean appended = false;
		bulider.append(groupLeftToken);
		if (group.getRules() != null) {
			for (rule rule : group.getRules()) {
				if (appended) {
					bulider.append(OperatorQuery.get(group.getOp()));
				}
				bulider.append(TranslateRule(rule, alias));
				appended = true;
			}
		}
		if (group.getGroups() != null) {
			for (FilterGroup subgroup : group.getGroups()) {
				if (appended) {
					bulider.append(OperatorQuery.get(group.getOp()));
				}
				bulider.append(TranslateGroup(subgroup, alias));
				appended = true;
			}
		}
		bulider.append(groupRightToken);
		if (appended == false)
			return " 1=1 ";
		return bulider.toString();
	}
	 
	/**
	 * 匹配当前用户信息
	 */
	private Map<String,Object> currentParmMatch = new HashMap<String,Object>();
	/**
	 *
	<pre>
	  * 方法 名 : setUser
	  * 功能描述：  设置当前User 参数信息
	* @param user
	* 创 建 人: guzhixiong
	* 日    期: 2013-5-29
	* 修 改 人: 
	* 日    期: 
	</pre>
	 */
	public void setUser(SysUserInfo user){
		if(user!=null){
			currentParmMatch.put("{CurrentUserID}", user.getUserid());
			currentParmMatch.put("{CurrentRoleID}", user.getRoleid());
			currentParmMatch.put("{CurrentOrgID}",user.getOrgid());
		}else{
			currentParmMatch.put("{CurrentUserID}", "");
			currentParmMatch.put("{CurrentRoleID}", "");
			currentParmMatch.put("{CurrentOrgID}","");
		}
		
	}
	
	public String TranslateRule(rule rule,String alias)
     {
         StringBuilder bulider = new StringBuilder();
         if (rule == null) return " 1=1 ";

        
         
         //操作符
         
		if (rule.getOp().equals("dataprivs")) {
			//各地区管理员
			
		}else{
			
			 //如果字段名采用了 用户信息参数
	         if (currentParmMatch.containsKey(rule.field))
	         {
	             String field = (String) currentParmMatch.get(rule.field);
	             bulider.append(CreateFilterParam(alias+field, "int"));
	         }
	         else
	         {
	             bulider.append( alias+rule.field);
	         }
			
        	 //一般处理
        	 bulider.append("   "+OperatorQuery.get(rule.getOp()));

             String op = rule.getOp().toLowerCase();
             if ("like".equals(op) || "endwith".equals(op))
             {
                 String value = rule.getValue().toString();
                 if (!value.startsWith(String.valueOf(this.likeToken)))
                 {
                     rule.value = this.likeToken + value;
                 }
             }
             if ("notlike".equals(op))
             {
                 String value = rule.getValue().toString();
                 if (!value.startsWith(String.valueOf(this.likeToken)))
                 {
                     rule.value = value + this.likeToken;
                 }
             }
             if ("like".equals(op) || "startwith".equals(op))
             {
                 String value = rule.value.toString();
                 if (!value.endsWith(String.valueOf(this.likeToken)))
                 {
                     rule.value = value + this.likeToken;
                 }
             }
             if ("in".equals(op) || "notin".equals(op))
             {
                 String[] values = rule.getValue().toString().split(",");
                 boolean appended = false;
                 bulider.append("(");
                 for (Object value : values)
                 {
                     if (appended) bulider.append(",");
                     //如果值使用了 用户信息参数 比如： in ({CurrentRoleID},4)
                     if (currentParmMatch.containsKey(value))
                     {
                         Object val = currentParmMatch.get(value);
                         bulider.append(CreateFilterParam(val, "int"));
                     }
                     else
                     {
                         bulider.append(CreateFilterParam(value, rule.type)); 
                     }
                     appended = true;
                 }
                 bulider.append(")");
             } 
             //is null 和 is not null 不需要值
             else if ( !"isnull".equals(op) && !"isnotnull".equals(op))
             {
                 //如果值使用了 用户信息参数 比如 [EmptID] = {CurrentEmptID}
                 if (rule.value != null && currentParmMatch.containsKey(rule.getValue().toString()))
                 {
                     Object value = currentParmMatch.get(rule.getValue());
                     bulider.append( CreateFilterParam(value, "int"));
                 }
                 else
                 {
                     bulider.append(CreateFilterParam(rule.value, rule.type));

                 }
             } 
         }
        
         return bulider.toString();
     }
	
	 private String CreateFilterParam(Object value,String type)
     {
         String paramName = "p"+ ++paramCounter;
         Object val = value;
         type = type ==null?"":type;
         if (type.equals("int") ||type.equals("digits")){
        	 val = Integer.valueOf(value.toString());
         }
         else if (type.equals("float") || type.equals("number")){
        	 val = Double.valueOf(value.toString());
         }
         this.Parms.put(paramName, val);
         return "?";
     }
	 
	 
	 private String CreateFilterParam2(Object value,String type)
     {
         String paramName = "p"+ ++paramCounter;
         Object val = value;
         type = type ==null?"":type;
         if (type.equals("int") ||type.equals("digits")){
        	 val = Integer.valueOf(value.toString());
         }
         else if (type.equals("float") || type.equals("number")){
        	 val = Double.valueOf(value.toString());
         }
         this.Parms.put(paramName, val);
         return "";
     }
	 
	 
	 public Object[] getParameters(){
		 List list = new ArrayList();
		 if(!Parms.isEmpty()){
			 //增加参数排序功能，解决原有参数排序问题
			List<String> keys = new ArrayList<String>();
			keys.addAll(Parms.keySet());	
			Collections.sort(keys, new Comparator<String>() {   
	            public int compare(String o1, String o2) {   
	                return Integer.valueOf(o1.substring(1))-Integer.valueOf(o2.substring(1));   
	            }   
			}); 
			for(String key:keys){
				 list.add(Parms.get(key));
			}
		 }
		 return list.toArray();
	 }
}
