package com.tuanyou.comm.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
/**
 * 
* @ClassName: TuanyouActionHandler
* @Description: TODO(全局action处理器)
* @author xf 
* @date 2014年8月19日 下午6:59:36
*
 */
public class TuanyouActionHandler extends Handler{
	private final String viewPostfix=".do";
	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
			int index = target.lastIndexOf(viewPostfix);
			if (index != -1)
				target = target.substring(0, index);
			nextHandler.handle(target, request, response, isHandled);
	}
}
