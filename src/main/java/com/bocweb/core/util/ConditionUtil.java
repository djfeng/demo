package com.bocweb.core.util;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.bocweb.core.vo.Page;
import com.bocweb.core.vo.QueryCondition;
/**
 * 解析查询参数的工具类
 * 从request对象中获取查询参数、分页参数、排序参数
 * 注：查询参数以"search_"开头
 * @author tongpuxin
 */
public class ConditionUtil {	
	public static final String prefix = "search_";

	/**
	 * 从request对象中获取查询参数、分页参数、排序参数
	 * @param request
	 * @return	QueryCondition
	 */
	public static QueryCondition getCondition(HttpServletRequest request){
		Assert.notNull(request, "request must not be null");
		QueryCondition qc = new QueryCondition();
		Map<String, Object> params = new HashMap<String, Object>();
		Enumeration<?> paramNames = request.getParameterNames();
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
//            logger.info("参数名称："+paramName);
            if ("".equals(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(unprefixed, values);
                } else if(StringUtils.isNotBlank(values[0])) {
                    params.put(unprefixed, values[0]);
                }
//                logger.info("参数值："+params.get(unprefixed));
            }else if("pageNo".equals(paramName)){
            	String pageNo = request.getParameter("pageNo");
            	if(StringUtils.isNotBlank(pageNo)){
					qc.setPageNo(Integer.parseInt(pageNo));
        		}
            }else if("start".equals(paramName)){
            	String start = request.getParameter("start");
            	if(StringUtils.isNotBlank(start)){
					qc.setStart(Integer.parseInt(start));
        		}
            }else if("limit".equals(paramName)){
            	String limit = request.getParameter("limit");
            	if(StringUtils.isNotBlank(limit)){
					qc.setLimit(Integer.parseInt(limit));
        		}
            }else if("isPage".equals(paramName)){
            	String isPage = request.getParameter("isPage");
            	if(StringUtils.isNotBlank(isPage)){
					qc.setIsPage(Boolean.parseBoolean(isPage));
        		}
            }else if("isCountPage".equals(paramName)){
            	String isCountPage = request.getParameter("isCountPage");
            	if(StringUtils.isNotBlank(isCountPage)){
					qc.setIsCountPage(Boolean.parseBoolean(isCountPage));
        		}
            }else if("sort".equals(paramName)){
            	String sort = request.getParameter("sort");
            	if(StringUtils.isNotBlank(sort)){
        			qc.setSort(sort);
        		}
            }else if("dir".equals(paramName)){
            	String dir = request.getParameter("dir");
            	if(StringUtils.isNotBlank(dir)){
        			qc.setDir(dir);
        		}
            }
        }
        qc.setQueryParams(params);
        return qc;
	}
	
	/**
	 * 从request对象中获取分页参数、排序参数
	 * @param request
	 * @return	Page
	 */
	public static Page getPage(HttpServletRequest request, Page page){
		Assert.notNull(request, "request must not be null");
		if(page == null)page = new Page();
		Enumeration<?> paramNames = request.getParameterNames();
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if("pageNo".equals(paramName)){
            	String pageNo = request.getParameter("pageNo");
            	if(StringUtils.isNotBlank(pageNo)){
    				page.setPageNo(Integer.parseInt(pageNo));
        		}
            }else if("start".equals(paramName)){
            	String start = request.getParameter("start");
            	if(StringUtils.isNotBlank(start)){
    				page.setStart(Integer.parseInt(start));
        		}
            }else if("limit".equals(paramName)){
            	String limit = request.getParameter("limit");
            	if(StringUtils.isNotBlank(limit)){
        			page.setLimit(Integer.parseInt(limit));
        		}
            }else if("isPage".equals(paramName)){
            	String isPage = request.getParameter("isPage");
            	if(StringUtils.isNotBlank(isPage)){
    				page.setIsPage(Boolean.parseBoolean(isPage));
        		}
            }else if("isCountPage".equals(paramName)){
            	String isCountPage = request.getParameter("isCountPage");
            	if(StringUtils.isNotBlank(isCountPage)){
    				page.setIsCountPage(Boolean.parseBoolean(isCountPage));
        		}
            }else if("sort".equals(paramName)){
            	String sort = request.getParameter("sort");
            	if(StringUtils.isNotBlank(sort)){
            		page.setSort(sort);
        		}
            }else if("dir".equals(paramName)){
            	String dir = request.getParameter("dir");
            	if(StringUtils.isNotBlank(dir)){
            		page.setDir(dir);
        		}
            }
        }
        return page;
	}
	
	/**
	 * 从request对象中获取分页参数、排序参数 
	 * 	(因是用Page直接new的对象，故无法强转为子类)
	 * @param request
	 * @return Page
	 */
	public static Page getPage(HttpServletRequest request){
		Page page = new Page();
		getPage(request, page);
		return page;
	}
	
	/**
	 * 处理返回查询条件
	 * @param paramName	search_开头的查询条件
	 * @return
	 */
	public static String getParamName(String paramName){
		if(paramName.startsWith(ConditionUtil.prefix)){
			paramName = paramName.substring(ConditionUtil.prefix.length());
		}
		return paramName;
	}
}
