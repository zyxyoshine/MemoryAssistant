<?php
	$name = $_GET['na'];
	$passw = $_GET['pw'];
	require 'db_con.php';
	$re = mysql_query("select * from user where User_Id='$name'");
	$row = mysql_fetch_array($re);
	function createRandomStr($length){ 
				$str = array_merge(range('a','z'),range('A','Z'),range(0,9)); 
				shuffle($str); 
				$str = implode('',array_slice($str,0,$length)); 
				return $str; 
			} 
	if ($row['User_PW'] == $passw){
		$res =  mysql_query("select * from online_rec where username='$name'");
		if (mysql_num_rows($res) > 0){
			mysql_query("update online_rec set onapp = '*' where username = '$name'");
		}else{
			$randn = createRandomStr(32);
			mysql_query("insert into online_rec(username,onapp,hashcode) values('$name','*','$randn')");
		}
		echo "Success Login,$name";
	}else{
		echo "Incorrect Password";
	}
?>