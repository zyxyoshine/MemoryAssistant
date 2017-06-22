<?php
	$name = $_GET['na'];
	$msg = $_GET['msg'];
	require 'db_con.php';
	mysql_query("insert into msg(na,time,msg) values('$name',now(),'$msg')");
?>