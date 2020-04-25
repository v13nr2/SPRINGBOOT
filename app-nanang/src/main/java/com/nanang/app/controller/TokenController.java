package com.nanang.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nanang.app.dto.ProductDto;
import com.nanang.app.model.DataResponse;
import com.nanang.app.model.TokenRequest;
import com.nanang.app.model.TokenResponse;
import com.nanang.app.service.TokenService;
import com.nanang.app.utility.Konstanta;

@RestController
@RequestMapping("auth")
public class TokenController {

	@Autowired
	private TokenService tokenService;
	
	
	
	@GetMapping("/getToken")
	public ResponseEntity<TokenResponse> getToken(
			@RequestParam("clientId") String clientId,
			@RequestParam("secret") String secret){
		final String clientIdParam = clientId==null?"":clientId;
		final String secretParam = secret==null?"":secret;
		TokenRequest request = new TokenRequest(clientIdParam,secretParam);
		TokenResponse response = tokenService.getToken(request);
		return new ResponseEntity<TokenResponse>(response,HttpStatus.OK);
				
	}
	

	@PostMapping("getTokenPost")
	public ResponseEntity<TokenResponse> getToken(@RequestBody TokenRequest token){
		TokenResponse response = tokenService.getToken(token);
		return new ResponseEntity<TokenResponse>(response,HttpStatus.OK);
		
	}
}
