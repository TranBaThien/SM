package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanPredicate;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AllPredicate;
import org.apache.commons.collections.functors.AnyPredicate;
import org.apache.commons.collections.iterators.FilterIterator;

/**
 * リストから条件に合致するオブジェクトを取得するユーティリティ<br>
 * @author Hitachi Information Systems CO., Ltd.
 * @version 1.7
 */
public class FilterUtil {
	
	/**
	 * リストから条件に完全一致するオブジェクトのイテレータを取得する。<br>
	 * @param <E> リスト要素の型<br>
	 * @param list リスト<br>
	 * List
	 * @param and AND条件の場合true、OR条件の場合false<br>
	 * @param properties プロパティ<br>
	 * Property
	 * @return イテレータ<br>
	 * Iterator
	 */
	public static <E> Iterator<E> equalIterator(List<E> list, boolean and, Property... properties) {
		List<BeanPredicate> predicates = new ArrayList<BeanPredicate>();
		for (Property p : properties) {
			predicates.add(new BeanPredicate(p.getName(), new EqualPredicate(p.getValue())));
		}
		return buildIterator(list, and, predicates);
	}
	
	/**
	 * リストから条件に前方一致するオブジェクトのイテレータを取得する。<br>
	 * @param <E> リスト要素の型<br>
	 * @param list リスト<br>
	 * List
	 * @param and AND条件の場合true、OR条件の場合false<br>
	 * @param properties プロパティ<br>
	 * Property
	 * @return イテレータ<br>
	 * Iterator
	 */
	public static <E> Iterator<E> forwardIterator(List<E> list, boolean and, Property... properties) {
		List<BeanPredicate> predicates = new ArrayList<BeanPredicate>();
		for (Property p : properties) {
			predicates.add(new BeanPredicate(p.getName(), new ForwardPredicate(p.getValue())));
		}
		return buildIterator(list, and, predicates);
	}
	
	/**
	 * リストから条件に後方一致するオブジェクトのイテレータを取得する。<br>
	 * @param <E> リスト要素の型<br>
	 * @param list リスト<br>
	 * List
	 * @param and AND条件の場合true、OR条件の場合false<br>
	 * @param properties プロパティ<br>
	 * Property
	 * @return イテレータ<br>
	 * Iterator
	 */
	public static <E> Iterator<E> backwardIterator(List<E> list, boolean and, Property... properties) {
		List<BeanPredicate> predicates = new ArrayList<BeanPredicate>();
		for (Property p : properties) {
			predicates.add(new BeanPredicate(p.getName(), new BackwardPredicate(p.getValue())));
		}
		return buildIterator(list, and, predicates);
	}
	
	/**
	 * リストから条件に部分一致するオブジェクトのイテレータを取得する。<br>
	 * @param <E> リスト要素の型<br>
	 * @param list リスト<br>
	 * List 
	 * @param and AND条件の場合true、OR条件の場合false<br>
	 * @param properties プロパティ<br>
	 * Property
	 * @return イテレータ<br>
	 * Iterator
	 */
	public static <E> Iterator<E> containIterator(List<E> list, boolean and, Property... properties) {
		
		List<BeanPredicate> predicates = new ArrayList<BeanPredicate>();
		for (Property p : properties) {
			predicates.add(new BeanPredicate(p.getName(), new ContainPredicate(p.getValue())));
		}
		return buildIterator(list, and, predicates);
	}

	/**
	 * 条件に一致するオブジェクトのイテレータを取得する。<br>
	 * @param <E> リスト要素の型<br>
	 * @param list リスト<br>
	 * List
	 * @param and AND条件の場合true、OR条件の場合false<br>
	 * @param predicates BeanPredicateのリスト<br>
	 * @return イテレータ<br>
	 * Iterator
	 */
	@SuppressWarnings("unchecked")
	private static <E> Iterator<E> buildIterator(List<E> list, boolean and, List<BeanPredicate> predicates) {
		BeanPredicate[] ps = predicates.toArray(new BeanPredicate[0]);
		Predicate condition = (and)? new AllPredicate(ps): new AnyPredicate(ps);
		return (Iterator<E>) new FilterIterator(list.iterator(), condition);
	}

	/**
	 * リストから条件に完全一致するオブジェクトのリストを取得する。<br>
	 * @param <E> リスト要素の型<br>
	 * @param list リスト<br>
	 * List
	 * @param and AND条件の場合true、OR条件の場合false<br>
	 * @param properties プロパティの配列<br>
	 * property
	 * @return リスト<br>
	 * List
	 */
	public static <E> List<E> equalFilter(List<E> list, boolean and, Property... properties) {
		List<E> result = new ArrayList<E>();
		
		Iterator<E> it = equalIterator(list, and, properties);
		while (it.hasNext()) {
			result.add(it.next());
		}
		return result;
	}

