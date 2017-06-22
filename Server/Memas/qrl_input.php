<?php
	$name = $_GET['na'];
	$passw = $_GET['pw'];
	$randnum = $_GET['hashn'];
	require 'db_con.php';
	mysql_query("update online_rec set username = '$name' ,onweb = '*' where hashcode = '$randnum'");
	echo "Success Login Web";
?>