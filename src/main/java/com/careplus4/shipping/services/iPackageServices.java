package com.careplus4.shipping.services;

import com.careplus4.shipping.entities.Package;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface iPackageServices {
    List<Package> findAll();

    Page<Package> findAll(Pageable pageable);

    <S extends Package> S save(S entity);

    Optional<Package> findById(String s);

    boolean existsById(String s);

    void deleteById(String s);

    void delete(Package entity);

    <S extends Package> long count(Example<S> example);

    <S extends Package> boolean exists(Example<S> example);

    Package findTopByOrderByIdDesc();

    Package getPackageByIdBill(String idBill);
}
