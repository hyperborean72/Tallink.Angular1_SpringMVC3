package ru.stepanov.conferences.beans;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author: a.stepanov
 */

@MappedSuperclass
public class LightEntity implements Serializable {
  @Id
  @GeneratedValue
  private int id;

  public int getId()
  {
    return id;
  }

  public void setId(int _id)
  {
    id = _id;
  }

  public LightEntity() {
  }
}
