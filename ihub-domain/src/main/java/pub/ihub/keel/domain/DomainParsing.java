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

import lombok.NoArgsConstructor;

import java.lang.reflect.AccessibleObject;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 领域模型解析
 *
 * @author liheng
 */
@NoArgsConstructor
public final class DomainParsing {

	public static DomainModel parsingModel(Class<?> domainClass) {
		return DomainModel.builder()
			.name(parsingName(domainClass))
			.fields(getDomainModel(domainClass.getDeclaredFields(), field ->
				DomainModel.builder().name(parsingName(field)).build()))
			.methods(getDomainModel(domainClass.getDeclaredMethods(), method ->
				DomainModel.builder().name(parsingName(method)).build()))
			.build();
	}

	private static String parsingName(Class<?> domainClass) {
		return domainClass.getAnnotation(ADomainModel.class).value();
	}

	private static String parsingName(AccessibleObject object) {
		return object.getAnnotation(ADomainModel.class).value();
	}

	private static <T extends AccessibleObject> List<DomainModel> getDomainModel(T[] objects,
																				 Function<T, DomainModel> mapper) {
		return Arrays.stream(objects).filter(object -> object.isAnnotationPresent(ADomainModel.class))
			.map(mapper).collect(Collectors.toList());
	}

}
