package com.liferay.change.tracking.web.internal.portlet.configuration;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import org.osgi.service.component.annotations.Component;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.servlet.http.HttpServletRequest;

@Component(
	property = "javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
	service = ConfigurationAction.class
)
public class SilencePopoverConfigurationAction  extends
	DefaultConfigurationAction {

	@Override
	public void processAction(
		PortletConfig portletConfig, ActionRequest actionRequest,
		ActionResponse actionResponse)
		throws Exception {

		setPreference(
			actionRequest, "silencePopoverTime",
			ParamUtil.getString(actionRequest, "silencePopoverTime"));

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {

		String cmd = ParamUtil.getString(httpServletRequest, Constants.CMD);

		return "/publications/configuration.jsp";
	}
}
