<%@taglib prefix="s" uri="/struts-tags"%>

<s:url action="Download" id="download">
	<s:param name="fileId"><s:property value="fileId"/></s:param>	
</s:url>


<s:iterator value="packagedata"> 
	<tr>
		<td><b><s:property value="key"/></b></td>
		<td><i><s:property value="value"/></i></td>
		
	</tr>
</s:iterator>
<tr><td><s:a href="%{download}">Download</s:a></td></tr>