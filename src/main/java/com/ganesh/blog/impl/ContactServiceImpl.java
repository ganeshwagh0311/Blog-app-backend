package com.ganesh.blog.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ganesh.blog.entities.contact; // ✅ Correct entity class
import com.ganesh.blog.payload.ContactDto;
import com.ganesh.blog.repo.ContactRepo;
import com.ganesh.blog.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public ContactDto saveContact(ContactDto contactDto) {
        contact contact = new contact();
        contact.setName(contactDto.getName());
        contact.setEmail(contactDto.getEmail());
        contact.setPhone(contactDto.getPhone());
        contact.setMessage(contactDto.getMessage());

        com.ganesh.blog.entities.contact saved = contactRepo.save(contact);

        ContactDto savedDto = new ContactDto();
        savedDto.setName(saved.getName());
        savedDto.setEmail(saved.getEmail());
        savedDto.setPhone(saved.getPhone());
        savedDto.setMessage(saved.getMessage());

        return savedDto;
    }
}
