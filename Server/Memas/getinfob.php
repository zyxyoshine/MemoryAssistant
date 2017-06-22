<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<?php
$pid = $_GET['pid'];
require 'db_con.php';
$re = mysql_query("select * from q_b where Q_B_No='$pid'");
$row = mysql_fetch_array($re);
$nrows = mysql_num_rows($re);
if ($nrows == 1) {
	echo $row['Q_B_Info'];
}
?>