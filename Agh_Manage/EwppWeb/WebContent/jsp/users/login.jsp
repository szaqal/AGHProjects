<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(document).ready(function() {
		$("#loginFormTable").show("fast");
	});

</script>
<table id="loginFormTable" style="display:none">
	<tr><td>
	<s:form action="LoginUser.action">
		<s:textfield name="login" label="Login" id="txtLogin"/>
		<s:password name="passwd" label="Haslo" />
		<s:hidden name="operation" value="login" />
		<s:submit value="Loguj"/>
	</s:form>

	<s:url action="RegisterUser" id="register" />

	<s:a href="%{register}">Rejestracja</s:a>

	</td>
	<td><img src="gfx/user-info.png" id="imgUsr"/></td>
	</tr>

</table>