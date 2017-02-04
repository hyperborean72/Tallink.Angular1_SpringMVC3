package ru.stepanov.conferences.beans;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author: a.stepanov
 */

@Entity(name="tallink_db.room")
public class Room extends LightEntity {

  @Basic( optional = false )
  private String name;

  @Basic( optional = false )
  private String location;

  @Column(name = "seat_number")
  @Basic( optional = false )
  private int seatNumber;


  /* Будем исходить из того, что на любую указанную дату
  комната может быть назначена лишь одной конференции */

  @ManyToOne(optional = true)
  private Conference conference;

  /* getters and setters */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public int getSeatNumber() {
    return seatNumber;
  }

  public void setSeatNumber(int seatNumber) {
    this.seatNumber = seatNumber;
  }


  public Conference getConference() {
    return conference;
  }

  public void setConference(Conference conference) {
    this.conference = conference;
  }

  public Room() {
  }

  /* equals and hashCode */
  @Override
  public boolean equals(Object o)
  {
    if( this == o ) return true;
    if( o == null || getClass() != o.getClass() ) return false;

    Room that = (Room) o;

    if( name != that.name) return false;
    if( location != that.location ) return false;
    if( seatNumber != that.seatNumber ) return false;

    return true;
  }

  @Override
  public int hashCode()
  {
    int result = (int) (getId() ^ (getId()>>> 32));
    return result;
  }

  @Override
  public String toString()
  {
    return "id:" + getId() + ", название: " + name + ", вместимость: " + seatNumber;
  }
}