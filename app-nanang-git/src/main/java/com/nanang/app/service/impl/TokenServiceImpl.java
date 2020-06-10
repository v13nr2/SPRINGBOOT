package com.nanang.app.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.nanang.app.model.TokenRequest;
import com.nanang.app.model.TokenResponse;
import com.nanang.app.security.jwt.JwtProvider;
import com.nanang.app.service.JwtUserDetailsService;
import com.nanang.app.service.TokenService;
import com.nanang.app.utility.Konstanta;
import com.nanang.app.utility.Utility;


@Service
public class TokenServiceImpl implements TokenService {
	
	@Value("${keys.clientId}")
	private String clientIdParam;
	@Value("${keys.secret}")
	private String SecretkeyParam;

	
	
	@Value("${keys.jwtSecret}")
	private String jwtSecret;

    
	@Value("${keys.regex1}")
    private String regex1;
 
	@Value("${keys.regex2}")
    private String regex2;

	@Autowired
    private JwtProvider jwtProvider;
	

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Override
	public TokenResponse getToken(TokenRequest token) {
		// TODO Auto-generated method stub
		final String clientId = token.getClientId() ==null?"":token.getClientId();
		final String secretKey = token.getSecret() ==null?"":token.getSecret();
		TokenResponse tokenResponse = new TokenResponse();
		
	    //jika sesuai generate Token
		//v13 jika sesuai dengan data di tabel
		final UserDetails userDetails = userDetailsService.loadUserByUsername(token.getClientId());
		

		//if(clientIdParam.equals(clientId) && SecretkeyParam.equals(secretKey)) {
		if(clientIdParam.equals(clientId) && SecretkeyParam.equals(secretKey)) {
		
//			List<String> roleString = Arrays.asList("ROLE_ADMIN","ROLE_USER");
//			String bodyToken = Utility.buildingBodyTokenUsernameAndRole(clientId, regex1, roleString, regex2);
//			String tokenData = jwtProvider.generateJwtTokenCustom(bodyToken);
//			tokenResponse.setStatus(Konstanta.SUCCESSHTTPOK);
//			tokenResponse.setToken(tokenData);
	    }	
		
		if(userDetails.getUsername() != null) {

			
			final String psDB = userDetails.getPassword().trim();
			final String secret = token.getSecret().trim();
			
			if(psDB.equals(secret)) {
				List<String> roleString = Arrays.asList("ROLE_ADMIN","ROLE_USER");
				String bodyToken = Utility.buildingBodyTokenUsernameAndRole(clientId, regex1, roleString, regex2);
				String tokenData = jwtProvider.generateJwtTokenCustom(bodyToken);
				tokenResponse.setStatus(Konstanta.SUCCESSHTTPOK);
				tokenResponse.setToken(tokenData);
				
			} else {
				//System.out.print("=>masuk TIDAK sama kondisi passwordnya");
			}
			
		} else {
			//System.out.print("=>TIDAK ADA USER ITU sama kondisi passwordnya");
		}
		
		
		return tokenResponse;
	}

}
