<?php
	$name = $_GET['na'];
	require 'db_con.php';
	$re = mysql_query("select * from user where User_Id='$name'");
	$nrows = mysql_num_rows($re);
	if ($nrows == 0){
		echo "_wr_";
	}else{
		$row = mysql_fetch_array($re);
		echo "$row[User_PW_Q]";
	}
?>