package com.knkweb.yarnshop.service;

import com.knkweb.yarnshop.command.QuickOrderCommand;

public interface OrderHeaderService {

    void saveOrUpdate(QuickOrderCommand quickOrderCommand);
}
