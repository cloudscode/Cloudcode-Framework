package com.cloudcode.framework.hibernate;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class PaginationSupport<T> {

	// ~ Static fields
	// ======================================================================================

	public static final int DEFAULT_CURRENT_PAGE = 1;
	public static final int DEFAULT_OFFSET = 0;
	public static final int DEFAULT_PAGE_SIZE = 10;
	public static final String DEFALUT_INDEX = "center";

	// ~ Instance fields
	// ====================================================================================

	private int offset = 0;
	private int pageSize = DEFAULT_PAGE_SIZE;
	private int currentPage = DEFAULT_CURRENT_PAGE;
	private int totalCount = 0;
	private int totalPage;
	private List<T> items = new LinkedList<T>();
	private String index = DEFALUT_INDEX; // index 为索引页位置, 可以选择 "center",
	// "forward", "half-full"
	private int[] indexes = new int[0];

	// ~ Constructors
	// =======================================================================================

	/**
	 * 不带任何参数的构建方法
	 */
	public PaginationSupport() {}

	public PaginationSupport(List<T> list) {
		this(list, list.size());
	}

	public PaginationSupport(Collection<T> list) {
		this(new LinkedList<T>(list));
	}

	public PaginationSupport(T[] a) {
		this(Arrays.asList(a));
	}

	/**
	 * 带参数的构建方法
	 * 
	 * @param items
	 *            结果列表
	 * @param totalCount
	 *            总结果数量
	 */
	public PaginationSupport(List<T> items, int totalCount) {
		setPageSize(DEFAULT_PAGE_SIZE);
		setTotalCount(totalCount);
		setItems(items);
		setOffset(0);
	}

	/**
	 * 带参数的构建方法
	 * 
	 * @param items
	 *            结果列表
	 * @param totalCount
	 *            总结果数量
	 * @param offset
	 *            位置
	 */
	public PaginationSupport(List<T> items, int totalCount, int offset) {
		setPageSize(DEFAULT_PAGE_SIZE);
		setTotalCount(totalCount);
		setItems(items);
		setOffset(offset);
	}

	/**
	 * 带参数的构建方法
	 * 
	 * @param items
	 *            结果列表
	 * @param totalCount
	 *            总结果数量
	 * @param offset
	 *            位置
	 * @param pageSize
	 *            页面大小
	 */
	public PaginationSupport(List<T> items, int totalCount, int offset, int pageSize) {
		setPageSize(pageSize);
		setTotalCount(totalCount);
		setItems(items);
		setOffset(offset);
		setCurrentPageNo(totalCount, offset, pageSize);
		setTotalPage(totalCount, offset, pageSize);
	}

	/**
	 * 带参数的构建方法
	 * 
	 * @param items
	 *            结果列表
	 * @param totalCount
	 *            总结果数量
	 * @param offset
	 *            位置
	 * @param pageSize
	 *            页面大小
	 * @param index
	 *            页面下标
	 */
	public PaginationSupport(List<T> items, int totalCount, int offset, int pageSize, String index) {
		setPageSize(pageSize);
		setTotalCount(totalCount);
		setItems(items);
		setOffset(offset);
		setIndex(index);
		setTotalPage(totalCount, offset, pageSize);
	}

	// ~ Methods
	// ============================================================================================

	/**
	 * @return
	 */
	public List<T> getItems() {
		return items;
	}

	/**
	 * @param items
	 */
	public void setItems(List<T> items) {
		this.items = (items == null) ? new LinkedList<T>() : items;
	}

	/**
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		if (pageSize == -1) {
			pageSize = Integer.MAX_VALUE;
		}
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;
			int count = totalCount / pageSize;
			if (totalCount % pageSize > 0) {
				count++;
			}
			indexes = new int[count];
			for (int i = 0; i < count; i++) {
				indexes[i] = pageSize * i;
			}
		} else {
			this.totalCount = 0;
		}
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int startIndex) {
		if (totalCount <= 0)
			this.offset = 0;
		else if (startIndex >= totalCount)
			this.offset = indexes[indexes.length - 1];
		else if (startIndex < 0)
			this.offset = 0;
		else {
			this.offset = indexes[startIndex / pageSize];
		}
	}
	
	public int getNextPage() {
		int p = getCurrentPageNo() + 1;
		if (p > totalPage) {
			p = totalPage;
		}
		return p;
	}
	
	public int getPreviousPage() {
		int p = getCurrentPageNo() - 1;
		if (p < 1) {
			p = 1;
		}
		return p;
	}

	public int getNextIndex() {
		int nextIndex = getOffset() + pageSize;
		return nextIndex >= totalCount ? getOffset() : nextIndex;
	}

	public int getPreviousIndex() {
		int previousIndex = getOffset() - pageSize;
		return previousIndex < 0 ? DEFAULT_OFFSET : previousIndex;
	}

	/**
	 * @return Returns the index.
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            The index to set.
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * 这个是取得结果集的Object数组
	 * 
	 * @return
	 */

	public T[] getArrayItems(T[] objs) {
		if (items != null) {
			return items.toArray(objs);
		} else {
			return null;
		}
	}

	public int getCurrentPageNo() {
		return currentPage;
	}

	public void setCurrentPageNo(int totalCount, int offest, int maxPageItems) {
		if (totalCount == 0 || offest == 0) {
			currentPage = DEFAULT_CURRENT_PAGE;
		} else {
			currentPage = offest / maxPageItems + 1;
		}

	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalCount, int offest, int maxPageItems) {
		if (totalCount == 0) {
			totalPage = 0;
		} else {
			if (totalCount % maxPageItems > 0) {
				totalPage = totalCount / maxPageItems + 1;
			} else {
				totalPage = totalCount / maxPageItems;
			}
		}
	}

	public int size() {
		return getItems().size();
	}

	public boolean isEmpty() {
		return getItems().size() == 0;
	}

	public T getItem(int index) {
		return getItems().get(index);
	}

}
