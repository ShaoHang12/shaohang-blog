package com.xsh.blog.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xsh.blog.business.article.entity.Article;
import com.xsh.blog.business.user.entity.User;
import com.xsh.blog.common.bean.BasePage;

import java.util.List;

/**
 * @author root
 */
public interface BasicServiceInterface<T> extends IService<T> {
    /**
     * 分页-自动获取分页功能
     * @param entity
     * @return
     */
    BasePage<T> page(T entity);

    /**
     * 分页-自动获取分页功能
     * @param page
     * @param entity
     * @param wrapper
     * @return
     */
    BasePage<T> page(Page<T> page, T entity, QueryWrapper<T> wrapper);

    /**
     * 查询全部
     * @param entity 实体
     * @return
     */
    List<T> list(T entity);


//    /**
//     * 获取数据，必须在该部门才可查
//     * @param id
//     * @return
//     */
//    T getDataById(Serializable id);

    /**
     * 分页
     * @param page
     * @param entity
     * @return
     */
    BasePage<T> page(Page<T> page, T entity);


//    /**
//     * 导出数据
//     * @param excelHeadTitle
//     * @param sheetName
//     * @param filename
//     * @param page
//     * @param entity
//     */
//    void exportExcel(String excelHeadTitle,String sheetName,String filename,Page<T> page, T entity);
//
//    /**
//     * 导入数据
//     * @param file
//     * @param clazz
//     * @return
//     */
//    boolean importExcel(MultipartFile file, Class<T> clazz);


//    /**
//     * 删除数据
//     * @param ids
//     * @return
//     */
//    boolean deleteByIds( List<String> ids);

    int count(T t);

}
