package com.tuanyou.comm;

import com.jfinal.core.Controller;


/**
 * CommonController
 */
public class CommonController extends Controller {
	
	public void index() {
		render("/pages/web/index.jsp");
	}
}
