/*
 * 本配置文件声明了整个应用的请求处理部分。
 */
var ioc = {
	failProcessor : {
		type : 'com.uxuexi.core.web.www.chain.WwwFailProcessor',
		singleton : false,
		fields : {
			kvConfig : {
				refer : "kvConfig"
			}

		}
	},
	"default" : {
		"ps" : [
				"org.nutz.mvc.impl.processor.EncodingProcessor",
				"org.nutz.mvc.impl.processor.ModuleProcessor",
				"org.nutz.mvc.impl.processor.ActionFiltersProcessor",
				"org.nutz.mvc.impl.processor.AdaptorProcessor",
				
				// 防SQL注入
		        "com.uxuexi.core.web.chain.SqlInjectProcessor",
		        //参数验证
				"com.uxuexi.core.web.chain.ValidProcessor",
				
				"org.nutz.mvc.impl.processor.MethodInvokeProcessor",
				"org.nutz.mvc.impl.processor.ViewProcessor"],
		"error" : "ioc:failProcessor"
	}
}