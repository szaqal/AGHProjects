<%@taglib prefix="s" uri="/struts-tags"%>
<table border="0" width="100%">
	<tr><td><b>Od:</b></td><td><s:property value="message.sender.label"/></td></tr>
	<tr><td><b>Do:</b></td><td><s:property value="message.recipient.label"/></td></tr>
	<tr>
		<td style="border-bottom-style:solid;border-bottom-color:black;border-bottom-width:1px"><b>Data:</b></td>
		<td style="border-bottom-style:solid;border-bottom-color:black;border-bottom-width:1px">
			<s:property value="message.createDate"/>
		</td></tr>
	<tr>
		<td style="border-bottom-style:solid;border-bottom-color:black;border-bottom-width:1px"><b>Temat:</b></td>
		<td style="border-bottom-style:solid;border-bottom-color:black;border-bottom-width:1px"><s:property value="message.title"/></td>
	</tr>
	<tr><td><b>Tresc:</b></td><td><s:property value="message.content"/></td></tr>
</table>