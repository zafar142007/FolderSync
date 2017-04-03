package com.zafar.folder.sync.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zafar.folder.sync.common.LoginRequest;

@Controller
public class MainController{
	@RequestMapping(value="/ping", method=RequestMethod.GET)
	@ResponseBody public String getPing(){
		return Constants.ALL_OKAY;
	}
	@RequestMapping(value="/signin", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody public String signIn(@RequestBody LoginRequest req){
		return Constants.ALL_OKAY;
	}
	
	
	
}