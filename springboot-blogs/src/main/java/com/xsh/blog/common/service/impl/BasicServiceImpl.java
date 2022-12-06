package com.xsh.blog.common.service.impl;


import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xsh.blog.common.bean.BaseEntity;
import com.xsh.blog.common.bean.BasePage;
import com.xsh.blog.common.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @program: StudentHeart
 * @description: 核心操作
 *
 **/
@Slf4j
public abstract class BasicServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> {
    private final static Logger logger = LoggerFactory.getLogger(BasicServiceImpl.class);



    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    public BasePage<T> page(T entity) {
        Page<T> page = new Page<T>(PageUtil.getPage(), PageUtil.getLimit());
        return page(page, entity);
    }

    public List<T> list(T entity) {
        // 默认开启查询下级的数据
        QueryWrapper<T> wrapper = queryWrapper(entity);
        this.defaultOrder(wrapper, entity);
        List<T> list = list(wrapper);
        return list;
    }

    public BasePage<T> page(Page<T> page, T entity, QueryWrapper<T> wrapper) {
        this.queryWrapper(wrapper, entity);
        Page<T> data = page(page, wrapper);
        data.setTotal(data.getTotal());
        BasePage<T> pageData = PageUtil.getPageData(data);
        return pageData;
    }

    public BasePage<T> page(Page<T> page, T entity) {
        QueryWrapper<T> wrapper = queryWrapper(entity);
        this.queryWrapper(wrapper, entity);
        Page<T> data = page(page, wrapper);
        data.setTotal(data.getTotal());
        BasePage<T> pageData = PageUtil.getPageData(data);
        return pageData;
    }


    public QueryWrapper<T> queryWrapper(T entity) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        this.queryWrapper(queryWrapper, entity);
        return queryWrapper;
    }

    public void queryWrapper(QueryWrapper<T> queryWrapper, T entity) {
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
        // 按时间排序
//        queryWrapper.orderByDesc("")
//        // 获取该实体类是否需要筛选部门数据
//        Dept annotation = AnnotationUtil.getAnnotation(entityClass, Dept.class);
//        if (ObjectUtil.isNotNull(annotation) && startDept) {
//            // 该表需要筛选
//            setDeptCondition(queryWrapper);
//        }
    }

    public void defaultOrder(QueryWrapper<T> wrapper, T entity) {
        Class<? extends Object> entityClass = entity.getClass();
        Field[] fields = ReflectUtil.getFields(entityClass);
        for (Field field : fields) {
            if (field.getName().equals("createTime")) {
                wrapper.orderByDesc("create_time");
            }
        }
    }



    private void where(QueryWrapper<T> queryWrapper, String name, String method, Object value) {
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


    public int count(T entity) {
        QueryWrapper<T> queryWrapper = this.queryWrapper(entity);
        return this.count(queryWrapper);
    }

}
