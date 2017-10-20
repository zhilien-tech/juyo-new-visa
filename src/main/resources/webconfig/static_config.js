/*
 * 本配置文件配置各个web项目的root路径，配置参数会注入request的web_root值中。
 */
var ioc = {
		staticConfig:{
			type:"org.nutz.ioc.impl.PropertiesProxy",
			fields:{
				paths:"system_kv_config_dev.properties"
			}
		},
		// 这个名称不能修改
		kvConfig : {
		type : 'com.uxuexi.core.web.config.KvConfig',
		fields : {
			values : {
				login_cookie_token_domain: 'com.xiaoka',
				sso_cookie_key : 'xiaokaToken',
				
				/*静态文件服务器配置*/
				fileServer : {java:"$staticConfig.get('fileServer')"},
				static_path:{java:"$staticConfig.get('static_path')"},
				version:{java:"$staticConfig.get('version')"},
				
				/*图片尺寸配置*/
				//小头像
				avatar_l : {java:"$staticConfig.get('avatar_l')"},
				
				//后台管理列表页封面图
				manage_list:{java:"$staticConfig.get('manage_list')"},
				
				//h5图片尺寸
				//首页轮播图
				h5_index_carousel:{java:"$staticConfig.get('h5_index_carousel')"},
				
				//首页课程
				h5_index_course:{java:"$staticConfig.get('h5_index_course')"},
				
				//课程列表
				h5_course_list:{java:"$staticConfig.get('h5_course_list')"},
				
				//课程详情
				h5_course_detail:{java:"$staticConfig.get('h5_course_detail')"},
				
				//个人中心头像
				h5_user_avatar:{java:"$staticConfig.get('h5_user_avatar')"}
			}
		}
	}
};
