package com.app.utils;

public interface Constant {
	
	public static final String CROSS_ORIGIN_LOCAL = "http://localhost:3000";

	/*
	 * define sort
	 */
	
	//sort by category
	public static final int SORT_BY_CATEGORY_ID = 1;
	public static final int SORT_BY_CATEGORY_NAME = 2;
	
	//sort by product
	public static final int SORT_BY_PRODUCT_ID = 1;
	public static final int SORT_BY_PRODUCT_NAME = 2;
	public static final int SORT_BY_PRODUCT_CATEGORY = 3;
	public static final int SORT_BY_PRODUCT_PRICE = 4;
	
	public static final String API_PREFIX = "/api/v1";
	
	// product API
	public static final String PRODUCT_API = API_PREFIX + "/products";
	public static final String PRODUCT_GET_LIST_PAGING_SORT_SEARCH_FILTER = "/product_get_list_paging_sort_search_filter";
	public static final String PRODUCT_GET_DETAIL = "/product_get_detail/{proId}";
	public static final String PRODUCT_DELETE = "/product_delete";
	public static final String PRODUCT_UPDATE = "/product_update";
	public static final String PRODUCT_CREATE = "/product_create";
	public static final String PRODUCT_GET_LIST_ACTIVE = "/product_get_list_active";
	
	//category API
	public static final String CATEGORY_API = API_PREFIX + "/categories";
	public static final String CATEGORY_GET_LIST_PAGING_SORT_SEARCH_FILTER = "/category_get_list_paging_sort_search_filter";
	public static final String CATEGORY_GET_DETAIL= "/category_get_detail/{cateId}";
	public static final String CATEGORY_DELETE = "/category_delete";
	public static final String CATEGORY_UPDATE = "/category_update";
	public static final String CATEGORY_CREATE = "/category_create";
	public static final String CATEGORY_GET_LIST_ACTIVE = "/category_get_list_active";
	
	public enum Status {
		INACTIVE(0),
		DELETE(1),
		ACTIVE(2);
		
		private final int value;

		private Status(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}
}
