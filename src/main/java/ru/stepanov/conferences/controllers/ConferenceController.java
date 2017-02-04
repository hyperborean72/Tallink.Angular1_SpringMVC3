package ru.stepanov.conferences.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.stepanov.conferences.beans.Conference;
import ru.stepanov.conferences.beans.ExceptionJSONInfo;
import ru.stepanov.conferences.exceptions.ConferenceReferencedException;
import ru.stepanov.conferences.services.AppService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: a.stepanov
 */

@Controller
@RequestMapping("/conference")
public class ConferenceController {

  static Logger log = Logger.getLogger(ConferenceController.class);

  @Autowired
  private AppService appService;

  @RequestMapping(value = "/conferenceList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody List<Conference> populateConferenceTable() {
    return appService.findAllConferences();
  }

  @RequestMapping(value = "/addConference", method = RequestMethod.POST)
  public @ResponseBody void addConference(@RequestBody Conference conference) {
    log.debug("in ConferenceController.addConference");
    appService.addConference(conference);
  }

  @RequestMapping(value = "/updateConference", method = RequestMethod.PUT)
  public @ResponseBody void updateConference(@RequestBody Conference conference) {
    log.debug("conference: " + conference);
    appService.updateConference(conference);
  }

  @RequestMapping(value = "/removeConference/{id}", method = RequestMethod.DELETE)
  public @ResponseBody void removeConference(@PathVariable("id") int id) throws ConferenceReferencedException{
    appService.deleteConferenceById(id);
  }

  @RequestMapping("/all")
  public String getTemplate() {
    return "conference";
  }

 /* @RequestMapping("/error")
  public String getErrorPage() {
    return "error";
  }


  //@ResponseStatus(value= HttpStatus.NOT_ACCEPTABLE)
  @ExceptionHandler(ConferenceReferencedException.class)
  public String handleIOException(){
    log.error("Conference has registered participants. Unregister participants first");
    return "error";
  }*/



  @ExceptionHandler(ConferenceReferencedException.class)
  public @ResponseBody ExceptionJSONInfo handleEmployeeNotFoundException(HttpServletRequest request, Exception ex){
    ExceptionJSONInfo response = new ExceptionJSONInfo();
    //response.setUrl(request.getRequestURL().toString());
    response.setMessage(ex.getMessage());

    return response;
  }

}

