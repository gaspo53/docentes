
function scrollTo(selector){
    $("html, body").delay(50).animate({
            scrollTop: $(selector).first().offset().top 
    }, 50);
}

function isValidUrl(url){
  var regexp = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
  return regexp.test(url);    
}

function setValidatorDefaults(){
	  $.validator.setDefaults({
		  	ignore: '.no-validate',
		    errorPlacement: $.noop,
		    errorClass: 'has-error',
		    validClass: 'has-success',
		    highlight: function (element, errorClass, validClass) {
		        formGroup = $(element).closest('.form-group');
                formGroup.removeClass(validClass);
		        formGroup.addClass(errorClass).addClass('has-feedback');

                if ( ($(element).is('input')) && (!$(element).parent().hasClass('input-group'))){
                    formGroup.children('span').remove();
                    var span = $('<span />').addClass('form-control-feedback').addClass('glyphicon glyphicon-remove');
                    var spanSrOnly = $('<span />').addClass('sr-only');
                    formGroup.append(span);
                    formGroup.append(spanSrOnly);
                }
		    },
		    unhighlight: function (element, errorClass, validClass) {
		        formGroup = $(element).closest('.form-group');
		        formGroup.removeClass(errorClass);
		        formGroup.addClass(validClass).addClass('has-feedback');

                if ( ($(element).is('input')) && (!$(element).parent().hasClass('input-group')) ){
                    formGroup.children('span').remove();
                    var span = $('<span />').addClass('form-control-feedback').addClass('glyphicon glyphicon-ok');
                    var spanSrOnly = $('<span />').addClass('sr-only');
                    formGroup.append(span);
                    formGroup.append(spanSrOnly);
                }
		    }
		});
}

function loadingDiv(selector,state){
	state = state || "off";
	
	if (state === "off"){
		$(selector).siblings().remove(".overlay");
		$(selector).siblings().remove(".loading-img");
	}else{
		var overlay = $("<div />").addClass("overlay");
		var loading_img = $("<div />").addClass("loading-img");
		var text = $("<p />").text("LOADING DIV - NEED TRANSLATION");
		$(selector).append(text);
		$(selector).parent().append(overlay);
		$(selector).parent().append(loading_img);
	}
}

function initializeCodeMirror(htmlCodeId){
    var cm = CodeMirror.fromTextArea(document.getElementById(htmlCodeId), {
      mode: "text/html",
  	  lineNumbers: true,
  	  lineWrapping: true,
      highlightActiveLine: true
  	});
    
    return cm;
}

function ruleDetailTrigger(triggerSelector,containerSelector,loaderSelector,codeMirrorInstance){
    $(triggerSelector).on('click',function(e){
  	  //Prevent the click on the link to be requested
  	  e.preventDefault();
      //Turn on the loader indicator
      $(containerSelector).removeClass('hide');
      $(containerSelector).html($(loaderSelector).html());
      scrollTo(containerSelector);
  	  
      var currentHref = $(this).attr('href');
      var ajax = $.ajax(currentHref);
      ajax.success(function(response){
          if (handleSessionTimeout(ajax)){
              $(containerSelector).html(response);
              ruleDetailTrigger('.pagination a',containerSelector,loaderSelector);
              ruleDetailResultInitialize(codeMirrorInstance);
              ruleSearch(containerSelector,loaderSelector,codeMirrorInstance);
          }
      });
      ajax.fail(function(response){
          handleError(response,containerSelector);
      });
    });
}

function ruleSearch(containerSelector,loaderSelector,codeMirrorInstance){
    var form = $('#ruleFilterForm');
    var currentHref = form.attr('action');
    form.on('submit',function(e){
        $(containerSelector).removeClass('hide');
        $(containerSelector).html($(loaderSelector).html());
        var ajax = $.ajax({
            type: form.attr('method'),
            url: currentHref,
            data: form.serialize()
        });
        ajax.success(function(response){
            if (handleSessionTimeout(ajax)){
                $(containerSelector).html(response);
                ruleDetailTrigger('.pagination a',containerSelector,loaderSelector);
                ruleDetailResultInitialize(codeMirrorInstance);
                ruleSearch(containerSelector,loaderSelector);
            }
        });
        return false; // avoid to execute the actual submit of the form.        
    });
}

function ruleDetailResultInitialize(codeMirrorInstance){
    initializeBootstrapComponents();
    //LineNumber show in CodeMirror event
    $('[data-action-type="lineNumberReference"]').on('click',function(e){
      e.preventDefault();
      var currentLineNumber = $(this).attr("data-lineNumber");
      showLineNumberInCodeMirror(currentLineNumber,codeMirrorInstance);
    });
    //HtmlTag reference to show in model
    $('[data-action-type="htmlTagReference"]').on('click',function(e){
      e.preventDefault();
      var tagName = $(this).attr("data-htmlTag");
      showHtmlTagReference(tagName);
    });
    //Validation suggestion to show in model
    $('[data-action-type="validationInformation"]').on('click',function(e){
      e.preventDefault();
      var validationId = $(this).attr("data-validation");
      showValidationDetail(validationId,'#tab_suggestion');
    });
    //Validation suggestion to show in model
    $('[data-action-type="ruleInformation"]').on('click',function(e){
      e.preventDefault();
      var validationId = $(this).attr("data-validation");
      showValidationDetail(validationId,'#tab_rule_information');
    });
}

