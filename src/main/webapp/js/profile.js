const tapContainer = document.querySelector('.about');
const flex_Container = document.querySelectorAll('.contents_container');
const taps = document.querySelectorAll('.about > span');


function openCity(e) {
    let elem = e.target;
    
    for (var i = 0; i < flex_Container.length; i++) {
        flex_Container[i].classList.remove('active');
        taps[i].classList.remove('on');
    }
    
    if(elem.matches('[class="nick_name"]')){
        
        flex_Container[0].classList.add('active');
        taps[0].classList.add('on');
        
    }else if(elem.matches('[class="book_mark"]')){
        
        flex_Container[1].classList.add('active');
        taps[1].classList.add('on');
        
    }
}
tapContainer.addEventListener('click', openCity);

let postJson;

async function getPostList() {
    try {
        const resp = await fetch("/userCtrl/postlist?email=" + document.getElementById("email").value);
        return await resp.json();
    } catch (error) {
        console.log(error);
    }
}

getPostList().then(result => {
    postJson = result;
    document.querySelector(".middle li:first-child").innerHTML = `게시물 ${result.length}`;
    let postList =  document.getElementById("postList");
    postList.innerHTML = '';
    let li = ``;
    if (result.length > 0) {
        for (let pvo of result) {
            li += `<div class="pic">`;
            li += `<a href="/postCtrl/detail?pid=${pvo.postId }" id="detailToggle" data-pid="${pvo.postId } class="post">`;
            if (pvo.files != null && pvo.files != '') {
                li += `<img src="../_fileUpload/post/${pvo.files }"></a></div>`;
            } else {
                li += `<img src="../imgs/img_section/img02.jpg"></a></div>`;
            }
        }
        
    } else {
        li = `<div>게시물이 없습니다.</div>`;
    }
    postList.innerHTML = li;
});


document.addEventListener("click", (e) => {
    if (e.target.parentElement.classList.contains("post")) {
        e.preventDefault();
        // console.log(postJson);
        let targetPostId = e.target.parentElement.getAttribute("data-set");
        // console.log("Target post_id : ", targetPostId);
        let targetPvo = {};
        for (let i = 0; i < postJson.length; i++) {
            if (postJson[i].postId == targetPostId) {
                // 게시물 순서가 최신순이면 i를 postJson.length - i로 변경
                targetPvo = postJson[i];
                break;
            }
        }
        // console.log(targetPvo);
        let body = document.querySelector("#postModal .modal-body");
        body.innerHTML = ``;
        let detail = ``;
        if (targetPvo.files != null && targetPvo.files != '') {
            detail += `<img src="../_fileUpload/post/${targetPvo.files }"></img><br>`;
        } else {
            detail += `<img src="../imgs/img_section/img02.jpg"></img><br>`;

        }
        detail += `<span>Content : ${targetPvo.content}</span><br>`;
        detail += `<span>like : ${targetPvo.likeCnt}</span><br>`;
        detail += `<span>modified At : ${targetPvo.modAt}</span><br>`;
        detail += `<span>read : ${targetPvo.readCnt}</span>`;
        body.innerHTML += detail;
    }
});

async function unfollowFormModal(from, to) {
    try {
        const resp = await fetch(`/userCtrl/unfollowFromModal?from=${from}&to=${to}`);
        return await resp.text();
    } catch (error) {
        console.log(error);
    }
}

const followerDel = document.getElementById("followerDel");
if (followerDel) {
    followerDel.addEventListener("click", () => {
        let targetEmail = followerDel.getAttribute("data-set");
        let email = document.getElementById("email").value;
        
        unfollowFormModal(targetEmail, email).then(result => {
            if (result.length > 0) {
                console.log(result);
                followerDel.setAttribute("disabled", true);
                followerDel.innerText = "삭제됨";
            }
        })
    })
}

const followingDel = document.getElementById("followingDel");
if (followingDel) {
    followingDel.addEventListener("click", () => {
        let targetEmail = followingDel.getAttribute("data-set");
        let email = document.getElementById("email").value;
        
        unfollowFormModal(email, targetEmail).then(result => {
            if (result.length > 0) {
                console.log(result);
                followingDel.setAttribute("disabled", true);
                followingDel.innerText = "삭제됨";
            }
        })
    })
}