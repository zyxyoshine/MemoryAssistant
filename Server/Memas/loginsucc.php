<h1>欢迎使用扫码上传功能!</h1>
<?php
	$name = $_GET['na'];
	echo "$name,请确认文件选择正确";
?>
<br />

<form action="upload.php?na=<?php echo $name ?>" method="post" enctype="multipart/form-data" >
       备注: <input type="text" name="username" value="" /><br>
    <input type="hidden" name="MAX_FILE_SIZE" value="1000000" />
              记忆配置: <input type="file" name="txt[]" value=""><br>
       资料文件: <input type="file" name="txt[]" value=""><br>
    <input type="submit" value="确认上传" /><br>
</form>

<br />
<a href="logout.php?na=<?php echo $name ?>">Log out</a>