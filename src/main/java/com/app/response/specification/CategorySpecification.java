package com.app.response.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.app.model.Category;
import com.app.utils.Constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@AllArgsConstructor
@Getter
@Setter
public class CategorySpecification implements Specification<Category>{
	private final String searchKey;
	private final int sortCase;
	private final boolean isAscSort;
	
	@Override
	public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		// TODO Auto-generated method stub
		List<Predicate> predicates = new ArrayList<>();
		
		if(searchKey != null && !searchKey.trim().isEmpty()) {
			String wrapSearch = "%" + searchKey.trim() + "%";
			Predicate cateName = criteriaBuilder.like(root.get("name"), wrapSearch);
			predicates.add(cateName);
		}
		
		
//		Predicate preActiveFlag = criteriaBuilder.equal(root.get("activeFlag"), 1);
//		predicates.add(preActiveFlag);
		
		Path orderClause;
		switch (sortCase) {
			case Constant.SORT_BY_CATEGORY_ID:
				orderClause = root.get("id");
				break;
			case Constant.SORT_BY_CATEGORY_NAME:
				orderClause = root.get("name");
				break;
			default:
				orderClause = root.get("id");
				break;
		}
		
		if(isAscSort) {
			query.orderBy(criteriaBuilder.asc(orderClause));
		}else {
			query.orderBy(criteriaBuilder.desc(orderClause));
		}
		
		
		return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
	}

	
	
}
