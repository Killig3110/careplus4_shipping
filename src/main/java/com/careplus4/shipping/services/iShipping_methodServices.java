package com.careplus4.shipping.services;

import com.careplus4.shipping.entities.Shipping_method;

import java.util.List;
import java.util.Optional;

public interface iShipping_methodServices {
    List<Shipping_method> findAll();

    <S extends Shipping_method> S save(S entity);

    Optional<Shipping_method> findById(String s);

    boolean existsById(String s);

    void deleteById(String s);

    Shipping_method getShipping_methodByAddress(String address);

    Shipping_method findTopByOrderByIdDesc();
}
