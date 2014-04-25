<%@taglib prefix="s" uri="/struts-tags"%>
<table width="100%">
	<tr>
		<td>
			<s:form action="Correction">
				<s:textarea name="comment" cols="60" rows="20"/>
				<s:hidden name="operation" value="corrent"/>
				<s:hidden name="projectId"/>
				<s:hidden name="projectStageId"/>
				<s:hidden name="projectStageIterationId"/>
				<s:submit value="Wyslij"/>
			</s:form>
		</td>
	</tr>
</table>