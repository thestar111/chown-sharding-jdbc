/**
 * 文 件 名:  BootstrapApplicationTest
 * 版    权:  Quanten Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zping
 * 修改时间:  2018/11/23 0023
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.chown.pine.sharding;

import com.chown.pine.sharding.boot.BootstrapApplication;
import com.chown.pine.sharding.database.entity.Order;
import com.chown.pine.sharding.database.mapper.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author zping
 * @version 2018/11/23 0023
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RunWith (SpringRunner.class)
@SpringBootTest (classes = BootstrapApplication.class)
public class BootstrapApplicationTest
{

	@Autowired
	protected OrderMapper orderMapper;

	/**
	 * 程序启动测试类
	 */
	@Test
	public void contentLoad ()
	{
		/*Order order = new Order ();
		order.setOrder_id (1000003l);
		order.setUser_id (1000003l);
		order.setNick_name ("zping");
		order.setPassWord ("12334");
		order.setUserName ("Pine Chown");
		order.setUserSex ("MAN");
		orderMapper.createOrder (order);*/

		List<Order> orders = orderMapper.queryAll ();

		System.out.println (orders);

	}
}
