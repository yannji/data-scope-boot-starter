package fun.yannji.data.scope.helper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import fun.yannji.data.scope.DataScope;
import fun.yannji.data.scope.holder.DataScopeHolder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataScopeHelper {

    private static final ThreadLocal<List<DataScope>> dataScopeList = new ThreadLocal<>();

    public static void startDataScope(String ...resources) {
        DataScopeHolder holder = SpringUtil.getBean(DataScopeHolder.class);
        List<DataScope> dataScopes = Arrays.stream(resources)
                .map(resource -> holder.getDataScopeMap().get(resource))
                .filter(ObjectUtil::isNotNull)
                .collect(Collectors.toList());
        dataScopeList.set(dataScopes);
    }

    public static List<DataScope> getDataScope() {
        return dataScopeList.get();
    }

    public static void clearDataScope() {
        dataScopeList.remove();
    }

}