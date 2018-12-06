/**
 * 文 件 名:  ModuloTableShardingAlgorithm
 * 版    权:  Quanten Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zping
 * 修改时间:  2018/11/22 0022
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.chown.pine.sharding.config;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * <简单分表策略实现>
 *
 * @author zping
 * @version 2018/11/22 0022
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ModuloTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Long>
{

	/**
	 * 键值分表策略
	 *
	 * @param tableNames    表集合
	 * @param shardingValue 分表策略字段如(order_id)
	 * @return
	 */
	@Override
	public String doEqualSharding (Collection<String> tableNames, ShardingValue<Long> shardingValue)
	{
		for (String tableName : tableNames)
		{
			if (tableName.endsWith (shardingValue.getValue () % 2 + ""))
			{
				return tableName;
			}
		}
		throw new IllegalArgumentException ();
	}

	/**
	 * In 处理查询策略
	 *
	 * @param tableNames    表集合
	 * @param shardingValue 分表策略键集合
	 * @return
	 */
	@Override
	public Collection<String> doInSharding (Collection<String> tableNames, ShardingValue<Long> shardingValue)
	{
		Collection<String> result = new LinkedHashSet<> (tableNames.size ());
		for (Long value : shardingValue.getValues ())
		{
			for (String tableName : tableNames)
			{
				if (tableName.endsWith (value % 2 + ""))
				{
					result.add (tableName);
				}
			}
		}
		return result;
	}

	/**
	 * Between 查询处理策略
	 *
	 * @param tableNames    表集合
	 * @param shardingValue Between查询范围键
	 * @return
	 */
	@Override
	public Collection<String> doBetweenSharding (Collection<String> tableNames, ShardingValue<Long> shardingValue)
	{
		Collection<String> result = new LinkedHashSet<> (tableNames.size ());
		Range<Long> range = shardingValue.getValueRange ();
		for (Long i = range.lowerEndpoint (); i <= range.upperEndpoint (); i++)
		{
			for (String each : tableNames)
			{
				if (each.endsWith (i % 2 + ""))
				{
					result.add (each);
				}
			}
		}
		return result;
	}
}
