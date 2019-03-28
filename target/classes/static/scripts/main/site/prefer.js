$(function () {
    var oExports = {
        initialize: fInitialize,
        encode: fEncode
    };
    oExports.initialize();

    function fInitialize() {
//        var that = this;
    }

    like = function (newsId) {
    	var that = this;
    	var like_btn_id = "like"+newsId;
    	var like_btn = $('#'+like_btn_id);
    	
    	var dis_btn_id = "dislike"+newsId;
    	var dislike_btn = $('#'+dis_btn_id);
//    	var bSubmit = false;
//    	if (bSubmit) {
//            return;
//        }
//    	// 上一个提交没结束之前，不再提交新的。
//        bSubmit = true;
    	$.ajax({
            url: '/like/',
            type: 'post',
            dataType: 'json',
            async: false,
            data: {newsId: newsId}
        }).done(function (oResult) {
            if (oResult.code !== 0) {
                return alert(oResult.msg || '提交失败，请重试');
            }
            var sHtml = ['<button id=','"','like',newsId,'" ','class="click-like up pressed" data-id=','"', newsId,'"', 'title="赞同"><i class="vote-arrow"></i><span class="count">', oResult.msg, '</span></button>'].join('');
            var cHtml = ['<button id=','"','dislike',newsId,'" ','class="click-dislike down" onclick="dislike(',newsId,');" data-id=','"', newsId,'"', 'title="反对"><i class="vote-arrow"></i>', '</button>'].join('');
            like_btn.replaceWith(sHtml);
            dislike_btn.replaceWith(cHtml);
        }).fail(function (oResult) {
            alert(oResult.msg || '提交失败，请重试');
        });
    }
    
    dislike = function (newsId) {
    	var that = this;
    	var like_btn_id = "like"+newsId;
    	var like_btn = $('#'+like_btn_id);
    	
    	var dis_btn_id = "dislike"+newsId;
    	var dislike_btn = $('#'+dis_btn_id);
//    	var bSubmit = false;
//    	if (bSubmit) {
//            return;
//        }
    	// 上一个提交没结束之前，不再提交新的
    	$.ajax({
            url: '/dislike/',
            type: 'post',
            dataType: 'json',
            async: false,
            data: {newsId: newsId}
        }).done(function (oResult) {
            if (oResult.code !== 0) {
                return alert(oResult.msg || '提交失败，请重试');
            }
            var sHtml = ['<button id=','"','dislike',newsId,'" ','class="click-dislike down pressed" data-id=','"', newsId,'" ', 'title="反对"><i class="vote-arrow"></i>', '</button>'].join('');
            var cHtml = ['<button id=','"','like',newsId,'" ','class="click-like up" onclick="like(',newsId,');" data-id=','" ', newsId,'"', 'title="赞同"><i class="vote-arrow"></i><span class="count">', oResult.msg, '</span></button>'].join('');
            like_btn.replaceWith(cHtml);
            dislike_btn.replaceWith(sHtml);
        }).fail(function (oResult) {
            alert(oResult.msg || '提交失败，请重试');
        });
    }

    function fEncode(sStr, bDecode) {
        var aReplace =["&#39;", "'", "&quot;", '"', "&nbsp;", " ", "&gt;", ">", "&lt;", "<", "&amp;", "&", "&yen;", "¥"];
        !bDecode && aReplace.reverse();
        for (var i = 0, l = aReplace.length; i < l; i += 2) {
             sStr = sStr.replace(new RegExp(aReplace[i],'g'), aReplace[i+1]);
        }
        return sStr;
    };

});