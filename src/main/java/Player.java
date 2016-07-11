public class Player {
  private Integer bushelsForFood = 0;
  private City city;

  public Player(City city){
    this.city = city;
  }

  public void setBushelsToEat(Integer bushelsForFood) {
    this.bushelsForFood = bushelsForFood;
  }

  public Integer getBushelsForFood() {
    return bushelsForFood;
  }

  public City getCity() {
    return city;
  }
}
