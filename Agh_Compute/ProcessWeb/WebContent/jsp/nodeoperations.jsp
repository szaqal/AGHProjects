<%@taglib prefix="s" uri="/struts-tags"%>
<table>
<s:iterator status="stat" value="operations" >
	<tr>
		<td><s:property value="key"/></td>
		<td><s:property value="value"/></td>
	</tr>
</s:iterator> 
</table>