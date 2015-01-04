package com.tuanyou.comm.controller;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.log.Logger;
/**
 * 
* @ClassName: Service
* @Description: TODO(使框架支持Service层)
* @author xf 
* @date 2014年8月19日 下午5:43:49
*
 */
public abstract class Service {
	private final static Logger log = Logger.getLogger(Service.class);
    protected Controller controller;
    private static Map<Class<? extends Service>, Service> INSTANCE_MAP = new HashMap<Class<? extends Service>, Service>();

    public static <Ser extends Service> Ser getInstance(Class<Ser> clazz, Controller controller){
        Ser service = (Ser) INSTANCE_MAP.get(clazz);
        if (service == null){
            try {
                service = clazz.newInstance();
                INSTANCE_MAP.put(clazz, service);
            } catch (InstantiationException e) {
            	log.debug(e.getMessage());
            } catch (IllegalAccessException e) {
               	log.debug(e.getMessage());
            }
        }
        service.controller = controller;
        return service;
    }
}
