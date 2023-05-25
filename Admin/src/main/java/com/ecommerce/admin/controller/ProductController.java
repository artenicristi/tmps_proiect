package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.*;
import com.ecommerce.library.repository.ClothingProductRepository;
import com.ecommerce.library.repository.ElectronicProductRepository;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.impl.ProductServiceProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Controller
@RequiredArgsConstructor
public class ProductController {
//    @Qualifier("productServiceImpl")
//    private final ProductService productService;
    private final ProductServiceProxy productServiceProxy;
    private final CategoryService categoryService;
    private final ElectronicProductRepository electronicProductRepository;
    private final ClothingProductRepository clothingProductRepository;



//    @GetMapping("/products")
//    public String products(Model model) {
//        List<ProductDto> products = productServiceProxy.allProduct();
//        model.addAttribute("products", products);
//        model.addAttribute("size", products.size());
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//            return "redirect:/login";
//        }
//        return "products";
//    }

    @GetMapping("/products")
    public String products(Model model) {
//        List<ProductDto> products = productServiceProxy.allProduct();

        List<IProduct> products = new ArrayList<>();

        List<ElectronicProduct> electronicProducts = electronicProductRepository.findAll();
        products.addAll(electronicProducts);

        List<ClothingProduct> clothingProducts = clothingProductRepository.findAll();
        products.addAll(clothingProducts);

        List<IProduct> discountedProducts = new ArrayList<>();

        for (IProduct product : products) {
            IProduct discountedProduct = new DiscountProductDecorator(product, 0.5);  // Apply discount decorator
            discountedProducts.add(discountedProduct);
        }

        model.addAttribute("products", products);
        model.addAttribute("size", products.size());
        model.addAttribute("discountedProducts", discountedProducts);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        return "products-discount";
    }



    @GetMapping("/products/{pageNo}")
    public String allProducts(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<ProductDto> products = productServiceProxy.getAllProducts(pageNo);
        model.addAttribute("title", "Manage Products");
        model.addAttribute("size", products.getSize());
        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        return "products";
    }

    @GetMapping("/search-products/{pageNo}")
    public String searchProduct(@PathVariable("pageNo") int pageNo,
                                @RequestParam(value = "keyword") String keyword,
                                Model model
    ) {
        Page<ProductDto> products = productServiceProxy.searchProducts(pageNo, keyword);
        model.addAttribute("title", "Result Search Products");
        model.addAttribute("size", products.getSize());
        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        return "product-result";

    }

    @GetMapping("/add-product")
    public String addProductPage(Model model) {
        model.addAttribute("title", "Add Product");
        List<Category> categories = categoryService.findAllByActivatedTrue();
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", new ProductDto());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        return "add-product";
    }

    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("productDto") ProductDto product,
                              @RequestParam("imageProduct") MultipartFile imageProduct,
                              RedirectAttributes redirectAttributes) {
        product.setSalePrice(product.getCostPrice() * 1.5);
        try {
            productServiceProxy.save(imageProduct, product);
            redirectAttributes.addFlashAttribute("success", "Add new product successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to add new product!");
        }
        return "redirect:/products/0";
    }

    @GetMapping("/update-product/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAllByActivatedTrue();
        ProductDto productDto = productServiceProxy.getById(id);
        model.addAttribute("title", "Add Product");
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productDto);
        return "update-product";
    }

    @PostMapping("/update-product/{id}")
    public String updateProduct(@ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct") MultipartFile imageProduct,
                                RedirectAttributes redirectAttributes) {
        try {

            productServiceProxy.update(imageProduct, productDto);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server, please try again!");
        }
        return "redirect:/products/0";
    }

    @RequestMapping(value = "/enable-product", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enabledProduct(Long id, RedirectAttributes redirectAttributes) {
        try {
            productServiceProxy.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Enabled successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Enabled failed!");
        }
        return "redirect:/products/0";
    }

    @RequestMapping(value = "/delete-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deletedProduct(RedirectAttributes redirectAttributes, @PathVariable Long id) {
        try {
            productServiceProxy.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Deleted failed!");
        }
        return "redirect:/products/0";
    }
}
