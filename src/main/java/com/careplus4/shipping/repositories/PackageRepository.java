package com.careplus4.shipping.repositories;

import com.careplus4.shipping.entities.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, String> {
    Package findTopByOrderByIdDesc(); // Lấy ra co ID lớn nhất
    Package getPackagesByIdBill (String idBill);
    Package findByIdBill (String idBill);
}
