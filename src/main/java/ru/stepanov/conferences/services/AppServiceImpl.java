package ru.stepanov.conferences.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.conferences.beans.Conference;
import ru.stepanov.conferences.beans.Participant;
import ru.stepanov.conferences.exceptions.ConferenceReferencedException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author: a.stepanov
 */

@Service
public class AppServiceImpl implements AppService {

  static Logger log = Logger.getLogger(AppServiceImpl.class);

  @PersistenceContext (unitName = "tallinkUnit")
  private EntityManager entityManager;


  @Override
  @Transactional
  public List<Conference> findAllConferences(){
    String queryString = "select a.id, a.name, a.date from conference a";
    Query query = entityManager.createQuery(queryString);
    return query.getResultList();
  };


  @Override
  @Transactional
  public void addConference(Conference conference)
  {

    entityManager.persist(conference);
  }


  @Override
  @Transactional
  public void deleteConferenceById(int id) throws ConferenceReferencedException{
    String countQueryString = "select p from participant p join p.conference c where c.id= (:conferenceId)";
    Query countQuery = entityManager.createQuery(countQueryString).setParameter("conferenceId", id);
    if (countQuery.getResultList().size() > 0){
      throw new ConferenceReferencedException(id);
    } else {
      String queryString = "delete from conference a where a.id = (:conferenceId)";
      Query query = entityManager.createQuery(queryString).setParameter("conferenceId", id);
      query.executeUpdate();
    }
  };


  @Override
  @Transactional
  public void updateConference(Conference conference){
    /*String queryString =  "update conference con " +
            "set con.name = :conferenceName" +
            "set con.date = :conferenceDate" +
            "where con.id = :conferenceId";
    Query query = entityManager.createQuery(queryString)
            .setParameter("conferenceId", conference.getId())
            .setParameter("conferenceName",conference.getName())
            .setParameter("conferenceDate",conference.getDate());*/

    /*String queryString =  "update conference con " +
            "set con.name = ?" +
            "set con.date = ?" +
            "where con.id = ?";
    Query query = entityManager.createQuery(queryString)
            .setParameter(0, conference.getName())
            .setParameter(1,conference.getDate())
            .setParameter(2, conference.getId());*/

    String queryString =  "update conference con " +
            "set con.name = ?" +
            "set con.date = ?" +
            "where con.id = ?";
    Query query = entityManager.createQuery(queryString)
            .setParameter(0, conference.getName())
            .setParameter(1,conference.getDate())
            .setParameter(2, conference.getId());


    /*String queryString =  "update tallink_db.conference set name =" + conference.getName()
            + ", conference_date = " + conference.getDate()
            + " where id = " + conference.getId();*/
    /*String queryString =  "update tallink_db.conference set name =" + conference.getName()
            + ", conference_date = TO_DATE(" + conference.getDate().toString() +", 'YYYY-MM-DD')"
            + " where id = " + conference.getId();
    Query query = entityManager.createNativeQuery(queryString);*/

    query.executeUpdate();
  };


  /*****************************************************/


  @Override
  @Transactional
  public List<Participant> findAllParticipants(){
    String queryString = "select p.id, p.name, p.date, p.conference.name from participant p";
    Query query = entityManager.createQuery(queryString);
    return query.getResultList();
  };


  @Override
  @Transactional
  public void addParticipant(Participant participant)
  {
    log.debug("participant in Add: " + participant);
    entityManager.persist(participant);
  }


  @Override
  @Transactional
  public void deleteParticipantById(int id){
    String queryString = "delete from participant p where p.id = (:participantId)";
    Query query = entityManager.createQuery(queryString).setParameter("participantId", id);
    query.executeUpdate();
  };


  @Override
  @Transactional
  public void updateParticipant(Participant participant){
    /*Что-то не срастается с этим HQL*/
    /*String queryString =  "update participant a " +
            "set a.name = :participantName" +
            "set a.date = :participantDate" +
            "where a.id = :participantId";
    Query query = entityManager.createQuery(queryString)
            .setParameter("participantId", participant.getId())
            .setParameter("participantName",participant.getName())
            .setParameter("participantDate",participant.getDate());*/

    String queryString =  "Update participant set name =" + participant.getName()
            + ", birth_date = " + participant.getDate()
            + ", conference_id = " + participant.getConference().getId()
            + " where id = " + participant.getId();
    Query query = entityManager.createNativeQuery(queryString);

    query.executeUpdate();
  };

}