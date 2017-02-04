package ru.stepanov.conferences.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.stepanov.conferences.beans.Participant;
import ru.stepanov.conferences.services.AppService;

import java.util.List;

/**
 * @author: a.stepanov
 */

@Controller
@RequestMapping("/participant")
public class ParticipantController {

  static Logger log = Logger.getLogger(ParticipantController.class);

  @Autowired
  private AppService appService;

  @RequestMapping(value = "/participantList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody List<Participant> populateParticipantTable() {
    return appService.findAllParticipants();
  }

  @RequestMapping(value = "/addParticipant", method = RequestMethod.POST)
  public @ResponseBody void addParticipant(@RequestBody Participant participant) {
    log.debug("in ParticipantController.addParticipant");
    appService.addParticipant(participant);
  }

  @RequestMapping(value = "/updateParticipant", method = RequestMethod.PUT)
  public @ResponseBody void updateParticipant(@RequestBody Participant participant) {
    appService.updateParticipant(participant);
  }

  @RequestMapping(value = "/removeParticipant/{id}", method = RequestMethod.DELETE)
  public @ResponseBody void removeParticipant(@PathVariable("id") int id) {
    appService.deleteParticipantById(id);
  }

  @RequestMapping("/all")
  public String getTemplate() {
    return "participant";
  }
}

