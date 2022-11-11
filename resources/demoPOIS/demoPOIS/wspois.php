<?php
	
	include "config.php";
	
	$path_params = explode('/', trim($_SERVER['PATH_INFO']));
	
	$input = json_decode(file_get_contents('php://input'),true);

		
		/*$long1 = @$input['long1'];
		$lat1 = @$input['lat1'];
		$long2 = @$input['long2'];
		$lat2 = @$input['lat2'];
		$effort = @$input['effort'];*/
		
		$category= @$input['category'];
		
		//print "$path_params[1]\n";
		/*$json = $path_params[1];
		$obj = json_decode($json);
		
		$long1 = $obj->{'long1'};
		$lat1 = $obj->{'lat1'};
		$long2 = $obj->{'long2'};
		$lat2 = $obj->{'lat2'};
		$effort = $obj->{'effort'};
		*/
			
			
	switch($_SERVER['REQUEST_METHOD'] ) {
		case 'GET':
				if($path_params[1] == "point_of_interest"){
					
					$sql="select * from point_of_interest";
					
					if (isset($path_params[2]) && is_numeric($path_params[2])){  // id
						$sql.= " where category_id=" . $path_params[2];
					} 
				
				$result=$db->query($sql);
			
				while($row = $result->fetch_assoc()){
							$list[]=$row;
						}
										
			
				}
				
				if($path_params[1] == "category"){
					
					$sql="select * from category";
					
					if (isset($path_params[2]) && is_numeric($path_params[2])){  // id
						$sql.= " where category_id=" . $path_params[2];
					} 
				
				$result=$db->query($sql);
			
				while($row = $result->fetch_assoc()){
							$list[]=$row;
						}
										
			
				}
				
				
			header("Access-Control-Allow-Origin: *"); 
			header('Content-Type: application/json');
			
			print json_encode($list, JSON_UNESCAPED_UNICODE);
			
			break;
			
		case 'POST':	
		echo 'POST metodo: ';
			
			/*print "$name $address $latitude $longitude $description <br>\n";
			
			$sql="insert into place(name, address, latitude, longitude, description) 
			values ('$name', '$address', '$latitude', '$longitude', '$description') ";
			
			//print "$sql"; // debug
			
			$result=$db->query($sql);
			print "new register:" . $db->insert_id;

			
		*/
				
			break;
			
		
		
		default: 
			echo "point_of_interest service";
	}
	
	// call 
	// this is your installed dir: http://localhost/services/
	// it's possible to omit the extention ".php" in apache with mod_rewrite
	
	// GET
	// Retrieve all places with a GET from URI http://localhost/services/ws.php/places
	// Retrieve one user with a GET from URI http://localhost/services/ws.php/places/1
	
	// POST INSERT http://localhost/services/ws.php/places ou sem places
	
	// put UPDATE URI http://localhost/services/ws.php/places/1
	
	// DELETE delete URI http://localhost/services/ws.php/places/1
?>