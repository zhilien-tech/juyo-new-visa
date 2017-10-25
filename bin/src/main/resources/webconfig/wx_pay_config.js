/*
 * 微信支付配置信息
 */
var ioc = {
		wxPayProp:{
			type:"org.nutz.ioc.impl.PropertiesProxy",
			fields:{
				paths:"system_kv_config_dev.properties"
			}
		},
		wxPayConfig : {
		type : 'com.uxuexi.core.web.config.KvConfig',
		fields : {
			values : {
				weixin_auth_url:{java:"$wxPayProp.get('weixin_auth_url')"},
				weixin_js_config_url:{java:"$wxPayProp.get('weixin_js_config_url')"},
				wx_pay_gateway:{java:"$wxPayProp.get('wx_pay_gateway')"},
				send_url_gateway:{java:"$wxPayProp.get('send_url_gateway')"},
				notify_url:{java:"$wxPayProp.get('notify_url')"},
				wxPay_order_rediskey:{java:"$wxPayProp.get('wxPay_order_rediskey')"},
				wx_news_plat_money:{java:"$wxPayProp.get('wx_news_plat_money')"},
				clientFlag:{java:"$wxPayProp.get('clientFlag')"}
			}
		}
	}
};