<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
jQuery(document).ready(function(){
	
	
	  jQuery("#list").jqGrid({
	    url:'AjaxGroupsList.action',
	    datatype: 'json',
	    mtype: 'GET',
	    colNames:['Nazwa','Rights'],
	    colModel :[    
	      {name:'name', index:'name', width:120},
	      {name:'rights', index:'rights', width:120,  hidden: true} ],
	    rowNum:20,
		rowList:[10,20,30,50],    
	    pager: jQuery('#pager'),
	    sortname: 'id',
	    sortorder: "desc",
	    viewrecords: true,
	    caption: 'Grupy',
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
				  url: "AjaxGroupDetails.action",
				  data: "id="+id,
				  cache: false,
				  success: function(response){
				  	var group=$.parseJSON(response);
				  	addInfo(group, id);
				  }
			});
	  }

	  function addInfo(group,id) {
		  var rights= group[1];
		  var htmlRights="<b>Uprawnienia</b><br/>";
		  if(typeof(rights)!==undefined && rights!==null){
			  rightsArr = rights.split(';');
			  for(i=0;i<rightsArr.length;i++) {
				  j=i+1;
				  htmlRights += j+" "+messageUtil[rightsArr[i]] + "<br/>"
				}
			  var rightsArr = rights[1].split(';');
			  $("#details").empty();
			  $("#details").append(htmlRights);
		  } else {
			  $("#details").empty();
			  $("#details").append("");
	  	  }
	  }

	    
}); 
</script>

<div class="list-panel">
	<div class="title-panel">
	 	<s:text name="groups.panel.list"/>
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
		<s:url action="GroupEdit" id="newGroup">
			<s:param name="operation">new</s:param>
		</s:url>
		<s:a href="%{newGroup}"><s:text name="groups.new"/></s:a>
		<div id="details">
		</div>
	</div>
</div>





