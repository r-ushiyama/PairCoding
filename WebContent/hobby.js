// AjaxでJSONを取得する
function executeAjax () {
	'use strict';

	// ?以降のパラメータを取得
	// 今回で言うとhttp://localhost:8080/wt1/hobby.html?q=0001でいう0001が取得される
	var parameter  = location.search.substring( 1, location.search.length );
	parameter = decodeURIComponent( parameter );
	parameter = parameter.split('=')[1];

	// --------------- TODO 編集ここから---------------
	var requestQuery = {
		syainId : parameter
	};

	console.dir(requestQuery);

	$.ajax({
		type : 'GET',
		url : '/wt2/api/hobby',
		dataType : 'json',
		data :requestQuery,
		success : function (json) {
			console.log(json);
			// DOM操作
			if(json[0]!=null){
				var syainName = json[0].syainName;
				var syainElement = syainName+'さんの趣味一覧'
				$('#syainName').html(syainElement);
			}


			for(var i=0;i<json.length;i++){
				var hobby = json[i];
				var tableElement='<tr>'
								+'<td>'+hobby.no+'</td>'
								+'<td>'+hobby.hobbyCategory+'</td>'
								+'<td>'+hobby.hobby+'</td>'
								+'</tr>'
				$('#syainData').append(tableElement);
			}

		}
	});

	// ---------------ここまで---------------

}

$(document).ready(function () {
	'use strict';

	// 初期表示用
	executeAjax();

	// 更新ボタンにイベント設定
	$('#searchBtn').bind('click',executeAjax);

});