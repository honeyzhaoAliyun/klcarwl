package com.klcarwl.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.klcarwl.util.Pager;
import com.klcarwl.util.Pager.OrderType;

/**
 * Dao实现类 - Dao实现类基类
 */

@Repository
public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	@Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	private Class<T> entityClass;
	protected SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		this.entityClass = null;
        Class c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
	}

	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T get(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public T load(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().load(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> get(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		String hql = "from " + entityClass.getName() + " as model where model.id in(:ids) and model.isUse =1";
		return getSession().createQuery(hql).setParameterList("ids", ids).list();
	}

	@SuppressWarnings("unchecked")
	public T get(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model." + propertyName + " = ? and model.isUse =1";
		return (T) getSession().createQuery(hql).setParameter(0, value).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getList(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model." + propertyName + " = ? and model.isUse =1";
		return getSession().createQuery(hql).setParameter(0, value).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getLikeList(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model." + propertyName + " like ? and model.isUse =1";
		return getSession().createQuery(hql).setParameter(0, "%"+value+"%").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getCreateHql(String hql) {
		return getSession().createQuery(hql).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		String hql = "from " + entityClass.getName();
		return getSession().createQuery(hql).list();
	}
	
	public Long getTotalCount() {
		String hql = "select count(*) from " + entityClass.getName() +" where model.isUse =1";
		return (Long) getSession().createQuery(hql).uniqueResult();
	}

	public boolean isUnique(String propertyName, Object oldValue, Object newValue) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(newValue, "newValue is required");
		if (newValue == oldValue || newValue.equals(oldValue)) {
			return true;
		}
		if (newValue instanceof String) {
			if (oldValue != null && StringUtils.equalsIgnoreCase((String) oldValue, (String) newValue)) {
				return true;
			}
		}
		T object = get(propertyName, newValue);
		return (object == null);
	}
	
	public boolean isExist(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		T object = get(propertyName, value);
		return (object != null);
	}

	@SuppressWarnings("unchecked")
	public PK save(T entity) {
		Assert.notNull(entity, "entity is required");
		return (PK) getSession().save(entity);
	}

	public void update(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().update(entity);
	}
	
	public void saveOrUpdate(T entity){
		Assert.notNull(entity, "entity is required");
		getSession().saveOrUpdate(entity);
	}

	public void delete(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().delete(entity);
	}

	public void delete(PK id) {
		Assert.notNull(id, "id is required");
		T entity = load(id);
		getSession().delete(entity);
	}

	public void delete(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		for (PK id : ids) {
			T entity = load(id);
			getSession().delete(entity);
		}
	}
	public void flush() {
		getSession().flush();
	}
	public void clear() {
		getSession().clear();
	}
	public void evict(Object object) {
		Assert.notNull(object, "object is required");
		getSession().evict(object);
	}	
	public Pager findByPager(Pager pager) {
		if (pager == null) {
			pager = new Pager();
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entityClass);
		return findByPager(pager, detachedCriteria);
	}
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		if (pager == null) {
			pager = new Pager();
		}
		Integer pageNumber = pager.getPageNumber();
		Integer pageSize = pager.getPageSize();
		String property = pager.getProperty();
		String keyword = pager.getKeyword();
		String orderBy = pager.getOrderBy();
		OrderType orderType = pager.getOrderType();
		
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		if (StringUtils.isNotEmpty(property) && StringUtils.isNotEmpty(keyword)) {
			String propertyString = "";
			if (property.contains(".")) {
				String propertyPrefix = StringUtils.substringBefore(property, ".");
				String propertySuffix = StringUtils.substringAfter(property, ".");
				criteria.createAlias(propertyPrefix, "model");
				propertyString = "model." + propertySuffix;
			} else {
				propertyString = property;
			}
			criteria.add(Restrictions.like(propertyString, "%" + keyword + "%"));
		}
		//删除了的数据不显示
		criteria.add(Restrictions.eq("isUse","1"));
		Integer totalCount = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
		
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult((pageNumber - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		if (StringUtils.isNotEmpty(orderBy) && orderType != null) {
			if (orderType == OrderType.asc) {
				criteria.addOrder(Order.asc(orderBy));
			} else {
				criteria.addOrder(Order.desc(orderBy));
			}
		}
		pager.setTotalCount(totalCount);
		pager.setList(criteria.list());
		return pager;
	}
	/**
	 * 查询单个对象
	 * @param hql
	 * @param params
	 * @return
	 */
	public Object queryForObject(final String hql, final Object[] params) {
		Query query = getSession().createQuery(hql);
		initParams(query, params);
		return query.uniqueResult();

	}
	/**
	 * 查询单个对象
	 * @param hql
	 * @param params
	 * @return
	 */
	public Object sqlQueryForObject(final String hql, final Object[] params) {
		Query query = getSession().createSQLQuery(hql);
		initParams(query, params);
		return query.uniqueResult();

	}
	/**
	 * hibernate分条件，分页查找
	 * @param hql
	 * @param params
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List queryForList(final String hql, final Object[] params) {		
		Query query = getSession().createQuery(hql);
		initParams(query, params);
		return query.list();
	}
	/**
	 * SQL分条件，分页查找
	 * @param hql
	 * @param params
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List sqlQueryForList(final String hql, final Object[] params) {		
		Query query = getSession().createSQLQuery(hql);
		initParams(query, params);
		return query.list();
	}
	/**
	 * hibernate分条件，分页查找
	 * @param hql
	 * @param params
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List queryForList(final String hql, final Object[] params,
			final int firstResult, final int maxResults) {		
		Query query = getSession().createQuery(hql);
		initParams(query, params);
		return query.setFirstResult(firstResult).setMaxResults(
				maxResults).list();
	}
	/**
	 * SQL分条件，分页查找
	 * @param hql
	 * @param params
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List sqlQueryForList(final String hql, final Object[] params,
			final int firstResult, final int maxResults) {		
		Query query = getSession().createSQLQuery(hql);
		initParams(query, params);
		return query.setFirstResult(firstResult).setMaxResults(
				maxResults).list();
	}
	/**
	 * 分条件查找参数添加
	 * @param query
	 * @param params
	 */
	protected void initParams(Query query, Object[] params) {
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
	}
	/**
	 * 根据DetachedCriteria对象进行查询.
	 * @param 
	 * @return List
	 */
	public List<T> find(DetachedCriteria detachedCriteria) {
		// TODO Auto-generated method stub
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());		
		//删除了的数据不显示
		criteria.add(Restrictions.eq("isUse","1"));	
		return criteria.list();
	}
}