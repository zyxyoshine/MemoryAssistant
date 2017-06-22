<?php
	$name = $_GET['na'];
	$pw = $_GET['pw'];
	require 'db_con.php';
	mysql_query("update user set User_PW = '$pw' where User_Id='$name'");
?>