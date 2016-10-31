package com.bocweb.core.dao;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.util.Assert;

import com.bocweb.core.enums.EnumLoader;
import com.bocweb.core.util.date.DateFormatUtil;
import com.bocweb.core.vo.Condition;
import com.bocweb.core.vo.Operator;
import com.bocweb.core.vo.Page;
import com.bocweb.core.vo.PropertyType;
import com.bocweb.core.vo.QueryCondition;



public abstract class DaoImpl extends SessionFactoryImpl {

	/**
	 * 根据HQL与参数列表创建Query对象.
	 * @param hql
	 * @param params 数量可变的参数,按顺序绑定.
	 */
	public Query createQuery(String hql, Object... params) {
		Assert.hasText(hql, "hql不能为空");
		Query query = getSession().createQuery(hql);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		logger.info("\nhql>> {} \nparams: {}", hql, params);
		return query;
	}
	/**
	 * 根据HQL与参数列表创建Query对象.
	 * @param hql
	 * @param params 命名参数,按名称绑定.
	 */
	public Query createQuery(String hql, Map<String, ?> params) {
		Assert.hasText(hql, "hql不能为空.");
		Query query = getSession().createQuery(hql);
		if(params != null){
			query.setProperties(params);
		}
		logger.info("\nhql>> {} \nparams: {}", hql, params);
		return query;
	}
	/**
	 * 根据SQL与参数列表创建SQLQuery对象.
	 * @param sql
	 * @param params 数量可变的参数,按顺序绑定.
	 */
	public SQLQuery createSQLQuery(String sql, Object... params) {
		Assert.hasText(sql, "sql不能为空.");
		SQLQuery query = getSession().createSQLQuery(sql);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		logger.info("\nsql>> {} \nparams: {}", sql, params);
		return query;
	}
	/**
	 * 根据SQL与参数列表创建SQLQuery对象.
	 * @param sql
	 * @param params 命名参数,按名称绑定.
	 */
	public SQLQuery createSQLQuery(String sql, Map<String, ?> params) {
		Assert.hasText(sql, "sql不能为空.");
		SQLQuery query = getSession().createSQLQuery(sql);
		if(params != null){
			query.setProperties(params);
		}
		logger.info("\nsql>> {} \nparams: {}", sql, params);
		return query;
	}

	/**
	 * 设置分页参数
	 * 
	 * @param query
	 *            Query对象
	 * @param page
	 *            分页参数
	 */
	public static void setPageParam(Query query, Page page) {
		if (page != null) {
			logger.info("分页参数: {} ", page.toString());
			Integer start = page.getStart();
			Integer limit = page.getLimit();
			if (start != null && limit != null && page.getIsPage()) {
				query.setFirstResult(start);
				query.setMaxResults(limit);
			} else {
				logger.warn("无法进行分页, 将查询所有数据 ");
			}
		} else {
			logger.warn("无法进行分页, 将查询所有数据 page is null");
		}
	}
	/**
	 * 处理统计SQL语句，去掉order by，并处理fetch关键字 
	 * @param sql	统计SQL
	 */
	public String replaceSqlCount(String sql){
		String rst = "";
		if(sql != null) {
			Matcher m1 = Pattern.compile("order\\s+by\\s+(([0-9a-z\\_\\#]+){0,1}\\s*(\\.\\s*[0-9a-z\\_\\#]+)*)(,?([0-9a-z\\_\\#]+){0,1}\\s*(\\.\\s*[0-9a-z\\_\\#]+)*)*(\\s*(asc|desc){0,1})",Pattern.CASE_INSENSITIVE).matcher(sql);  
			rst = m1.replaceAll("");
			m1 = Pattern.compile("\\s+fetch\\s+",Pattern.CASE_INSENSITIVE).matcher(rst);  
			rst = m1.replaceAll(" ");
		}
		return rst;
	}
	/**
	 * 替换order by 满足大部分sql 和 hql 如果 特殊情况 请单独实现 一般用于统计总数时去掉排序sql
	 * 
	 * @param sql
	 *            SQL
	 */
	public static String replaceOrderBy(String sql) {
		String rst = "";
		if (sql != null) {
			Matcher m1 = Pattern
					.compile(
							"order\\s+by\\s+(([0-9a-z\\_\\#]+){0,1}\\s*(\\.\\s*[0-9a-z\\_\\#]+)*)(,?([0-9a-z\\_\\#]+){0,1}\\s*(\\.\\s*[0-9a-z\\_\\#]+)*)*(\\s*(asc|desc){0,1})",
							Pattern.CASE_INSENSITIVE).matcher(sql);
			rst = m1.replaceAll("");
			m1 = Pattern.compile("\\s+fetch\\s+", Pattern.CASE_INSENSITIVE)
					.matcher(rst);
			rst = m1.replaceAll(" ");
		}
		return rst;
	}
	/**
	 * 向SQL中附件order by 满足大部分sql 和 hql 如果 特殊情况 请单独实现
	 * 
	 * @param sql SQL
	 * @param page 排序
	 */
	public static String appendOrderBy(String sql, Page page) {
		if (sql != null && page != null && page.getSort() != null) {
			if(page.getDir() == null)page.setDir("asc");
			Matcher m1 = Pattern.compile("order\\s+by\\s", Pattern.CASE_INSENSITIVE).matcher(sql);
			if(!m1.find()){//没有order by语句
				StringBuilder str = new StringBuilder(sql);
				str.append(" order by ").append(page.getSort()).append(" ").append(page.getDir()).append(" ");
				return str.toString();
			}
		}
		return sql;
	}

