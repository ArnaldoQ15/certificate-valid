package br.com.certificatevalid.controller;

import br.com.certificatevalid.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/address")
public class AddressController {

    @Autowired
    private UserAddressService service;


    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Long addressId) {
        return service.delete(addressId);
    }

}