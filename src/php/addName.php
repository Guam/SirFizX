<?
	//mysql_real_escape_string(unescaped_string);
	$arr = array();

	//if($_POST['Submit']){  //THIS LINE WAS THE ISSUE
		$arr[0] = $_POST["var1"];
		$arr[1] = $_POST["var2"];

		// Connect database.
		$host="localhost";
		$db_user="sirfizx_test"; //Whatever your user name is
		$db_password="3L39af99U11";
		$database="sirfizx_test";
		$db = mysql_connect($host,$db_user,$db_password);
		if (!($db)){
			echo "SQL ERROR: Connection failed: "; //I echo this so it responds to Java applet
			die('SQL ERROR: Connection failed: ' . mysql_error($db));
		}

		$dbSelected = mysql_select_db($database,$db);

		if(!$dbSelected)
		{
			echo "SQL ERROR: Selection Failed ";
			die("Can\'t use ".$database." ".mysql_error());
		}

		$tbl_name="clients";
		$sql="SELECT * FROM $tbl_name";
		$result=mysql_query($sql);
		//fetch tha data from the database
		while ($row = mysql_fetch_array($result)) {
   		echo $row{'first'}." ".$row{'last'};
		}
		//echo "success"; //RESPOND TO APPLET
	//}
?>


