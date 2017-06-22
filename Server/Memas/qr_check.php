<?php
	require 'db_con.php';
	$randnum = $_GET['randn'];
	$re = mysql_query("select * from online_rec where hashcode ='$randnum'");
	$row = mysql_fetch_array($re);
	if (($row['username'] != "") && ($row['onweb'] == "*")) {
		$rena = $row['username'];
		echo "$rena";
	}else
		echo "__fail__";
?>