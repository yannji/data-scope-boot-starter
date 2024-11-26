package fun.yannji.data.scope;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;

public interface DataScope {

	/**
	 * DataScope唯一表示
	 * @return
	 */
	String getDataScopeKey();

	/**
	 * 是否需要过滤此表
	 * @param tableName
	 * @return
	 */
	boolean includes(String tableName);

	/**
	 * 构建条件表达式
	 * @param tableName
	 * @param tableAlias
	 * @return
	 */
	Expression getExpression(String tableName, Alias tableAlias);

}