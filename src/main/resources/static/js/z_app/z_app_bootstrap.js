function initializeBootstrapComponents(){
	$('.dummy-link').on('click',function(e){
		e.preventDefault();
	})
	$("[data-toggle='tooltip']").tooltip();
	$("[data-toggle='popover']").popover();
}

function init(validationRequiredMessage,validationEqualToMessage,currentLocale){
	  jQuery.extend(jQuery.validator.messages, {
		    required: validationRequiredMessage,
		    equalTo:  validationEqualToMessage
	  });
	
	  // Set language bootbox
	  bootbox.setDefaults({
	      locale: currentLocale
	  });
	
	  // Menu activation
	  $("#menu > li").removeClass("active");
	  var currentMenu = $("meta[name=menu]").attr("content");
	  var currentSubMenu = $("meta[name=submenu]").attr("content");
	  var subMenuHidden = $("meta[name=submenu]").attr("visible") == "false";
	  $("[data-menu-option="+currentMenu+"]").addClass("active");
	  if(!subMenuHidden){
	      $("#submenu-"+currentMenu).show();
	      $("#submenu-"+currentMenu+" > li").removeClass("active");
	      $("[data-sub-menu-option="+currentSubMenu+"]").addClass("active");
	  }
	  
	  // Table check and menu activation
	  var selectedRow = function(){
	      var cks = $( "td.select > input:checked" ).length;
	      if ($("#moreOptions").length > 0){
	          if (cks == 0){
	              $("#moreOptions").addClass("hidden");
	              $("#moreOptions").next(".dropdown-menu").find("li").hide();
	          }else{
	              $("#moreOptions").removeClass("hidden");
	              if (cks == 1){
	                  $("#moreOptions").next(".dropdown-menu").find("li").show();
	              }else{
	                  $("#moreOptions").next(".dropdown-menu").find("li[data-batch=true]").show();
	                  $("#moreOptions").next(".dropdown-menu").find("li[data-batch=false]").hide();
	              }
	          }
	      }else if ($("#siteActions").length > 0){
	          if (cks == 0){
	              $("#selectAll").prop("checked", false);
	              $("#siteActions").addClass("inactive");
	              $("#siteActions > li").removeClass("inactive");
	          }else{
	              $("#siteActions").removeClass("inactive");
	          }
	      }               
	  };
	  
	  $("#selectAll").on("click",function(){
	      if ($(this).is(":checked")){
	          $("td.select > input[type=checkbox]").prop("checked", true);
	      }else{
	          $("td.select > input[type=checkbox]").prop("checked", false);
	      }
	      selectedRow();
	  });
	  
	  $("td.select > input[type=checkbox]").on("click", selectedRow);
	  
	  $('.ctrlEnter').keydown(function(e){
	      if (e.ctrlKey && e.keyCode == 13) {
	          $(this).closest('form').submit();
	          e.preventDefault();
	      }
	  });
	  
	  initializeBootstrapComponents();
};