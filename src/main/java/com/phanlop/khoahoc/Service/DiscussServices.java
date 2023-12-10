package com.phanlop.khoahoc.Service;

import java.util.List;

import com.phanlop.khoahoc.DTO.DiscussDTO;
import com.phanlop.khoahoc.DTO.InboxDTO;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Discuss;
import com.phanlop.khoahoc.Entity.User;

public interface DiscussServices {
    public List<Discuss> findAllByCourse(Course course);
    public void save(Discuss discuss);
    public List<InboxDTO> findAllInboxByUser(User user);
    public List<InboxDTO> findAllInboxByTeacher(User user);
    public List<DiscussDTO> findAllDiscussDTOByCourse(Course course);
    public List<InboxDTO> fillterBySearch(User user, String search);
    public List<InboxDTO> filterBySearchTeacher(User admin, String search);
}
