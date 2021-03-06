<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
VirtualCPTypeHelper virtualCPTypeHelper = (VirtualCPTypeHelper)request.getAttribute("virtualCPTypeHelper");

CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);

CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(request);
CPInstance cpInstance = cpContentHelper.getDefaultCPInstance(request);

long cpDefinitionId = cpCatalogEntry.getCPDefinitionId();

CPDefinitionVirtualSetting cpDefinitionVirtualSetting = virtualCPTypeHelper.getCPDefinitionVirtualSetting(cpDefinitionId);
%>

<c:choose>
	<c:when test="<%= cpDefinitionVirtualSetting == null %>">
		<div class="alert alert-info mx-auto">
			<liferay-ui:message key="the-product-is-not-configured-correctly" />
		</div>
	</c:when>
	<c:otherwise>
		<div class="container-fluid product-detail" id="<portlet:namespace /><%= cpDefinitionId %>ProductContent">
			<div class="product-detail-header">
				<div class="row">
					<div class="col-lg-6 col-md-7">
						<div class="row">
							<div class="col-lg-2 col-md-3 col-xs-2">
								<div id="<portlet:namespace />thumbs-container">

									<%
									for (CPAttachmentFileEntry cpAttachmentFileEntry : cpContentHelper.getImages(cpDefinitionId)) {
										String url = cpContentHelper.getImageURL(cpAttachmentFileEntry.getFileEntry(), themeDisplay);
									%>

										<div class="card thumb" data-url="<%= url %>">
											<img class="center-block img-responsive" src="<%= url %>">
										</div>

									<%
									}
									%>

								</div>
							</div>

							<div class="col-lg-10 col-md-9 col-xs-10 full-image">
								<c:if test="<%= Validator.isNotNull(cpCatalogEntry.getDefaultImageFileUrl()) %>">
									<img class="center-block img-responsive" id="<portlet:namespace />full-image" src="<%= cpCatalogEntry.getDefaultImageFileUrl() %>">
								</c:if>
							</div>
						</div>
					</div>

					<div class="col-lg-6 col-md-5">
						<h1><%= cpCatalogEntry.getName() %></h1>

						<c:choose>
							<c:when test="<%= cpInstance != null %>">
								<h4 class="sku"><%= cpInstance.getSku() %></h4>

								<div class="price"><%= cpInstance.getPrice() %></div>

								<div class="availability"><%= cpContentHelper.getAvailabilityLabel(request) %></div>

								<div class="availabilityEstimate"><%= cpContentHelper.getAvailabilityEstimateLabel(request) %></div>

								<div class="stockQuantity"><%= cpContentHelper.getStockQuantityLabel(request) %></div>
							</c:when>
							<c:otherwise>
								<h4 class="sku" data-text-cp-instance-sku=""></h4>

								<div class="price" data-text-cp-instance-price=""></div>

								<div class="availability" data-text-cp-instance-availability=""></div>

								<div class="AvailabilityEstimate" data-text-cp-instance-availability-estimate=""></div>

								<div class="stockQuantity" data-text-cp-instance-stock-quantity=""></div>
							</c:otherwise>
						</c:choose>

						<div class="row">
							<div class="col-md-12">
								<c:if test="<%= Validator.isNotNull(virtualCPTypeHelper.getSampleURL(cpDefinitionId, themeDisplay)) %>">
									<a class="btn btn-primary" href="<%= virtualCPTypeHelper.getSampleURL(cpDefinitionId, themeDisplay) %>" style="margin: 50px 0;">
										<liferay-ui:message key="download-sample-file" />
									</a>
								</c:if>
							</div>
						</div>

						<div class="row">
							<div class="col-md-12">
								<div class="options">
									<%= cpContentHelper.renderOptions(renderRequest, renderResponse) %>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-12">
								<liferay-commerce:compare-product CPDefinitionId="<%= cpDefinitionId %>" />
							</div>
						</div>

						<div class="row">
							<div class="col-md-12">
								<liferay-util:dynamic-include key="com.liferay.commerce.product.content.web#/add_to_cart#" />
							</div>
						</div>
					</div>
				</div>
			</div>

			<%
			List<CPDefinitionSpecificationOptionValue> cpDefinitionSpecificationOptionValues = cpContentHelper.getCPDefinitionSpecificationOptionValues(cpDefinitionId);
			List<CPOptionCategory> cpOptionCategories = cpContentHelper.getCPOptionCategories(scopeGroupId);
			List<CPAttachmentFileEntry> cpAttachmentFileEntries = cpContentHelper.getCPAttachmentFileEntries(cpDefinitionId);
			%>

			<div class="row">
				<div class="product-detail-body w-100">
					<div class="nav-tabs-centered">
						<ul class="justify-content-center nav nav-tabs" role="tablist">
							<li class="nav-item" role="presentation">
								<a aria-controls="<portlet:namespace />description" aria-expanded="true" class="active nav-link" data-toggle="tab" href="#<portlet:namespace />description" role="tab">
									<%= LanguageUtil.get(resourceBundle, "description") %>
								</a>
							</li>

							<c:if test="<%= cpContentHelper.hasCPDefinitionSpecificationOptionValues(cpDefinitionId) %>">
								<li class="nav-item" role="presentation">
									<a aria-controls="<portlet:namespace />specification" aria-expanded="false" class="nav-link" data-toggle="tab" href="#<portlet:namespace />specification" role="tab">
										<%= LanguageUtil.get(resourceBundle, "specifications") %>
									</a>
								</li>
							</c:if>

							<c:if test="<%= !cpAttachmentFileEntries.isEmpty() %>">
								<li class="nav-item" role="presentation">
									<a aria-controls="<portlet:namespace />attachments" aria-expanded="false" class="nav-link" data-toggle="tab" href="#<portlet:namespace />attachments" role="tab">
										<%= LanguageUtil.get(resourceBundle, "attachments") %>
									</a>
								</li>
							</c:if>
						</ul>

						<div class="tab-content">
							<div class="active tab-pane" id="<portlet:namespace />description">
								<p><%= cpCatalogEntry.getDescription() %></p>
							</div>

							<c:if test="<%= cpContentHelper.hasCPDefinitionSpecificationOptionValues(cpDefinitionId) %>">
								<div class="tab-pane" id="<portlet:namespace />specification">
									<div class="table-responsive">
										<table class="table table-bordered table-striped">

											<%
											for (CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue : cpDefinitionSpecificationOptionValues) {
												CPSpecificationOption cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption();
											%>

												<tr>
													<td><%= cpSpecificationOption.getTitle(languageId) %></td>
													<td><%= cpDefinitionSpecificationOptionValue.getValue(languageId) %></td>
												</tr>

											<%
											}
											%>

										</table>
									</div>

									<%
									for (CPOptionCategory cpOptionCategory : cpOptionCategories) {
										List<CPDefinitionSpecificationOptionValue> categorizedCPDefinitionSpecificationOptionValues = cpContentHelper.getCategorizedCPDefinitionSpecificationOptionValues(cpDefinitionId, cpOptionCategory.getCPOptionCategoryId());
									%>

										<c:if test="<%= !categorizedCPDefinitionSpecificationOptionValues.isEmpty() %>">
											<div class="table-responsive">
												<table class="table table-bordered table-striped">
													<tr>
														<th><%= cpOptionCategory.getTitle(languageId) %></th>
														<th></th>
													</tr>

													<%
													for (CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue : categorizedCPDefinitionSpecificationOptionValues) {
														CPSpecificationOption cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption();
													%>

														<tr>
															<td><%= cpSpecificationOption.getTitle(languageId) %></td>
															<td><%= cpDefinitionSpecificationOptionValue.getValue(languageId) %></td>
														</tr>

													<%
													}
													%>

												</table>
											</div>
										</c:if>

									<%
									}
									%>

								</div>
							</c:if>

							<c:if test="<%= !cpAttachmentFileEntries.isEmpty() %>">
								<div class="tab-pane" id="<portlet:namespace />attachments">
									<div class="table-responsive">
										<table class="table table-bordered table-striped">

											<%
											for (CPAttachmentFileEntry curCPAttachmentFileEntry : cpAttachmentFileEntries) {
												FileEntry fileEntry = curCPAttachmentFileEntry.getFileEntry();
											%>

												<tr>
													<td>
														<span><%= curCPAttachmentFileEntry.getTitle(locale) %></span>

														<span>
															<aui:icon cssClass="icon-monospaced" image="download" markupView="lexicon" url="<%= cpContentHelper.getDownloadFileEntryURL(fileEntry, themeDisplay) %>" />
														</span>
													</td>
												</tr>

											<%
											}
											%>

										</table>
									</div>
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>

		<aui:script>
			$(document).ready(
				function() {
					$(".thumb").click(
						function() {
							$("#<portlet:namespace />full-image").attr("src", $(this).attr("data-url"));
						}
					);
				}
			);
		</aui:script>

		<aui:script use="liferay-commerce-product-content">
			var productContent = new Liferay.Portlet.ProductContent(
				{
					cpDefinitionId: <%= cpDefinitionId %>,
					fullImageSelector : '#<portlet:namespace />full-image',
					namespace: '<portlet:namespace />',
					productContentSelector: '#<portlet:namespace /><%= cpDefinitionId %>ProductContent',
					thumbsContainerSelector : '#<portlet:namespace />thumbs-container',
					viewAttachmentURL: '<%= String.valueOf(cpContentHelper.getViewAttachmentURL(liferayPortletRequest, liferayPortletResponse)) %>'
				}
			);

			Liferay.component('<portlet:namespace /><%= cpDefinitionId %>ProductContent', productContent);
		</aui:script>
	</c:otherwise>
</c:choose>