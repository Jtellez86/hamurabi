package com.hamurabi;

import java.io.IOException;

public class Application {
  public static void main(String args[]) throws IOException {
    Hamurabi newGame = new Hamurabi();
    newGame.runGameLoop();
  }
}
