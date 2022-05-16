const pwInput = $(".pwdEyeInput");
const pwdPasswordEye = $(".pwdEyeInputIcon");

function showHideEye(e) {
	if (e) { // Prevent warning
		if ($(e.target).val() == null || $(e.target).val().length === 0) {
			// Hide image target
			$(e.target).next("img").hide();
		} else {
			// Show image target
			$(e.target).next("img").show();
		}
	} else {
		pwInput.each(function (index, item) {
			if (item.value == null || item.value.length === 0) {
				pwInput.next("img").hide();
			} else {
				pwInput.next("img").show();
			}
		});
	}
}

pwdPasswordEye.mousedown(function(e) {
	// Update input type target
	$(e.target).prev("input").prop('type', 'text');
});

pwdPasswordEye.on('mouseup mouseleave', function(e) {
	// Update input type target
	$(e.target).prev("input").prop('type', 'password');
});

pwInput.on('input', function(e) {
	showHideEye(e);
});