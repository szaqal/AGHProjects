<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(document).ready(function() {
		$("#students").autocomplete('AjaxUsersList.action',{
		      dataType: 'json',
		      parse: function(data) {
		          var rows = new Array();
		          for(var i=0; i<data.length; i++){
		              rows[i] = { data:data[i], value:data[i].uniqueId, result:data[i].label };
		          }
		          return rows;
		      },
		      formatItem: function(row, i, n) {
		          return row.label;
		      },
		      width: 300,
		      mustMatch: true
		  });

		$('#addStudent').nyroModal();
		$.nyroModalSettings({
			height: 600,
	        width: 600
		});
	});

	function removeStudent(id) {
		$("#hid"+id).remove();
		$("#tr"+id).remove();
	}

	function validate() {
		var curUsrId = $("#curUsrId").val();
		var select="hid"+curUsrId;
		var element = document.getElementById(select);
		var choosedLectIdx = document.getElementById('EditProject_lecturerId').selectedIndex;
		var choosedLectVal = document.getElementById('EditProject_lecturerId').options[choosedLectIdx].value;
		var isLecturer = curUsrId == choosedLectVal;
		if(element || isLecturer) {
			return true;
		} else {
			alert('Nie mozna utworzyc projektu nie bedac jego czescia');
			return false;
		}
		
	}
	
</script>

<table border="0" width="100%">
	<tr>
		<td align="right"><img src="gfx/redhat-programming.png"/></td>
		<td><b>Nowy projekt</b></td>
	</tr>
	<tr>
		<td colspan="2" style="border-bottom-style:solid;border-bottom-color:black;border-bottom-width:1px"></td>
	</tr>
	
	<tr>
		<td colspan="2" align="left">Studenci:</td>
	</tr>
	
	<tr>
		<td colspan="2" align="center">
			<s:hidden id="curUsrId" name="usrId"></s:hidden>
			<table id="studentsTab">
			</table>
		</td>
	</tr>

	<tr>
			<td colspan="2" align="right"><a
				href="UsersList.action?operation=pickstudent" id="addStudent"
				class="nyroModal">Dodaj</a>
			</td>
	</tr>
	
	<tr>
	<td colspan="2">
		<s:form action="EditProject" onsubmit="return validate();">
			<s:select list="lecturers" name="lecturerId" listKey="%{uniqueId}" listValue="%{label}" label="Prowadzacy"/>
			<s:textfield name="topic" label="Temat" cssStyle="width:370px"/>
			<s:textarea name="description" label="Opis" rows="10" cols="50"/>
			<s:hidden name="operation" value="save"/>
			<s:submit value="Rozpocznij"/>
		</s:form>
		</td>
	</tr>
</table>