	/**
	 * 转换总记录数，部分类型无法直接转换成Long型，故用此函数
	 * 
	 * @param obj
	 *            需转换成Long型的对象
	 */
	public Long convertTotalCount(Object obj) {
		if (obj != null && !"".equals(obj.toString())) {
			if (obj instanceof BigDecimal) {
				BigDecimal bd = (BigDecimal) obj;
				return bd.longValue();
			} else {
				return Long.parseLong(obj.toString());
			}
		}
		return 0l;
	}
	
	/**
	 * 解析查询条件
	 * @param condition
	 */
	public Conjunction parseCondition(QueryCondition condition){
		Conjunction conjunction = Restrictions.conjunction();
		Map<String, Object> searchParams = condition.getQueryParams();
		if(searchParams == null)return conjunction;
		Set<String> keys = searchParams.keySet();
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			String key = iterator.next();
			// 过滤掉空值
			Object value = searchParams.get(key);
//			logger.info("解析查询参数："+key+", 值："+value);
			// 拆分operator与filedAttribute
			String[] names = StringUtils.split(key, "_");
			String propertyTypeCode = names[0].substring(names[0].length() - 1).toUpperCase();
			PropertyType propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode);
			String filedName = names[1];//字段名称
			/*if (propertyTypeCode.equals("E")) {//枚举类型
				if(filedName.indexOf(".") != -1){
					filedName = filedName.substring(filedName.indexOf(".")+1);
				}
				value = EnumLoader.getEnumValue(filedName, value);
			}*/
			logger.info("查询字段："+key+", 类型："+propertyType.getValue());
			//查询方式
			String operatorCode = names[0].substring(0, names[0].length() - 1);
			Operator operator = Operator.valueOf(operatorCode.toUpperCase());
			
