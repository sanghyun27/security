let nowPage = 1
load(nowPage)

function load(nowPage) {
	const searchFlag = document.querySelector(".search-select").value
	const searchValue = document.querySelector(".search-input").value
	
	$.ajax({
		async: false,
		type: "get",
		url: "/api/v1/notice/list/" + nowPage,
		data: {
			"searchFlag": searchFlag,
			"searchValue": searchValue
		},
		dataType: "json",
		success: (response) => {
			getList(response.data)
		},
		error: (error) => {
			console.log(error)
		}
	})
}

function getList(list) {
	const tbody = document.querySelector("tbody")
	tbody.innerHTML = ""
	list.forEach(notice => {
		tbody.innerHTML += `
			<tr class="notice-row">
	            <td>${notice.noticeCode}</td>
	            <td>${notice.noticeTitle}</td>
	            <td>${notice.userId}</td>
	            <td>${notice.createDate}</td>
	            <td>${notice.noticeCount}</td>
	        </tr>
		`
	})
}













function getWriteButton() {
	const listFooter = document.querySelector(".list-footer")
	
	if(getUser() != null) {
		if(getUser().userRoles.includes("ROLE_ADMIN")) {
			listFooter.innerHTML += `
				<button type="button" class="notice-add-button">글 쓰기</button>
			`
			
			const noticeAddButton = document.querySelector(".notice-add-button")
			noticeAddButton.onclick = () => {
				location.href = "/notice/addition"
			}
		}
	}
	
}

getWriteButton()