<?php
	$user = $_GET['na'];
	$tp = $_GET['tp'];
	require 'db_con.php';
	if ($tp == 1) {
		$re = mysql_query("select * from msg where na='$user'");
		while ($row = mysql_fetch_array($re)) {
			$na = $row["na"];
			$time = $row["time"];
			$msg = $row["msg"];
			echo "$time $na\n";
	        echo "$msg\n";
			echo "\n";
		}
	}
	if ($tp == 2) {
		$re = mysql_query("select * from msg where na<>'$user'");
		while ($row = mysql_fetch_array($re)) {
			$na = $row["na"];
			$time = $row["time"];
			$msg = $row["msg"];
			echo "$time $na\n";
	        echo "$msg\n";
			echo "\n";
		}
	}
	
?>