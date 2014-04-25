<%@taglib prefix="s" uri="/struts-tags"%>
<table>
	<tr>
		<td>
			<s:form>
				<s:textfield cssStyle="width:440px" name="noteTitle" label="Tytul" />
				<s:textarea name="noteContent" label="Tresc" cols="60" rows="25" />
				<s:hidden name="operation" value="noteAdd"/>
				<s:hidden name="projectId"/>
				<s:submit value="Dodaj"/>
			</s:form>
		</td>
	</tr>
</table>