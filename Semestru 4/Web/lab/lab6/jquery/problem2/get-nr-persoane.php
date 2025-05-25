
<?php
	$con = mysqli_connect("localhost", "root", "","persoane_db");
	if (!$con) {
		die('Could not connect: ' . mysqli_error());
	}

	$result = mysqli_query($con, "SELECT COUNT(*) FROM persoane");

    if ($result) {
        $row = mysqli_fetch_array($result);
        echo $row[0];
    } else {
        echo "Error: " . mysqli_error($con);
    }
	mysqli_close($con);
?> 
