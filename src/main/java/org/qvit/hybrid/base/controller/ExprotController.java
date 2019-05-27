package org.qvit.hybrid.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sunyl on 16/7/29.
 */
@Controller
public class ExprotController {

    @RequestMapping("/export/add")
    public String query(HttpServletRequest request) {
        System.err.println("/export/add");
        return "exprot/add";
    }
}
