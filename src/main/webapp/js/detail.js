const closeBtn = document.getElementById("close_btn");
const submitBtn = document.getElementById("submitBtn");

const handleCloseBtnClick = () => {
  history.back();
};

const handleSubmitBtnClick = async () => {
  const cvo = {
    pid: document.querySelector("[name='pid']").value,
    writer: document.querySelector("[name='writer']").value,
    content: document.querySelector("[name='content']").value,
  };
  const config = {
    method: "POST",
    content: "application/json; charset=utf-8",
    body: JSON.stringify(cvo),
  };
  const res = await fetch("/cmtCtrl/post", config);
  const response = await res.text();
  console.log(`comment added ${response > 0 ? "successfully" : "failed"}`);
  return response;
};

const convertTime = function (time) {
  const today = new Date();
  const date = new Date(time);
  const betweenMinutes = Math.floor((today.getTime() - date.getTime()) / 1000 / 60);
  if (betweenMinutes < 1) return "방금전";
  if (betweenMinutes < 60) return `${betweenMinutes}분 전`;
  const betweenHours = Math.floor(betweenMinutes / 60);
  if (betweenHours < 24) return `${betweenHours}시간 전`;
  const betweenDays = Math.floor(betweenMinutes / 60 / 24);
  if (betweenDays < 365) return `${betweenDays}일 전`;

  return `${Math.floor(betweenDays / 365)}년 전`;
};

const getHashTags = (str) => {
  let tags = [];
  let str_ = str;
  while (str_.contains("#")) {
    str_.substring(str_.indexOf("#"), str_.indexOf(" ", str_.indexOf("#")));
    console.log(str_.substring(str_.indexOf("#"), str_.indexOf(" ", str_.indexOf("#"))));
    tags.append(str_.match(/#[a-z]+/, ""));
    str_.replace(/#[a-z]+/, "");
  }
  return str_;
};

const addToList = () => {
  document.querySelector("div.scroll_div").innerHTML =
    ` <div class="cmt">
                <div class="cmt_img">
                  <img class="avatar" src="https://picsum.photos/25/25/" alt="" />
                </div>
                <div class="cmt_text">
                  <div class="cmt_writer_area">
                    <div>
                      <span class="cmt_writer_nickname">${
                        document.querySelector("[name='writer']").value
                      }</span>
                      <span class="cmt_writer_email">${document.querySelector("[name='writer']").value}</span>
                    </div>
                    <span class="cmt_write_time">방금전</span>
                  </div>
                  <div class="cmt_content">${document.querySelector("[name='content']").value}</div>
                </div>
              </div>` + document.querySelector("div.scroll_div").innerHTML;
};

closeBtn.addEventListener("click", handleCloseBtnClick);
submitBtn.addEventListener("click", (e) => {
  e.preventDefault();
  handleSubmitBtnClick();
  addToList();
});

document.addEventListener("DOMContentLoaded", () => {
  document.querySelectorAll("span.timmer").forEach((each) => {
    each.innerText = convertTime(each.innerText);
  });
});
