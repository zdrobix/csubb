
<?php
	$con = mysqli_connect("localhost", "root", "","trenuri");
	if (!$con) {
		die('Could not connect: ' . mysqli_error());
	}

	$result = mysqli_query($con, "SELECT sosire FROM rute WHERE plecare = '" .$_GET["name"] ."'");

	while($row = mysqli_fetch_array($result)){
		echo "<option value='" . htmlspecialchars($row[0]) . "'>" . htmlspecialchars($row[0]) . "</option>";
	}
	mysqli_close($con);
?> 
