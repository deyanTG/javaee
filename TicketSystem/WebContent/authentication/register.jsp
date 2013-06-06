<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>s
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<title>Untitled Page</title>
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,700"
	rel="stylesheet" type="text/css" />
<link href="../css/default.css" rel="stylesheet" type="text/css" />
 <script src="/TicketSystem/js/jquery-1.8.3.js" type="text/javascript"></script>
<script src="/TicketSystem/js/JQueryCheckin.js" type="text/javascript"></script>
<script src="/TicketSystem/js/JQuery.js" type="text/javascript"></script>
<script src="/TicketSystem/js/regvalidation.js" type="text/javascript"></script>
 </head>
<body>
	<center>
		<form name="objForm" action="./doRegister.jsp" method="post"
			onSubmit="return validate(this)">
			<table>
				<tr>
					<td class="deepbluetExampletbold"><b>Create a user account</b></td>
				</tr>
				<tr>
					<td class="colouredCell"><b>Name*</b></td>
					<td><input class="uname" id="t1" name="t1" /><span class="status"></span></td>
				</tr>
				<tr>
					<td class="colouredCell"><b>Password*</b></td>
					<td><input name="t2"  id="t2" type="password" /></td>
				</tr>
				<tr>
					<td class="colouredCell"><b>Confirm password*</b></td>
					<td><input name="t3" id="t3" type="password" /></td>
				</tr>
			</table>
			<input type="submit" id="button" value="Register" />
		</form>
	</center>
	
	<script type="text/javascript">
    $(document).ready(function () {
    	document.getElementById("button").disabled=true;
        $(".uname").keyup(function () {
            var uname = $(this).val();
            if (uname.length > 3) {
                $(".status").html("Checking availability...");
                $.ajax({
                        type: "POST",
                        url: "/TicketSystem/authentication/check",
                        data: "uname=" + uname,
                        success: function (msg) {
                            $(".status").ajaxComplete(function (event, request, settings) {
                                $(".status").html(msg);
                                if(msg.indexOf("is avaliable") !== -1){
                                	document.getElementById("button").disabled=false;
                                }else{
                                	document.getElementById("button").disabled=true;
                                 }
                            });
                        }
                    });
            } else {
                $(".status").html("username should be 3 chars");
                document.getElementById("button").disabled=true;
            }

        });
    });
</script>
</body>
</html>