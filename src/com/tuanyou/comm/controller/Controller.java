package com.tuanyou.comm.controller;

import java.lang.reflect.Field;

import com.jfinal.log.Logger;
/**
 * 
* @ClassName: Controller
* @Description: TODO(重写控制层)
* @author xf 
* @date 2014年8月19日 下午5:44:30
*
 */
public abstract class Controller extends com.jfinal.core.Controller {
	private final static Logger log = Logger.getLogger(Controller.class);
    public Controller(){
        Field[] fields =this.getClass().getDeclaredFields();
        for (int i=0;i < fields.length; i++){
            Field field = fields[i];
            Class clazz = field.getType();
            if(Service.class.isAssignableFrom(clazz) && clazz != Service.class){
                try {
                    field.set(this, Service.getInstance(clazz, this));
                } catch (IllegalAccessException e) {
                	log.debug(e.getMessage());
                }
            }
        }
    }
}