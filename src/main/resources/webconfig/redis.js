/*
 * 本配置文件声明了整个应用的redis连接部分。
 */
var ioc = {
	redisDs : {
		type : 'com.uxuexi.core.redis.support.impl.RedisDataSource',
		fields : {
			config : {
				type : 'com.uxuexi.core.redis.support.RedisConfig',
				args : [ '192.168.1.66', 6379, 10000 ]
			}
		}
	},
	redisDao : {
		type : "com.uxuexi.core.redis.RedisDao",
		args : [ {
			refer : "redisDs"
		} ]
	}
}