package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping(value = "/admin/categories/{categoryId}/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> addProduct(
           /* @Valid @ModelAttribute ProductDTO productDTO, // Use @RequestPart for ProductDTO*/
            @RequestParam("productName") String productName, // Use @RequestParam for productName
            @RequestParam("description") String description, // Use @RequestParam for productDescription
            @RequestParam("price") String price, // Use @RequestParam for productPrice
            @RequestParam("quantity") String quantity, // Use @RequestParam for productQuantity
            @RequestParam("discount") String discount, // Use @RequestParam for productDiscount
            @RequestParam("specialPrice") String specialPrice, // Use @RequestParam for productSpecialPrice

            @PathVariable Long categoryId, // Use @PathVariable for categoryId
            @RequestParam("image") MultipartFile image) throws IOException{
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName(productName);
        productDTO.setDescription(description);
        productDTO.setPrice(Double.parseDouble(price));
        productDTO.setQuantity(Integer.parseInt(quantity));
        productDTO.setDiscount(Double.parseDouble(discount));
        productDTO.setSpecialPrice(Double.parseDouble(specialPrice));

        ProductDTO savedProductDTO = productService.addProduct(categoryId, productDTO, image);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }
    @PutMapping(value = "/admin/products/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> updateProduct(
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "quantity", required = false) String quantity,
            @RequestParam(value = "discount", required = false) String discount,
            @RequestParam(value = "specialPrice", required = false) String specialPrice,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @PathVariable Long productId) throws IOException {


        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName(productName);
        productDTO.setDescription(description);
        productDTO.setPrice(Double.parseDouble(price));
        productDTO.setQuantity(Integer.parseInt(quantity));
        productDTO.setDiscount(Double.parseDouble(discount));
        productDTO.setSpecialPrice(Double.parseDouble(specialPrice));

        ProductDTO updatedProductDTO = productService.updateProduct(productId, productDTO, image);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ){
        ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder, keyword, category);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId,
                                                                 @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                 @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                 @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
                                                                 @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder){
        ProductResponse productResponse = productService.searchByCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword,
                                                                @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
                                                                @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder){
        ProductResponse productResponse = productService.searchProductByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }




    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO deletedProduct = productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }

//    @PutMapping("/products/{productId}/image")
//    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
//                                                         @RequestParam("image")MultipartFile image) throws IOException {
//        ProductDTO updatedProduct = productService.updateProductImage(productId, image);
//        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
//    }
}
