<?php
	$con = mysqli_connect("localhost", "root", "","trenuri");
	if (!$con) {
		die('Could not connect: ' . mysqli_error($con));
	}

	$result = mysqli_query($con, "SELECT distinct plecare FROM rute");

	while($row = mysqli_fetch_array($result)){
		$plecare = htmlspecialchars($row['plecare']);
    	echo "<option value=\"$plecare\">$plecare</option>";
	}
	mysqli_close($con);
?> 