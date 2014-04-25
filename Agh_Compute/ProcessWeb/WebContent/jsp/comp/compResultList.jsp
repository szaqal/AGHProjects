<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
jQuery(document).ready(function(){
	jQuery("#list").jqGrid({
	    url:'AjaxComputationResultList.action',
	    datatype: 'json',
	    mtype: 'GET',
	    colNames:['Data','Obliczenie'],
	    colModel :[
		  {name:'date', index:'date', width:120},    
	      {name:'computation', index:'computation', width:120}],
	    rowNum:20,
		rowList:[10,20,30,50],    
	    pager: jQuery('#pager'),
	    sortname: 'id',
	    sortorder: "desc",
	    viewrecords: true,
	    caption: 'Wyniki obliczen',
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


	 function loadDetails(id) {
		 var url = $("#downloadLink").attr("href");

		  if(url.indexOf('resultId')!==-1) {
			  url=url.substring(0, url.indexOf('resultId'));
		  } 

		  $("#downloadLink").attr("href", url+"resultId="+id);
						  
		  }
	  
});
</script>	

<div class="list-panel">
	<div class="title-panel">
	 	<s:text name="computatios.results"/>
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
	 	<s:text name="ComputationResult"/>
	</div>
	<div class="content-panel-list">
		<a href="ComputationResult.action?resultId=" id="downloadLink" onclick="return checkLink(this, 'Nie wybrano pakietu','resultId');">
			<s:text name="common.get"/>
		</a>
		
		<div id="details">
		</div>
	</div>

</div>