<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	function startMarket() {
		var url = "http://www.52solution.com"
		$.ajax({
			type : "POST",
			url : "solution/startMarket",
			data : "url=" + url,
			dataType : "json",
			beforeSend : function() {
				alert("方案超市爬取中！");
				$("#sub1").attr({
					disabled : "disabled"
				});
				$("#sub2").attr({
					disabled : "disabled"
				});
			},
			success : function(json) {
				if (json != null) {
					alert("方案超市爬取完成！");
				}
			},
			complete : function() {
				$("#sub1").removeAttr("disabled");
				$("#sub2").removeAttr("disabled");
			},

		});
	}
	function startMsg() {
		var url = "http://www.52solution.com"
		$.ajax({
			type : "POST",
			url : "solution/startFangAnXun",
			data : "url=" + url,
			dataType : "json",
			beforeSend : function() {
				alert("方案讯爬取中！");
				$("#sub1").attr({
					disabled : "disabled"
				});
				$("#sub2").attr({
					disabled : "disabled"
				});
			},
			success : function(json) {
				if (json != null) {
					alert("方案讯爬取完成！");

				}
			},
			complete : function() {
				$("#sub1").removeAttr("disabled");
				$("#sub2").removeAttr("disabled");
			},

		});
	}
</script>
</head>
<body>
	<form action="">

		<input id="sub1" type="submit" value="爬取方案超市" onClick="startMarket()" />
		<input id="sub2" type="submit" value="爬取方案讯" onClick="startMsg()" />

	</form>
</body>
</html>