package com.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.exception.ApplicationException;
import com.app.model.Category;
import com.app.model.Product;
import com.app.model.request.CreateProductRequest;
import com.app.model.request.ProductPagingSearchSortModel;
import com.app.model.request.UpdateProductRequest;
import com.app.model.response.ModelResponse;
import com.app.model.response.ProductResponse;
import com.app.response.APIResponse;
import com.app.response.APIStatus;
import com.app.service.CategoryService;
import com.app.service.ProductService;
import com.app.utils.Constant;
import com.app.utils.ResponseUtil;

@RestController
@RequestMapping(value = Constant.PRODUCT_API)
@CrossOrigin(value = {Constant.CROSS_ORIGIN_LOCAL, Constant.CROSS_ORIGIN_SN_STORE})
public class ProductController {
	
	private ProductService productService;
	private CategoryService categoryService;
	
	@Autowired
	public ProductController(ProductService productService, CategoryService categoryService) {
		this.productService = productService;
		this.categoryService = categoryService;
	}

	private ModelMapper mapper = new ModelMapper();
	
	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	
	@PostMapping(Constant.PRODUCT_GET_LIST_PAGING_SORT_SEARCH_FILTER)
	public ResponseEntity<APIResponse> getListPagingSortSearchFilter(@RequestBody ProductPagingSearchSortModel ppssm) {
			
		try {
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			
			Page<Product> page = productService.doFilterSearchPagingProduct(ppssm.getSearchKey(), ppssm.getCategoryId(), 
					 ppssm.getPageSize(), ppssm.getPageNumber(), 
					ppssm.getSortCase(), ppssm.isAscSort());
			if(page == null) {
				throw new ApplicationException(APIStatus.ERR_PRODUCT_LIST_IS_EMPTY);
			}
			//mapper
			List<ProductResponse> responses = page.getContent().stream().map(item -> {
				ProductResponse productResponse = mapper.map(item, ProductResponse.class);
				Category category = categoryService.findById(item.getCategoryId());
				if(category != null) {
					productResponse.setCategoryId(category.getId());
					productResponse.setCategoryName(category.getName());
				}
				return productResponse;
			}).collect(Collectors.toList());
			
			ModelResponse modelResponse = 
					new ModelResponse(responses, page.getTotalElements(), page.getPageable());
			
			return ResponseUtil.responseSuccess(modelResponse);
		} catch (Exception e) {
			throw new ApplicationException(APIStatus.ERR_PRODUCT_LIST_IS_EMPTY);
		}
	}
	
	@GetMapping(Constant.PRODUCT_GET_DETAIL)
	public ResponseEntity<APIResponse> getProductDetail(@PathVariable("proId") long proId) {
		//find product by id
		Product product = productService.findById(proId);
		if(product != null) {
			//mapper
			ProductResponse productResponse = mapper.map(product, ProductResponse.class);
			Category category = categoryService.findById(product.getCategoryId());
			if(category != null) {
				productResponse.setCategoryId(category.getId());
				productResponse.setCategoryName(category.getName());
			}
			return ResponseUtil.responseSuccess(productResponse);
		}else {
			throw new ApplicationException(APIStatus.ERR_PRODUCT_ID_NOT_EXIST);
		}
	}
	
	@PostMapping(Constant.PRODUCT_DELETE)
	public ResponseEntity<APIResponse> getProductDetail(@RequestBody List<Long> ids) {
		try {
			for (Long proId : ids) {
//				boolean getProduct = productService.isExist(proId);
//				if(getProduct) {
//					productService.delete(proId);
//					log.info("Delete product by {}", proId);
//				}
				Product getProduct = productService.findById(proId);
				if(getProduct == null) {
					log.error("Error delete product id not exist {}", proId);
					throw new ApplicationException(APIStatus.ERR_PRODUCT_ID_NOT_EXIST); 
				} 
				getProduct.setActiveFlag(Constant.Status.DELETE.getValue());
				productService.update(getProduct);
			}
			return ResponseUtil.responseSuccess("Delete product successfully");
		} catch (Exception e) {
			log.error("Error delete product ");
			throw new ApplicationException(APIStatus.ERR_PRODUCT_ID_NOT_EXIST);
		}
	}
	
	@PostMapping(Constant.PRODUCT_CREATE)
	public ResponseEntity<APIResponse> createProduct(@Validated @RequestBody CreateProductRequest productRequest) {

		Product getProduct = productService.findByName(productRequest.getName());
		if (getProduct == null) {
			try {
				mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
				
				Product product = mapper.map(productRequest, Product.class);
				
				Category category = categoryService.findById(productRequest.getCategoryId());
				if(category == null) {
					log.error("Error create product category id is null");
					throw new ApplicationException(APIStatus.ERR_PRODUCT_CATEGORY_IS_NULL);
				}
				product.setCategoryId(productRequest.getCategoryId());
				productService.insert(product);
				log.info("Create product successfully");
			} catch (Exception e) {
				log.error("Error create product");
				throw new ApplicationException(APIStatus.ERR_CREATE_PRODUCT);
			}
		} else {
			log.error("Error create product code already exists {}", getProduct.getName());
			throw new ApplicationException(APIStatus.ERR_PRODUCT_NAME_ALREADY_EXISTS);
		}
		return ResponseUtil.responseSuccess("Create product successfully");

	}
	
	@PutMapping(Constant.PRODUCT_UPDATE)
	public ResponseEntity<APIResponse> updateProduct(@Validated @RequestBody UpdateProductRequest productRequest) {
		
		Product productByName = productService.findByName(productRequest.getName());
		Product productById = productService.findById(productRequest.getId());
		if(productById != null) {
			if(productByName != null) {
				if(!productByName.getName().equals(productById.getName())) {
					log.error("Error update product name already exists {}", productRequest.getName());
					throw new ApplicationException(APIStatus.ERR_PRODUCT_NAME_ALREADY_EXISTS);
				} 
			}
			try {
				mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
				
				Product product = mapper.map(productRequest, Product.class);
				
				Category category = categoryService.findById(productRequest.getCategoryId());
				if(category == null) {
					log.error("Error create product category id is null");
					throw new ApplicationException(APIStatus.ERR_PRODUCT_CATEGORY_IS_NULL);
				}
				product.setCategoryId(productRequest.getCategoryId());
				
				product.setActiveFlag(productById.getActiveFlag());
				product.setCreatedDate(productById.getCreatedDate());
				productService.update(product);
				
				log.info("Update product successfully");
			} catch (Exception e) {
				log.error("Error update product");
				throw new ApplicationException(APIStatus.ERR_UPDATE_PRODUCT);
			}
		}else {
			throw new ApplicationException(APIStatus.ERR_PRODUCT_ID_NOT_EXIST);
		}
		return ResponseUtil.responseSuccess("Update product successfully");
	}
}
