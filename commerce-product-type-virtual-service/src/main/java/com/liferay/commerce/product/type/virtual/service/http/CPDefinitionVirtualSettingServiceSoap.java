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

package com.liferay.commerce.product.type.virtual.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link CPDefinitionVirtualSettingServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSettingSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting}, that is translated to a
 * {@link com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSettingSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionVirtualSettingServiceHttp
 * @see com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSettingSoap
 * @see CPDefinitionVirtualSettingServiceUtil
 * @generated
 */
@ProviderType
public class CPDefinitionVirtualSettingServiceSoap {
	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSettingSoap addCPDefinitionVirtualSetting(
		long cpDefinitionId, long fileEntryId, String url,
		int activationStatus, long duration, int maxUsages, boolean useSample,
		long sampleFileEntryId, String sampleUrl, boolean termsOfUseRequired,
		String[] termsOfUseContentMapLanguageIds,
		String[] termsOfUseContentMapValues,
		long termsOfUseJournalArticleResourcePrimKey,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> termsOfUseContentMap = LocalizationUtil.getLocalizationMap(termsOfUseContentMapLanguageIds,
					termsOfUseContentMapValues);

			com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting returnValue =
				CPDefinitionVirtualSettingServiceUtil.addCPDefinitionVirtualSetting(cpDefinitionId,
					fileEntryId, url, activationStatus, duration, maxUsages,
					useSample, sampleFileEntryId, sampleUrl,
					termsOfUseRequired, termsOfUseContentMap,
					termsOfUseJournalArticleResourcePrimKey, serviceContext);

			return com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSettingSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSettingSoap fetchCPDefinitionVirtualSettingByCPDefinitionId(
		long cpDefinitionId) throws RemoteException {
		try {
			com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting returnValue =
				CPDefinitionVirtualSettingServiceUtil.fetchCPDefinitionVirtualSettingByCPDefinitionId(cpDefinitionId);

			return com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSettingSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSettingSoap updateCPDefinitionVirtualSetting(
		long cpDefinitionVirtualSettingId, long fileEntryId, String url,
		int activationStatus, long duration, int maxUsages, boolean useSample,
		long sampleFileEntryId, String sampleUrl, boolean termsOfUseRequired,
		String[] termsOfUseContentMapLanguageIds,
		String[] termsOfUseContentMapValues,
		long termsOfUseJournalArticleResourcePrimKey,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> termsOfUseContentMap = LocalizationUtil.getLocalizationMap(termsOfUseContentMapLanguageIds,
					termsOfUseContentMapValues);

			com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting returnValue =
				CPDefinitionVirtualSettingServiceUtil.updateCPDefinitionVirtualSetting(cpDefinitionVirtualSettingId,
					fileEntryId, url, activationStatus, duration, maxUsages,
					useSample, sampleFileEntryId, sampleUrl,
					termsOfUseRequired, termsOfUseContentMap,
					termsOfUseJournalArticleResourcePrimKey, serviceContext);

			return com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSettingSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPDefinitionVirtualSettingServiceSoap.class);
}