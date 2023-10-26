package com.phanlop.khoahoc.Service;

import java.util.List;

import com.phanlop.khoahoc.Entity.Chatlog;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;

public interface ChatlogServices {
     List<Chatlog> getAllMessagesBetween(User user,Course course);
     void saveChat(Chatlog lChatlog);
}
