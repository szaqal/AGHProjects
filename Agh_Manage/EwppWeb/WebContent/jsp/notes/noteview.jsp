<%@taglib prefix="s" uri="/struts-tags"%>
<table width="100%">
	<tr>
		<td><b>Dodal:</b></td>
		<td style="font-size:small"><s:property value="owner.label"/></td>
	</tr>
	<tr>
		<td><b>Tytul:</b></td>
		<td style="font-size:small"><s:property value="note.title"/></td>
	</tr>
	<tr>
		<td><b>Tresc:</b></td>
		<td style="font-size:small"><s:property value="note.content"/></td>
	</tr>
</table>