package ru.stepanov.conferences.services;

import ru.stepanov.conferences.beans.Conference;
import ru.stepanov.conferences.beans.Participant;
import ru.stepanov.conferences.exceptions.ConferenceReferencedException;

import java.util.List;

/**
 * @author: a.stepanov
 */

public interface AppService {

  List<Conference> findAllConferences();

  void addConference(Conference conference);

  void deleteConferenceById(int id) throws ConferenceReferencedException;

  void updateConference(Conference conference);

  List<Participant> findAllParticipants();

  void addParticipant(Participant participant);

  void deleteParticipantById(int id);

  void updateParticipant(Participant participant);
}
