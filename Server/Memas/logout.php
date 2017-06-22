<?php
	$name = $_GET['na'];
	require 'db_con.php';
	mysql_query("delete from online_rec where username = '$name'");
	echo "Log out success"
?>