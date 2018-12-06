/**
 * 文 件 名:  DataSourceConfig
 * 版    权:  Quanten Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zping
 * 修改时间:  2018/11/22 0022
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.chown.pine.sharding.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.BindingTableRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * <数据源配置>
 *
 * @author zping
 * @version 2018/11/22 0022
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
@Configuration
@PropertySource (value = { "classpath:/datasource.properties" }, encoding = "UTF-8")
@ConfigurationProperties (prefix = "druid")
public class DataSourceConfig
{

	/**
	 * 实体类包路径
	 */
	private static final String ENTITY_SCAN_PATH = "com.chown.pine.sharding.database.entity";

	/**
	 * mybatis配置文件路径
	 */
	private static final String MYBATIS_CONFIG_PATH = "classpath:/mybatis-config.xml";

	/**
	 * mybatis映射文件路径
	 */
	private static final String MAPPER_PATH = "classpath:/mapper/*.xml";

	/**
	 * 心跳检测SQL
	 */
	private String validationQuery;

	private Boolean testWhileIdle;

	private Boolean testOnBorrow;

	private Boolean testOnReturn;

	private Boolean poolPreparedStatements;

	/**
	 * 最大等待数
	 */
	private Integer maxWait;

	/**
	 * 最大活跃数
	 */
	private Integer maxActive;

	/**
	 * 最小空闲数
	 */
	private Integer minIdle;

	/**
	 * 初始化连接数
	 */
	private Integer initialSize;

	/**
	 * 配置过滤器(注意：如果使用了Druid的密码加密，一定要配置过滤器config，要不然他无法解密数据库密码)
	 */
	private String filters;

	private Integer timeBetweenEvictionRunsMillis;

	private Integer minEvictableIdleTimeMillis;

	private Integer maxPoolPreparedStatementPerConnectionSize;

	/**
	 * 配置数据源0，数据源的名称最好要有一定的规则，方便配置分库的计算规则
	 *
	 * @return
	 */
	@Bean (name = "dataSource0")
	@ConfigurationProperties (prefix = "spring.datasource.ds0")
	public DruidDataSource dataSource0 ()
	{
		DruidDataSource dataSource0 = new DruidDataSource ();
		dataSource0.setMinIdle (minIdle);
		dataSource0.setMaxActive (maxActive);
		dataSource0.setInitialSize (initialSize);
		dataSource0.setMinEvictableIdleTimeMillis (minEvictableIdleTimeMillis);
		dataSource0.setTimeBetweenConnectErrorMillis (timeBetweenEvictionRunsMillis);
		dataSource0.setMaxPoolPreparedStatementPerConnectionSize (maxPoolPreparedStatementPerConnectionSize);
		dataSource0.setMaxWait (maxWait);
		dataSource0.setTestWhileIdle (testWhileIdle);
		dataSource0.setTestOnReturn (testOnReturn);
		dataSource0.setTestOnBorrow (testOnBorrow);
		dataSource0.setValidationQuery (validationQuery);
		dataSource0.setPoolPreparedStatements (poolPreparedStatements);
		try
		{
			dataSource0.setFilters (filters);
		}
		catch (SQLException e)
		{
			e.printStackTrace ();
		}
		return dataSource0;
	}

	/**
	 * 配置数据源1，数据源的名称最好要有一定的规则，方便配置分库的计算规则
	 *
	 * @return
	 */
	@Bean (name = "dataSource1")
	@ConfigurationProperties (prefix = "spring.datasource.ds1")
	public DruidDataSource dataSource1 ()
	{
		DruidDataSource dataSource1 = new DruidDataSource ();
		dataSource1.setInitialSize (initialSize);
		dataSource1.setMaxActive (maxActive);
		dataSource1.setMinIdle (minIdle);
		dataSource1.setTimeBetweenConnectErrorMillis (timeBetweenEvictionRunsMillis);
		dataSource1.setMinEvictableIdleTimeMillis (minEvictableIdleTimeMillis);
		dataSource1.setTestOnBorrow (testOnBorrow);
		dataSource1.setTestOnReturn (testOnReturn);
		dataSource1.setTestWhileIdle (testWhileIdle);
		dataSource1.setMaxWait (maxWait);
		dataSource1.setPoolPreparedStatements (poolPreparedStatements);
		dataSource1.setValidationQuery (validationQuery);
		try
		{
			dataSource1.setFilters (filters);
		}
		catch (SQLException e)
		{
			e.printStackTrace ();
		}
		return dataSource1;
	}

	/**
	 * 配置数据源规则，即将多个数据源交给sharding-jdbc管理，并且可以设置默认的数据源，
	 * 当表没有配置分库规则时会使用默认的数据源
	 *
	 * @return
	 */
	@Bean
	public DataSourceRule dataSourceRule (@Qualifier ("dataSource0") DruidDataSource dataSource0,
			@Qualifier ("dataSource1") DruidDataSource dataSource1)
	{
		//设置分库映射
		Map<String, DataSource> dataSourceMap = new HashMap<> ();
		dataSourceMap.put ("dataSource0", dataSource0);
		dataSourceMap.put ("dataSource1", dataSource1);
		//设置默认库，两个库以上时必须设置默认库。默认库的数据源名称必须是dataSourceMap的key之一
		return new DataSourceRule (dataSourceMap, "dataSource0");
	}

	/**
	 * 配置数据源策略和表策略，具体策略需要自己实现
	 *
	 * @param dataSourceRule
	 * @return
	 */
	@Bean
	public ShardingRule configShardingRule (DataSourceRule dataSourceRule)
	{
		//配置分库分表策略
		TableRule orderTableRule = TableRule.builder ("t_order")
				//设置物理表
				.actualTables (Arrays.asList ("t_order_0", "t_order_1"))
				//设置分表策略
				.tableShardingStrategy (new TableShardingStrategy ("order_id", new ModuloTableShardingAlgorithm ()))
				//设置数据源
				.dataSourceRule (dataSourceRule).build ();

		//绑定表策略，在查询时会使用主表策略计算路由的数据源，因此需要约定绑定表策略的表的规则需要一致，可以一定程度提高效率
		List<BindingTableRule> bindingTableRules = new ArrayList<> ();
		bindingTableRules.add (new BindingTableRule (Arrays.asList (orderTableRule)));

		//构建分库分表策略规则
		return ShardingRule.builder ()
				//设置数据源策略规则
				.dataSourceRule (dataSourceRule)
				//设置分表策略规则
				.tableRules (Arrays.asList (orderTableRule))
				//绑定分表策略
				.bindingTableRules (bindingTableRules)
				//设置分库策略
				.databaseShardingStrategy (
						new DatabaseShardingStrategy ("user_id", new ModuloDatabaseShardingAlgorithm ()))
				//分表策略
				.tableShardingStrategy (new TableShardingStrategy ("order_id", new ModuloTableShardingAlgorithm ()))
				.build ();
	}

	/**
	 * 创建sharding-jdbc的数据源DataSource, MyBatisAutoConfiguration会使用此数据源
	 *
	 * @param shardingRule 分库分表策略配置
	 * @return
	 * @throws SQLException
	 */
	@Bean (name = "dataSource")
	public DataSource shardingDataSource (ShardingRule shardingRule) throws SQLException
	{
		return ShardingDataSourceFactory.createDataSource (shardingRule);
	}

	/**
	 * 创建SQLSessionFactory工厂对象
	 *
	 * @param dataSource
	 * @return
	 */
	@Bean (name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory (DataSource dataSource)
	{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean ();
		sqlSessionFactoryBean.setDataSource (dataSource);

		/*扫描DOMAIN包，可以使用别名*/
		sqlSessionFactoryBean.setTypeAliasesPackage (ENTITY_SCAN_PATH);

		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver ();

		try
		{
			/*扫描mybatis配置文件*/
			sqlSessionFactoryBean.setConfigLocation (resolver.getResource (MYBATIS_CONFIG_PATH));
			sqlSessionFactoryBean.setMapperLocations (resolver.getResources (MAPPER_PATH));
			return sqlSessionFactoryBean.getObject ();
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			return null;
		}
	}

	/**
	 * 创建SqlSessionTemplate模板对象
	 *
	 * @param sqlSessionFactory
	 * @return
	 */
	@Bean
	public SqlSessionTemplate sqlSessionTemplate (SqlSessionFactory sqlSessionFactory)
	{
		return new SqlSessionTemplate (sqlSessionFactory);
	}
}
