<%@taglib prefix="s" uri="/struts-tags"%>
<table width="100%">
	<tr>
		<td><b>Nazwa:</b></td>
		<td style="font-size:small"><s:property value="file.fileName"/></td>
	</tr>
	<tr>
		<td><b>Mime:</b></td>
		<td style="font-size:small"><s:property value="file.contentType"/></td>
	</tr>
	<tr>
		<td><b>MD5:</b></td>
		<td style="font-size:small"><s:property value="file.md5Hash"/></td>
	</tr>
	<tr>
		<td><b>Data dodania: </b></td>
		<td style="font-size:small"><s:date name="file.projectItem.createDate" format="yyyy-MM-dd"/></td>
	</tr>
	<tr>
		<td>
			<s:url id="downloadLink" action="Download">
				<s:param name="fileId"><s:property value="file.uniqueId"/></s:param>
			</s:url>
			<s:a href="%{downloadLink}">Pobierz</s:a>
		</td>
	</tr>
</table>