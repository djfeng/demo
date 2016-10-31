package com.bocweb.service;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bocweb.core.service.CrudBaseServiceImpl;
import com.bocweb.po.User;
@Repository
@Transactional
public class UserServiceImpl extends CrudBaseServiceImpl<User> implements UserService{

}
