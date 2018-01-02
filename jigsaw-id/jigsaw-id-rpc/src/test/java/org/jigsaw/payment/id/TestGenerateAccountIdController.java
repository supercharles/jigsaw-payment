/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jigsaw.payment.id;

import org.jigsaw.payment.id.rpc.GenerateAccountIdController;
import org.jigsaw.payment.id.rpc.GeneratePayOrderIdController;
import org.jigsaw.payment.model.AccountTitle;
import org.jigsaw.payment.model.AccountType;
import org.jigsaw.payment.rpc.IdService.GenerateAccountIdRequest;
import org.jigsaw.payment.rpc.IdService.GenerateAccountIdResponse;
import org.jigsaw.payment.rpc.IdService.GeneratePayOrderIdRequest;
import org.jigsaw.payment.rpc.IdService.GeneratePayOrderIdResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author shamphone@gmail.com
 * @version 1.0.0
 * @date 2017年8月22日
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest(classes = TestConfiguration.class, webEnvironment = WebEnvironment.NONE)
public class TestGenerateAccountIdController {
	@Autowired
	@Qualifier("generateAccountId")
	private GenerateAccountIdController controller;

	@Test
	public void testGen() throws Exception {		
		long uid = System.currentTimeMillis();
		GenerateAccountIdRequest.Builder request = GenerateAccountIdRequest
				.newBuilder();
		request.setUserName("payment");
		request.setPassword("123456");
		request.setSubId(uid);
		request.setAccountTitle(AccountTitle.PERSONAL_DEPOSIT);
		request.setAccountType(AccountType.FOR_PERSONAL);
		GenerateAccountIdResponse response = controller.process(request
				.build());
		Assert.assertEquals(response.getAccountId() /10 %10, uid % 10);
		Assert.assertEquals(response.getAccountId() /100 % 128, uid /10 % 128);
	}
}
