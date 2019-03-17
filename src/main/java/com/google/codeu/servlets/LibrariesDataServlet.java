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

  @Override
  public void init() {
    String sURL = "https://raw.githubusercontent.com/geoiq/gc_data/master/datasets/877.geojson";
    JsonArray rootArray = new JsonArray();

    try {
      URL url = new URL(sURL);
      URLConnection request = url.openConnection();
      request.connect();

      JsonParser jp = new JsonParser();
      JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
      JsonObject rootObj = root.getAsJsonObject();
      rootArray = rootObj.getAsJsonArray("features");
    } catch (MalformedURLException mex) {
      // error handeling here
    } catch (IOException iex) {
      // error handeling here
    }

    Gson gson = new Gson();

    // Each array element "pa" includes a JsonObject with desired key value pairs
    for (JsonElement pa : rootArray) {
      JsonObject propertyObj = pa.getAsJsonObject();
      String libraryname = propertyObj.get("libraryname").getAsString();
      String address = propertyObj.get("address").getAsString();
      String city = propertyObj.get("city").getAsString();
      String state = propertyObj.get("state").getAsString();
      Double lat = propertyObj.get("lat").getAsDouble();
      Double lng = propertyObj.get("lon").getAsDouble();

      librariesArray.add((gson.toJsonTree(new Library(libraryname, address, city, state, lat, lng))));
    }

  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    response.getOutputStream().println(librariesArray.toString());
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
