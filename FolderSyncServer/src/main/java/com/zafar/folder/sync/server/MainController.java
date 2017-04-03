package com.zafar.folder.sync.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zafar.folder.sync.common.CreateUserResponse;
import com.zafar.folder.sync.common.LoginRequest;
import com.zafar.folder.sync.common.LoginResponse;
import com.zafar.folder.sync.server.auth.AuthService;

@Controller
public class MainController{
	@Autowired
	private AuthService authService;
	
	@RequestMapping(value="/ping", method=RequestMethod.GET)
	@ResponseBody public String getPing(){
		return Constants.ALL_OKAY;
	}
	@RequestMapping(value="/signin", method = RequestMethod.POST, consumes={ MediaType.APPLICATION_JSON_VALUE}, produces = { MediaType.APPLICATION_JSON_VALUE })	
	public @ResponseBody ResponseEntity<LoginResponse> signIn(@RequestBody LoginRequest req){
		boolean result=authService.authenticate(req);
		return new ResponseEntity<LoginResponse>(new LoginResponse(result, req), HttpStatus.OK);
	}
	
	@RequestMapping(value="/signup", method = RequestMethod.POST, consumes={ MediaType.APPLICATION_JSON_VALUE}, produces = { MediaType.APPLICATION_JSON_VALUE })	
	public @ResponseBody ResponseEntity<CreateUserResponse> signUp(@RequestBody LoginRequest req){
		CreateUserResponse result=authService.createUser(req);
		return new ResponseEntity<CreateUserResponse>(result, HttpStatus.OK);
	}
}