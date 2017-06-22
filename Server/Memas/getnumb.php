<?php
$pid = $_GET['pid'];
$anst = $_GET['anst'];
require 'db_con.php';
$re = mysql_query("select * from q_b where Q_B_No='$pid'");
$row = mysql_fetch_array($re);
$nrows = mysql_num_rows($re);
if ($nrows == 1) {
	$ans = $row["Q_B_Prob$anst"];
	if ($ans)
		echo $row["Q_B_Prob$anst"];
}
?>