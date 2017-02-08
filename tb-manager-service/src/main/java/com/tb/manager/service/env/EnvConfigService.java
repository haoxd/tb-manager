package com.tb.manager.service.env;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author acer11
 *  作者：郝旭东
* 创建时间：2017年2月7日 下午9:22:39  
* 项目名称：tb-manager-service  

* 文件名称：GetEnvConfigService.java  
* 类说明：通用获取配置文件值的service
 */
@Service("envConfigService")
public class EnvConfigService {
	
	@Value(value="${REPOSITORY_PATH}")
	public String REPOSITORY_PATH;
	
	@Value(value="${IMAGE_BASE_URL}")
	public String IMAGE_BASE_URL;
	
	

}
