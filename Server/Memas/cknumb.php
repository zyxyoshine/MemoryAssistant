<?php
$pid = $_GET['pid'];
//$anst = $_GET['anst'];
$answ = $_GET['ans'];
require 'db_con.php';
$re = mysql_query("select * from q_b where Q_B_No='$pid'");
$row = mysql_fetch_array($re);
$nrows = mysql_num_rows($re);
if ($nrows == 1) {
	$ans = $row["Q_B_Ans1"].$row["Q_B_Ans2"].$row["Q_B_Ans3"].$row["Q_B_Ans4"];
	if ($ans == $answ)
		echo 1;
	else
		echo 0;
}
?>