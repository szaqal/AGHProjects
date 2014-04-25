<%@taglib prefix="s" uri="/struts-tags"%>
Edycja konfiguracji
<table width="100%">
<s:form action="Configuration">
		<s:iterator value="configurationKeys" var="confKey">
			<tr>
				<td>
					<s:textfield name="configs['%{#confKey}']" label="%{getText(#confKey)}"></s:textfield>
				</td>
			</tr>
		</s:iterator>
		<s:hidden name="operation" value="save"/>
		<s:submit value="Zapisz"/>
</s:form>
</table>