// See post: http://asmaloney.com/2014/01/code/creating-an-interactive-map-with-leaflet-and-openstreetmap/
var myURL = jQuery( 'script[src$="leaf-demo.js"]' ).attr( 'src' ).replace( 'leaf-demo.js', '' );

var myIcon = L.icon({
  iconUrl: myURL + 'images/pin24.png',
  iconRetinaUrl: myURL + 'images/pin48.png',
  iconSize: [29, 24],
  iconAnchor: [9, 21],
  popupAnchor: [0, -14]
});

var myIconv2 = L.icon({
  iconUrl: myURL + 'images/pin24v2.png',
  iconRetinaUrl: myURL + 'images/pin48v2.png',
  iconSize: [29, 24],
  iconAnchor: [9, 21],
  popupAnchor: [0, -14]
});

var pois = new L.layerGroup();
var poisFG = new L.featureGroup();



$.getJSON('http://quasar.ptws.net/isctelisboa/wspois.php/point_of_interest', function(data) {
  //data is the JSON string
  //var jsonObj = JSON.parse(data); 
  var obj= jQuery.parseJSON(JSON.stringify(data));
	for ( var i=0; i < obj.length; ++i )
		{
		var m = L.marker( [obj[i].latitude, obj[i].longitude], {icon: myIcon} )
		  .bindPopup( '<a href="' + obj[i].point_url + '" target="_blank">' + obj[i].point_name + '</a>' );
		  m.zIndexOffset = obj[i].point_id;
		  m.addTo(pois);
		  m.addTo(poisFG);
		}
	
});





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
poisFG.addTo(map);


//streets.addTo(map);
//satellite.addTo(map);
//basemap.addTo( map );

map.on('click', 
		 	function(e){
				var x = document.getElementById("info");
				if (x.style.display === "none") {
					x.style.display = "block";
					var coord = e.latlng.toString().split(',');
					var lat = coord[0].split('(');
					var lng = coord[1].split(')');
					//alert("You clicked the map at LAT: " + lat[1] + " and LONG: " + lng[0]);
					var m = L.marker(e.latlng, {icon: myIconv2}).bindPopup(lat[1]+lng[0]);
					m.addTo(pois);
					m.addTo(poisFG);
					document.getElementById("lat").innerHTML = lat[1];
					document.getElementById("long").innerHTML = lng[0];
					
				} else {
					x.style.display = "none";
				}
				
				
				
				
 	});
	

poisFG.on("click", function (event) {
    var clickedMarker = event.layer;
    document.getElementById("demo2").innerHTML = event.latlng.toString();

		
	var x = document.getElementById("update_del");
				if (x.style.display === "none") {
					x.style.display = "block";
					var coord = event.latlng.toString().split(',');
						var lat = coord[0].split('(');
						var lng = coord[1].split(')');
					document.getElementById("latitude").innerHTML = lat[1];
					document.getElementById("longitude").innerHTML = lng[0];
				
				} else {
					x.style.display = "none";
				}
				
});
	
var baseLayers = {
	"Satellite": satellite,
	//"Streets": streets,
	"BaseMap": basemap
};

var overlayMaps = {
    "PointsOfInterest": pois
};



L.control.layers(baseLayers,overlayMaps).addTo(map);

