<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<?php
$pid = $_GET['pid'];
$anst = $_GET['anst'];
require 'db_con.php';
$re = mysql_query("select * from q_a where Q_A_No='$pid'");
$row = mysql_fetch_array($re);
$nrows = mysql_num_rows($re);

if ($nrows == 1) {
	$ans = $row["Q_A_Ans$anst"];
	if ($ans)
		echo $row["Q_A_Ans$anst"];
}
?>