			if (key.contains("_OR_")) {
				Disjunction d = Restrictions.disjunction();
				for (int i = 1; i < names.length; i++) {
                    if (i % 2 == 1) {//奇数索引位置为属性名称
                    	filedName = names[i];//字段名称
//                    	logger.info("字段名称：or "+filedName);
        				// nested path translate,
        				// 如Task的名为"user.name"的filedName,
        				// 转换为Task.user.name属性
        				String[] filedNames = StringUtils.split(filedName, ".");
        				if(filedNames.length > 1){
        					String alias = filedNames[0].toLowerCase();
	    					if(!condition.getFetchMode().containsKey(filedNames[0])){
	    						condition.setFetchMode(filedNames[0]);
	    					}
	    					filedName = alias + "." + filedNames[1];
    					}
        				Criterion c = this.logicOperator(operator, propertyType, filedName, value);
        				if(c != null){
        					d.add(c);
        				}
                    }
                }
				conjunction.add(d);
			} else {
//            	logger.info("字段名称：and "+filedName);
				// nested path translate,
				// 如Task的名为"user.name"的filedName,
				// 转换为Task.user.name属性
				String[] filedNames = StringUtils.split(filedName, ".");
				if(filedNames.length > 1){
					String alias = filedNames[0].toLowerCase();
					if(!condition.getFetchMode().containsKey(filedNames[0])){
						condition.setFetchMode(filedNames[0]);
					}
					filedName = alias + "." + filedNames[1];
				}
				Criterion c = this.logicOperator(operator, propertyType, filedName, value);
				if(c != null){
					conjunction.add(c);
				}
			}
		}
		//OR查询
		List<Condition[]> orParams = condition.getQueryORParams();
		if(orParams.isEmpty()){
			return conjunction;
		}
		int size = orParams.size();
		for (int i = 0; i < size; i++) {
			Condition[] searchOrParams = orParams.get(i);
			if(searchOrParams != null && searchOrParams.length > 0){
				Disjunction d = Restrictions.disjunction();
				int psize = searchOrParams.length;
				for (int j = 0; j < psize; j++) {
					Condition condit = searchOrParams[j];
					String key = condit.getKey();
					Object value = condit.getValue();
					String[] names = StringUtils.split(key, "_");
					String propertyTypeCode = names[0].substring(names[0].length() - 1).toUpperCase();
					PropertyType propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode);
					//查询方式
					String operatorCode = names[0].substring(0, names[0].length() - 1);
					Operator operator = Operator.valueOf(operatorCode.toUpperCase());
					String filedName = names[1];//字段名称
					String[] filedNames = StringUtils.split(filedName, ".");
					if(filedNames.length > 1){
						String alias = filedNames[0].toLowerCase();
						if(!condition.getFetchMode().containsKey(filedNames[0])){
							condition.setFetchMode(filedNames[0]);
						}
						filedName = alias + "." + filedNames[1];
					}
					Criterion c = this.logicOperator(operator, propertyType, filedName, value);
					if(c != null){
						d.add(c);
					}
				}
				conjunction.add(d);
			}
		}
		return conjunction;
	}
	
	/**
	 * 解析级联加载的对象
	 * @param criteria
	 * @param condition
	 */
	public void parseFetchMode(Criteria criteria, QueryCondition condition){
		Map<String, JoinType> fetchs = condition.getFetchMode();
		if(fetchs != null){
			Set<String> keys = fetchs.keySet();
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
				String key = iterator.next();
	//			logger.info("级联加载fetch："+key+", "+fetchs.get(key));
				if(JoinType.INNER_JOIN.equals(fetchs.get(key))){
					criteria.setFetchMode(key, FetchMode.JOIN);
					criteria.createAlias(key, key.toLowerCase(), JoinType.INNER_JOIN);
				}else if(JoinType.RIGHT_OUTER_JOIN.equals(fetchs.get(key))){
					criteria.setFetchMode(key, FetchMode.JOIN);
					criteria.createAlias(key, key.toLowerCase(), JoinType.RIGHT_OUTER_JOIN);
				}else if(JoinType.LEFT_OUTER_JOIN.equals(fetchs.get(key))){
					criteria.setFetchMode(key, FetchMode.JOIN);
					criteria.createAlias(key, key.toLowerCase(), JoinType.LEFT_OUTER_JOIN);
				}else{
					criteria.setFetchMode(key, FetchMode.JOIN);
					criteria.createAlias(key, key.toLowerCase(), JoinType.FULL_JOIN);
				}
			}
		}
	}
	
	/**
	 * 解析排序
	 * @param criteria
	 * @param condition
	 */
	public void parseOrder(Criteria criteria, QueryCondition condition){
		if(StringUtils.isBlank(condition.getSort()))return;
		String sorts[] = condition.getSort().split(",");
		String dirs[] = null;
		if(StringUtils.isNotBlank(condition.getDir())){
			dirs = condition.getDir().split(",");
		}
		for(int i=0; i<sorts.length; i++){
			String sort = sorts[i], dir = "ASC";
			if(dirs!=null && dirs.length > i){
				dir = dirs[i].toUpperCase();
			}
			String[] filedNames = StringUtils.split(sort, ".");
			if(filedNames.length > 1){
				String alias = filedNames[0].toLowerCase();
				if(!condition.getFetchMode().containsKey(filedNames[0])){
					condition.setFetchMode(filedNames[0]);
					criteria.setFetchMode(filedNames[0], FetchMode.JOIN);
					criteria.createAlias(filedNames[0], alias, JoinType.LEFT_OUTER_JOIN);
				}
				sort = alias + "." + filedNames[1];
			}
			if("DESC".equals(dir)){
				criteria.addOrder(Order.desc(sort));
			}else{
				criteria.addOrder(Order.asc(sort));
			}
		}
	}
	
	/**
	 * 解析排序
	 * @param alias	别名
	 * @param page	分页对象
	 */
	public String parseOrder(String alias, Page page){
		if(StringUtils.isBlank(page.getSort()))return "";
		StringBuilder order = new StringBuilder();
		String sorts[] = page.getSort().split(",");
		String dirs[] = null;
		if(StringUtils.isNotBlank(page.getDir())){
			dirs = page.getDir().split(",");
		}
		for(int i=0; i<sorts.length; i++){
			String sort = sorts[i], dir = "ASC";
			if(dirs!=null && dirs.length > i){
				dir = dirs[i].toUpperCase();
			}
			if(i == 0){
				order.append("ORDER BY ").append(alias).append(".").append(sort).append(" ").append(dir);
			}else{
				order.append(", ").append(alias).append(".").append(sort).append(" ").append(dir);
			}
		}
		return order.toString();
	}
	
	/**
	 * 此方法不用，因为类型太多，无法概全
	 * @param propertyName
	 * @param propertyType
	 * @param value
	 * @return
	 */
	private Serializable convertValue(String propertyName, PropertyType propertyType, Object value){
		if(propertyType != null && value != null){
			if(propertyType.equals(PropertyType.S)){
				return value.toString();
			}else if(propertyType.equals(PropertyType.I)){
				if(value instanceof Object[]){
					Object values[] = (Object[]) value;
					Integer[] num = new Integer[values.length];
			        for (int idx = 0; idx < values.length; idx++) {
			            num[idx] = Integer.parseInt(values[idx].toString());
			        }
			        return num;
				}
				return Integer.parseInt(value.toString());
			}else if(propertyType.equals(PropertyType.L)){
				if(value instanceof Object[]){
					Object values[] = (Object[]) value;
					Long[] num = new Long[values.length];
			        for (int idx = 0; idx < values.length; idx++) {
			            num[idx] = Long.parseLong(values[idx].toString());
			        }
			        return num;
				}
				return Long.parseLong(value.toString());
			}else if(propertyType.equals(PropertyType.N)){
				if(value instanceof Object[]){
					Object values[] = (Object[]) value;
					Double[] num = new Double[values.length];
			        for (int idx = 0; idx < values.length; idx++) {
			            num[idx] = Double.parseDouble(values[idx].toString());
			        }
			        return num;
				}
				return Double.parseDouble(value.toString());
			}else if(propertyType.equals(PropertyType.D)){
				if(value instanceof Date){
					return (Serializable) value;
				}
				return DateFormatUtil.convertDateYMDHMS(value.toString());
			}else if(propertyType.equals(PropertyType.B)){
				return Boolean.parseBoolean(value.toString());
			}else if(propertyType.equals(PropertyType.E)){
				if(value instanceof Collection){
					Collection<?> c = (Collection<?>)value;
					for (Iterator<?> iterator = c.iterator(); iterator.hasNext();) {
						Object o = iterator.next();
						o = EnumLoader.getValue(propertyName, o);
					}
					return (Serializable) c;
				}else if(value instanceof Object[]){
					Object[] o = (Object[]) value, returnObj = new Object[o.length];
					for (int i = 0; i < o.length; i++) {
						returnObj[i] = EnumLoader.getValue(propertyName, o[i]);
					}
					return returnObj;
				}else if(value.toString().indexOf(",") > -1){
					Object[] mvs = value.toString().split(","), returnObj = new Object[mvs.length];
					for (int i = 0; i < mvs.length; i++) {
						returnObj[i] = EnumLoader.getValue(propertyName, mvs[i]);
					}
					return returnObj;
				}else if(value.toString().indexOf("#") > -1){
					Object[] mvs = value.toString().split("#"), returnObj = new Object[mvs.length];
					for (int i = 0; i < mvs.length; i++) {
						returnObj[i] = EnumLoader.getValue(propertyName, mvs[i]);
					}
					return returnObj;
				}else{
					return (Serializable) EnumLoader.getValue(propertyName, value);
				}
			}
		}
		return (Serializable) value;
	}
	
	private Criterion logicOperator(Operator operator, PropertyType propertyType, String propertyName, Object val){
		Serializable value = this.convertValue(propertyName, propertyType, val);
		switch (operator) {
			case EQ:
				return Restrictions.eq(propertyName, value);
			case LIKE:
				return Restrictions.like(propertyName, "%" + value + "%");
			case LLIKE:
				return Restrictions.like(propertyName, value + "%");
			case RLIKE:
				return Restrictions.like(propertyName, "%" + value);
			case GT:
				return Restrictions.gt(propertyName, value);
			case LT:
				return Restrictions.lt(propertyName, value);
			case GTE:
				return Restrictions.ge(propertyName, value);
			case LTE:
				return Restrictions.le(propertyName, value);
			case NULL:
				return Restrictions.isNull(propertyName);
			case NNULL:
				return Restrictions.isNotNull(propertyName);
			case NLIKE:
				return Restrictions.not(Restrictions.like(propertyName, value));
			case NEQ:
				return Restrictions.ne(propertyName, value);
			case IN: {
				if(value instanceof Collection){
					return Restrictions.in(propertyName, (Collection<?>)value);
				}else if(value instanceof Object[]){
					return Restrictions.in(propertyName, (Object[]) value);
				}else if(value.toString().indexOf(",") > -1){
					Object[] mvs = value.toString().split(",");
					return Restrictions.in(propertyName, (Object[]) this.convertValue(propertyName, propertyType, mvs));
				}else if(value.toString().indexOf("#") > -1){
					Object[] mvs = value.toString().split("#");
					return Restrictions.in(propertyName, (Object[]) this.convertValue(propertyName, propertyType, mvs));
				}else{
					throw new IllegalArgumentException("IN查询的值["+value+"]没有按规则编写,无法转换.");
				}
			}
		}
		return null;
	}
}
