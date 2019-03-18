// JavaScript functions for loading the map page

function createLibrariesMap() {
  fetch('/libraries-data').then(function(response) {
    return response.json();
  }).then((libraries) => {

    const map = new google.maps.Map(document.getElementById('map'), {
      center: { lat: 37.3351874, lng: -121.8810715 },
      zoom: 15
    });

    libraries.forEach((library) => {
      addMarker(map, library.lat, library.lng, library.libraryname, 'City:' + library.city);
    });
  });
}

function addMarker(map, lat, lng, title, description) {
  const marker = new google.maps.Marker({
    position: { lat: lat, lng: lng },
    map: map,
    title: title
  });

  var infoWindow = new google.maps.InfoWindow({
    content: description
  });

  marker.addListener('click', function() {
    infoWindow.open(map, marker);
  });
}
