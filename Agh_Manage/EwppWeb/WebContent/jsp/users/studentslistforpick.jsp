<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
jQuery(document).ready(function(){ 
	  jQuery("#list").jqGrid({
	    url:'AjaxStudentsList.action',
	    datatype: 'json',
	    mtype: 'GET',
	    colNames:['Akcja','Imie','Nazwisko', 'EMail','Telefon'],
	    colModel :[ 	
		  {name:'act',index:'act', width:75,sortable:false},     
	      {name:'firstName', index:'firstName', width:120}, 
	      {name:'invdate', index:'invdate', width:120}, 
	      {name:'amount', index:'amount', width:120}, 
	      {name:'tax', index:'tax', width:120} ],
	    pager: jQuery('#pager'),
	    rowNum:10,
	    rowList:[10,20,30,50],
	    sortname: 'id',
	    sortorder: "desc",
	    viewrecords: true,
	    imgpath: 'css/basic/images',
	    caption: 'Uzytkownicy',
	    height: 300,
	    loadComplete: function(){ 
		    var ids = jQuery("#list").getDataIDs(); 
		    for(var i=0;i<ids.length;i++){ 
			    var cl = ids[i];
			    var usrId = cl;
			    var usrfirstName = jQuery("#list").getCell(cl, 1);
			    var usrlastName = jQuery("#list").getCell(cl, 2);
			    be = "<img src=\"gfx/stock_new-bcard.png\" border=\"0\" onclick=\"addStudent('"
				    +usrId+"','"+usrfirstName+"','"+usrlastName+"');\"/></a>";
			    jQuery("#list").setRowData(ids[i],{act:be});
			} 
		}
		    
	  }); 
	}); 


	var timeoutHnd; 
	var flAuto = false; 
	function doSearch(ev){ 
		if(!flAuto) return; 
		if(timeoutHnd) 
			clearTimeout(timeoutHnd) ;
		timeoutHnd = setTimeout(gridReload,500);
	} 
	
	function gridReload() {
		var lname = $("#txtLname").val();
		var fname = $("#txtFname").val();
		$("#list").setGridParam( {
			url : "AjaxStudentsList.action?fname=" + fname + "&lname=" + lname,
			page : 1
		}).trigger("reloadGrid");
	}


	function enableAutosubmit(state) {
		flAuto = state;
		$("#submitButton").attr("disabled", state);
	}

	function addStudent(id, firstName, lastName) {
		$("#studentsTab").append(
				"<tr id=\"tr"+id+"\"><td>"
				+firstName+" "+lastName+
				"<img src=\"gfx/cancel.png\" onclick=\"removeStudent('"+id+"'); \"/>"+
				"</td></tr>"
		);

		$("#EditProject_operation").parent().append(
				"<input type=\"hidden\" id=\"hid"+id+"\" value=\""+id+"\" name=\"selectedStudents\"/>"
		);
		$.nyroModalRemove();
	}
</script>
<table width="100%">
	<tr>
		<td><img src="gfx/im-msn.png" /></td>
		<td>
		<h3>Uzytkownicy</h3>
		</td>
	</tr>
	<tr>
		<td colspan="2"
			style="border-bottom-color: black; border-bottom-style: solid; border-bottom-width: 1px" />
	</tr>
	
	<tr>
		<td colspan="2">
			<div>  
				<input type="checkbox" id="autosearch" onclick="enableAutosubmit(this.checked)"> Autowyszukiwanie <br/>  
				Imie: <input type="text" id="txtFname" onkeydown="doSearch(arguments[0]||event)" /> 
			</div> 
			<div>  
				Nazwisko :  
				<input type="text" id="txtLname" onkeydown="doSearch(arguments[0]||event)" /> 
			 	<button onclick="gridReload()" id="submitButton" style="margin-left:30px;">Szukaj</button> 
			</div> 
		<table id="list" class="scroll"></table>
		<div id="pager" class="scroll" style="text-align: center;"></div>
		</td>
	</tr>
</table>