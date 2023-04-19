package com.example.userservice;

import com.example.userdatamodel.config.EnableUserDataModel;
import com.example.userdatamodel.config.liquibase.EnableUserLiquibase;
import com.example.userdatamodel.entity.UserAccount;
import com.example.userdatamodel.entity.enumtype.AccountRoleEnum;
import com.example.userservice.repository.UserAccountRepository;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@EnableDiscoveryClient
@EnableUserDataModel
@EnableUserLiquibase
@SpringBootApplication
public class UserServiceApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserAccountRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@PostConstruct
	public void addUser(){
		ArrayList<UserAccount> userAccounts = new ArrayList<>();
		try {
			userAccounts.add(UserAccount.builder()
					.username("admin")
					.password(passwordEncoder.encode("admin"))
					.role(AccountRoleEnum.ADMIN)
					.fName("admin")
					.build());

			userAccounts.add(UserAccount.builder()
					.username("duongnt")
					.password(passwordEncoder.encode("duong@123"))
					.role(AccountRoleEnum.USER)
					.fName("Duong")
					.lName("Nguyen Thanh")
					.address("VietNam")
					.phoneNum("0123456789")
					.build());

			userRepository.saveAll(userAccounts);
		}catch(Exception exception) {

		}
	}

}
