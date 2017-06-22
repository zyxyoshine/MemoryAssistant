<?php
	$name = $_GET['na'];
	$passw = $_GET['pw'];
	$pwq = $_GET['pwq'];
	$pwa = $_GET['pwa'];
	$info = $_GET['info'];
	require 'db_con.php';
	$re = mysql_query("select * from user where User_Id='$name'");
	$nrows = mysql_num_rows($re);
	if ($nrows > 0){
		echo "Username has been used by another person";
	}else{
		mysql_query("insert into user(User_Id,User_PW,User_Info,User_PW_Q,User_PW_A) values('$name','$passw','$info','$pwq','$pwa')");
		echo "Sign up success";
	}
?>