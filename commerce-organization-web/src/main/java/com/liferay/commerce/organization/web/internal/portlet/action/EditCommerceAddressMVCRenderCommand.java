/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.organization.web.internal.portlet.action;

import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.organization.web.internal.constants.CommerceOrganizationPortletKeys;
import com.liferay.commerce.organization.web.internal.display.context.CommerceOrganizationAddressesDisplayContext;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceOrganizationPortletKeys.COMMERCE_ORGANIZATION,
		"mvc.command.name=editCommerceAddress"
	},
	service = MVCRenderCommand.class
)
public class EditCommerceAddressMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		CommerceOrganizationAddressesDisplayContext
			commerceOrganizationAddressesDisplayContext =
				new CommerceOrganizationAddressesDisplayContext(
					_commerceAddressService, _commerceCountryService,
					_commerceOrganizationHelper, _commerceOrganizationService,
					_commerceRegionService, httpServletRequest, _portal);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceOrganizationAddressesDisplayContext);

		return "/edit_address.jsp";
	}

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceCountryService _commerceCountryService;

	@Reference
	private CommerceOrganizationHelper _commerceOrganizationHelper;

	@Reference
	private CommerceOrganizationService _commerceOrganizationService;

	@Reference
	private CommerceRegionService _commerceRegionService;

	@Reference
	private Portal _portal;

}