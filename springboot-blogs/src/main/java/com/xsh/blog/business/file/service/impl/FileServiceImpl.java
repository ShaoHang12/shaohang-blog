package com.xsh.blog.business.file.service.impl;

import com.xsh.blog.business.file.entity.BlogFile;
import com.xsh.blog.business.file.mapper.FileMapper;
import com.xsh.blog.business.file.service.IFileService;
import com.xsh.blog.common.service.impl.BasicServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class FileServiceImpl extends BasicServiceImpl<FileMapper, BlogFile> implements IFileService {

}
