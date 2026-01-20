package com.duha.duhabank.account.services;

import java.util.List;

import com.duha.duhabank.account.dtos.AccountDTO;
import com.duha.duhabank.account.entity.Account;
import com.duha.duhabank.auth_users.entity.User;
import com.duha.duhabank.enums.AccountType;
import com.duha.duhabank.res.Response;

public interface AccountService {
	Account createAccount(AccountType accountType, User user);
	
	Response<List<AccountDTO>> getMyAccount();
	
	Response<?> closeAccount(String accountNumber);
}
