/*
 * Copyright (C) 2020 Yunify, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this work except in compliance with the License.
 * You may obtain a copy of the License in the LICENSE file, or at:
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qingstor.sdk.request;

import com.qingstor.sdk.common.OperationContext;
import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.model.OutputModel;
import com.qingstor.sdk.model.RequestInputModel;
import java.util.Map;
import okhttp3.Request;

@SuppressWarnings({"rawtypes", "unchecked"})
public class QSRequest implements ResourceRequest {

    public static void sendApiRequestAsync(
            String requestUrl, OperationContext opCtx, ResponseCallBack callback) {
        EnvContext envContext = (EnvContext) opCtx.credentials();
        Request request = new Request.Builder().url(requestUrl).build();
        QSOkHttpRequestClient.getInstance(envContext)
                .requestActionAsync(request, envContext.isSafeOkHttp(), callback);
    }

    public static OutputModel sendApiRequest(
            String requestUrl, OperationContext opCtx, Class<? extends OutputModel> outputClass)
            throws QSException {
        EnvContext envContext = (EnvContext) opCtx.credentials();
        Request request = new Request.Builder().url(requestUrl).build();
        return QSOkHttpRequestClient.getInstance(envContext)
                .requestAction(request, envContext.isSafeOkHttp(), outputClass);
    }

    public static RequestHandler getRequestAsync(
            OperationContext opCtx, RequestInputModel paramBean, ResponseCallBack callback)
            throws QSException {
        return new RequestHandler(opCtx, paramBean, callback);
    }

    public static RequestHandler getRequest(
            OperationContext opCtx,
            RequestInputModel paramBean,
            Class<? extends OutputModel> outputClass)
            throws QSException {
        return new RequestHandler(opCtx, paramBean, outputClass);
    }

    @Deprecated
    @Override
    public void sendApiRequestAsync(String requestUrl, Map context, ResponseCallBack callback)
            throws QSException {
        EnvContext envContext = (EnvContext) context.get(QSConstant.ENV_CONTEXT_KEY);
        Request request = new Request.Builder().url(requestUrl).build();
        QSOkHttpRequestClient.getInstance(envContext)
                .requestActionAsync(request, envContext.isSafeOkHttp(), callback);
    }

    @Deprecated
    @Override
    public OutputModel sendApiRequest(
            String requestUrl, Map context, Class<? extends OutputModel> outputClass)
            throws QSException {
        EnvContext envContext = (EnvContext) context.get(QSConstant.ENV_CONTEXT_KEY);
        Request request = new Request.Builder().url(requestUrl).build();
        return QSOkHttpRequestClient.getInstance(envContext)
                .requestAction(request, envContext.isSafeOkHttp(), outputClass);
    }

    @Deprecated
    @Override
    public RequestHandler getRequestAsync(
            Map context, RequestInputModel paramBean, ResponseCallBack callback)
            throws QSException {
        return new RequestHandler(context, paramBean, callback);
    }

    @Deprecated
    @Override
    public RequestHandler getRequest(
            Map context, RequestInputModel paramBean, Class<? extends OutputModel> outputClass)
            throws QSException {
        return new RequestHandler(context, paramBean, outputClass);
    }
}
