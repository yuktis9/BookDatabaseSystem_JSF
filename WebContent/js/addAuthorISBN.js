		$ = jQuery;
    	$(document).ready(function(){

    		$("#myForm\\:searchAuthorType").change(function() {

    			  var typeSelected = $("#myForm\\:searchAuthorType_input").val();
    			  
    			  $("#myForm\\:authorPojo2_input").val("");
    			  $("#myForm\\:authorPojo1_input").val("");
    			  		
    			  if(typeSelected == "FirstName"){
    			  		$("#myForm\\:authorPojo1").show();
    			  		$("#myForm\\:authorPojo2").hide();
    			  		$("#myForm\\:authorPojo1_input").show();
    			  		$("#myForm\\:authorPojo2_input").hide();	
    			  }
    			  else if(typeSelected == "LastName"){
    			  		$("#myForm\\:authorPojo2").show();
    			  		$("#myForm\\:authorPojo1").hide();
    			  		$("#myForm\\:authorPojo2_input").show();
    			  		$("#myForm\\:authorPojo1_input").hide();
    			  		
    			  }else{
    			  		$("#myForm\\:authorPojo2").hide();
    			  		$("#myForm\\:authorPojo1").hide();
    			  		$("#myForm\\:authorPojo2_input").hide();
    			  		$("#myForm\\:authorPojo1_input").hide();		
    			  }
    			  
    			});
			
			$("#myForm\\:searchTitleType").change(function() {

    			  var typeSelected = $("#myForm\\:searchTitleType_input").val();
    			  $("#myForm\\:titlePojo2_input").val("");
    			  $("#myForm\\:titlePojo1_input").val("");
    			  if(typeSelected == "Title"){
    			  		$("#myForm\\:titlePojo1").show();
    			  		$("#myForm\\:titlePojo2").hide();
    			  		$("#myForm\\:titlePojo1_input").show();
    			  		$("#myForm\\:titlePojo2_input").hide();
    			  		
    			  }
    			  else if(typeSelected == "ISBN"){
    			  		$("#myForm\\:titlePojo2").show();
    			  		$("#myForm\\:titlePojo1").hide();
    			  		$("#myForm\\:titlePojo2_input").show();
    			  		$("#myForm\\:titlePojo1_input").hide();
    			  }else{
    			  		$("#myForm\\:titlePojo2").hide();
    			  		$("#myForm\\:titlePojo1").hide();
    			  		$("#myForm\\:titlePojo2_input").hide();
    			  		$("#myForm\\:titlePojo1_input").hide();	
    			  }
    			  
    			});
			
			
			$("#myForm\\:titlePojo2_input").keypress(function (e) {
			     //if the letter is not digit then display error and don't type anything
			     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
			        //display error message
			        $("#errorMSG").show().fadeOut("slow");
			               return false;
			    }
			   });
			
			
    	});
