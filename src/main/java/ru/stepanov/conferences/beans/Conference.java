package ru.stepanov.conferences.beans;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: a.stepanov
 */

@Entity(name="conference")
public class Conference extends LightEntity {

  @Basic( optional = false )
  private String name;

  @Temporal(TemporalType.DATE)
  @Column(name = "conference_date")
  @Basic( optional = false )
  private Date date;


  /*@ManyToMany
  @JoinTable
          (
            name = "conference_room",
            joinColumns = @JoinColumn(name = "conference"),
            inverseJoinColumns = @JoinColumn(name = "room")
          )*/

  /*@OneToMany(mappedBy = "conference", cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<Room> rooms;*/

  /* getters and setters */
  public String getName() {
    return name;
  }

  public void setName(String _name) {
    this.name = _name;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date _date) {
    this.date = _date;
  }

/*    @JsonIgnore
  public Set<Room> getRooms() {
    return rooms;
  }

  public void setRooms(Set<Room> _rooms) {
    this.rooms = _rooms;
  }*/

  /* equals and hashCode */

  @Override
  public boolean equals(Object o)
  {
    if( this == o ) return true;
    if( o == null || getClass() != o.getClass() ) return false;

    Conference that = (Conference) o;

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

  public Conference(int id, String _name, Date _date) {
    this.name = _name;
    this.date = _date;
    this.setId(id);
  }

  public Conference() {
  }

  public Conference(int id) {
    this.setId(id);
  }

  @Override
  public String toString()
  {
    return "id:" + getId() + ", название: " + name + ", дата: " + date;
  }


}