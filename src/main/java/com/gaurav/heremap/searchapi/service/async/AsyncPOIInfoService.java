package com.gaurav.heremap.searchapi.service.async;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gaurav.heremap.searchapi.service.proxy.FetchPOIInfoProxy;

@Service
public class AsyncPOIInfoService {
	
	@Autowired
	FetchPOIInfoProxy riProxy;
	
	@Value("${poi.app.app_id}")
	String app_id;
	
	@Value("${poi.app.app_code}")
	String app_code;
	
	@Async
	public CompletableFuture<String> getPlaceInfo(String at, String cat){
		return CompletableFuture.completedFuture(riProxy.getPlaceInfo(at, cat, app_id, app_code));
	}

}
