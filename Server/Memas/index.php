<html>
	<head>
		<title>QR Login</title>
		<meta charset="utf-8" />
	</head>
	<h1>欢迎使用扫码上传功能!</h1>
	请使用APP扫描下方二维码<br />
	<body>
		<?php
			require 'db_con.php';
			$randn = "";
			function createRandomStr($length){ 
				$str = array_merge(range('a','z'),range('A','Z'),range(0,9)); 
				shuffle($str); 
				$str = implode('',array_slice($str,0,$length)); 
				return $str; 
			}
			$randn = createRandomStr(32);
			mysql_query("insert into online_rec(hashcode) values('$randn')");
		?>
		<img src="http://qr.liantu.com/api.php?text=<?php echo $randn; ?>" width="300px" />
		<input hidden="hidden" type="text" name="randn" id="randn" value="<?php echo $randn; ?>" />
	</body>
	<script>
		//console.log('123123122');
		function qr_check() {
			var myhttp;
			if (window.XMLHttpRequest){
				myhttp = new XMLHttpRequest();
			}else{
				myhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			//console.log("1111111111111111");
			myhttp.onreadystatechange = function(){
				if (myhttp.status == 200 && myhttp.readyState == 4){
					re = myhttp.responseText;
					//console.log(re);
					if (re != '__fail__'){
						//alert(re);
						window.location.href = 'loginsucc.php?na=' + re;
					}
				}
			}
			//console.log("22222222222222");
			randnum = document.getElementById('randn').value;
			//console.log(randnum);
			myhttp.open("GET","qr_check.php?randn="+randnum,true);
			myhttp.send();
		}
		
		setInterval("qr_check()",1000);
	</script>
</html>