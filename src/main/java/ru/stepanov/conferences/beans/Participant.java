package ru.stepanov.conferences.beans;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: a.stepanov
 */

@Entity(name="participant")
public class Participant extends LightEntity {

  @Basic(optional = false)
  private String name;

  @Temporal(TemporalType.DATE)
  @Column(name = "birth_date")
  @Basic( optional = false )
  private Date date;

  /* Будем исходить из того, что в любой день
  участвовать возможно лишь в одной конференции */

  @ManyToOne(optional = true)
  private Conference conference;

  /* getters and setters */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }


  public Conference getConference() {
    return conference;
  }

  public void setConference(Conference conference) {
    this.conference = conference;
  }

  /* equals and hashCode */
  @Override
  public boolean equals(Object o)
  {
    if( this == o ) return true;
    if( o == null || getClass() != o.getClass() ) return false;

    Participant that = (Participant) o;

    if( name != that.name) return false;
    if( date != that.date ) return false;

    return true;
  }

  @Override
  public int hashCode()
  {
    int result = (int) (getId() ^ (getId()>>> 32));
    /*result = 31 * result + name;
    result = 31 * result + date;*/
    return result;
  }

  public Participant() {
  }

  public Participant(int id) {
    this.setId(id);
  }

  public Participant(String name, Date date, Conference conference) {
    this.name = name;
    this.date = date;
    this.conference = conference;
  }

  @Override
  public String toString()
  {
    return "id:" + getId() + ", имя: " + name;
  }
}