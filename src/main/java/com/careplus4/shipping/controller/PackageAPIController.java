package com.careplus4.shipping.controller;

import com.careplus4.shipping.entities.Package;
import com.careplus4.shipping.entities.Shipping_method;
import com.careplus4.shipping.model.Response;
import com.careplus4.shipping.services.Impl.PackageServicesImpl;
import com.careplus4.shipping.services.Impl.Shipping_methodServicesImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/api/packages")
public class PackageAPIController {
    @Autowired
    private PackageServicesImpl packageService;

    @Autowired
    private Shipping_methodServicesImpl shipping_methodService;

    @GetMapping
    public ResponseEntity<Response> getPackages() {
        List<Package> packages = packageService.findAll();
        Response Responses = new Response(true, "Lấy danh sách thành công", packages);
        return new ResponseEntity<>(Responses, HttpStatus.OK);
    }

    @PostMapping("/add_shipping")
    @CrossOrigin(origins = "http://localhost:9090")
    public ResponseEntity<Response> saveShipping(@RequestParam("idBill") String idBill,
                                                 @RequestParam("receiverName") String receiverName,
                                                 @RequestParam("userPhone") String userPhone,
                                                 @RequestParam("address") String address,
                                                 @RequestParam("province") String province,
                                                 @RequestParam("totalAmount") BigDecimal totalAmount) {

        Shipping_method shipping_method = shipping_methodService.getShipping_methodByAddress(province);
        String idShippingMethod = shipping_method.getId();

        System.out.println("idBill: " + idBill);
        System.out.println("idShippingMethod: " + idShippingMethod);
        System.out.println("receiverName: " + receiverName);
        System.out.println("userPhone: " + userPhone);
        System.out.println("address: " + address);
        System.out.println("totalAmount: " + totalAmount);

        Package packages = new Package();
        packages.setIdBill(idBill);
        packages.setIdShippingMethod(idShippingMethod);
        packages.setReceiverName(receiverName);
        packages.setUserPhone(userPhone);
        packages.setAddress(address);
        packages.setStatus("SHIPPING");
        LocalDate localDate = LocalDate.now();
        java.sql.Date date = java.sql.Date.valueOf(localDate.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        packages.setUpdateDate(date);
        packages.setTotalAmount(totalAmount);
        packageService.save(packages);

        Response Responses = new Response(true, "Thêm gói hàng thành công", packages);
        return new ResponseEntity<>(Responses, HttpStatus.CREATED);
    }

    @GetMapping("/checkStatus")
    @CrossOrigin(origins = "http://localhost:9090")
    public ResponseEntity<Response> checkStatus(@RequestParam("idBill") String idBill) {
        Package packages = packageService.findByIdBill(idBill);
        if (packages == null) {
            Response Responses = new Response(false, "Không tìm thấy gói hàng", null);
            return new ResponseEntity<>(Responses, HttpStatus.NOT_FOUND);
        }
        Response Responses = new Response(true, "Trạng thái gói hàng", packages.getStatus());
        return new ResponseEntity<>(Responses, HttpStatus.OK);
    }

    @GetMapping("/getPackageById")
    public ResponseEntity<Response> getPackageById(@RequestParam("id") String id) {
        Optional<Package> packages = packageService.findById(id);
        if (packages.isEmpty()) {
            Response Responses = new Response(false, "Không tìm thấy gói hàng", null);
            return new ResponseEntity<>(Responses, HttpStatus.NOT_FOUND);
        }

        Package packagesCheck = packages.get();
        Response Responses = new Response(true, "Thông tin gói hàng", packagesCheck);
        return new ResponseEntity<>(Responses, HttpStatus.OK);
    }

    @GetMapping("/getPackageByBill")
    public ResponseEntity<Response> getPackageByBill(@RequestParam("idBill") String idBill) {
        Package packages = packageService.getPackageByIdBill(idBill);
        if (packages == null) {
            Response Responses = new Response(false, "Không tìm thấy gói hàng", null);
            return new ResponseEntity<>(Responses, HttpStatus.NOT_FOUND);
        }

        Response Responses = new Response(true, "Thông tin gói hàng", packages);
        return new ResponseEntity<>(Responses, HttpStatus.OK);
    }

    @PutMapping("/update_status/{idBill}")
    public ResponseEntity<Response> updateStatus(@PathVariable("idBill") String idBill,
                                                 @RequestParam("status") String status) {
        Package packages = packageService.getPackageByIdBill(idBill);
        if (packages == null) {
            Response Responses = new Response(false, "Không tìm thấy gói hàng", null);
            return new ResponseEntity<>(Responses, HttpStatus.NOT_FOUND);
        }

        if (status.equals("SHIPPED") || status.equals("CANCELLED")) {
            packages.setStatus(status);
            LocalDate localDate = LocalDate.now();
            Date date = java.sql.Date.valueOf(localDate);
            packages.setUpdateDate(date);
            packageService.save(packages);
            return new ResponseEntity<>(new Response(true, "Cập nhật trạng thái thành công", packages), HttpStatus.OK);
        } else {
            Response Responses = new Response(false, "Trạng thái không hợp lệ", null);
            return new ResponseEntity<>(Responses, HttpStatus.BAD_REQUEST);
        }
    }

}
