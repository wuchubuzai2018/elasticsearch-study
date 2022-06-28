package com.china.elasticsearch.service.impl;

import com.china.elasticsearch.dao.IFirstDao;
import com.china.elasticsearch.service.IFirstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date 2019-08-10
 */
@Service
public class FirstService implements IFirstService {

    @Autowired
    private IFirstDao firstDao;


}
