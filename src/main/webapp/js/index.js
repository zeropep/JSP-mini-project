const a = document.getElementById("list");

if (a) {
    a.click();
}

async function tryLogin(data) {
    try {
        const url = "/userCtrl/login";
        const config = {
            method: "POST",
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
            },
            body: JSON.stringify(data)
        };
        const resp = await fetch(url, config);
        return await resp.text();
    } catch (error) {
        console.log(error);
    }
}

document.querySelector(".submit_btn").addEventListener("click", (e) => {
    e.preventDefault();
    let data = {
        email: document.getElementById("email").value,
        pwd: document.getElementById("pwd").value,
    }
    tryLogin(data).then(result => {
        if (result.length > 0) {
            alert("로그인에 실패하였습니다.")
        }
    });
});