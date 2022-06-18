//戻るボタンキャンセル処理
function cancelHistoryBack() {
	if(window.history && window.history.pushState){
		// dummyの履歴をセットする
		history.pushState(null, null, "");
		$(window).on("popstate", function(event){
			// 戻るボタン実行時に再度dummyの履歴をセットする
			if(!event.originalEvent.state){
				history.pushState(null, null, "");
				return;
			}
		});
	}
}
