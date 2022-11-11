// See post: http://asmaloney.com/2014/01/code/creating-an-interactive-map-with-leaflet-and-openstreetmap/
var myURL = jQuery( 'script[src$="leaf-demo.js"]' ).attr( 'src' ).replace( 'leaf-demo.js', '' );

var myIcon = L.icon({
  iconUrl: myURL + 'images/pin24.png',
  iconRetinaUrl: myURL + 'images/pin48.png',
  iconSize: [29, 24],
  iconAnchor: [9, 21],
  popupAnchor: [0, -14]
});

var pois = new L.layerGroup();

for ( var i=0; i < markers.length; ++i )
{
 L.marker( [markers[i].latitude, markers[i].longitude], {icon: myIcon} )
  .bindPopup( '<a href="' + markers[i].point_url + '" target="_blank">' + markers[i].point_name + '</a>' )
  .addTo(pois);
}
//.addTo( map )

var mbAttr = 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' + '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
'Imagery � <a href="http://mapbox.com">Mapbox</a>' + ', icons by <a href="http://www.flaticon.com/">Freepik</a>';
var	mbUrl = 'https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibzJicGlja2luIiwiYSI6ImNpbXUyMzNldTAyNTF1cmtrZXdnbWZycDIifQ.JdYE50MBxDn1fdZtVFYXZw';

var streets = L.tileLayer(mbUrl,{id:'mapbox.streets',attribution:mbAttr});
var satellite  = L.tileLayer(mbUrl, {id: 'mapbox.satellite', attribution: mbAttr});

var basemap = L.tileLayer( 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
  attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>',
  subdomains: ['a', 'b', 'c']
});

var map = L.map( 'map', {
  center: [38.71, -9.13],
  
  zoom: 16,
  layers:[basemap,pois]
});

//streets.addTo(map);
//satellite.addTo(map);
//basemap.addTo( map );


var baseLayers = {
	"Satellite": satellite,
	//"Streets": streets,
	"BaseMap": basemap
};

var overlayMaps = {
    "PointsOfInterest": pois
};

L.control.layers(baseLayers,overlayMaps).addTo(map);
