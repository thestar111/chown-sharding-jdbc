/**
 * 文 件 名:  ModuloDatabaseShardingAlgorithm
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
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * <简单分库策略实现>
 *
 * @author zping
 * @version 2018/11/22 0022
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ModuloDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Long>
{

	/**
	 * 键值分库策略处理
	 *
	 * @param databaseNames 数据库名称集合
	 * @param shardingValue 分库策略配置字段值(如：user_id)
	 * @return
	 */
	@Override
	public String doEqualSharding (Collection<String> databaseNames, ShardingValue<Long> shardingValue)
	{
		for (String databaseName : databaseNames)
		{
			if (databaseName.endsWith (shardingValue.getValue () % 2 + ""))
			{
				return databaseName;
			}
		}
		throw new IllegalArgumentException ();
	}

	/**
	 * In 查询处理策略
	 *
	 * @param databaseNames
	 * @param shardingValue
	 * @return
	 */
	@Override
	public Collection<String> doInSharding (Collection<String> databaseNames, ShardingValue<Long> shardingValue)
	{
		Collection<String> result = new LinkedHashSet<> (databaseNames.size ());

		for (Long tablesShardingValue : shardingValue.getValues ())
		{
			for (String tableName : databaseNames)
			{
				if (tableName.endsWith (tablesShardingValue % 2 + ""))
				{
					result.add (tableName);
				}
			}
		}
		return result;
	}

	/**
	 * Between方式查询处理策略
	 *
	 * @param databaseNames 数据库集合
	 * @param shardingValue 分库键值逻辑值集合
	 * @return
	 */
	@Override
	public Collection<String> doBetweenSharding (Collection<String> databaseNames, ShardingValue<Long> shardingValue)
	{
		Collection<String> result = new LinkedHashSet<> (databaseNames.size ());
		Range<Long> range = (Range<Long>) shardingValue.getValueRange ();
		for (Long i = range.lowerEndpoint (); i <= range.upperEndpoint (); i++)
		{
			for (String databaseName : databaseNames)
			{
				if (databaseName.endsWith (i % 2 + ""))
				{
					result.add (databaseName);
				}
			}
		}
		return result;
	}
}
