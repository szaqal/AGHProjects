<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
jQuery(document).ready(function(){
	jQuery("#list").jqGrid({
	    url:'AjaxComputationsList.action',
	    datatype: 'json',
	    mtype: 'GET',
	    colNames:['Identyfikator','Opis'],
	    colModel :[    
	      {name:'computationId', index:'computationId', width:120}, 
	      {name:'description', index:'description', width:120}],
	    rowNum:20,
		rowList:[10,20,30,50],    
	    pager: jQuery('#pager'),
	    sortname: 'id',
	    sortorder: "desc",
	    viewrecords: true,
	    caption: 'Obliczena',
	    imgpath: 'css/steel/images',
	    height: 335,
	    width:525,
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
			  url: "AjaxProcessDetails.action",
			  data: "computationId="+id,
			  cache: false,
			  success: function(response){
					var pkg=$.parseJSON(response);
					addInfo(pkg, id);
					
			  }
		});
					  
	  }

	 function addInfo(pkg, id) {
		  $("#details").empty();
		  $("#details").append(
				  "<b>Nazwa : </b>"+pkg[0]+"<BR/><b>Opis : </b>"+pkg[1]+
				  "<br><b>Obliczenia</b><br>"				                                   				  
			);

		  for(var i=2;i<pkg.length;i+=2) {
			  $("#details").append("<br><b>Nazwa : </b>"+pkg[i]+"<br/><b> klasa : </b>"+pkg[i+1]+"</li>");
		  }


		  var url = $("#runLink").attr("href");
		  var createUrl = $("#runLink").attr("href");
		  var createUrlFile = $("#runWithFileLink").attr("href");

		  if(url.indexOf('fileId')!==-1) {
			  url=url.substring(0, url.indexOf('fileId'));
		  } 

		  if(createUrl.indexOf('?computationId')!==-1) {
			  createUrl=createUrl.substring(0, createUrl.indexOf('?computationId'));
		  } 

		  if(createUrlFile.indexOf('?computationId')!==-1) {
			  createUrlFile=createUrl.substring(0, createUrl.indexOf('?computationId'));
		  } 

		  $("#runLink").attr("href", createUrl +"?computationId="+id);
		  $("#runWithFileLink").attr("href", createUrlFile +"?computationId="+id);
		  
	  }
	  
});
</script>	


<div class="list-panel">
	<div class="title-panel">
	 	<s:text name="process.list"/>
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
	 	<s:text name="process.detail"/>
	</div>
	<div class="content-panel-list">
		<s:url action="RunComputation" id="runUrl"></s:url>
		<s:url action="RunComputationWithFileAction" id="runWithFileUrl"></s:url>
		
		
		<s:a href="%{runUrl}" id="runLink" onclick="return checkLink(this, 'Nie wybrano obliczenia','computationId');">
			<s:text name="common.run"/>
		</s:a> |
		
		<s:a href="%{runWithFileUrl}" id="runWithFileLink" onclick="return checkLink(this, 'Nie wybrano obliczenia','computationId');">
			<s:text name="common.runwithfile"/>
		</s:a>
		
		
			
		<div id="details">
		</div>
	</div>

</div>