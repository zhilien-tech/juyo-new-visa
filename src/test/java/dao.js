/*
 * 本配置文件声明了整个应用的数据库连接部分。
 */
var ioc = {
	/*
	 * 数据库连接池，采用 Apache 的 BasiceDataSource，具体的配置信息，请视自己本地数据库 情况进行修改
	 */
	dataSource : {
		type : 'org.h2.jdbcx.JdbcConnectionPool',
		events : {
			depose : 'dispose'
		},
		args : [ "jdbc:h2:mem:we-core-db;AUTO_RECONNECT=TRUE", "sa", "sa" ]
	},
	/*
	 * nutDao
	 */
	nutDao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [ {
			refer : "dataSource"
		} ]
	},
	/*
	 * 单元测试使用的主键生成策略
	 */
	idGen : {
		type : "com.uxuexi.core.db.dao.impl.TestNGIdGen"
	},
	/*
	 * DbDao 
	 **/
	dbDao : {
		type : "com.uxuexi.core.db.dao.impl.DbDao",
		args : [ {
			refer : "nutDao"
		}, {
			refer : "idGen"
		} 
		]
	}
};