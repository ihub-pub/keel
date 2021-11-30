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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 业务异常
 *
 * @author liheng
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 2592523326379310944L;

    /**
     * 业务编码
     */
    private BusinessCode code;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(BusinessCode code, String message) {
        this(message);
        this.code = code;
    }

    public BusinessException(BusinessCode code, String message, Throwable cause) {
        this(message, cause);
        this.code = code;
    }

    public BusinessException(BusinessCode code, Throwable cause) {
        this(cause);
        this.code = code;
    }

}
