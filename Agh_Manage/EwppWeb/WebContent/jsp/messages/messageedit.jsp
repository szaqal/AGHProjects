<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function(){ 
		$('#addRecipient').nyroModal();
		$.nyroModalSettings({
			height: 600,
	        width: 600
		});
		
	});

	function removeRecipient(id) {
		$("#hid"+id).remove();
		$("#tr"+id).remove();
	}
</script>
<table>
		<tr>
			<td>Adresaci:</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<table id="recipientsTab">
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="right"><a
				href="UsersList.action?operation=pick" id="addRecipient"
				class="nyroModal">Dodaj adresta</a></td>
		</tr>
		<tr>
			
			<td>
				<s:form action="MessageEdit" >
				<s:textfield name="message.title" label="Temat" />
				<s:textarea name="message.content" cols="60" rows="20" /> 
				<s:hidden name="operation" value="send" id="hidOperation"/> <s:submit value="Wyslij" />
				</s:form>
			</td>
			
		</tr>


</table>