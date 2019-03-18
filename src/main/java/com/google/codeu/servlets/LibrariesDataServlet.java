package com.google.codeu.servlets;

import java.net.*;
import java.io.*;

import java.util.Scanner;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
/**
 * Returns California Libraries data as a JSON array, e.g. [{"name": "UC Library",..., "lat": 38.4404675, "lng": -122.7144313}]
 */
@WebServlet("/libraries-data")
public class LibrariesDataServlet extends HttpServlet {

  JsonArray librariesArray;
  String root;

  @Override
  public void init() {
    librariesArray = new JsonArray();
    Gson gson = new Gson();
    Scanner scanner = new Scanner(getServletContext().getResourceAsStream("/WEB-INF/california-pub-libraries.json"));
    // while(scanner.hasNextLine()) {
    root = scanner.nextLine();
      // String[] cells = line.split(",");
      //
      // String state = cells[0];
      // double lat = Double.parseDouble(cells[1]);
      // double lng = Double.parseDouble(cells[2]);

      //librariesArray.add(gson.toJsonTree(new Library(libraryname, address, city, state, lat, lng)));
    // }

    scanner.close();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    response.getOutputStream().println(root);
    //response.getOutputStream().println(librariesArray.toString());
  }

  private static class Library {
    String libraryname;
    String address;
    String city;
    String state;
    double lat;
    double lng;

    private Library(String libraryname, String address, String city, String state, double lat, double lng) {
      this.libraryname = libraryname;
      this.address = address;
      this.city = city;
      this.state = state;
      this.lat = lat;
      this.lng = lng;
    }
  }
}
