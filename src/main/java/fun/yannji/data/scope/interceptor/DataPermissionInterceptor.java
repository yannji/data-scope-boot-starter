package fun.yannji.data.scope.interceptor;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import fun.yannji.data.scope.DataScope;
import fun.yannji.data.scope.helper.DataScopeHelper;
import fun.yannji.data.scope.processor.DataScopeSqlProcessor;
import fun.yannji.data.scope.util.CollectionUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.List;

public class DataPermissionInterceptor implements InnerInterceptor {

    private final DataScopeSqlProcessor dataScopeSqlProcessor = new DataScopeSqlProcessor();

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        List<DataScope> dataScopeList = DataScopeHelper.getDataScope();
        if (CollectionUtil.isEmpty(dataScopeList)) {
            return;
        }
        DataScopeHelper.clearDataScope();
        String newSql = this.dataScopeSqlProcessor.parserSingle(boundSql.getSql(), dataScopeList);
        PluginUtils.MPBoundSql mpBoundSql = PluginUtils.mpBoundSql(boundSql);
        mpBoundSql.sql(newSql);
    }
}
