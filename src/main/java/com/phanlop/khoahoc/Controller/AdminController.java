package com.phanlop.khoahoc.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping({"/course","/"})
    public String getCoursePage(){
        return "admin";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping({"/chapter"})
    public String getChapterPage(){
        return "admin_chapter";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping({"/discuss"})
    public String getDiscussionPage(){
        return "admin_discuss";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping({"/document"})
    public String getDocumentPage(){
        return "admin_document";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping({"/assignment"})
    public String getAssignmentPage(){
        return "admin_assignment";
    }
}
