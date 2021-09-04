package com.app.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPagingSearchSortModel {
	private String searchKey;
	private long categoryId;
    private int pageNumber;
    private int pageSize;
    private int sortCase;
    private boolean ascSort;	
}
