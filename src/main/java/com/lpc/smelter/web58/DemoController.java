package com.lpc.smelter.web58;

import com.bj58.wf.mvc.ActionResult;
import com.bj58.wf.mvc.MvcController;
import com.bj58.wf.mvc.annotation.Path;

/**
 * 基类控制器
 *
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-08-17 11:02
 */
public class DemoController extends MvcController {

	@Path("demo")
	public ActionResult demo() {
		return ActionResult.view("demo");
	}
}
