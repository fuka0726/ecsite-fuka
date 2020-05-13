$(function(){

	var name = $('#searchForm [name="name"]').val();
//	var currentPage = parseInt($('#searchForm [name=page]').val());
	
	
	$('.page-button').on("click", function(){
		var page = $(this).val();
		var sort = $('#sort-select option:selected').val();
		var name = $('#searchForm [name="name"]').val();
		$('#searchForm [name="page"]').val(page);
		$('#searchForm [name="name"]').val(name);
		$('#searchForm [name="sort"]').val(sort);
		$('#searchForm').submit();
	});
	
	
	
	
	$('#sort-select').on('change', function(){
		var name = $('#searchForm [name="name"]').val();
		var sort = $('#sort-select option:selected').val();
		$('#searchForm [name="page"]').val(1);
		$('#searchForm [name="name"]').val(name);
		$('#searchForm [name="sort"]').val(sort);
		$('#searchForm').submit();
		
		
	});
	
	
	 $("#reset").on("click", function () {
	        $("#searchName").val("");
	  
	    });
	
	
	
	
});
