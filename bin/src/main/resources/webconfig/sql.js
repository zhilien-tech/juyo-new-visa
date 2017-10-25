/*
 * 本配置文件声明了整个应用的数据库连接部分。
 */
var ioc = {
	sqlManager : {
		type : "org.nutz.dao.impl.FileSqlManager",
		args : [ "websql/","sql" ]
	}
};