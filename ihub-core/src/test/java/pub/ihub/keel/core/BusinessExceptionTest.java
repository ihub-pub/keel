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
package pub.ihub.keel.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author liheng
 */
@DisplayName("业务异常测试")
class BusinessExceptionTest {

    @DisplayName("业务异常测试")
    @Test
    void getCode() {
        assertNull(new BusinessException().getCode());
        assertNotNull(new BusinessException(EBusinessCode.ERROR).getCode());
        assertNotNull(new BusinessException(EBusinessCode.ERROR, "系统错误！").getCode());
        assertNotNull(new BusinessException(EBusinessCode.ERROR, "系统错误！", new RuntimeException("系统错误！")).getCode());
        assertNotNull(new BusinessException(EBusinessCode.ERROR, new RuntimeException("系统错误！")).getCode());
    }

    private enum EBusinessCode implements BusinessCode {
        /**
         * 错误
         */
        ERROR
    }

}
