package fun.yannji.data.scope.helper;

import fun.yannji.data.scope.DataScope;
import fun.yannji.data.scope.holder.DataScopeHolder;
import fun.yannji.data.scope.util.SpringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataScopeHelper {

    private static final ThreadLocal<List<DataScope>> dataScopeList = new ThreadLocal<>();

    public static void startDataScope(String ...dataScopeKey) {
        DataScopeHolder holder = SpringUtil.getBean(DataScopeHolder.class);
        List<DataScope> dataScopes = Arrays.stream(dataScopeKey)
                .map(resource -> holder.getDataScopeMap().get(resource))
                .filter(e -> e != null)
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