//�߂�{�^���L�����Z������
function cancelHistoryBack() {
	if(window.history && window.history.pushState){
		// dummy�̗������Z�b�g����
		history.pushState(null, null, "");
		$(window).on("popstate", function(event){
			// �߂�{�^�����s���ɍēxdummy�̗������Z�b�g����
			if(!event.originalEvent.state){
				history.pushState(null, null, "");
				return;
			}
		});
	}
}
