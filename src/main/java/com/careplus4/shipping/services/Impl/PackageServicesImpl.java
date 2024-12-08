package com.careplus4.shipping.services.Impl;

import com.careplus4.shipping.entities.Package;
import com.careplus4.shipping.repositories.PackageRepository;
import com.careplus4.shipping.services.GeneratedId;
import com.careplus4.shipping.services.iPackageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PackageServicesImpl implements iPackageServices {
    @Autowired
    private PackageRepository packageRepository;

    @Override
    public List<Package> findAll() {
        return packageRepository.findAll();
    }

    @Override
    public Page<Package> findAll(Pageable pageable) {
        return packageRepository.findAll(pageable);
    }

    @Override
    public <S extends Package> S save(S entity) {
        if (entity.getId() == null) {
            Package lastPackage = findTopByOrderByIdDesc();
            String previousID = lastPackage == null ? "P000000" : lastPackage.getId();
            entity.setId(GeneratedId.getGeneratedId(previousID));
        }
        return packageRepository.save(entity);
    }

    @Override
    public Optional<Package> findById(String s) {
        return packageRepository.findById(s);
    }

    @Override
    public boolean existsById(String s) {
        return packageRepository.existsById(s);
    }

    @Override
    public void deleteById(String s) {
        packageRepository.deleteById(s);
    }

    @Override
    public void delete(Package entity) {
        packageRepository.delete(entity);
    }

    @Override
    public <S extends Package> long count(Example<S> example) {
        return packageRepository.count(example);
    }

    @Override
    public <S extends Package> boolean exists(Example<S> example) {
        return packageRepository.exists(example);
    }

    @Override
    public Package findTopByOrderByIdDesc() {
        return packageRepository.findTopByOrderByIdDesc();
    }

    @Override
    public Package getPackageByIdBill(String idBill) {
        return packageRepository.getPackagesByIdBill(idBill);
    }

    @Override
    public Package findByIdBill(String idBill) {
        return packageRepository.findByIdBill(idBill);
    }

    @Override
    public List<Map<String, String>> getIdBillAndStatus() {
        List<Package> packages = packageRepository.findAll();
        return packages.stream().map(pkg -> {
            Map<String, String> map = Map.of("idBill", pkg.getIdBill(), "status", pkg.getStatus());
            return map;
        }).toList();
    }
}
