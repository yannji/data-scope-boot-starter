/*
 * Copyright 2023-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fun.yannji.data.scope.interceptor;

import cn.hutool.core.collection.CollectionUtil;
import fun.yannji.data.scope.DataScope;
import fun.yannji.data.scope.helper.DataScopeHelper;
import fun.yannji.data.scope.processor.DataScopeSqlProcessor;
import fun.yannji.data.scope.util.PluginUtils;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.List;

/**
 * 数据权限拦截器
 */
@RequiredArgsConstructor
@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class DataPermissionInterceptor implements Interceptor {

	private final DataScopeSqlProcessor dataScopeSqlProcessor;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object target = invocation.getTarget();
		StatementHandler sh = (StatementHandler) target;
		PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
		MappedStatement ms = mpSh.mappedStatement();
		SqlCommandType sct = ms.getSqlCommandType();
		PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();

		List<DataScope> dataScopeList = DataScopeHelper.getDataScope();
		if (CollectionUtil.isEmpty(dataScopeList)) {
			return invocation.proceed();
		}
		DataScopeHelper.clearDataScope();
		if (sct == SqlCommandType.SELECT) {
			mpBs.sql(this.dataScopeSqlProcessor.parserSingle(mpBs.sql(), dataScopeList));
		}
		// 执行 sql
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

}
