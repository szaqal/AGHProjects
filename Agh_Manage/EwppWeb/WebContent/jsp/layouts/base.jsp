<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><tiles:insertAttribute name="title" /></title>
	<script type="text/javascript" src="js/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="js/jquery.autocomplete.js"></script>
	<script type="text/javascript" src="js/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
	<script type="text/javascript" src="js/nyro/jquery.nyroModal-1.5.2.pack.js"></script>
	<link rel="stylesheet" type="text/css" href="css/styles.css" />
	<link rel="stylesheet" type="text/css" href="css/steel/grid.css" />
	<link rel="stylesheet" type="text/css" href="css/jquery.autocomplete.css" />
	<link rel="stylesheet" type="text/css" href="css/smoothness/jquery-ui-1.7.2.custom.css" />
	<link rel="stylesheet" type="text/css" href="css/nyro/nyroModal.css"></link>
</head>

<body bottommargin="0" leftmargin="0" marginheight="0" marginwidth="0"
	rightmargin="0" topmargin="0" background="gfx/greenBack.jpg">

<table width="780" height="180" cellpadding="0" cellspacing="0" border="0" align="center">
	<tr valign="middle" background="gfx/header_learn.jpg" align="right">
		<td width="100%"><font color="white"> <b><tiles:insertAttribute
			name="sysname" /><br />
		<tiles:insertAttribute name="headertitle" /></b> </font></td>
	</tr>
</table>
<table width="780" height="650" cellpadding="0" cellspacing="0" border="0" align="center" bgcolor="white">
	<tr valign="top">
		<td width="175">
			<table width="175" cellpadding="4" cellspacing="0" border="0">
				<tr valign="top">
					<td width="175"><tiles:insertAttribute name="menu" /></td>
				</tr>
			</table>
		</td>
		<td width="510">
			<table width="510" cellpadding="5" cellspacing="5" border="0">
				<s:if test="#session.sessionData.userLabel != ''">
					<tr>
						<td align="center" style="font-size:small;border-bottom-color:black;border-bottom-style:dotted;border-bottom-width:1px">
							Zalogowany: <s:property value="#session.sessionData.userLabel" />
						</td>
					</tr>
				</s:if>
				<tr valign="top">
					<td width="510" align="center"><tiles:insertAttribute name="body" /></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr valign="bottom" height="85" align="center">
		<td colspan="2" style="border-top-color:black;border-top-style:solid;border-top-width:1px">
		<tiles:insertAttribute name="footer" />
		</td>
	</tr>
</table>
</body>
</html>
