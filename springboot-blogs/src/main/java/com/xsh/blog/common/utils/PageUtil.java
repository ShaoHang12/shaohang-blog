package com.xsh.blog.common.utils;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xsh.blog.common.bean.BaseEntity;
import com.xsh.blog.common.bean.BasePage;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: StudentHeart
 * @description: 分页工具类
 *
 **/
public class PageUtil {
    public static final String PAGE = "page";
    public static final String LIMIT = "limit";

    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    public static int getPage() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        try {
            if (request.getParameter(PAGE) != null  && request.getParameter(PAGE).length() > 0 )  {
                return Integer.parseInt(request.getParameter(PAGE) );
            } else {
                return 1;
            }
        }catch (NumberFormatException e){
            return 1;
        }
    }

    public static int getStart(){
        int start = (getPage() - 1) * getLimit();
        return start;
    }
    public static int getLimit(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        try{
            if (request.getParameter(LIMIT) != null && request.getParameter(LIMIT).length() > 0) {
                return Integer.parseInt(request.getParameter(LIMIT));
            } else {
                return 10;
            }
        }catch (NumberFormatException e){
            return 10;
        }
    }



    public static <T extends BaseEntity> BasePage<T> getPageData(Page<T> data) {
        return getBasePage(data);
    }

    public static <T> BasePage<T> getBasePage(Page<T> data) {
        BasePage<T> page = new BasePage<>();
        List<T> records = data.getRecords();
        long current = data.getCurrent();
        long size = data.getSize();
        long total = data.getTotal();
        long pages = data.getPages();
        page.setCurrent(current);
        page.setSize(size);
        page.setTotal(total);
        page.setPages(pages);
        page.setRecords(records);
        return page;
    }

    public static <T> Page<T> getPageModel(T entity){
        Page<T> page = new Page<T>(PageUtil.getPage(), PageUtil.getLimit());
        return page;
    }


    public static  <T>QueryWrapper<T> queryWrapper(T entity) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper(queryWrapper, entity);
        return queryWrapper;
    }

    public static  <T>void queryWrapper(QueryWrapper<T> queryWrapper, T entity) {
        Class<? extends Object> entityClass = entity.getClass();
        Field[] fields = ReflectUtil.getFields(entityClass);
        for (Field field : fields) {
            String name = convert(ReflectUtil.getFieldName(field));
            Object fieldValue = ReflectUtil.getFieldValue(entity, field);
            Object value = null;
            String method = null;
            // 判断该字段是否忽略
            // exist 为真 则 不忽略
            // exist 为假 则 忽略
            Field classField = ReflectUtil.getField(entityClass, name);
            boolean exist = true;
            try {
                // 获取到该字段的忽略状态 -此操作会异常,则默认为不忽略
                exist = AnnotationUtil.getAnnotationValue(classField, TableField.class, "exist");
            } catch (Exception e) {
                // 如果没有处理，则默认不忽略字段
                exist = true;
            }

            try {
                if (fieldValue instanceof Long) {
                    // 整数类型
                    value = String.valueOf(fieldValue);
                    method = "eq";
                } else if (fieldValue instanceof Boolean) {
                    value = fieldValue;
                    method = "eq";
                } else if (fieldValue instanceof String) {
                    // 字符串类型
                    // 获取搜索方式
                    String[] values = String.valueOf(fieldValue).split(":");
                    if (values != null && values.length > 1) {
                        value = values[0];
                        method = values[1];
                    } else {
                        value = values[0];
                        method = "like";
                    }

                }
            } catch (Exception e) {
                if (ObjectUtil.isNull(value)) {
                    value = null;
                }
                method = "";
            }

            if (!Objects.isNull(value) && StrUtil.isNotBlank(value.toString()) && exist) {
                where(queryWrapper, name, method, value);
            }
        }

    }


    public static  <T>void defaultOrder(QueryWrapper<T> wrapper, T entity) {
        Class<? extends Object> entityClass = entity.getClass();
        Field[] fields = ReflectUtil.getFields(entityClass);
        for (Field field : fields) {
            if (field.getName().equals("createTime")) {
                wrapper.orderByDesc("create_time");
            }
        }
    }



    private static  <T>void where(QueryWrapper<T> queryWrapper, String name, String method, Object value) {
        // 过滤
        if ("serial_version_u_i_d".equals(name)) {
            return;
        }
        if ("eq".equals(method)) {
            queryWrapper.eq(name, value);
        } else if ("like".equals(method)) {
            queryWrapper.like(name, value);
        } else {
            queryWrapper.like(name, value);
        }
    }


    /**
     * 转换为驼峰命名格式
     *
     * @param str 属性名
     * @return 驼峰命名格式属性名
     */
    public static String convert(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }



}

