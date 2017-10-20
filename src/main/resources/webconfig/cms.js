/*
 * 本配置文件声明了整个应用的cms部分。
 */
var ioc = {
	cms : {
		type : 'com.uxuexi.core.web.cms.Cms',
		fields : {
			changes : [ 
				{refer : 'dateCms'},{refer : 'staticCms'}
		    ]
		}
	},
	dateCms : {
		type : 'com.uxuexi.core.web.cms.interfaces.impl.DateCms'
	},
	staticCms : {
		type : 'com.uxuexi.core.web.cms.interfaces.impl.StaticCms',
		fields : {
			context : {
				app : '$servlet'
			}
		}
	},
	configuration:{
		type:'freemarker.template.Configuration'
	},
	urlProp:{
		type:'org.nutz.ioc.impl.PropertiesProxy',
		fields : {
			paths : []
		}
	}
}