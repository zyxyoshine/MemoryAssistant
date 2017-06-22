<?php
$vnow = $_GET['vnow'];
$now = 2;
if ($now > $vnow)
	echo "Auto updating";
else
	echo "Do not need update"
?>