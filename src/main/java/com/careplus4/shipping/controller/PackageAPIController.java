package com.careplus4.shipping.controller;

import com.careplus4.shipping.entities.Package;
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
    public ResponseEntity<Response> saveShipping(@RequestParam("idBill") String idBill,
                                                 @RequestParam("idShippingMethod") String idShippingMethod,
                                                 @RequestParam("receiverName") String receiverName,
                                                 @RequestParam("userPhone") String userPhone,
                                                 @RequestParam("address") String address,
                                                 @RequestParam("Province") String province,
                                                 @RequestParam("totalAmount") BigDecimal totalAmount) {
        // Kiem tra xem idShippingMethod co ton tai trong database khong
        Boolean check = shipping_methodService.existsById(idShippingMethod);
        if (check == false) {
            Response Responses = new Response(false, "Phương thức vận chuyển không tồn tại", null);
            return new ResponseEntity<>(Responses, HttpStatus.BAD_REQUEST);
        }

        // Kiem tra xem trong address co chua tinh hay khong
        if (address.contains(province) == false) {
            address = address + ", " + province;
        }

        Package packages = new Package();
        packages.setIdBill(idBill);
        packages.setIdShippingMethod(idShippingMethod);
        packages.setReceiverName(receiverName);
        packages.setUserPhone(userPhone);
        packages.setAddress(address);
        packages.setStatus("Đang vận chuyển");
        packages.setTotalAmount(totalAmount);
        packageService.save(packages);

        Response Responses = new Response(true, "Thêm gói hàng thành công", packages);
        return new ResponseEntity<>(Responses, HttpStatus.CREATED);
    }

    @PutMapping("/update_shipping/{id}")
    public ResponseEntity<Response> updateShipping(@PathVariable("id") String id,
                                                   @RequestParam("idBill") String idBill,
                                                   @RequestParam("idShippingMethod") String idShippingMethod,
                                                   @RequestParam("receiverName") String receiverName,
                                                   @RequestParam("userPhone") String userPhone,
                                                   @RequestParam("address") String address,
                                                   @RequestParam("date") Date date,
                                                   @RequestParam("updateDate") Date updateDate,
                                                   @RequestParam("Province") String province,
                                                   @RequestParam("status") String status,
                                                   @RequestParam("totalAmount") BigDecimal totalAmount) {
        Optional<Package> packages = packageService.findById(id);
        if (packages.isEmpty()) {
            Response Responses = new Response(false, "Không tìm thấy gói hàng", null);
            return new ResponseEntity<>(Responses, HttpStatus.NOT_FOUND);
        }

        // Kiem tra xem idShippingMethod co ton tai trong database khong
        Boolean check = shipping_methodService.existsById(idShippingMethod);
        if (check == false) {
            Response Responses = new Response(false, "Phương thức vận chuyển không tồn tại", null);
            return new ResponseEntity<>(Responses, HttpStatus.BAD_REQUEST);
        }

        // Kiem tra xem trong address co chua tinh hay khong
        if (address.contains(province) == false) {
            address = address + ", " + province;
        }

        Package packagesUpdate = packages.get();
        packagesUpdate.setIdBill(idBill);
        packagesUpdate.setIdShippingMethod(idShippingMethod);
        packagesUpdate.setReceiverName(receiverName);
        packagesUpdate.setUserPhone(userPhone);
        packagesUpdate.setAddress(address);
        packagesUpdate.setDate(date);
        packagesUpdate.setUpdateDate(updateDate);
        packagesUpdate.setStatus(status);
        packagesUpdate.setTotalAmount(totalAmount);
        packageService.save(packagesUpdate);

        Response Responses = new Response(true, "Cập nhật gói hàng thành công", packagesUpdate);
        return new ResponseEntity<>(Responses, HttpStatus.OK);

    }

    @GetMapping("/checkStatus")
    public ResponseEntity<Response> checkStatus(@RequestParam("id") String id) {
        Optional<Package> packages = packageService.findById(id);
        if (packages.isEmpty()) {
            Response Responses = new Response(false, "Không tìm thấy gói hàng", null);
            return new ResponseEntity<>(Responses, HttpStatus.NOT_FOUND);
        }

        Package packagesCheck = packages.get();
        Response Responses = new Response(true, "Trạng thái gói hàng", packagesCheck.getStatus());
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

        if (status.equals("Đã giao hàng") || status.equals("Đã hủy")) {
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