	/**
	 * リストから条件に前方一致するオブジェクトのリストを取得する。<br>
	 * @param <E> リスト要素の型<br>
	 * @param list リスト<br>
	 * List
	 * @param and AND条件の場合true、OR条件の場合false<br>
	 * @param properties プロパティの配列<br>
	 * @return リスト<br>
	 * List
	 */
	public static <E> List<E> forwardFilter(List<E> list, boolean and, Property... properties) {
		List<E> result = new ArrayList<E>();
		
		Iterator<E> it = forwardIterator(list, and, properties);
		while (it.hasNext()) {
			result.add(it.next());
		}
		return result;
	}

	/**
	 * リストから条件に後方一致するオブジェクトのリストを取得する。<br>
	 * @param <E> リスト要素の型<br>
	 * @param list リスト<br>
	 * List
	 * @param and AND条件の場合true、OR条件の場合false<br>
	 * @param properties プロパティの配列<br>
	 * @return リスト<br>
	 * List
	 */
	public static <E> List<E> backwardFilter(List<E> list, boolean and, Property... properties) {
		List<E> result = new ArrayList<E>();
		
		Iterator<E> it = backwardIterator(list, and, properties);
		while (it.hasNext()) {
			result.add(it.next());
		}
		return result;
	}

	/**
	 * リストから条件に部分一致するオブジェクトのリストを取得する。<br> 
	 * @param <E> リスト要素の型<br>
	 * @param list リスト<br>
	 * List
	 * @param and AND条件の場合true、OR条件の場合false<br>
	 * @param properties プロパティの配列<br>
	 * @return リスト<br>
	 * List
	 */
	public static <E> List<E> containFilter(List<E> list, boolean and, Property... properties) {
		List<E> result = new ArrayList<E>();
		
		Iterator<E> it = containIterator(list, and, properties);
		while (it.hasNext()) {
			result.add(it.next());
		}
		return result;
	}

	/**
	 * 完全一致条件<br>
	 */
	public static class EqualPredicate implements Predicate {
		private final String value;
		public EqualPredicate(Object value) {
			this.value = String.valueOf(value);
		}
		public boolean evaluate(Object obj) {
			if (obj != null) {
				return String.valueOf(obj).equals(value);
			} else {
				return false;
			}
		}
	}

	/**
	 * 前方一致条件<br>
	 */
	public static class ForwardPredicate implements Predicate {
		private final String value;
		public ForwardPredicate(Object value) {
			this.value = String.valueOf(value);
		}
		public boolean evaluate(Object obj) {
			if (obj != null) {
				return String.valueOf(obj).startsWith(value);
			} else {
				return false;
			}
		}
	}

	/**
	 * 後方一致条件<br>
	 */
	public static class BackwardPredicate implements Predicate {
		private final String value;
		public BackwardPredicate(Object value) {
			this.value = String.valueOf(value);
		}
		public boolean evaluate(Object obj) {
			if (obj != null) {
				return String.valueOf(obj).endsWith(value);
			} else {
				return false;
			}
		}
	}

	/**
	 * 部分一致条件<br>
	 */
	public static class ContainPredicate implements Predicate {
		private final String value;
		public ContainPredicate(Object value) {
			this.value = String.valueOf(value);
		}
		public boolean evaluate(Object obj) {
			if (obj != null) {
				return String.valueOf(obj).contains(value);
			} else {
				return false;
			}
		}
	}

	/**
	 * プロパティ<br> 
	 */
	public static class Property {
		
		/**
		 * プロパティ名<br>
		 */
		private String name;
		
		/**
		 * プロパティ値<br>
		 */
		private Object value;

		/**
		 * コンストラクタ<br>
		 */
		public Property() {}

		/**
		 * コンストラクタ<br>
		 * @param name プロパティ名<br>
		 * @param value プロパティ値<br>
		 */
		public Property(String name, Object value) {
			this.name = name;
			this.value = value;
		}
		
		/**
		 * プロパティ名を取得します。<br>
		 * @return プロパティ名<br>
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * プロパティ名を設定します。<br>
		 * @param name プロパティ名<br>
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		/**
		 * プロパティ値を取得します。<br>
		 * @return プロパティ値<br>
		 */
		public Object getValue() {
			return value;
		}
		
		/**
		 * プロパティ値を設定します。<br>
		 * @param value プロパティ値<br>
		 */
		public void setValue(Object value) {
			this.value = value;
		}
	}
}
