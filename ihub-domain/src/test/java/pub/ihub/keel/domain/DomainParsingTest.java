/*
 * Copyright (c) 2021 Henry 李恒 (henry.box@outlook.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pub.ihub.keel.domain;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author liheng
 */
@DisplayName("领域模型测试")
class DomainParsingTest {

	@DisplayName("领域模型解析测试")
	@Test
	void parsingModel() {
		List<DomainModel> fields = new ArrayList<>();
		fields.add(DomainModel.builder().name("a").build());
		fields.add(DomainModel.builder().name("b").build());
		fields.add(DomainModel.builder().name("c").build());
		Assertions.assertEquals(DomainModel.builder().name("demo").fields(fields)
			.methods(Collections.singletonList(DomainModel.builder().name("test").build()))
			.build(), DomainParsing.parsingModel(Demo.class));
	}

	@ADomainModel("demo")
	@Data
	private static class Demo implements Serializable {

		private static final long serialVersionUID = -3114298899271818391L;

		@ADomainModel("a")
		private String a;
		@ADomainModel("b")
		private String b;
		@ADomainModel("c")
		private String c;

		@ADomainModel("test")
		private String test() {
			return toString();
		}

	}

}
