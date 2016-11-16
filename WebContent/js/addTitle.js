$(document).ready(function(){
		
		$( "#ISBN" ).focusout(function() {
			
			var ISBN = $("#ISBN").val();
			if(ISBN != ""){
				$.ajax({
			  		  method: "GET",
			  		  url: "/bookData/rest/checkForISBN/"+ISBN,
			  		  
			  		})
			  		  .done(function( isISBNExist ) {
			  		    if(isISBNExist == "true"){
				  		    $("#ISBNError").show();
				  		}else{
				  			$("#ISBNError").hide();
					  	}
			  		  });
			}		  
		});

		$("#editionNo,#ISBN,#copyRight").keypress(function (e) {
		     //if the letter is not digit then display error and don't type anything
		     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		        //display error message
		        $("#isISBNExist").show().fadeOut("slow");
		               return false;
		    }
		   });
		   
		
	});
