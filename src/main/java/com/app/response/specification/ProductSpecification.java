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
import com.app.model.Product;
import com.app.utils.Constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductSpecification implements Specification<Product>{

	private final String searchKey;
	private final long categoryId;
    private final int sortCase;
    private final boolean isAscSort;

	@Override
	public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		// TODO Auto-generated method stub
		List<Predicate> predicates = new ArrayList<>();
		
		if(searchKey != null && !searchKey.trim().isEmpty()) {
			String wrapSearch = "%" + searchKey.trim() +"%";
			System.out.println(wrapSearch);
			Predicate proName = criteriaBuilder.like(root.get("name"), wrapSearch);
			predicates.add(proName);
		}
		 
		if(categoryId != 0) {
			Root<Category> cateRoot = query.from(Category.class);
			predicates.add(criteriaBuilder.equal(cateRoot.get("id"), categoryId));
			predicates.add(criteriaBuilder.equal(root.get("category.id"), cateRoot.get("id")));
		}

		//select * from s  inner join a on a.id = s.id where a.id = 1
		
//		Predicate preActiveFlag = criteriaBuilder.equal(root.get("activeFlag"), 1);
//		predicates.add(preActiveFlag);
		
		Path orderClause;
		switch (sortCase) {
			case Constant.SORT_BY_PRODUCT_ID:
				orderClause = root.get("id");
				break;
			case Constant.SORT_BY_PRODUCT_NAME:
				orderClause = root.get("name");
				break;
			case Constant.SORT_BY_PRODUCT_CATEGORY:
				orderClause = root.get("category.id");
				break;
			case Constant.SORT_BY_PRODUCT_PRICE:
				orderClause = root.get("price");
				break;
			default:
				orderClause = root.get("id");
		}
		if(isAscSort) {
			System.out.println("asc");
			query.orderBy(criteriaBuilder.asc(orderClause));
		}else {
			System.out.println("desc");
			query.orderBy(criteriaBuilder.desc(orderClause));
		}
		
		return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
	}
	
}
