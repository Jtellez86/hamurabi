public class Player {
  private Integer bushelsToSave = 0;
  private City city;

  public Player(City city){
    this.city = city;
  }

  public void setBushelsToSave(Integer bushelsToSave) {
    this.bushelsToSave = bushelsToSave;
  }

  public Integer getBushelsToSave() {
    return bushelsToSave;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }
}
