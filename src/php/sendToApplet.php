<?
	$arr = array();

	//if($_POST['Submit']){  //THIS LINE WAS THE ISSUE
		$arr[0] = $_POST["var1"];
		$arr[1] = $_POST["var2"];
 
		// Connect database.
		$host="localhost";
		$db_user="yourusername"; //Whatever your user name is
		$db_password="yourpass";
		$database="yourdbname";
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

		
		echo "success"; //RESPOND TO APPLET
	//}
?>


