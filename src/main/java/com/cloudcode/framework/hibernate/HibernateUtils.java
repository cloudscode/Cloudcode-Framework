package com.cloudcode.framework.hibernate;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.CriteriaImpl.OrderEntry;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.Assert;

import com.cloudcode.framework.utils.JdbcUtils;

public abstract class HibernateUtils {

	private static Log logger = LogFactory.getLog(HibernateUtils.class);

	public static final String CRITERIA_ASSERT_ERROR_MESSAGE = " 's type is not " + CriteriaImpl.class + ", please make sure you are using Hibernate 3.0.5!!! ";

	/**
	 * @param criteria
	 * @param offset
	 * @param maxPageItems
	 * @return
	 * @throws HibernateException
	 */
	@SuppressWarnings("rawtypes")
	public static List getPageResult(Criteria criteria, int offset, int maxPageItems) throws HibernateException {
		if (offset >= 0) {
			criteria.setFirstResult(offset);
		}
		if (maxPageItems > 0) {
			criteria.setMaxResults(maxPageItems);
		}
		return criteria.list();
	}

	/**
	 * @param hibernateTemplate
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PaginationSupport findByCriteria(HibernateTemplate hibernateTemplate, final DetachedCriteria criteria, final int firstResult,
			final int maxResults) throws DataAccessException {

		return (PaginationSupport) hibernateTemplate.executeWithNativeSession(

		new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria executableCriteria = criteria.getExecutableCriteria(session);
				// Get the orginal orderEntries
				OrderEntry[] orderEntries = HibernateUtils.getOrders(executableCriteria);
				// Remove the orders
				executableCriteria = HibernateUtils.removeOrders(executableCriteria);
				// Get the original projection
				Projection projection = HibernateUtils.getProjection(executableCriteria);
				// Get total record count
				int totalCount = ((Long) executableCriteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
				executableCriteria.setProjection(projection);
				if (projection == null) {
					// Set the ResultTransformer to get the same object
					// structure with hql
					executableCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				}
				// Add the orginal orderEntries
				executableCriteria = HibernateUtils.addOrders(executableCriteria, orderEntries);
				// Now, the Projection and the orderEntries have been resumed
				List items = HibernateUtils.getPageResult(executableCriteria, firstResult, maxResults);
				return new PaginationSupport(items, totalCount, firstResult, maxResults);
			}

		});

	}

	/**
	 * @param hibernateTemplate
	 * @param queryString
	 * @param isNamedQuery
	 * @param paramNames
	 * @param paramValues
	 * @return
	 * @throws IllegalArgumentException
	 * @throws DataAccessException
	 */
	@SuppressWarnings("rawtypes")
	public static int getTotalCount(HibernateTemplate hibernateTemplate, String queryString, boolean isNamedQuery, String[] paramNames, Object[] paramValues)
			throws IllegalArgumentException, DataAccessException {

		if (StringUtils.isBlank(queryString)) {
			throw new IllegalArgumentException(" queryString can't be blank ");
		}
		String countQueryString = " select count (*) " + JdbcUtils.removeSelect(JdbcUtils.removeOrders(queryString));
		List countList = !isNamedQuery ? hibernateTemplate.findByNamedParam(countQueryString, paramNames, paramValues) : hibernateTemplate
				.findByNamedQueryAndNamedParam(queryString, paramNames, paramValues);
		return ((Integer) countList.get(0)).intValue();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static PaginationSupport findByQuery(HibernateTemplate hibernateTemplate, final String queryString, final boolean isNamedQuery,
			final String[] paramNames, final Object[] values, final int firstResult, final int maxResults) throws IllegalArgumentException, DataAccessException {

		if (StringUtils.isBlank(queryString)) {
			throw new IllegalArgumentException(" queryString can't be blank ");
		}
		// int totalCount = 10;
		int totalCount = getTotalCount(hibernateTemplate, queryString, isNamedQuery, paramNames, values);
		List items = (List) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				Query query = isNamedQuery ? session.getNamedQuery(queryString) : session.createQuery(queryString);
				for (int i = 0; i < values.length; i++) {
					Object value = values[i];
					if (value instanceof List) {
						query.setParameterList(paramNames[i], (List) value);
					} else if (value.getClass().isArray()) {
						query.setParameterList(paramNames[i], (Object[]) value);
					} else {
						query.setParameter(paramNames[i], value);
					}
				}
				query.setFirstResult(firstResult);
				query.setMaxResults(maxResults);
				return query.list();
			}
		});
		return new PaginationSupport(items, totalCount, firstResult, maxResults);
	}

	/**
	 * 
	 * @see CriteriaImpl#getProjection()
	 * @param criteria
	 *            the criteria
	 * @return the Projection
	 */
	public static Projection getProjection(Criteria criteria) {
		assertType(criteria);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		return impl.getProjection();
	}

	/**
	 * @param criteria
	 */
	private static void assertType(Criteria criteria) {
		Assert.notNull(criteria, " criteria is required. ");
		String message = criteria + CRITERIA_ASSERT_ERROR_MESSAGE;
		if (!CriteriaImpl.class.isInstance(criteria)) {
			if (logger.isDebugEnabled()) {
				logger.debug(message);
			}
		}
	}

	/**
	 * 
	 * 
	 * @param criteria
	 *            the criteria
	 * @return the OrderEntry[]
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static OrderEntry[] getOrders(Criteria criteria) {
		assertType(criteria);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		Field field = getOrderEntriesField(criteria);
		try {
			return (OrderEntry[]) ((List) field.get(impl)).toArray(new OrderEntry[0]);
		} catch (Exception e) {
			logAndThrowException(criteria, e);
			throw new InternalError(" Runtime Exception impossibility can't throw ");
		}
	}

	/**
	 * @param criteria
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Criteria removeOrders(Criteria criteria) {
		assertType(criteria);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		try {
			Field field = getOrderEntriesField(criteria);
			field.set(impl, new ArrayList());
			return impl;
		} catch (Exception e) {
			logAndThrowException(criteria, e);
			throw new InternalError(" Runtime Exception impossibility can't throw ");
		}
	}

	/**
	 * @param criteria
	 * @param orderEntries
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Criteria addOrders(Criteria criteria, OrderEntry[] orderEntries) {
		assertType(criteria);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		try {
			Field field = getOrderEntriesField(criteria);
			for (int i = 0; i < orderEntries.length; i++) {
				List<OrderEntry> innerOrderEntries = (List<OrderEntry>) field.get(criteria);
				innerOrderEntries.add(orderEntries[i]);
			}
			return impl;
		} catch (Exception e) {
			logAndThrowException(criteria, e);
			throw new InternalError(" Runtime Exception impossibility can't throw ");
		}
	}

	/**
	 * @param criteria
	 * @param e
	 */
	private static void logAndThrowException(Criteria criteria, Exception e) {
		String message = criteria + CRITERIA_ASSERT_ERROR_MESSAGE;
		if (logger.isDebugEnabled()) {
			logger.debug(message, e);
		}
	}

	/**
	 * @param criteria
	 * @return
	 */
	private static Field getOrderEntriesField(Criteria criteria) {
		Assert.notNull(criteria, " criteria is requried. ");
		try {
			Field field = CriteriaImpl.class.getDeclaredField("orderEntries");
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			logAndThrowException(criteria, e);
			throw new InternalError();
		}
	}

}
