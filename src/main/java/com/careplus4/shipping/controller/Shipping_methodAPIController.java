package com.careplus4.shipping.controller;

import com.careplus4.shipping.entities.Shipping_method;
import com.careplus4.shipping.model.Response;
import com.careplus4.shipping.services.Impl.Shipping_methodServicesImpl;
import com.careplus4.shipping.services.iShipping_methodServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/api/shipping_method")
public class Shipping_methodAPIController {
    @Autowired
    private Shipping_methodServicesImpl shipping_methodService;

    @GetMapping
    public ResponseEntity<Response> getSipping_method() {
        List<Shipping_method> shipping_methods = shipping_methodService.findAll();
        Response Responses = new Response(true, "Lấy danh sách thành công", shipping_methods);
        return new ResponseEntity<>(Responses, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:9090")
    @GetMapping(value = "/getMethod")
    public ResponseEntity<Response> getShipping_methodById(@RequestParam("Address") String Address) {
        Shipping_method shipping_method = shipping_methodService.getShipping_methodByAddress(Address);
        if (shipping_method != null) {
            Response Responses = new Response(true, "Tìm thấy phương thức vận chuyển", shipping_method);
            return new ResponseEntity<>(Responses, HttpStatus.OK);
        } else {
            Response Responses = new Response(false, "Không tìm thấy phương thức vận chuyển", null);
            return new ResponseEntity<>(Responses, HttpStatus.NOT_FOUND);
        }
    }
}
