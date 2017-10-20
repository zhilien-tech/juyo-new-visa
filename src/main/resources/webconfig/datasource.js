/*
 * 本配置文件声明了整个应用的数据库连接部分。
 */
var ioc = {
	//使用属性文件进行配置
	dbConfig:{
		type:"org.nutz.ioc.impl.PropertiesProxy",
		fields:{
			paths:"system_kv_config_dev.properties"
		}
	},
	dataSource : {
		type : "com.alibaba.druid.pool.DruidDataSource",
		events : {
			depose : 'close'
		},
		fields : {
			url : {java:"$dbConfig.get('jdbc_url')"},
			username : {java:"$dbConfig.get('jdbc_username')"},
			password : {java:"$dbConfig.get('jdbc_password')"},
			
			/*初始化时建立物理连接的个数*/
			InitialSize : 20,
			
			/*最小连接池数量*/
			minIdle : 5,
			
			/*最大连接池数量*/
			maxActive : 20,
			
			/*获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁*/
			/*maxWait:5000,*/
			
			/**/
			MinEvictableIdleTimeMillis : 30000,
			
			/* 
			 * 1) Destroy线程会检测连接的间隔时间
			 * 2) testWhileIdle的判断依据
			 */
			TimeBetweenEvictionRunsMillis : 1801,
			
			/*用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用*/
			validationQuery:"SELECT '' FROM DUAL",
			
			/*
			 * 建议配置为true，不影响性能，并且保证安全性。
			 * 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，
			 * 执行validationQuery检测连接是否有效
			 */
			testWhileIdle: true,
			
			/*申请连接时是否执行validationQuery检测*/
			TestOnBorrow : false,
			
			/*归还连接时是否执行validationQuery检测*/
			testOnReturn : false,
			
			/*是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭*/
			poolPreparedStatements : false,
			
			/*
			 * 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：
			 * 1,监控统计用的filter:stat 
			 * 2,日志用的filter:log4j 
			 * 3,防御sql注入的filter:wall
			 */
			filters : 'stat'
		}
	},
	nutDao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [ {
			refer : "dataSource"
		} ]
	}
};