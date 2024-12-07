package com.careplus4.shipping;

import com.careplus4.shipping.entities.Shipping_method;
import com.careplus4.shipping.repositories.Shipping_methodRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class Careplus4ShippingApplication {

    public static void main(String[] args) {
        SpringApplication.run(Careplus4ShippingApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(Shipping_methodRepository shippingMethodRepository) {
        return args -> {
            if (shippingMethodRepository.count() == 0) {
                Shipping_method method1 = new Shipping_method("SPM0001", "Ship nội thành", new BigDecimal("30000"));
                Shipping_method method2 = new Shipping_method("SPM0002", "Ship ngoại thành", new BigDecimal("50000"));
                shippingMethodRepository.save(method1);
                shippingMethodRepository.save(method2);
                System.out.println("Đã thêm dữ liệu mẫu vào bảng Shipping_method.");
            } else {
                System.out.println("Dữ liệu mẫu đã tồn tại.");
            }
        };
    }
}
