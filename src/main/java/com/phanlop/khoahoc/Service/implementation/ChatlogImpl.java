package com.phanlop.khoahoc.Service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phanlop.khoahoc.Entity.Chatlog;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.CartRepository;
import com.phanlop.khoahoc.Repository.ChatlogRepository;
import com.phanlop.khoahoc.Service.ChatlogServices;
@Service
public class ChatlogImpl implements ChatlogServices{
    @Autowired
    private ChatlogRepository chatlogRepository;
    @Override
    public List<Chatlog> getAllMessagesBetween(User user, Course course) {
        return chatlogRepository.getContentOf(user, course);
    }
    @Override
    public void saveChat(Chatlog chatlog) {
       chatlogRepository.save(chatlog);
    }
    
}
