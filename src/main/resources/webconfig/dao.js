/*
 * 本配置文件声明了整个应用的数据库连接部分。
 */
var ioc = {
	/*别使用它啦*/
	/*redisIdGen : {
		type : "com.uxuexi.core.web.www.db.RedisIdGen",
		fields : {
			dao : {
				refer : "redisDao"
			}
		}
	},*/
	dbDao : {
		type : "com.uxuexi.core.db.dao.impl.DbDao",
		args : [ {
			refer : "nutDao"
		} /*,{
			refer : "redisIdGen"
		}*/]
	}
};