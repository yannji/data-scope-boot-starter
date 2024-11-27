### 基于MybatisPlus的通用数据权限插件

#### 食用方式：
1. 引入依赖
```xml
<dependency>
    <groupId>fun.yannji</groupId>
    <artifactId>data-scope-boot-starter</artifactId>
    <version>1.1.0</version>
</dependency>
```
2. 定义数据权限类实现 DataScope 接口，并将其加入Spring容器中。
```java
@Component
public class TestDataScope implements DataScope {

    /**
     * 数据权限对象的唯一标识
     * @return
     */
    @Override
    public String getDataScopeKey() {
        return "TEST_DATA_SCOPE";
    }

    /**
     * 条件表达式（这里表示只查询出sys_role表中，code 列值为 PickingMaterial的行数据）
     * @param tableName
     * @param tableAlias
     * @return
     */
    @Override
    public Expression getExpression(String tableName, Alias tableAlias) {

        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(new Column("code"));
        equalsTo.setRightExpression(new StringValue("PickingMaterial"));
        
        return equalsTo;
    }

    /**
     * 是否需要过滤此表
     * @param tableName
     * @return
     */
    @Override
    public boolean includes(String tableName) {
        if ("sys_role".equals(tableName)) {
            return true;
        }
        return false;
    }
}
```
3. 在需要过滤的查询语句前声明要使用的权限对象
```java
DataScopeHelper.startDataScope("TEST_DATA_SCOPE");
List<SysRole> sysRoleList = sysRoleDao.listByEntity(param);
```