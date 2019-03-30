let map;

function createMap(){

  map = new google.maps.Map(document.getElementById('map'), {
    center: {lat: 38.5949, lng: -94.8923},
    zoom: 4
  });

  map.addListener('click', (event) => {
    const clickLatLng = event.latLng;
    console.log(clickLatLng.lat() + ', ' + clickLatLng.lng());
  });
}
