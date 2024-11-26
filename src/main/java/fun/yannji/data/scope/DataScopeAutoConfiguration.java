package fun.yannji.data.scope;

import fun.yannji.data.scope.holder.DataScopeHolder;
import fun.yannji.data.scope.interceptor.DataPermissionInterceptor;
import fun.yannji.data.scope.processor.DataScopeSqlProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;


@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnBean(DataScope.class)
public class DataScopeAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public DataPermissionInterceptor dataPermissionInterceptor() {
		return new DataPermissionInterceptor(new DataScopeSqlProcessor());
	}

	@Bean
	@ConditionalOnMissingBean
	public DataScopeHolder dataScopeHolder() {
		return new DataScopeHolder();
	}

}