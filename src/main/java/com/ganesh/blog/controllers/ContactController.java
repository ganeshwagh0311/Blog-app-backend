package com.ganesh.blog.controllers;


import com.ganesh.blog.payload.ContactDto;
import com.ganesh.blog.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contact")
//@CrossOrigin(origins = "*") // Allow cross-origin if frontend is hosted separately
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactDto> saveContact(@RequestBody ContactDto contactDto) {
        ContactDto saved = contactService.saveContact(contactDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}

