/**
 * 文 件 名:  Order
 * 版    权:  Quanten Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zping
 * 修改时间:  2018/11/23 0023
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.chown.pine.sharding.database.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author zping
 * @version 2018/11/23 0023
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class Order implements Serializable
{
	private Integer id;

	private Long order_id;

	private Long user_id;

	private String userName;

	private String passWord;

	private String userSex;

	private String nick_name;

	@Override
	public String toString ()
	{
		final StringBuilder sb = new StringBuilder ("Order{");
		sb.append ("id=").append (id);
		sb.append (", order_id='").append (order_id).append ('\'');
		sb.append (", user_id='").append (user_id).append ('\'');
		sb.append (", userName='").append (userName).append ('\'');
		sb.append (", passWord='").append (passWord).append ('\'');
		sb.append (", userSex='").append (userSex).append ('\'');
		sb.append (", nick_name='").append (nick_name).append ('\'');
		sb.append ('}');
		return sb.toString ();
	}
}
