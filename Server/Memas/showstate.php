<?php
	require 'db_con.php';
	$re = mysql_query("select * from online_rec");
	echo "<ul>";
	while ($row = mysql_fetch_array($re)) {
		$hash = $row["hashcode"];
		$na = $row["username"];
		$onweb = $row["onweb"];
		$onapp = $row["onapp"];
		if (($onweb == '*') || ($onapp == '*')) {
			echo "<li>HASH：$hash</li>";
	        echo "<li>NAME：$na</li>";
	        echo "<li>Onweb：$onweb</li>";
			echo "<li>Onapp：$onapp</li>";
			echo "-------------------------------------------------------";
       }
	}
	echo "</ul>";
?>