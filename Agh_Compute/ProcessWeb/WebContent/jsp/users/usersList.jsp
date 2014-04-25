<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
jQuery(document).ready(function(){
	
	
	  jQuery("#list").jqGrid({
	    url:'AjaxUsersList.action',
	    datatype: 'json',
	    mtype: 'GET',
	    colNames:['Imie','Nazwisko', 'email'],
	    colModel :[    
	      {name:'firstName', index:'firstName', width:120}, 
	      {name:'lastName', index:'lastName', width:120}, 
	      {name:'email', index:'email', width:120} ],
	    rowNum:20,
		rowList:[10,20,30,50],    
	    pager: jQuery('#pager'),
	    sortname: 'id',
	    sortorder: "desc",
	    viewrecords: true,
	    caption: 'Uzytkownicy',
	    imgpath: 'css/steel/images',
	    height: 335,
		width: 525,
		onSelectRow: function(id){ 
	      loadDetails(id);
	   	},
	   	gridComplete: function(){ 
		   var ids = jQuery("#list").jqGrid('getDataIDs'); 
		   for(var i=0;i<ids.length;i++){ 
		   	jQuery("#list").jqGrid('setRowData',ids[i],{});
		   }
	  	} 
	  	
		    
	  });
	  jQuery("#list").jqGrid('navGrid','#pager',{edit:false,add:false,del:false});

	  function loadDetails(id) {
			$.ajax({
				  url: "AjaxUserDetails.action",
				  data: "id="+id,
				  cache: false,
				  success: function(response){
				  	var user=$.parseJSON(response);
				  	addInfo(user, id);
				  }
			});
	  }

	  function addInfo(user,id) {
		  $("#details").empty();
		  $("#details").append(
				  "<b>Imie : </b>"+user[0]+"<BR/><b>Nazwisko : </b>"+user[1]+"<BR/><b>E-mail : </b>"+user[2]
			);
		  var remUrl = $("#linkRemove").attr("href");
		  var editUrl = $("#linkEdit").attr("href");

		  if(remUrl.indexOf('&id')!==-1) {
			  remUrl=remUrl.substring(0, remUrl.indexOf('&id'));
		  } 
		  $("#linkRemove").attr("href", remUrl+"&id="+id);

		  if(editUrl.indexOf('&id')!==-1) {
			  editUrl=editUrl.substring(0, editUrl.indexOf('&id'));
		  } 
		  $("#linkEdit").attr("href",editUrl+"&id="+id);
  
	  }

	    
}); 
</script>	


<div class="list-panel">
	<div class="title-panel">
	 	<s:text name="users.panel.list"/>
	</div>
	<div class="content-panel-list">
		<div id="gridDiv" style="font-size: small">		
			<table id="list" class="scroll"></table>
		<div id="pager" class="scroll" style="text-align: center;"></div>
		</div>
	</div>
</div>

<div class="details-panel">
	<div class="title-panel">
	 	<s:text name="common.details"/>
	</div>
	<div class="content-panel-list">
		<s:url action="UserEdit" id="newUser">
			<s:param name="operation">new</s:param>
		</s:url> 
		<s:a href="%{newUser}"><s:text name="users.new"/></s:a> | 
		
		<s:url action="UserEdit" id="delUser">
			<s:param name="operation">delete</s:param>
		</s:url>
		<s:a href="%{delUser}" id="linkRemove" onclick="return checkLink(this, 'Nie wybrano uzytkownika','id');">
			<s:text name="common.remove"/>
		</s:a> | 
		
		
		<s:url action="UserEdit" id="editUser">
			<s:param name="operation">edit</s:param>
		</s:url>
		<s:a href="%{editUser}" id="linkEdit" onclick="return checkLink(this, 'Nie wybrano uzytkownika','id');">
			<s:text name="common.edit"/>
		</s:a>
				
		<div id="details">
		</div>
	</div>
</div>

