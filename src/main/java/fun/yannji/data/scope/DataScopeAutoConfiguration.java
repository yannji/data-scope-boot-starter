package fun.yannji.data.scope;

import fun.yannji.data.scope.holder.DataScopeHolder;
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
	public DataScopeHolder dataScopeHolder() {
		return new DataScopeHolder();
	}

}