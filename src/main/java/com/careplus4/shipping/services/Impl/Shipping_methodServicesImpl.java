package com.careplus4.shipping.services.Impl;

import com.careplus4.shipping.entities.Shipping_method;
import com.careplus4.shipping.repositories.Shipping_methodRepository;
import com.careplus4.shipping.services.GeneratedId;
import com.careplus4.shipping.services.iShipping_methodServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Shipping_methodServicesImpl implements iShipping_methodServices {

    @Autowired
    private Shipping_methodRepository shipping_methodRepository;

    @Override
    public List<Shipping_method> findAll() {
        return shipping_methodRepository.findAll();
    }

    @Override
    public <S extends Shipping_method> S save(S entity) {
        if (entity.getId() == null) {
            Shipping_method lastShipping_method = findTopByOrderByIdDesc();
            String previousID = lastShipping_method == null ? "SPM0000" : lastShipping_method.getId();
            entity.setId(GeneratedId.getGeneratedId(previousID));
        }
        return shipping_methodRepository.save(entity);
    }

    @Override
    public Optional<Shipping_method> findById(String s) {
        return shipping_methodRepository.findById(s);
    }

    @Override
    public boolean existsById(String s) {
        return shipping_methodRepository.existsById(s);
    }

    @Override
    public void deleteById(String s) {
        shipping_methodRepository.deleteById(s);
    }

    @Override
    public Shipping_method getShipping_methodByAddress(String address) {
        if (address.equals("Hồ Chí Minh")) {
            return shipping_methodRepository.findById("SPM0001").get();
        }
        return shipping_methodRepository.findById("SPM0002").get();
    }

    @Override
    public Shipping_method findTopByOrderByIdDesc() {
        return shipping_methodRepository.findTopByOrderByIdDesc();
    }
}
