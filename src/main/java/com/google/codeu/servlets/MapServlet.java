package com.google.codeu.servlets;

import java.io.IOException;
import java.util.Scanner;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

/**
 * Returns California Libraries data as a JSON array, e.g. [{"name": "UC Library", "lat": 38.4404675, "lng": -122.7144313}]
 */
@WebServlet("/california-libraries-data")
public class CaliforniaLibrariesServlet extends HttpServlet {

  JsonArray ufoSightingArray;

  @Override
  public void init() {
    String sURL = "https://raw.githubusercontent.com/geoiq/gc_data/master/datasets/877.geojson";

    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.connect();

    // Convert to a JSON object to print data
    JsonParser jp = new JsonParser();
    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
    JsonObject rootobj = root.getAsJsonObject();
    JsonArray rootArray = rootobj.get("features");

    Gson gson = new Gson();

    for (int i = 0; i < rootArray.length(); i++) {
      JsonObject propertyJson = rootArray[i];
      String libraryname = propertyJson.get("libraryname").toString;
      String address = propertyJson.get("address").toString;
      String city = propertyJson.get("city").toString;
      String state = propertyJson.get("state").toString;
      Double lat = propertyJson.get("lat");
      Double lon = propertyJson.get("lon");
      
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

    private Libarary (String libraryname, String address, String city, String state, double lat, double lng) {
      this.libraryname = libraryname;
      this.address = address;
      this.city = city;
      this.state = state;
      this.lat = lat;
      this.lng = lng;
     }
 }
}
