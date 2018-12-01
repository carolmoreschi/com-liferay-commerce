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

package com.liferay.commerce.data.integration.manager.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.data.integration.manager.model.Process;
import com.liferay.commerce.data.integration.manager.service.ProcessLocalService;
import com.liferay.commerce.data.integration.manager.service.persistence.HistoryPersistence;
import com.liferay.commerce.data.integration.manager.service.persistence.ProcessPersistence;
import com.liferay.commerce.data.integration.manager.service.persistence.ScheduledTaskPersistence;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the process local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.data.integration.manager.service.impl.ProcessLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.commerce.data.integration.manager.service.impl.ProcessLocalServiceImpl
 * @see com.liferay.commerce.data.integration.manager.service.ProcessLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class ProcessLocalServiceBaseImpl extends BaseLocalServiceImpl
	implements ProcessLocalService, IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.commerce.data.integration.manager.service.ProcessLocalServiceUtil} to access the process local service.
	 */

	/**
	 * Adds the process to the database. Also notifies the appropriate model listeners.
	 *
	 * @param process the process
	 * @return the process that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Process addProcess(Process process) {
		process.setNew(true);

		return processPersistence.update(process);
	}

	/**
	 * Creates a new process with the primary key. Does not add the process to the database.
	 *
	 * @param processId the primary key for the new process
	 * @return the new process
	 */
	@Override
	@Transactional(enabled = false)
	public Process createProcess(long processId) {
		return processPersistence.create(processId);
	}

	/**
	 * Deletes the process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param processId the primary key of the process
	 * @return the process that was removed
	 * @throws PortalException if a process with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Process deleteProcess(long processId) throws PortalException {
		return processPersistence.remove(processId);
	}

	/**
	 * Deletes the process from the database. Also notifies the appropriate model listeners.
	 *
	 * @param process the process
	 * @return the process that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Process deleteProcess(Process process) {
		return processPersistence.remove(process);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(Process.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return processPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.data.integration.manager.model.impl.ProcessModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return processPersistence.findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.data.integration.manager.model.impl.ProcessModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return processPersistence.findWithDynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return processPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return processPersistence.countWithDynamicQuery(dynamicQuery, projection);
	}

	@Override
	public Process fetchProcess(long processId) {
		return processPersistence.fetchByPrimaryKey(processId);
	}

	/**
	 * Returns the process matching the UUID and group.
	 *
	 * @param uuid the process's UUID
	 * @param groupId the primary key of the group
	 * @return the matching process, or <code>null</code> if a matching process could not be found
	 */
	@Override
	public Process fetchProcessByUuidAndGroupId(String uuid, long groupId) {
		return processPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the process with the primary key.
	 *
	 * @param processId the primary key of the process
	 * @return the process
	 * @throws PortalException if a process with the primary key could not be found
	 */
	@Override
	public Process getProcess(long processId) throws PortalException {
		return processPersistence.findByPrimaryKey(processId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(processLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(Process.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("processId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(processLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(Process.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName("processId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(processLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(Process.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("processId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {
		final ExportActionableDynamicQuery exportActionableDynamicQuery = new ExportActionableDynamicQuery() {
				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(stagedModelType,
						modelAdditionCount);

					long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(portletDataContext,
							stagedModelType);

					manifestSummary.addModelDeletionCount(stagedModelType,
						modelDeletionCount);

					return modelAdditionCount;
				}
			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(new ActionableDynamicQuery.AddCriteriaMethod() {
				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(dynamicQuery,
						"modifiedDate");
				}
			});

		exportActionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<Process>() {
				@Override
				public void performAction(Process process)
					throws PortalException {
					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						process);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(Process.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return processLocalService.deleteProcess((Process)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return processPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the processes matching the UUID and company.
	 *
	 * @param uuid the UUID of the processes
	 * @param companyId the primary key of the company
	 * @return the matching processes, or an empty list if no matches were found
	 */
	@Override
	public List<Process> getProcessesByUuidAndCompanyId(String uuid,
		long companyId) {
		return processPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of processes matching the UUID and company.
	 *
	 * @param uuid the UUID of the processes
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of processes
	 * @param end the upper bound of the range of processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching processes, or an empty list if no matches were found
	 */
	@Override
	public List<Process> getProcessesByUuidAndCompanyId(String uuid,
		long companyId, int start, int end,
		OrderByComparator<Process> orderByComparator) {
		return processPersistence.findByUuid_C(uuid, companyId, start, end,
			orderByComparator);
	}

	/**
	 * Returns the process matching the UUID and group.
	 *
	 * @param uuid the process's UUID
	 * @param groupId the primary key of the group
	 * @return the matching process
	 * @throws PortalException if a matching process could not be found
	 */
	@Override
	public Process getProcessByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {
		return processPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.data.integration.manager.model.impl.ProcessModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of processes
	 * @param end the upper bound of the range of processes (not inclusive)
	 * @return the range of processes
	 */
	@Override
	public List<Process> getProcesses(int start, int end) {
		return processPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of processes.
	 *
	 * @return the number of processes
	 */
	@Override
	public int getProcessesCount() {
		return processPersistence.countAll();
	}

	/**
	 * Updates the process in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param process the process
	 * @return the process that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Process updateProcess(Process process) {
		return processPersistence.update(process);
	}

	/**
	 * Returns the history local service.
	 *
	 * @return the history local service
	 */
	public com.liferay.commerce.data.integration.manager.service.HistoryLocalService getHistoryLocalService() {
		return historyLocalService;
	}

	/**
	 * Sets the history local service.
	 *
	 * @param historyLocalService the history local service
	 */
	public void setHistoryLocalService(
		com.liferay.commerce.data.integration.manager.service.HistoryLocalService historyLocalService) {
		this.historyLocalService = historyLocalService;
	}

	/**
	 * Returns the history persistence.
	 *
	 * @return the history persistence
	 */
	public HistoryPersistence getHistoryPersistence() {
		return historyPersistence;
	}

	/**
	 * Sets the history persistence.
	 *
	 * @param historyPersistence the history persistence
	 */
	public void setHistoryPersistence(HistoryPersistence historyPersistence) {
		this.historyPersistence = historyPersistence;
	}

	/**
	 * Returns the process local service.
	 *
	 * @return the process local service
	 */
	public ProcessLocalService getProcessLocalService() {
		return processLocalService;
	}

	/**
	 * Sets the process local service.
	 *
	 * @param processLocalService the process local service
	 */
	public void setProcessLocalService(ProcessLocalService processLocalService) {
		this.processLocalService = processLocalService;
	}

	/**
	 * Returns the process persistence.
	 *
	 * @return the process persistence
	 */
	public ProcessPersistence getProcessPersistence() {
		return processPersistence;
	}

	/**
	 * Sets the process persistence.
	 *
	 * @param processPersistence the process persistence
	 */
	public void setProcessPersistence(ProcessPersistence processPersistence) {
		this.processPersistence = processPersistence;
	}

	/**
	 * Returns the scheduled task local service.
	 *
	 * @return the scheduled task local service
	 */
	public com.liferay.commerce.data.integration.manager.service.ScheduledTaskLocalService getScheduledTaskLocalService() {
		return scheduledTaskLocalService;
	}

	/**
	 * Sets the scheduled task local service.
	 *
	 * @param scheduledTaskLocalService the scheduled task local service
	 */
	public void setScheduledTaskLocalService(
		com.liferay.commerce.data.integration.manager.service.ScheduledTaskLocalService scheduledTaskLocalService) {
		this.scheduledTaskLocalService = scheduledTaskLocalService;
	}

	/**
	 * Returns the scheduled task persistence.
	 *
	 * @return the scheduled task persistence
	 */
	public ScheduledTaskPersistence getScheduledTaskPersistence() {
		return scheduledTaskPersistence;
	}

	/**
	 * Sets the scheduled task persistence.
	 *
	 * @param scheduledTaskPersistence the scheduled task persistence
	 */
	public void setScheduledTaskPersistence(
		ScheduledTaskPersistence scheduledTaskPersistence) {
		this.scheduledTaskPersistence = scheduledTaskPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.commerce.data.integration.manager.model.Process",
			processLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.commerce.data.integration.manager.model.Process");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return ProcessLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return Process.class;
	}

	protected String getModelClassName() {
		return Process.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = processPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.commerce.data.integration.manager.service.HistoryLocalService.class)
	protected com.liferay.commerce.data.integration.manager.service.HistoryLocalService historyLocalService;
	@BeanReference(type = HistoryPersistence.class)
	protected HistoryPersistence historyPersistence;
	@BeanReference(type = ProcessLocalService.class)
	protected ProcessLocalService processLocalService;
	@BeanReference(type = ProcessPersistence.class)
	protected ProcessPersistence processPersistence;
	@BeanReference(type = com.liferay.commerce.data.integration.manager.service.ScheduledTaskLocalService.class)
	protected com.liferay.commerce.data.integration.manager.service.ScheduledTaskLocalService scheduledTaskLocalService;
	@BeanReference(type = ScheduledTaskPersistence.class)
	protected ScheduledTaskPersistence scheduledTaskPersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}