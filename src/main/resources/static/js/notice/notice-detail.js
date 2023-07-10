const prevButton = document.querySelector(".prev")
const nextButton = document.querySelector(".next")

let noticeCode = location.pathname.substring(location.pathname.lastIndexOf("/") + 1)

load("/api/v1/notice/")
function load(uri) {
	$.ajax({
		async: false,
		type: "get",
		url: uri + noticeCode,
		dataType: "json",
		success: (response) => {
			getNotice(response.data)
		},
		error: (error) => {
			console.log(error)
		}
	})
}

function getNotice(notice) {
	const noticeDetailTitle = document.querySelector(".notice-detail-title")
	const noticeDetailDescription = document.querySelectorAll(".notice-detail-description h3")
	const noticeContent = document.querySelector(".notice-content")
	const noticeFile = document.querySelector(".notice-file")
	
	noticeCode = notice.noticeCode
	
	noticeDetailTitle.innerHTML = notice.noticeTitle
	noticeDetailDescription[0].innerHTML = "작성자: " + notice.userId
	noticeDetailDescription[1].innerHTML = "작성일: " + notice.createData
	noticeDetailDescription[2].innerHTML = "조회수: " + notice.noticeCount
	
	noticeContent.innerHTML = "<h3> 첨부파일 </h3>"
	
	let noticeFileArray = new Array()
	notice.downloadFiles.forEach(file => {
		if(file.fileCode != undefined) {
			noticeFileArray.push(`<a href="/api/v1/notice/file/download/${file.fileTempName}">${file.fileOriginName}</a>`)
		}
	})
	noticeFile.innerHTML = noticeFileArray.join("/")
}

prevButton.onclick = () => {
	load("/api/v1/notice/prev/")
}

nextButton.onclick = () => {
	load("/api/v1/notice/next/")
}





















