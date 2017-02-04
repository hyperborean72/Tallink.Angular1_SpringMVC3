package ru.stepanov.conferences.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: a.stepanov
 */

@Controller
@RequestMapping("/")
public class IndexController {

  @RequestMapping
  public String getIndexPage() {
    return "index";
  }
}
