package com.app.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPagingSearchSortModel {
	private String searchKey;
	private int pageNumber;
    private int pageSize;
    private int sortCase;
    private boolean ascSort;
}
