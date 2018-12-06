/**
 * 文 件 名:  BootstrapApplication
 * 版    权:  Quanten Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zping
 * 修改时间:  2018/11/22 0022
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.chown.pine.sharding.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * <应用程序启动入口>
 *
 * @author zping
 * @version 2018/11/22 0022
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@SpringBootApplication
@ComponentScan (basePackages = "com.chown.pine.sharding")
public class BootstrapApplication
{

	/**
	 * 启动入口
	 *
	 * @param args
	 */
	public static void main (String[] args)
	{
		SpringApplication.run (BootstrapApplication.class, args);
	}
}
