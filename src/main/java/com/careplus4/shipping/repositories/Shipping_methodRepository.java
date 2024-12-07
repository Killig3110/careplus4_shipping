package com.careplus4.shipping.repositories;

import com.careplus4.shipping.entities.Shipping_method;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Shipping_methodRepository extends JpaRepository<Shipping_method, String> {
    Shipping_method findTopByOrderByIdDesc();
}
