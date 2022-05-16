/*
 * @(#) TestPostController.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/12/20
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author PVHung3
 *
 */
@Controller
public class TestPostController {

	@GetMapping(value = {"/TESTPOST"})
	public String show(Model model, HttpServletRequest request) {
		return "TESTPOST";
	}
}
