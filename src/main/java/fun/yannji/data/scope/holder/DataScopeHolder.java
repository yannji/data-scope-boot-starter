package fun.yannji.data.scope.holder;

import fun.yannji.data.scope.DataScope;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataScopeHolder implements InitializingBean, ApplicationContextAware {

    @Getter
    private final Map<String, DataScope> dataScopeMap = new ConcurrentHashMap<>();
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, DataScope> beansOfType = applicationContext.getBeansOfType(DataScope.class);
        beansOfType.forEach((k, v) -> {
            dataScopeMap.put(v.getDataScopeKey(), v);
        });
    }
}
