package com.yunmu.geek.service.impl;

import com.yunmu.geek.entity.Account;
import com.yunmu.geek.service.AccountService;
import com.yunmu.geek.service.TransactionService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    final private AccountService accountService;

    @Autowired(required = false)
    public TransactionServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public void transaction() {
        transactionA();
        transactionB();
    }

    private void transactionA() {
        Account account = new Account();
        account.setId(1L);
        account.setUsWallet(-1L);
        account.setCnyWallet(7L);
        accountService.pay(account);
    }

    private void transactionB() {
        Account account = new Account();
        account.setId(2L);
        account.setUsWallet(1L);
        account.setCnyWallet(-7L);
        accountService.pay(account);
    }
}
