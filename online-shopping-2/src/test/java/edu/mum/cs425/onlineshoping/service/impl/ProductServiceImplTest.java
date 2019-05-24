package edu.mum.cs425.onlineshoping.service.impl;

//

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.mum.cs425.onlineshopping.entity.ProductInfo;
import edu.mum.cs425.onlineshopping.enums.ProductStatusEnum;
import edu.mum.cs425.onlineshopping.service.ProductService;

import static org.junit.Assert.*;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    ProductService productService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("B00001");
        Assert.assertEquals("B0001",productInfo.getProductId());
    }

    @Test
    public void findUpAll() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void findAllInCategory() {
    }

    @Test
    public void increaseStock() {
        Integer old = productService.findOne("B0001").getProductStock();
        productService.increaseStock("B0001", 50);
        Integer added = productService.findOne("B0001").getProductStock();
        Assert.assertEquals(50, added - old);
    }

    @Test
    public void decreaseStock() {
        Integer old = productService.findOne("B0001").getProductStock();
        productService.decreaseStock("B0001", 50);
        Integer decreased = productService.findOne("B0001").getProductStock();
        Assert.assertEquals(50,  old - decreased);
    }

    @Test
    public void offSale() {
        productService.offSale("C0003");
        Assert.assertEquals(ProductStatusEnum.DOWN.getCode(),  productService.findOne("C0003").getProductStatus());
    }

    @Test
    public void onSale() {
        productService.onSale("C0003");
        Assert.assertEquals(ProductStatusEnum.UP.getCode(),  productService.findOne("C0003").getProductStatus());
    }
}