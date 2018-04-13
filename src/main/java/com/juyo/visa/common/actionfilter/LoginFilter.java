package com.juyo.visa.common.actionfilter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.json.JsonFormat;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;
import org.nutz.mvc.view.ServerRedirectView;

import com.juyo.visa.admin.login.service.LoginService;
import com.juyo.visa.common.annotation.NoFilter;
import com.juyo.visa.common.base.MobileResult;
import com.uxuexi.core.web.view.Utf8JsonTransferView;

/**
 * 登录过滤器
 * @author 朱晓川
 *
 */
public class LoginFilter extends BaseActionFilter {

	@Override
	public View match(ActionContext ac) {
		if (ac.getModule().getClass().isAnnotationPresent(NoFilter.class)) {
			return null;
		}

		HttpSession session = Mvcs.getHttpSession(false);
		if (session == null)
			return new ServerRedirectView("/admin/login.html");
		Object obj = session.getAttribute(LoginService.LOGINUSER);

		if (null == obj) {
			//判断访问类型
			HttpServletRequest req = ac.getRequest();
			if (isAjax(req)) {
				Utf8JsonTransferView jsonView = new Utf8JsonTransferView(JsonFormat.full());

				Map<String, Object> data = MobileResult.error("对不起,您尚未登录,或登录已失效。", null);
				jsonView.setData(data);
				return jsonView;
			} else {
				return new ServerRedirectView("/admin/login.html");
			}

		}

		return null;
	}

}
