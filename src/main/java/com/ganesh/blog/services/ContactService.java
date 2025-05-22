package com.ganesh.blog.services;


import com.ganesh.blog.payload.ContactDto;

public interface ContactService {
    ContactDto saveContact(ContactDto contactDto);
}

