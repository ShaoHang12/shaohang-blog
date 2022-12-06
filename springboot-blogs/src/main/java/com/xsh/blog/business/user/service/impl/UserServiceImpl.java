package com.xsh.blog.business.user.service.impl;

import com.xsh.blog.business.user.entity.User;
import com.xsh.blog.business.user.mapper.UserMapper;
import com.xsh.blog.business.user.service.IUserService;
import com.xsh.blog.common.service.impl.BasicServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 *
 */
@Service
public class UserServiceImpl extends BasicServiceImpl<UserMapper, User> implements IUserService {

}
