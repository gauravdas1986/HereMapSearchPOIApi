package com.gaurav.heremap.searchapi.service.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name="RESTAURANT-INFO-SERVICE", url="${poi.app.search.basepath}")
public interface FetchPOIInfoProxy {
	@GetMapping("/discover/explore")
	public String getPlaceInfo(@RequestParam(name="at") String at,
			@RequestParam(name="cat") String cat,
			@RequestParam(name="app_id") String app_id,
			@RequestParam(name="app_code") String app_code);

}
