/**
 * 文 件 名:  OrderMapper
 * 版    权:  Quanten Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zping
 * 修改时间:  2018/11/23 0023
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.chown.pine.sharding.database.mapper;

import com.chown.pine.sharding.database.entity.Order;

import java.util.List;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author zping
 * @version 2018/11/23 0023
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface OrderMapper
{
	/**
	 * 创建Order对象
	 *
	 * @param order
	 * @return
	 */
	int createOrder (final Order order);

	/**
	 * 查询所有数据
	 *
	 * @return
	 */
	List<Order> queryAll ();
}