function loadContent(where,href){
    var ajax = $.ajax(href);
    ajax.done(function(response, status, xhr){
		  if ( status == "error" ) {
			  //FIXME develop a custom error template
			  loadContent(where,href);
		  }

    });
}

function jumpToLine(lineNumber,codeMirrorInstance) { 
    var t = codeMirrorInstance.charCoords({line: lineNumber, ch: 0}, "local").top; 
    var middleHeight = codeMirrorInstance.getScrollerElement().offsetHeight / 2; 
    codeMirrorInstance.scrollTo(null, t - middleHeight - 5); 
    scrollTo('.CodeMirror');
} 

function showLineNumberInCodeMirror(lineNumber,codeMirrorInstance){
    lineNumber = lineNumber -1;
    var line = codeMirrorInstance.getLineHandle(lineNumber);
    var markedLine = codeMirrorInstance.addLineClass(line, 'background', 'line-highlight');
    setTimeout(function() {
      codeMirrorInstance.removeLineClass(markedLine, 'background', 'line-highlight');
    },5000);
    jumpToLine(lineNumber,codeMirrorInstance);
}

function showHtmlTagReference(tagName){
    var loaderAnimation = $('#loader-animation').html();
    $('#htmlTagReferenceModal .modal-body').html(loaderAnimation);
    $('#htmlTagReferenceModal').modal('show');
    var post = $.post(contextPath+'validation/misc/getHtmlTagReference/',{tag : tagName});
    post.done(function(html) {
        if (handleSessionTimeout(post)){
            $('#htmlTagReferenceModal .modal-body').html(html);
        }
    });
}


function showValidationDetail(validationId,preferredTab){
    var loaderAnimation = $('#loader-animation').html();
    $('#elementValidationModal .modal-body').html(loaderAnimation);
    $('#elementValidationModal').modal('show');
    var post = $.post(contextPath+'validation/misc/elementValidationDetail',{validation : validationId});
    post.done(function(html) {
        if (handleSessionTimeout(post)){
            $('#elementValidationModal .modal-body').html(html);
            initializeBootstrapComponents();
            highlightCode();

            if ($(preferredTab).size() > 0){
              //Add clase active to the tab panel
              $('#elementValidationModal .tab-content .tab-pane ').removeClass('active');
              $('#elementValidationModal .nav-tabs > li').removeClass('active');
              $('#elementValidationModal .nav-tabs > li > a[href="'+preferredTab+'"]').parent().addClass('active');
              
              $(preferredTab).addClass('active');
              //Initialize the falsePositive event
            }
            $('[data-action-type="falsePositive"]').on('click',function(e){
              var elementValidationID = $(this).attr("data-elementValidationID");
              markValidationAsFalsePositive(elementValidationID,this);
            });
        }
    });
}

function markValidationAsFalsePositive(validationId){
    var currentModal = $('#elementValidationModal');
    currentModal.modal('hide');
    bootbox.confirm(FALSE_POSITIVE_CONFIRM, function(result) {
        if (result){
            var postURL = contextPath+'validation/falsePositive';
            var data = {elementValidationID : validationId};
            var post = $.post( postURL, data);
            
            post.done(function(result) {
                if (handleSessionTimeout(post)){
                    updateRulesCount(validationId);
                    var tr = $('#validation-element-row-'+validationId);
                    tr.animate( {backgroundColor:'yellow'}, 1000)
                            .fadeOut(1000,function() {
                                    tr.remove();
                            });
                    showValidationDetail(validationId);
                    return result;
                }
            });
        }else{
          currentModal.modal('show');
        }
    }); 
}

function updateRulesCount(elementValidationID){
    // validation-rules-count
    var detailUrl = contextPath+'validation/misc/rulesCount';
    var data = {elementValidationID : elementValidationID};
    var post = $.post(detailUrl,data);
    post.done(function(result){
        if (handleSessionTimeout(post)){
            $('#htmlValidationRulesCount').html(result);
            ruleDetailTrigger('.rule-detail-link','#rulesContainer','#ruleLoaderDiv');
        }
    });
}


function startValidationOverlay(){
	$('#validationModal').modal({
		keyboard: false,
		backdrop: 'static'
	});
}

function closeValidationOverlay(){
	$('#validationModal').modal('hide');
}

function bindOnEnterEvent(selector){
	$(selector).keyup(function(e){
	    if(e.keyCode == 13){
	        $(this).trigger("enterKey");
	    }
	});	
}

function highlightCode(){
   $('.code').each(function(index, e) {
        $(e).addClass('cm-s-default'); // apply a theme class
        CodeMirror.runMode($(e).text(), "javascript", $(e)[0]);
    });
}


/** Auth AJAX handling **/

function handleSessionTimeout(response){
    var currentHref = window.location.href;
    var status = response.status;
    var authHeader = response.getResponseHeader("REQUIRES_AUTH");

    if (authHeader == "true"){
        window.location.href = currentHref;
        return false;
    }
    return true;
}


function handleError(response,container){
    $(container).html(response.responseText);
}