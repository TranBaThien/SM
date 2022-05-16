const SCA0120_FIELD_NAME_INSPAECTION_RESULT = '審査結果';
const SCA0120_FIELD_NAME_TXA_NOTE = '備考';

const SCA0120_MAX_LENGTH_TXA_NOTE = '300';

/* Error code for Required/必須 for select */
const REQUIRED_FOR_SELECT = 'E0001';
/* Error code for Prohibited characters/ 禁則文字 */
const SPECIALCHARACTERS = 'E0015';

/* Error code for maximum/最大*/
const MAXIMUM = 'E0012';
/* Radio */
var radio1 = [0];
var radio2 = [0];

$(document).ready(function() {

/* Set check Unfocus for type radio */
    $("input[name='rdoInspectionResult']").addClass("check-vertical-radio");
    $(".check-vertical-radio").parent().on( "keypress keydown click focusout", function(e) {
        checkRadioByClassId(this, e, radio2, SCA0120_FIELD_NAME_INSPAECTION_RESULT, false);
    });

/* Set event tabIndex for checkBox */
    $("input[name='chkConfirm']").addClass("checkbox-class");
    $(".checkbox-class").parent().on( "keypress", function(e) {
        $(this).children().spacePress(e);
        var checkBox = document.getElementById("chkConfirm");
        if (checkBox.checked == true){
            $("#btn-register").prop('disabled', false);
        } else {
            $("#btn-register").prop('disabled', true);
        }
    });
			$("input[type='radio']").click(function(e){
				if ($(this).is(':checked')){
					$(this).closest('div').focus();
				}
			});		
			
			//Check disable button when not yet checked chkConfirm
			if (!$('.chkConfirm').checked) {
				$('.btn-register').prop('disabled', true);
			}
			//Check disable/enable submit button when not yet selected confirm
			$('.chkConfirm').change(function(){
				$('.btn-register').prop('disabled', !this.checked);
			});
			
			if (checkError == true) {
				$('#chkConfirm').prop('checked', true);
				$('.btn-register').prop('disabled', false);
			}
			if (checkError == false) {
				//Check button when rdoInspectionResult Approval
				if("2" == rdoInspectionResult) {
					$("#checkDisable").find("input,button,textarea,select").attr("disabled", "disabled");
					$("#checkDisable").addClass("disabledItem");
					$(".table-ex-info").addClass("disabledItem");
					$(".radio-and-label").css("background-color", "#F0F0F0");
					$("#checkDisable").css("background-color", "#F0F0F0");
					$('#radio-0').prop('checked', true);
					$('#1').prop('checked', true);
					$('#radio-0').prop('disabled', true);
					$('#radio-1').prop('disabled', true);
					$('#txaNote').prop('disabled', true);
					$('#text-are').css('disabled', '#F0F0F0');
					$('#chkConfirm').prop('disabled', true);
					$('#chkConfirm').prop('checked', true);
					$('#btnSearch').prop('disabled', true);
					$('#btnSearch').addClass("disabledItem");					
					document.getElementById("text-box").style.color = "#999999";
				} else if ("3" == rdoInspectionResult){
					$("#checkDisable").find("input,button,textarea,select").attr("disabled", "disabled");
					$("#checkDisable").addClass("disabledItem");
					$(".table-ex-info").addClass("disabledItem");
					$(".radio-and-label").css("background-color", "#F0F0F0");
					$("#checkDisable").css("background-color", "#F0F0F0");
					$('#radio-1').prop('checked', true);
					$('#radio-1').prop('disabled', true);
					$('#radio-0').prop('disabled', true);
					$('#txaNote').prop('disabled', true);
					$('#text-are').css('disabled', '#F0F0F0');
					$('#chkConfirm').prop('disabled', true);
					$('#chkConfirm').prop('checked', true);
					$('#btnSearch').prop('disabled', true);
					$('#btnSearch').addClass("disabledItem");
					$('#rdoApartmentSelect').hide();
					document.getElementById("text-box").style.color = "#999999";
				} else {
					$('#radio-0').prop('checked', false);
					$('#radio-1').prop('checked', false);
				}
	 		}
			
			// check when rdoApartmentSelect choose
			if ('off' != rdoApartmentSelect) {
				var res = rdoApartmentSelect.substring(0,1);
				document.getElementById(res).checked = true;
			}
			
			//Event onclick button update
			$('#btn-register').on('click', function() {
			$(".formMansion").submit();
			});
			
			//Jquery validation engine
			$(".formMansion").validationEngine('attach',{
						promptPosition : "bottomLeft",
						maxErrorsPerField : 1,
						showArrowOnRadioAndCheckbox : true,
						focusFirstField : true,
						scroll : false,
						'custom_error_messages' : {
							'#txaNote' : {
								'custom2[prohibitionCharacters]' : {
									'message' : getMessage(SPECIALCHARACTERS, SCA0120_FIELD_NAME_TXA_NOTE)
								},
								'maxSize' : {
									'message' : getMessage(MAXIMUM, [SCA0120_FIELD_NAME_TXA_NOTE, SCA0120_MAX_LENGTH_TXA_NOTE ])
								},
							},
							'#radio-0' : {
								'minCheckbox' : {
									'message' : getMessage(REQUIRED_FOR_SELECT, SCA0120_FIELD_NAME_INSPAECTION_RESULT)
								},
							},
						},
						onValidationComplete : function(form, status) {
							if (status) {
								form[0].submit();
							} 
						}
					});
			
			
		});

(function($) {
	$.fn.validationEngineLanguage = function() {
	};
	$.validationEngineLanguage = {
		newLang : function() {
			$.validationEngineLanguage.allRules = {
				"required" : {
					"regex" : "none",
					"alertText" : ' requied',
				},
				"minCheckbox" : {
					"regex" : "none",
					"alertText" : " check",
				},
				"prohibitionCharacters" : {
					"regex" : patternRegex.isNotSpecialCharacter,
					"alertText" : getMessage(SPECIALCHARACTERS, [ '*' ])
				},
				"maxSize" : {
					"regex" : "none",
					"alertText" : '',
				}
			};
		}
	};
	$.validationEngineLanguage.newLang();
})(jQuery);

function submitMoveFormGJA0110() {
	$('#submitRedirect_3').attr('action', baseUrl + '/GJA0110/show');
	$('#submitRedirect_3 input[name=applicationNo]').val($('input[name=applicationNo]').val());
	$('#submitRedirect_3').submit();
}






