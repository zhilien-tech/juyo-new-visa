package com.juyo.visa.common.actionfilter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.mvc.ActionContext;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;
import org.nutz.mvc.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.juyo.visa.admin.login.service.LoginService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.entities.TFunctionEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.StringUtil;
import com.uxuexi.core.common.util.Util;

public class AuthFilter extends BaseActionFilter {

	private Logger logger = LoggerFactory.getLogger(AuthFilter.class);

	/**请求路径后缀标志符*/
	public static final String REQ_TAIL_FLAG = ".";

	@Override
	public View match(ActionContext actionContext) {
		//首先确定该请求是否需要进行权限验证
		if (!isNeedValidate(actionContext)) {
			return null;
		}
		boolean isAllowed = true;

		//获取访问路径，去掉参数
		HttpServletRequest request = actionContext.getRequest();
		String requestPath = request.getServletPath();

		HttpSession session = Mvcs.getHttpSession();
		String currentPageIndex = request.getParameter("currentPageIndex");
		session.setAttribute("currentPageIndex", currentPageIndex);

		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		if (!Util.isEmpty(user) && CommonConstants.SUPER_ADMIN.equals(user.getName())) {
			return null;
		}

		isAllowed = hasPermission(session, requestPath);

		/*FunctionViewService funcService = Mvcs.getIoc().get(FunctionViewService.class, "functionViewService");
		TFunctionEntity function = funcService.findFuctionByRequestPath(requestPath);

		if (Util.isEmpty(function)) {
			isAllowed = true;
			logger.info("注意:访问地址:[" + requestPath + "]未设置未功能.");
		} else {
			logger.info("当前访问路径:[" + requestPath + "].");
			logger.info((null == user ? "--游客" : user.getName()) + "--访问后台功能:[" + function.getName()
					+ (isAllowed == true ? "]--允许" : "]--权限不足"));
		}

		if (isAllowed) {
			if (!Util.isEmpty(function)) {
				//SLogService sLogService = Mvcs.getIoc().get(SLogService.class, "sLogService");
				String ip = IpUtil.getIpAddr(request);
				//sLogService.addSyslog(function, user, ip);
			}
			return null;
		} else {
			String msg = "对不起，你没有访问该功能的权限！";
			//判断访问类型
			HttpServletRequest req = actionContext.getRequest();
			if (isAjax(req)) {
				Utf8JsonTransferView jsonView = new Utf8JsonTransferView(JsonFormat.full());

				Map<String, Object> data = MobileResult.error(msg, null);
				jsonView.setData(data);
				return jsonView;
			} else {
				return new JspView("common.auth_msg");
			}
		}*/
		return new JspView("common.auth_msg");
	}

	public boolean hasPermission(final HttpSession session, final String requestPath) {

		String reqPath = StringUtil.trimRight(requestPath, REQ_TAIL_FLAG);

		@SuppressWarnings("unchecked")
		List<TFunctionEntity> functions = (List<TFunctionEntity>) session.getAttribute(LoginService.AUTHS_KEY);
		if (Util.isEmpty(functions)) {
			return false;
		}
		for (TFunctionEntity f : functions) {
			String furl = f.getUrl();
			furl = StringUtil.trimRight(furl, REQ_TAIL_FLAG);
			if (!Util.isEmpty(furl)) {
				furl = furl.trim();
			}

			if (reqPath.equals(furl)) {
				return true;
			}
		}
		return false;
	}
}
