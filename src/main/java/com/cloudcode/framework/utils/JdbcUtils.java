package com.cloudcode.framework.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;


public abstract class JdbcUtils {

	/**
	 * @param jdbcTemplate
	 *            the jdbcTemplate
	 * @param queryString
	 *            sql
	 * @param paramValues
	 *            the arguments value
	 * @return the total count
	 * @throws IllegalArgumentException
	 *             if the queryString is blank
	 * @throws DataAccessException
	 *             if there is any problem
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static int getTotalCount(JdbcTemplate jdbcTemplate, String queryString, Object[] paramValues) throws IllegalArgumentException, DataAccessException {

		if (StringUtils.isBlank(queryString)) {
			throw new IllegalArgumentException(" queryString can't be blank ");
		}
		String countQueryString = " select count (*) " + removeSelect(removeOrders(queryString));

		Object object = jdbcTemplate.queryForObject(countQueryString, paramValues, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Integer(rs.getInt(1));
			}
		});
		return ((Integer) object).intValue();
	}

	/**
	 * @param sql
	 *            the sql
	 * @return the sql after removed order
	 */
	public static String removeOrders(String sql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(sql);

		StringBuffer sb = new StringBuffer();

		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);

		return sb.toString();
	}

	/**
	 * @param sql
	 *            the sql
	 * @return the sql after removed select
	 */
	public static String removeSelect(String sql) {
		Assert.notNull(sql, "sql must be specified ");
		int beginPos = sql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " the sql : " + sql + " must has a keyword 'from'");
		return sql.substring(beginPos);
	}

	/**
	 * Unit test
	 * 
	 * @param args
	 * 
	 *            public static void main(String[] args) { String sql = "select a.aa fRom A where a.ss = 'aa' ORDER by a12.ss , a4.cc";
	 *            System.out.println(removeSelect(removeOrders(sql))); }
	 */

}
