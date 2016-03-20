package com.coolpeng.admin.controller;

import com.coolpeng.blog.service.UserService;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.mvc.TMSResponse;
import com.coolpeng.framework.mvc.TmsCurrentRequest;
import com.coolpeng.framework.mvc.TmsUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/admin/admin"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView adminIndex()
            throws FieldNotFoundException, ParameterErrorException {
        return new ModelAndView("admin/admin");
    }

    @RequestMapping(value = {"/admin/login"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView adminLogin() {
        return new ModelAndView("admin/login");
    }

    @RequestMapping(value = {"/admin/logout"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView adminLogout() {
        TmsCurrentRequest.setCurrentUser(null);
        return new ModelAndView("admin/login");
    }

    @RequestMapping(value = {"/admin/login/submit"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String loginSubmit(String username, String password, HttpServletRequest request) {
        TMSResponse result = this.userService.doValidate(username, password);
        if (result.getResponseCode() == 0) {
            TmsCurrentRequest.setCurrentUser((TmsUserEntity) result.getData());
            return "redirect:/admin/admin.shtml";
        }

        request.setAttribute("msg", Boolean.valueOf(true));
        return "admin/login";
    }
}