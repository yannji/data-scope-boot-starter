package fun.yannji.data.scope.util;

import java.util.Collection;

public final class CollectionUtil {

	private CollectionUtil() {
	}

	/**
	 * 校验集合是否为空
	 * @param collection 集合
	 * @return boolean
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	/**
	 * 校验集合是否不为空
	 * @param collection 集合
	 * @return boolean
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

}
