package hqr.o365.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.URLUtil;

@Service
public class GoogleRecaptchaV3 {
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@Value("${UA}")
    private String ua;
	
	public boolean verify(String token) {
		boolean res = false;
		
		System.out.println("Response is "+token);
		String endpoint = "https://www.google.com/recaptcha/api/siteverify?secret=6LcPZIEgAAAAAHJbHUsahwghDHohind6zUFreZx3&response="+token;
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.USER_AGENT, ua);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		String json = "";
		HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);

		try{
			ResponseEntity<String> response= restTemplate.postForEntity(endpoint, requestEntity, String.class);
			System.out.println("Google Response:"+response.getBody());
			if(response.getStatusCodeValue()==200) {
				JSONObject jo = JSON.parseObject(response.getBody());
				res = jo.getBoolean("success");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Google verify is "+res);
		return res;
	}
}
