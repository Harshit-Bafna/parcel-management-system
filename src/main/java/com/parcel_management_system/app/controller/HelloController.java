package com.parcel_management_system.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/v1/hello")
public class HelloController {
    @GetMapping("/public")
    public ResponseEntity<String> helloPublic() {
        return new ResponseEntity<>("message, Hello from Ship In!", HttpStatus.OK);
    }

    @GetMapping("/protected")
    public ResponseEntity<String> helloProtected() {
        return new ResponseEntity<>("message, Hello from Ship In!", HttpStatus.OK);
    }
}