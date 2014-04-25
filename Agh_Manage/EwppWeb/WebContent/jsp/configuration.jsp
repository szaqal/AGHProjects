<%@taglib prefix="s" uri="/struts-tags"%>
Konfiguracja<br/>

<s:url action="Configuration" id="confEdit">
	<s:param name="operation">edit</s:param>	
</s:url>
<table width="100%">
	<s:iterator value="configurationKeys" var="confKey">
		<tr>
			<td>
				<s:text name="%{getText(#confKey)}"></s:text>
			</td>
			<td>
				<s:property value="configs[#confKey]"/>
			</td>
		</tr>
	</s:iterator>
	<tr>
		<td><s:a href="%{confEdit}">Edytuj</s:a></td>
		
	</tr>
</table>
