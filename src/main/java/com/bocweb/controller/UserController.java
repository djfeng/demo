package com.bocweb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bocweb.core.util.AppContextUtil;
import com.bocweb.core.vo.Condition;
import com.bocweb.core.vo.QueryCondition;
import com.bocweb.po.User;
import com.bocweb.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	
	
	 @RequestMapping("/getUsers.do")  
	    public ModelAndView helloWorld(HttpServletRequest request,QueryCondition condition) {  
	        List<User> users = userService.queryByCondition(condition);
	        request.setAttribute("users", users);
	        return new ModelAndView("Hello.ftl");  
	    }  
}
